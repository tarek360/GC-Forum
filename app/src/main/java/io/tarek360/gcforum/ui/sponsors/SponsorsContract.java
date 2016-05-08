package io.tarek360.gcforum.ui.sponsors;

import io.tarek360.gcforum.domain.model.SponsorsSection;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public interface SponsorsContract {

  interface View {

    void showSwipeContainerIndicator(boolean refreshing);

    void showSponsors(List<SponsorsSection> sponsorsSections);

    void showSnackBar(int resId);

    void showTVMessage(int resId);

    void hideTVMessage();

    void openSponsorUrl(String url);
  }

  interface ActionsListener {

    void loadSponsors();

    void openSponsorUrl(String url);

    void onDestroyView();
  }
}
