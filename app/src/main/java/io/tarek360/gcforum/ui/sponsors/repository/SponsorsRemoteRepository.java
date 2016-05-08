package io.tarek360.gcforum.ui.sponsors.repository;

import android.util.Log;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.data.rest.SponsorsClient;
import io.tarek360.gcforum.domain.model.SponsorsSection;
import io.tarek360.gcforum.util.Logger;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tarek on 4/5/16.
 */
public class SponsorsRemoteRepository implements SponsorsRepository {

  private static final String TAG = SponsorsRemoteRepository.class.getSimpleName();

  SponsorsCallBack sponsorsCallBack;
  private boolean isLoading;
  private Call<List<SponsorsSection>> call;

  public SponsorsRemoteRepository(SponsorsCallBack sponsorsCallBack) {
    this.sponsorsCallBack = sponsorsCallBack;
  }

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  @Override public boolean loadSponsors() {

    if (isLoading) {
      // reject the order.
      Log.d(TAG, "Loading order is rejected");
      return false;
    }

    isLoading = true;

    // Get the REST adapter which points the API endpoint.
    SponsorsClient client = ServiceGenerator.getSponsorsClient();
    call = client.getSponsors();

    call.enqueue(new Callback<List<SponsorsSection>>() {
      @Override public void onResponse(Response<List<SponsorsSection>> response) {

        if (response.isSuccess()) {

          if (sponsorsCallBack == null) {
            Logger.error(SponsorsRemoteRepository.this,
                "request success but sponsorsCallBack is null");
            return;
          }
          // request successful (status code 200, 201)
          Log.d(TAG, "request success");

          List<SponsorsSection> apiResponse = response.body();

          sponsorsCallBack.onSponsorsLoadedResponse(apiResponse);

          //Log.d(TAG, "products.get(0).getProductTitle() " + apiResponse.getData().get(0).getProductTitle());
        } else {
          //request not successful (like 400,401,403 etc)
          //Handle errors
          sponsorsCallBack.onSponsorsLoadedFailure();
          Log.e(TAG, "request not successful");
        }
        isLoading = false;
      }

      @Override public void onFailure(Throwable t) {
        Log.e(TAG, "onFailure");
        t.printStackTrace();
        isLoading = false;

        if (!call.isCanceled()) {
          sponsorsCallBack.onSponsorsLoadedFailure();
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
    sponsorsCallBack = null;
  }
}
