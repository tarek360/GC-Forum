package io.tarek360.gcforum.gcm;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by tarek on 4/14/16.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

  @Override public void onTokenRefresh() {
    // Fetch updated Instance ID token and notify of changes
    Intent intent = new Intent(this, RegistrationIntentService.class);
    startService(intent);
  }
}
