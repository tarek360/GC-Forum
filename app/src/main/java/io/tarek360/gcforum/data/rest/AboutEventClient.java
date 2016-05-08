package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.AboutEvent;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tarek on 2/17/16.
 */

public interface AboutEventClient {

  @GET("about") Call<AboutEvent> loadData();
}

