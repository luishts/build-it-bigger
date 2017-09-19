package com.example.jokesandroidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private TextView mTvJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mTvJoke = (TextView) findViewById(R.id.tv_joke);

        if (getIntent() != null && getIntent().hasExtra("joke")) {
            mTvJoke.setText(getIntent().getStringExtra("joke"));
        }
    }
}
