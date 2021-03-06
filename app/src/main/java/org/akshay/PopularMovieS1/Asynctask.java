package org.akshay.PopularMovieS1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Asynctask extends AsyncTask<String, Void, JSONObject> {

    public interface OnTaskStatusListener {
        void onTaskStart();

        void onResponseReceived(JSONObject response);

        void onTaskEnd();
    }

    public static final String TAG = Asynctask.class.getSimpleName();

    private OnTaskStatusListener mTaskStatusListener;

    public Asynctask(OnTaskStatusListener listener) {
        mTaskStatusListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mTaskStatusListener != null) {
            mTaskStatusListener.onTaskStart();
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        JSONObject responseObject = null;

        String response = httpReq.doNetworkRequest(params[0]);
        if (response != null) {
            Log.d(TAG, response);
            try {
                responseObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                responseObject = null;
            }
        } else {
            Log.d(TAG, "Error Occured while requesting data.");
        }
        return responseObject;
    }

    @Override
    protected void onPostExecute(JSONObject response) {
        super.onPostExecute(response);

        if (mTaskStatusListener != null) {
            mTaskStatusListener.onResponseReceived(response);
            mTaskStatusListener.onTaskEnd();
        }
    }

    /**
     * Created by CodeMyMobile on 05-02-2016.
     */
    public static class httpReq {

        public static final String TAG = httpReq.class.getSimpleName();

        public static String doNetworkRequest(String urlString) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL(urlString);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return forecastJsonStr;
        }
    }
}