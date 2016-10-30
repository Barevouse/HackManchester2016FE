package com.bright.cworkspacehackmanchester2016fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "lsoMiOYqptZ6MdxxTiM1sIsc7";
    private static final String TWITTER_SECRET = "7x15u25SsTNKhXDG5hRrChV2P3zl3RzC0SxJPs6BMiBKzG1nzi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        TwitterLoginButton loginButton = getLoginButton();
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                processLogin(result);
            }

            @Override
            public void failure(TwitterException exception) {
                logFailure(exception);
            }

            private void processLogin(Result<TwitterSession> result) {
                AuthToken authToken = result.data.getAuthToken();



                Log.d("TwitterKit", authToken.toString());
            }

            private void logFailure(TwitterException exception) {
                Log.d("TwitterKit", exception.getMessage());
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TwitterLoginButton loginButton = getLoginButton();
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private TwitterLoginButton getLoginButton() {
        return (TwitterLoginButton) findViewById(R.id.login_button);
    }
}
