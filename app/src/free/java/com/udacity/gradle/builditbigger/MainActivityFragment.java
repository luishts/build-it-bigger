package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jokesandroidlib.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Button mJokeButton;
    private InterstitialAd mInterstitialAd;

    private static final int ID_JOKE_LOADER = 1;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJokeButton = (Button) root.findViewById(R.id.tell_joke_btn);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });


        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
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
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                }

                @Override
                public void onAdClosed() {
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).showProgressDialog(false, null);
                    }

                    Intent intent = new Intent(getActivity(), JokeActivity.class);
                    intent.putExtra(Util.JOKE_KEY, joke);
                    startActivity(intent);

                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<String> loader) {
            if (getLoaderManager().getLoader(ID_JOKE_LOADER) != null) {
                getLoaderManager().restartLoader(ID_JOKE_LOADER, null, this).forceLoad();
            }
        }
    };
}
