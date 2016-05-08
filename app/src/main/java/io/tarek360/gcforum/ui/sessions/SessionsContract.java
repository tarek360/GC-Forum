package io.tarek360.gcforum.ui.sessions;

import io.tarek360.gcforum.domain.model.EventDay;
import io.tarek360.gcforum.domain.model.Session;

/**
 * Created by tarek on 4/5/16.
 */
public interface SessionsContract {

  interface View {

    void showSwipeContainerIndicator(boolean refreshing);

    void showEventDay(EventDay eventDay);

    void showSnackBar(int resId);

    void showTVMessage(int resId);

    void hideTVMessage();

    void showContentDialog(CharSequence title, CharSequence content);
  }

  interface ActionsListener {

    void loadEventDay(int day);

    void openSession(Session session);

    void onDestroyView();
  }
}
