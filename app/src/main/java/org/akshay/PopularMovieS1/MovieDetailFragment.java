package org.akshay.PopularMovieS1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MovieDetailFragment extends Fragment {


    private static final String ARG_EXTRA = "movie_data";
    private movieAPI.setMovie mMovie;
    private TextView mTitleView;
    private TextView mReleaseView;
    private TextView mDetailView;
    private TextView mRatingView;
    private ImageView mThumbView;

    public static MovieDetailFragment getInstance(movieAPI.setMovie item) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EXTRA,item);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMovie = args.getParcelable(ARG_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mTitleView = (TextView) rootView.findViewById(R.id.title);
        mReleaseView = (TextView) rootView.findViewById(R.id.release_date);
        mDetailView = (TextView) rootView.findViewById(R.id.overview);
        mRatingView = (TextView) rootView.findViewById(R.id.rating);
        mThumbView = (ImageView) rootView.findViewById(R.id.thumb);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mMovie == null) {
            return;
        }
        if(mMovie.getOriginalTitle() !=null && !mMovie.getOriginalTitle().isEmpty()){
            mTitleView.setText(mMovie.getOriginalTitle());
        }else{
            mTitleView.setText(movieAPI.NOT_AVAILABLE);
        }
        if (mMovie.getReleaseDate() != null && !mMovie.getReleaseDate().isEmpty()) {
            mReleaseView.setText(mMovie.getReleaseDate());
        }else{
            mReleaseView.setText(movieAPI.NOT_AVAILABLE);
        }
        if (mMovie.getOverview() != null && !mMovie.getOverview().isEmpty()) {
            mDetailView.setText(mMovie.getOverview());
        }else{
            mDetailView.setText(movieAPI.NOT_AVAILABLE);
        }

        String rating = mMovie.getRating() + "/10";
        mRatingView.setText(rating);
        Glide.with(getActivity()).load(movieAPI.IMAGE_BASE_URL+ mMovie.getImage())
                .error(R.drawable.no_thumb)
                .placeholder(R.drawable.no_thumb)
                .into(mThumbView);
    }
}
