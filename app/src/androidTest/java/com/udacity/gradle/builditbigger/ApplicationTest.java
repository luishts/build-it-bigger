package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertFalse;

/**
 * Created by Luis on 19/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    private CountDownLatch mCountDownLatch;

    @Before
    public void setup() {
        mCountDownLatch = new CountDownLatch(1);
    }

    @Test
    public void testAsyncTask() throws InterruptedException {
        JokeAsyncTask task = new JokeAsyncTask(new JokeAsyncTask.JokeCallback() {
            @Override
            public void onJokeReceived(String joke) {
                assertFalse(TextUtils.isEmpty(joke));
                mCountDownLatch.countDown();
            }
        });
        task.execute();
        mCountDownLatch.await();
    }

}
