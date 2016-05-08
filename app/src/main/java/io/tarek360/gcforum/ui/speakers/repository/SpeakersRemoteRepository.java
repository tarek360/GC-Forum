package io.tarek360.gcforum.ui.speakers.repository;

import android.util.Log;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.data.rest.SpeakersClient;
import io.tarek360.gcforum.domain.model.APIResponse;
import io.tarek360.gcforum.domain.model.Speaker;
import io.tarek360.gcforum.util.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tarek on 4/9/16.
 */
public class SpeakersRemoteRepository implements SpeakersRepository {

  private static final String TAG = SpeakersRemoteRepository.class.getSimpleName();

  SpeakersCallBack speakersCallBack;
  private boolean isLoading;
  private Call<APIResponse<Speaker>> call;

  public SpeakersRemoteRepository(SpeakersCallBack speakersCallBack) {
    this.speakersCallBack = speakersCallBack;
  }

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  @Override public boolean loadSpeakers(int page, int perPage) {

    if (isLoading) {
      // reject the order.
      Log.d(TAG, "Loading order is rejected");
      return false;
    }

    isLoading = true;

    // Get the REST adapter which points the API endpoint.
    SpeakersClient client = ServiceGenerator.getSpeakersClient();
    call = client.getSpeakers(page, perPage);

    call.enqueue(new Callback<APIResponse<Speaker>>() {
      @Override public void onResponse(Response<APIResponse<Speaker>> response) {

        if (response.isSuccess()) {

          if (speakersCallBack == null) {
            Logger.error(SpeakersRemoteRepository.this,
                "request success but speakersCallBack is null");
            return;
          }
          // request successful (status code 200, 201)
          Log.d(TAG, "request success");

          APIResponse<Speaker> apiResponse = response.body();

          speakersCallBack.onSpeakersLoadedResponse(apiResponse.getData(),
              apiResponse.getLastPage());

          //Log.d(TAG, "products.get(0).getProductTitle() " + apiResponse.getData().get(0).getProductTitle());
        } else {
          //request not successful (like 400,401,403 etc)
          //Handle errors
          speakersCallBack.onSpeakersLoadedFailure();
          Log.e(TAG, "request not successful");
        }
        isLoading = false;
      }

      @Override public void onFailure(Throwable t) {
        Log.e(TAG, "onFailure");
        t.printStackTrace();
        isLoading = false;

        if (!call.isCanceled()) {
          speakersCallBack.onSpeakersLoadedFailure();
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
    speakersCallBack = null;
  }
}
