package com.bright.cworkspacehackmanchester2016fe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Daniel.Harrison on 30/10/2016.
 */

public interface TwitterService {
    @GET("twitter/getfeedpath")
Call<List<Tweet>> getFeedPath(@Query("token") String token, @Query("tokenSecret") String tokenSecret, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("screenName") String screenName);

    @POST("twitter/guessanswer")
    Call<GuessAnswerResponse> guessAnswer(@Body GuessAnswerRequest request);
}
