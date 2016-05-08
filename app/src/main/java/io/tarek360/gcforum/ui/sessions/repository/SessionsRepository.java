package io.tarek360.gcforum.ui.sessions.repository;

import io.tarek360.gcforum.domain.model.Session;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public interface SessionsRepository {

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @param day number to load.
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  boolean loadEventDay(int day);

  void cancelLoading();

  interface SessionsCallBack {

    void onEventDayLoadedResponse(List<Session> sessions);

    void onEventDayLoadedFailure();
  }
}
