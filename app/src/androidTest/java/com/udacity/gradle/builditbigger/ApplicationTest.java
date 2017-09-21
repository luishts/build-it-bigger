package com.udacity.gradle.builditbigger;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.LoaderTestCase;
import android.text.TextUtils;

import com.example.builditbigger.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Created by Luis on 19/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends LoaderTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTask() throws InterruptedException {

        Context appContext = InstrumentationRegistry.getTargetContext();

        getLoaderResultSynchronously(new AsyncTaskLoader<String>(appContext) {
            @Override
            public String loadInBackground() {

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
                JokeApi mJokeApiService = builder.build();

                try {
                    String joke = mJokeApiService.findJoke().execute().getJoke();
                    assertFalse(TextUtils.isEmpty(joke));
                } catch (IOException e) {
                    return "";
                }
                return "";
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            protected void onStopLoading() {
                cancelLoad();
            }
        });
    }
}
