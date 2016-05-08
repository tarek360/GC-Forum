package io.tarek360.gcforum.gcm;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import io.tarek360.gcforum.util.Logger;

public class GcmHelper {
  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

  /**
   * Check the device to make sure it has the Google Play Services APK. If
   * it doesn't, display a dialog that allows users to download the APK from
   * the Google Play Store or enable it in the device's system settings.
   */
  public static boolean checkPlayServices(Activity activity) {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(resultCode)) {
        apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
            .show();
      } else {
        Logger.info(activity, "This device is not supported.");
        activity.finish();
      }
      return false;
    }
    return true;
  }
}
