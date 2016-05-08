package io.tarek360.gcforum.ui.sponsors;

import android.util.Log;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.SponsorsSection;
import io.tarek360.gcforum.ui.sponsors.repository.SponsorsRemoteRepository;
import io.tarek360.gcforum.ui.sponsors.repository.SponsorsRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class SponsorsPresenter
    implements SponsorsContract.ActionsListener, SponsorsRepository.SponsorsCallBack {

  private static final String TAG = SponsorsPresenter.class.getSimpleName();
  private SponsorsRepository sponsorsRepository;
  private SponsorsContract.View view;

  public SponsorsPresenter(SponsorsContract.View view) {
    this.view = view;
    this.sponsorsRepository = new SponsorsRemoteRepository(this);
  }

  @Override public void loadSponsors() {
    view.showSwipeContainerIndicator(true);
    sponsorsRepository.loadSponsors();
  }

  @Override public void openSponsorUrl(String url) {
    Log.d(TAG, "openSponsorUrl");
    this.view.openSponsorUrl(url);
  }

  @Override public void onDestroyView() {
    sponsorsRepository.cancelLoading();
    this.view = null;
  }

  @Override public void onSponsorsLoadedResponse(List<SponsorsSection> sponsorsSections) {
    //
    view.showSwipeContainerIndicator(false);

    int firstItemIndex = 0;
    if (sponsorsSections.size() == firstItemIndex) {
      view.showTVMessage(R.string.no_data);
      SponsorsSection sponsorsSection = new SponsorsSection();
      sponsorsSection.setSponsors(new ArrayList<>());
      sponsorsSections.add(sponsorsSection);
      view.showSponsors(sponsorsSections);
    } else {
      view.hideTVMessage();
      view.showSponsors(sponsorsSections);
    }
  }

  @Override public void onSponsorsLoadedFailure() {
    view.showSwipeContainerIndicator(false);
    view.showSnackBar(R.string.error_connection);
  }
}
