package com.ravijeet.teleportal.youtube;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.ravijeet.teleportal.R;

/**
 * Created by Ravijeet on 3/12/18.
 */

public abstract class YoutubeFailureRecoveryActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult
            youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player),
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(getResources().getString(R.string.dev_key), this);
        }
    }

    protected abstract YouTubePlayer.Provider getYouTubePlayerProvider();
}
