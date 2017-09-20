package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jokesandroidlib.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeAsyncTask.JokeCallback {

    private Button mJokeButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mJokeButton = (Button) root.findViewById(R.id.tell_joke_btn);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).showProgressDialog(true,
                            getString(R.string.loading_joke));
                }
                tellJoke();
            }
        });
        return root;
    }

    public void tellJoke() {
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(this);
        jokeAsyncTask.execute();
    }

    @Override
    public void onJokeReceived(String joke) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showProgressDialog(false, null);
        }
        Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra(Util.JOKE_KEY, joke);
        startActivity(intent);
    }
}
