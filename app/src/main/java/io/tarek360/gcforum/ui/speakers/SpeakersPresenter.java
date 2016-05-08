package io.tarek360.gcforum.ui.speakers;

import android.util.Log;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Speaker;
import io.tarek360.gcforum.ui.speakers.repository.SpeakersRemoteRepository;
import io.tarek360.gcforum.ui.speakers.repository.SpeakersRepository;
import io.tarek360.gcforum.util.Navigator;
import java.util.List;

/**
 * Created by tarek on 4/9/16.
 */
public class SpeakersPresenter
    implements SpeakersContract.ActionsListener, SpeakersRepository.SpeakersCallBack {

  private static final String TAG = SpeakersPresenter.class.getSimpleName();
  private final static int FIRST_PAGE = 1;
  private SpeakersRepository speakersRepository;
  private SpeakersContract.View view;
  private Navigator mNavigator;
  private int pageIndex = 1;
  private boolean isNoMoreData;

  public SpeakersPresenter(SpeakersContract.View view, Navigator navigator) {
    this.view = view;
    this.mNavigator = navigator;
    this.speakersRepository = new SpeakersRemoteRepository(this);
  }

  @Override public void loadSpeakers() {

    if (!isNoMoreData) {
      if (pageIndex == FIRST_PAGE) {
        view.showSwipeContainerIndicator(true);
      }
      // set 16 per page
      speakersRepository.loadSpeakers(pageIndex, 16);
    } else {
      Log.i(TAG, "No more products");
    }
  }

  @Override public void refreshSpeakers() {
    isNoMoreData = false;
    pageIndex = FIRST_PAGE;

    // set 16 per page
    speakersRepository.loadSpeakers(pageIndex, 16);
  }

  @Override public void openSpeaker(Speaker speaker) {
    mNavigator.navigateToSpeakerDetailsActivity(speaker);
  }

  @Override public void onDestroyView() {
    speakersRepository.cancelLoading();
    this.view = null;
  }

  @Override public void onSpeakersLoadedResponse(List<Speaker> speakers, int lastPage) {
    view.showSwipeContainerIndicator(false);

    int firstItemIndex = 0;
    if (speakers.size() == firstItemIndex) {
      isNoMoreData = true;
      if (lastPage == 0) {
        view.showTVMessage(R.string.no_data);
      }
      return;
    }

    if (pageIndex == FIRST_PAGE) {
      view.showSpeakers(true, speakers);
    } else {
      view.showSpeakers(false, speakers);
    }

    // prepare pageIndex for the second page
    pageIndex++;
  }

  @Override public void onSpeakersLoadedFailure() {
    view.showSwipeContainerIndicator(false);
    view.showSnackBar(R.string.error_connection);
  }
}
