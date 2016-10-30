package com.bright.cworkspacehackmanchester2016fe;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel.Harrison on 30/10/2016.
 */

public class ClueListViewActivity extends AppCompatActivity implements android.location.LocationListener, SwipeRefreshLayout.OnRefreshListener {

    private Date mLastRefreshed = new Date(0);
    private int PollFrequencyInSeconds = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Activity thisActivity = this;

        setContentView(R.layout.activity_cluelistview);

        SwipeRefreshLayout layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        layout.setOnRefreshListener(this);

        beginLocationUpdates(thisActivity);
    }

    private void beginLocationUpdates(Activity thisActivity) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (!shouldRefresh()) return;
        mLastRefreshed = new Date();
        final Activity thisActivity = this;

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hackmcr16-api.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TwitterService service = retrofit.create(TwitterService.class);

        service.getFeedPath(authToken.token, authToken.secret,
                location.getLatitude(), location.getLongitude(), "barevouse")
                .enqueue(new Callback<List<Tweet>>() {

                    @Override
                    public void success(Result<List<Tweet>> result) {
                        ListView clueListView = (ListView) thisActivity.findViewById(R.id.clue_list_view);
                        clueListView.setAdapter(new ClueListArrayAdapter(thisActivity,
                                R.layout.list_view_item, result.data));
                        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) thisActivity.findViewById(R.id.swipe_refresh_layout);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });

        endLocationUpdates();
    }

    private boolean shouldRefresh() {
        Date now = new Date();
        now.setSeconds(now.getSeconds()-PollFrequencyInSeconds);
        return mLastRefreshed.before(now);
    }

    private void endLocationUpdates() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onRefresh() {
        beginLocationUpdates(this);
    }
}
