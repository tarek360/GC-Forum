package io.tarek360.gcforum.data.rest;

import io.tarek360.gcforum.domain.model.APIResponse;
import io.tarek360.gcforum.domain.model.Notification;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tarek on 4/5/16.
 */

public interface NotificationsClient {

  @GET("notifications") Call<APIResponse<Notification>> getNotifications();
}

