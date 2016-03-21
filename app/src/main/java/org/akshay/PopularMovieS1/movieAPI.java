package org.akshay.PopularMovieS1;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class movieAPI {

    public static final String API_KEY = "YOUR_API_KEY";
    public static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String NOT_AVAILABLE = "Not Available";

    public static final class JSON {
        public static final String ID = "id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String TITLE = "title";
        public static final String POSTER_IMAGE = "poster_path";
        public static final String OVERVIEW = "overview";
        public static final String RATING = "vote_average";
        public static final String POPULARITY = "popularity";
        public static final String RELEASE_DATE = "release_date";
        public static final String RESULTS = "results";
        public static final String VOTE_COUNT = "vote_count";
        public static final String SORT_BY = "sort_by";

        public static final String POPULAR_MOVIE_URL =
                Uri.parse(movieAPI.BASE_URL)
                .buildUpon()
                .appendQueryParameter(SORT_BY, POPULARITY + ".desc")
                .appendQueryParameter("api_key", movieAPI.API_KEY)
                .build().toString();

        public static final String HIGH_RATED_MOVIE_URL = Uri.parse(movieAPI.BASE_URL)
                .buildUpon()
                .appendQueryParameter(SORT_BY, RATING + ".desc")
                .appendQueryParameter(VOTE_COUNT + ".gte", "50")
                .appendQueryParameter("api_key", movieAPI.API_KEY)
                .build().toString();
    }

    public static class AdapterClass extends ArrayAdapter<setMovie> {
    
        private Context mContext;
        private List<setMovie> mList;
    
        public AdapterClass(Context context, List<setMovie> list) {
            super(context, 0, list);
            this.mContext = context;
            this.mList = list;
        }
    
        @Override
        public int getCount() {
            return mList.size();
        }
    
        @Override
        public setMovie getItem(int position) {
            return mList.get(position);
        }
    
        @Override
        public long getItemId(int position) {
            return position;
        }
    
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_movie_grid, parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
    
            setMovie movie = getItem(position);
    
            if (movie.getTitle() != null && !movie.getTitle().isEmpty()) {
                holder.title.setText(movie.getTitle());
            }else{
                holder.title.setText(NOT_AVAILABLE);
    
            }
    
                Glide.with(mContext).load(IMAGE_BASE_URL + getItem(position).getImage())
                        .error(R.drawable.no_thumb)
                        .placeholder(R.drawable.no_thumb)
                        .into(holder.imageView);
    
            return convertView;
        }
    
        private class ViewHolder {
            ImageView imageView;
            TextView title;
            public ViewHolder(View rootView) {
                imageView = (ImageView) rootView.findViewById(R.id.thumb);
                title = (TextView) rootView.findViewById(R.id.title);
            }
        }
    }

    /**
     * Created by CodeMyMobile on 06-02-2016
     */

    public static class setMovie implements Parcelable {
        private int id;
        private String originalTitle;
        private String title;
        private String image;
        private String overview;
        private double rating;
        private String releaseDate;

        public String getImage() {
            return image;
        }

        public setMovie() {
        }

        public setMovie(JSONObject movie) throws JSONException {
            this.id = movie.getInt(JSON.ID);
            this.originalTitle = movie.getString(JSON.ORIGINAL_TITLE);
            this.title = movie.getString(JSON.TITLE);
            this.image = movie.getString(JSON.POSTER_IMAGE);
            this.overview = movie.getString(JSON.OVERVIEW);
            this.rating = movie.getDouble(JSON.RATING);
            this.releaseDate = movie.getString(JSON.RELEASE_DATE);
        }

        private setMovie(Parcel parcel) {
            id = parcel.readInt();
            originalTitle = parcel.readString();
            title = parcel.readString();
            overview = parcel.readString();
            releaseDate = parcel.readString();
            rating = parcel.readDouble();
            image = parcel.readString();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String title) {
            this.originalTitle = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(originalTitle);
            dest.writeString(title);
            dest.writeString(overview);
            dest.writeString(releaseDate);
            dest.writeDouble(rating);
            dest.writeString(image);
        }

        public static final Creator<setMovie> CREATOR = new Creator<setMovie>() {
            @Override
            public setMovie createFromParcel(Parcel parcel) {
                return new setMovie(parcel);
            }

            @Override
            public setMovie[] newArray(int size) {
                return new setMovie[size];
            }
        };
    }
}
