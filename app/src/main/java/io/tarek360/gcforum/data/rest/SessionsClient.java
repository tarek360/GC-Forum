package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.Session;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tarek on 4/5/16.
 */

public interface SessionsClient {

  @GET("schedules") Call<List<Session>> getSessions(@Query("day") int day);
}

