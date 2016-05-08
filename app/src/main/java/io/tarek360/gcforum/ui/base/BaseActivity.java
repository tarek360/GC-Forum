package io.tarek360.gcforum.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.gcm.GcmMessageHandler;
import io.tarek360.gcforum.gcm.Notification;
import io.tarek360.gcforum.gcm.NotificationModel;
import io.tarek360.gcforum.util.Navigator;
import io.tarek360.gcforum.util.UIHelper;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Created by tarek on 1/25/16.
 */
public class BaseActivity extends AppCompatActivity {

  @Inject public Navigator mNavigator;
  @Inject public SharedPreferencesHelper mSharedPreferencesHelper;
  @Inject public NotificationModel mNotificationModel;
  private Subscription mSubscription;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((App) getApplication()).getAppComponent().inject(this);
    mNavigator.setContext(this);

    // Make TranslucentStatus
    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    //  Window w = getWindow(); // in Activity's onCreate() for instance
    //  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
    //      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    //}

  }

  @Override public void onResume() {
    super.onResume();

    //subscribe your activity onResume()
    mSubscription = mNotificationModel.getNotification().subscribe(notification -> {
      onNotificationReceivedRx(notification);
    });
  }

  @Override protected void onPause() {
    super.onPause();

    //Must unsubscribe onPause()
    if (mSubscription != null) {
      mSubscription.unsubscribe();
    }
  }

  protected void onNotificationReceivedRx(Notification notification) {
    if (notification.getType().equalsIgnoreCase(GcmMessageHandler.NOTIFICATION_TYPE_CHAT_KEY)) {
      //UIHelper.createChatNotification(getBaseContext(), notification.getMessage());
    } else if (notification.getType()
        .equalsIgnoreCase(GcmMessageHandler.NOTIFICATION_TYPE_CENTRAL_KEY)) {
      UIHelper.createCentralNotification(getBaseContext(), notification.getMessage(), true);
    }
  }
}
