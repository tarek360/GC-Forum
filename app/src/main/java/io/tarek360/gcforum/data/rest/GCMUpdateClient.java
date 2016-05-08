package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.GCM;
import io.tarek360.gcforum.domain.model.SimpleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tarek on 2/17/16.
 */

public interface GCMUpdateClient {

  @POST("user/update-gcm") Call<SimpleResponse> updateGCMId(@Query("token") String token,
      @Body GCM gcm);
}

