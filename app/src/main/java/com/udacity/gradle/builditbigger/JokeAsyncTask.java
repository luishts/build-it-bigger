package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.builditbigger.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by ltorres on 19/09/2017.
 */

public class JokeAsyncTask extends AsyncTask<Void, Void, String> {

    private JokeCallback mJokeCallback;
    private static JokeApi mJokeApiService = null;

    public JokeAsyncTask(JokeCallback callback) {
        this.mJokeCallback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {

        if (mJokeApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8085/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            mJokeApiService = builder.build();
        }

        //context = params[0].first;
        //String name = params[0].second;

        try {
            return mJokeApiService.findJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mJokeCallback != null) {
            mJokeCallback.onJokeReceived(result);
        }
    }

    public interface JokeCallback {
        void onJokeReceived(String joke);
    }
}
