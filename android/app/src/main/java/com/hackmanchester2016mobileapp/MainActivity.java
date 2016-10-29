package com.hackmanchester2016mobileapp;

import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "9BAg1WtTf9k9ujUcQNdMcYCEV";
    private static final String TWITTER_SECRET = "lRS3D6hwzQMsbvGtgT0HN5njUptaiHQa9yO4vFDgEvOdZNGcQV";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "HackManchester2016MobileApp";
    }
}
