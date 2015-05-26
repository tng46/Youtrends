package com.example.santo.youtrends2.app.fragments;

import android.os.Bundle;

import com.example.santo.youtrends2.app.player.Config;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoPlayerFragment extends YouTubePlayerSupportFragment {

    public VideoPlayerFragment() { }

    public static VideoPlayerFragment newInstance(String url) {

        VideoPlayerFragment f = new VideoPlayerFragment();

        Bundle b = new Bundle();
        b.putString("url", url);

        f.setArguments(b);
        f.init();

        return f;
    }



    private void init() {

        initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.loadVideo(getArguments().getString("url"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}