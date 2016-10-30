package com.bright.cworkspacehackmanchester2016fe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.IOException;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                onLoginSuccess(result);
            }

            @Override
            public void failure(TwitterException exception) {
                logFailure(exception);
            }

            private void onLoginSuccess(Result<TwitterSession> result) {

                Intent intent = new Intent(getApplicationContext(), ClueListViewActivity.class);
                startActivity(intent);
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
