package com.bright.cworkspacehackmanchester2016fe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel.Harrison on 30/10/2016.
 */

public class ClueListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Activity thisActivity = this;

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hackmcr16-api.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TwitterService service = retrofit.create(TwitterService.class);

        service.getFeedPath(authToken.token, authToken.secret, 53.5, 2.2, "barevouse").enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                ListView clueListView = (ListView)thisActivity.findViewById(R.id.clue_list_view);
                clueListView.setAdapter(new ArrayAdapter<Tweet>(getApplicationContext(),
                        R.layout.list_view_item, result.data));
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });


        setContentView(R.layout.activity_cluelistview);
    }
}
