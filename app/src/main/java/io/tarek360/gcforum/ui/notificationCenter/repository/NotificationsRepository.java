package io.tarek360.gcforum.ui.notificationCenter.repository;

import io.tarek360.gcforum.domain.model.Notification;
import java.util.List;

/**
 * Created by tarek on 4/20/16.
 */
public interface NotificationsRepository {

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  boolean loadNotifications();

  void cancelLoading();

  interface NotificationsCallBack {

    void onNotificationsLoadedResponse(List<Notification> notifications);

    void onNotificationsLoadedFailure();
  }
}
