package io.tarek360.gcforum.ui.sessions;

import android.text.TextUtils;
import android.util.Log;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.EventDay;
import io.tarek360.gcforum.domain.model.Session;
import io.tarek360.gcforum.ui.sessions.repository.SessionsRemoteRepository;
import io.tarek360.gcforum.ui.sessions.repository.SessionsRepository;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class SessionsPresenter
    implements SessionsContract.ActionsListener, SessionsRepository.SessionsCallBack {

  private static final String TAG = SessionsPresenter.class.getSimpleName();
  private SessionsRepository sessionsRepository;
  private SessionsContract.View view;

  public SessionsPresenter(SessionsContract.View view) {
    this.view = view;
    this.sessionsRepository = new SessionsRemoteRepository(this);
  }

  @Override public void loadEventDay(int day) {
    view.showSwipeContainerIndicator(true);
    sessionsRepository.loadEventDay(day);
  }

  @Override public void openSession(Session session) {
    Log.d(TAG, "openSession");
    if (!TextUtils.isEmpty(session.getDetails())) {
      this.view.showContentDialog(session.getTitle(), session.getDetails());
    }
  }

  @Override public void onDestroyView() {
    sessionsRepository.cancelLoading();
    this.view = null;
  }

  @Override public void onEventDayLoadedResponse(List<Session> sessions) {
    //
    view.showSwipeContainerIndicator(false);

    int firstItemIndex = 0;
    if (sessions.size() == firstItemIndex) {
      view.showTVMessage(R.string.no_data);
      EventDay eventDay = new EventDay();
      eventDay.setSessions(sessions);
      view.showEventDay(eventDay);
    } else {
      view.hideTVMessage();
      EventDay eventDay = new EventDay();
      eventDay.setSessions(sessions);
      eventDay.setDate(sessions.get(firstItemIndex).getStartTime());
      view.showEventDay(eventDay);
    }
  }

  @Override public void onEventDayLoadedFailure() {
    view.showSwipeContainerIndicator(false);
    view.showSnackBar(R.string.error_connection);
  }
}
