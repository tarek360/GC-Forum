package io.tarek360.gcforum.ui.notificationCenter;

import io.tarek360.gcforum.domain.model.Notification;
import java.util.List;

/**
 * Created by tarek on 4/20/16.
 */
public interface NotificationsContract {

  interface View {

    void showSwipeContainerIndicator(boolean refreshing);

    void showNotifications(List<Notification> notifications);

    void showSnackBar(int resId);

    void showTVMessage(int resId);

    void hideTVMessage();

    void showContentDialog(CharSequence title, CharSequence content);
  }

  interface ActionsListener {

    void loadNotifications();

    void openNotification(Notification notification);

    void onDestroyView();
  }
}
