package io.tarek360.gcforum.ui.notificationCenter.repository;

import android.util.Log;
import io.tarek360.gcforum.data.rest.NotificationsClient;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.domain.model.APIResponse;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.util.Logger;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tarek on 4/20/16.
 */
public class NotificationsRemoteRepository implements NotificationsRepository {

  private static final String TAG = NotificationsRemoteRepository.class.getSimpleName();

  NotificationsCallBack sessionsCallBack;
  private boolean isLoading;
  private Call<APIResponse<Notification>> call;

  public NotificationsRemoteRepository(NotificationsCallBack sessionsCallBack) {
    this.sessionsCallBack = sessionsCallBack;
  }

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  @Override public boolean loadNotifications() {

    // Get the REST adapter which points the API endpoint.
    NotificationsClient client = ServiceGenerator.getNotificationsClient();
    call = client.getNotifications();

    if (isLoading) {
      // reject the order.
      Log.d(TAG, "Loading order is rejected");
      return false;
    }

    isLoading = true;

    call.enqueue(new Callback<APIResponse<Notification>>() {
      @Override public void onResponse(Response<APIResponse<Notification>> response) {

        if (response.isSuccess()) {

          if (sessionsCallBack == null) {
            Logger.error(NotificationsRemoteRepository.this,
                "request success but sessionsCallBack is null");
            return;
          }
          // request successful (status code 200, 201)
          Log.d(TAG, "request success");

          List<Notification> apiResponse = response.body().getData();

          sessionsCallBack.onNotificationsLoadedResponse(apiResponse);

          //Log.d(TAG, "products.get(0).getProductTitle() " + apiResponse.getData().get(0).getProductTitle());
        } else {
          //request not successful (like 400,401,403 etc)
          //Handle errors
          sessionsCallBack.onNotificationsLoadedFailure();
          Log.e(TAG, "request not successful");
        }
        isLoading = false;
      }

      @Override public void onFailure(Throwable t) {
        Log.e(TAG, "onFailure");
        t.printStackTrace();
        isLoading = false;

        if (!call.isCanceled()) {
          sessionsCallBack.onNotificationsLoadedFailure();
        }
      }
    });

    return true;
  }

  @Override public void cancelLoading() {
    Log.d(TAG, "cancelLoading");
    if (call != null) {
      call.cancel();
    }
    sessionsCallBack = null;
  }
}
