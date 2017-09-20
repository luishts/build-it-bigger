package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jokesandroidlib.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button mJokeButton;
    private static final int ID_JOKE_LOADER = 1;

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
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showProgressDialog(true,
                    getString(R.string.loading_joke));
        }

        if (getLoaderManager().getLoader(ID_JOKE_LOADER) != null) {
            getLoaderManager().restartLoader(ID_JOKE_LOADER, null, mLoaderCallback).forceLoad();
        } else {
            getLoaderManager().initLoader(ID_JOKE_LOADER, null, mLoaderCallback).forceLoad();
        }
    }

    LoaderManager.LoaderCallbacks<String> mLoaderCallback = new LoaderManager.LoaderCallbacks<String>() {

        @Override
        public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
            return new JokeTaskLoader(getActivity());
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<String> loader, final String joke) {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).showProgressDialog(false, null);
            }
            Intent intent = new Intent(getActivity(), JokeActivity.class);
            intent.putExtra(Util.JOKE_KEY, joke);
            startActivity(intent);
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<String> loader) {
            if (getLoaderManager().getLoader(ID_JOKE_LOADER) != null) {
                getLoaderManager().restartLoader(ID_JOKE_LOADER, null, this).forceLoad();
            }
        }
    };
}
