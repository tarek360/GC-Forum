package io.tarek360.gcforum.gcm;

import android.app.Activity;
import android.content.Intent;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;

public class GcmPresenter implements GcmContract.Action {
  private Activity mActivity;

  public GcmPresenter(Activity activity, final SharedPreferencesHelper sharedPreferencesHelper) {

    this.mActivity = activity;
  }

  @Override public void startRegisterGcm() {
    if (GcmHelper.checkPlayServices(mActivity)) {
      // Start IntentService to register this application with GCM.
      Intent intent = new Intent(mActivity, RegistrationIntentService.class);
      mActivity.startService(intent);
    }
  }
}
