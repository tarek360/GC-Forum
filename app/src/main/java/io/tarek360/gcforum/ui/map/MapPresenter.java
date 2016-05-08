package io.tarek360.gcforum.ui.map;

import android.util.Log;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.data.rest.MapDataClient;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.domain.model.MapData;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tarek on 1/25/16.
 */
public class MapPresenter implements MapContract.ActionsListener {

  private static final String TAG = MapPresenter.class.getSimpleName();

  private MapContract.View view;
  private SharedPreferencesHelper sharedPreferencesHelper;

  private boolean isLoading;

  private CompositeSubscription mCompositeSubscription;

  public MapPresenter(MapContract.View view, SharedPreferencesHelper sharedPreferencesHelper) {
    this.view = view;
    // now it come with null
    this.sharedPreferencesHelper = sharedPreferencesHelper;
    mCompositeSubscription = new CompositeSubscription();
  }

  @Override public void getMapData() {

    if (isLoading) {
      // reject the order.
      Log.d(TAG, "Loading order is rejected");
      return;
    }

    view.showIndeterminateDialog();

    // Get the REST adapter which points API endpoint.
    MapDataClient client = ServiceGenerator.getMapDataClient();

    Observable<MapData> observable = client.loadMapData();

    mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(new Subscriber<MapData>() {
          @Override public void onCompleted() {
            Log.d(TAG, "loginRx onCompleted");
          }

          @Override public void onError(Throwable e) {
            Log.d(TAG, "loginRx onError");

            isLoading = false;
            view.dismissIndeterminateDialog();

            view.showErrorDialog(R.string.error, R.string.error_connection);
          }

          @Override public void onNext(MapData mapData) {
            Log.d(TAG, "loginRx onNext getSuccess");

            isLoading = false;
            view.dismissIndeterminateDialog();

            view.showMap(mapData);
          }
        }));
  }

  @Override public void onDestroyView() {
    Log.d(TAG, "cancelLoading");

    if (mCompositeSubscription != null) {
      mCompositeSubscription.unsubscribe();
    }
  }
}
