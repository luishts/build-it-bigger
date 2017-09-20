package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.builditbigger.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by ltorres on 20/09/2017.
 */

public class JokeTaskLoader extends AsyncTaskLoader<String> {

    private static JokeApi mJokeApiService = null;

    public JokeTaskLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        if (mJokeApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            mJokeApiService = builder.build();
        }

        try {
            return mJokeApiService.findJoke().execute().getJoke();
        } catch (IOException e) {
            return "";
        }
    }
}
