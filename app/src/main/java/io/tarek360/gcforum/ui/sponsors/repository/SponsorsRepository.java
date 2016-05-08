package io.tarek360.gcforum.ui.sponsors.repository;

import io.tarek360.gcforum.domain.model.SponsorsSection;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public interface SponsorsRepository {

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  boolean loadSponsors();

  void cancelLoading();

  interface SponsorsCallBack {

    void onSponsorsLoadedResponse(List<SponsorsSection> sponsorsSections);

    void onSponsorsLoadedFailure();
  }
}
