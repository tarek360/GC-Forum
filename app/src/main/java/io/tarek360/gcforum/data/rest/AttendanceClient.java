package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.APIResponse;
import io.tarek360.gcforum.domain.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tarek on 4/9/16.
 */

public interface AttendanceClient {

  @GET("users") Call<APIResponse<User>> getAttendance(@Query("page") int page,
      @Query("per_page") int perPage, @Query("user_id") String userId);
}

