package io.tarek360.gcforum.ui.notificationCenter;

import android.util.Log;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.ui.notificationCenter.repository.NotificationsRemoteRepository;
import io.tarek360.gcforum.ui.notificationCenter.repository.NotificationsRepository;
import io.tarek360.gcforum.util.Navigator;
import java.util.List;

/**
 * Created by tarek on 4/20/16.
 */
public class NotificationsPresenter implements NotificationsContract.ActionsListener,
    NotificationsRepository.NotificationsCallBack {

  private static final String TAG = NotificationsPresenter.class.getSimpleName();
  private NotificationsRepository notificationsRepository;
  private NotificationsContract.View view;
  private Navigator mNavigator;

  public NotificationsPresenter(NotificationsContract.View view, Navigator navigator) {
    this.view = view;
    this.mNavigator = navigator;
    this.notificationsRepository = new NotificationsRemoteRepository(this);
  }

  @Override public void loadNotifications() {
    view.showSwipeContainerIndicator(true);
    notificationsRepository.loadNotifications();
  }

  @Override public void openNotification(Notification notification) {
    Log.d(TAG, "openNotification");
    mNavigator.navigateToNotificationDetailsActivity(notification);
  }

  @Override public void onDestroyView() {
    notificationsRepository.cancelLoading();
    this.view = null;
  }

  @Override public void onNotificationsLoadedResponse(List<Notification> notifications) {
    //
    view.showSwipeContainerIndicator(false);

    int firstItemIndex = 0;
    if (notifications.size() == firstItemIndex) {
      view.showTVMessage(R.string.no_notifications);
    } else {
      view.hideTVMessage();
    }
    view.showNotifications(notifications);
  }

  @Override public void onNotificationsLoadedFailure() {
    view.showSwipeContainerIndicator(false);
    view.showSnackBar(R.string.error_connection);
  }
}
