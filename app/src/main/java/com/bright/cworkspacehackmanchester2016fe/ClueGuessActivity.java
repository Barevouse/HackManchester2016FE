package com.bright.cworkspacehackmanchester2016fe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel.Harrison on 30/10/2016.
 */

public class ClueGuessActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clueguess);

        initialiseToolbar();

        Button buttonSubmitGuess = (Button) findViewById(R.id.button_submit_guess);
        buttonSubmitGuess.setOnClickListener(this);
    }

    private void initialiseToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.actionbar_logo);
    }

    @Override
    public void onClick(View view) {

        final Activity thisActivity = this;

        TextView convertedView = (TextView)view;

        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hackmcr16-api.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TwitterService service = retrofit.create(TwitterService.class);

        GuessAnswerRequest request = new GuessAnswerRequest();
        request.Token = authToken.token;
        request.TokenSecret = authToken.secret;
        request.Screenname = session.getUserName();
        request.Guess = convertedView.getText().toString();

        service.guessAnswer(request).enqueue(new Callback<GuessAnswerResponse>() {
            @Override
            public void success(Result<GuessAnswerResponse> result) {
                TextView confirmationMessage = (TextView) thisActivity.findViewById(R.id.confirmation_message);
                confirmationMessage.setText(result.data.Message);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }
}
