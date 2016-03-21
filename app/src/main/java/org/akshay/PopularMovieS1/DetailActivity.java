package org.akshay.PopularMovieS1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "movie_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        movieAPI.setMovie item = intent.getParcelableExtra(EXTRA_DATA);

        if (savedInstanceState == null) {
            Fragment fragment = MovieDetailFragment.getInstance(item);
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, null).commit();
        }

    }
}
