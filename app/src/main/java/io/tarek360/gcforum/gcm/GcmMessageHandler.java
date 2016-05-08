package io.tarek360.gcforum.gcm;

/**
 * Created by tarek on 4/14/16.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.android.gms.gcm.GcmListenerService;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.util.Logger;
import io.tarek360.gcforum.util.UIHelper;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelperImpl;
import javax.inject.Inject;

public class GcmMessageHandler extends GcmListenerService {

  public static final String MESSAGE_KEY = "message";
  public static final String NOTIFICATION_TYPE_CHAT_KEY = "chat";
  public static final String NOTIFICATION_TYPE_CENTRAL_KEY = "central";
  public static final String TYPE_KEY = "type";
  @Inject public NotificationModel mNotificationModel;

  @Override public void onCreate() {
    super.onCreate();
    Logger.debug(this, "onCreate");
  }

  @Override public void onMessageReceived(String from, Bundle data) {
    Logger.debug(this, "onMessageReceived");

    ((App) getApplication()).getAppComponent().inject(this);

    String message = data.getString(MESSAGE_KEY);
    String type = data.getString(TYPE_KEY);

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferencesHelper sharedPreferencesHelper =
        new SharedPreferencesHelperImpl(sharedPreferences);

    String userToken = sharedPreferencesHelper.getString(User.FLAG_USER_TOKEN_DEFAULT);

    if (message != null && type != null) {
      if (mNotificationModel.hasObservers()) { // Foreground Mode
        mNotificationModel.setNotification(new Notification(type, message));
      } else { // Background Mode
        if (type.equalsIgnoreCase(NOTIFICATION_TYPE_CHAT_KEY) && !TextUtils.isEmpty(userToken)) {
          //UIHelper.createChatNotification(getBaseContext(), message);
        } else if (type.equalsIgnoreCase(NOTIFICATION_TYPE_CENTRAL_KEY)) {
          UIHelper.createCentralNotification(getBaseContext(), message,
              !TextUtils.isEmpty(userToken));
        }
      }
    }
  }
}
