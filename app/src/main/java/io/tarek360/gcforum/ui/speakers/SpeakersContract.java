package io.tarek360.gcforum.ui.speakers;

import io.tarek360.gcforum.domain.model.Speaker;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public interface SpeakersContract {

  interface View {

    void showSwipeContainerIndicator(boolean refreshing);

    void showSpeakers(boolean clearing, List<Speaker> speakers);

    void showSnackBar(int resId);

    void showTVMessage(int resId);

    void hideTVMessage();

    void openSponsorUrl(String url);
  }

  interface ActionsListener {

    void loadSpeakers();

    void refreshSpeakers();

    void openSpeaker(Speaker speaker);

    void onDestroyView();
  }
}
