package io.tarek360.gcforum.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.data.rest.GCMUpdateClient;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.domain.model.GCM;
import io.tarek360.gcforum.domain.model.SimpleResponse;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelperImpl;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tarek on 4/14/16.
 */
public class RegistrationIntentService extends IntentService {

  public static final String GCM_TOKEN = "gcmToken";
  // abbreviated tag name
  private static final String TAG = RegistrationIntentService.class.getSimpleName();
  SharedPreferencesHelper mSharedPreferencesHelper;

  public RegistrationIntentService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    mSharedPreferencesHelper = new SharedPreferencesHelperImpl(sharedPreferences);

    // Make a call to Instance API
    InstanceID instanceID = InstanceID.getInstance(this);
    String senderId = getResources().getString(R.string.gcm_defaultSenderId);
    try {
      // request token that will be used by the server to send push notifications
      String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
      Log.d(TAG, "GCM Registration Token: " + token);

      mSharedPreferencesHelper.setString(GCM_TOKEN, token);

      // pass along this data
      sendRegistrationToServer(token);
    } catch (IOException e) {
      e.printStackTrace();
      sharedPreferences.edit()
          .putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false)
          .apply();
    }
  }

  private void sendRegistrationToServer(String token) throws IOException {

    String userToken = mSharedPreferencesHelper.getString(User.FLAG_USER_TOKEN_DEFAULT);
    if (!TextUtils.isEmpty(userToken)) {

      Log.d(TAG, "sendRegistrationToServer");

      GCM gcm = new GCM();
      gcm.setGcmId(token);

      // Get the REST adapter which points API endpoint.
      GCMUpdateClient client = ServiceGenerator.getGCMUpdateClient();
      Call<SimpleResponse> call = client.updateGCMId(userToken, gcm);
      Response<SimpleResponse> retrofitResponse = call.execute();

      if (retrofitResponse.isSuccess()) {
        SimpleResponse simpleResponse = retrofitResponse.body();
        if (simpleResponse != null && simpleResponse.getSuccess()) {
          mSharedPreferencesHelper.setBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true);
          Crashlytics.log(1, QuickstartPreferences.SENT_TOKEN_TO_SERVER, "true");
        } else {
          mSharedPreferencesHelper.setBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
          Crashlytics.log(1, QuickstartPreferences.SENT_TOKEN_TO_SERVER, "false");
        }
      }
    } else {
      Log.d(TAG, "empty user token");
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }
}
