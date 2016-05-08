package io.tarek360.gcforum.ui.sessions.repository;

import android.util.Log;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.data.rest.SessionsClient;
import io.tarek360.gcforum.domain.model.Session;
import io.tarek360.gcforum.util.Logger;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tarek on 4/5/16.
 */
public class SessionsRemoteRepository implements SessionsRepository {

  private static final String TAG = SessionsRemoteRepository.class.getSimpleName();

  SessionsCallBack sessionsCallBack;
  private boolean isLoading;
  private Call<List<Session>> call;

  public SessionsRemoteRepository(SessionsCallBack sessionsCallBack) {
    this.sessionsCallBack = sessionsCallBack;
  }

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @param day number to load.
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  @Override public boolean loadEventDay(int day) {

    // Get the REST adapter which points the API endpoint.
    SessionsClient client = ServiceGenerator.getSessionsClient();
    call = client.getSessions(day);
    return loadEventDay();
  }

  private boolean loadEventDay() {
    if (isLoading) {
      // reject the order.
      Log.d(TAG, "Loading order is rejected");
      return false;
    }

    isLoading = true;

    call.enqueue(new Callback<List<Session>>() {
      @Override public void onResponse(Response<List<Session>> response) {

        if (response.isSuccess()) {

          if (sessionsCallBack == null) {
            Logger.error(SessionsRemoteRepository.this,
                "request success but sessionsCallBack is null");
            return;
          }
          // request successful (status code 200, 201)
          Log.d(TAG, "request success");

          List<Session> apiResponse = response.body();

          sessionsCallBack.onEventDayLoadedResponse(apiResponse);

          //Log.d(TAG, "products.get(0).getProductTitle() " + apiResponse.getData().get(0).getProductTitle());
        } else {
          //request not successful (like 400,401,403 etc)
          //Handle errors
          sessionsCallBack.onEventDayLoadedFailure();
          Log.e(TAG, "request not successful");
        }
        isLoading = false;
      }

      @Override public void onFailure(Throwable t) {
        Log.e(TAG, "onFailure");
        t.printStackTrace();
        isLoading = false;

        if (!call.isCanceled()) {
          sessionsCallBack.onEventDayLoadedFailure();
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
