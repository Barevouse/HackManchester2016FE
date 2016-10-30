package com.bright.cworkspacehackmanchester2016fe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

public class MissionSelectorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionselector);

        initialiseToolbar();

        final Activity thisActivity = this;

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hackmcr16-api.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TwitterService service = retrofit.create(TwitterService.class);

        service.getFollowing(authToken.token, authToken.secret).enqueue(new Callback<List<Mission>>() {
            @Override
            public void success(Result<List<Mission>> result) {
                ListView missionListView = (ListView) thisActivity.findViewById(R.id.mission_list_view);
                missionListView.setAdapter(new MissionListArrayAdapter(thisActivity,
                        R.layout.mission_list_item, result.data));
                missionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textViewScreenName = (TextView) view.findViewById(R.id.text_view_screen_name);

                        Intent intent = new Intent(thisActivity, ClueListViewActivity.class);
                        intent.putExtra("SelectedMission", textViewScreenName.getText());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    private void initialiseToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.actionbar_logo);
    }
}
