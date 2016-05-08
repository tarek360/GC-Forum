package io.tarek360.gcforum.ui.speakers.repository;

import io.tarek360.gcforum.domain.model.Speaker;
import java.util.List;

/**
 * Created by tarek on 4/9/16.
 */
public interface SpeakersRepository {

  /**
   * invoke this method to load products from the implemented repository.
   *
   * @return false if the repository reject the order because it has a last order to deliver.
   */
  boolean loadSpeakers(int page, int perPage);

  void cancelLoading();

  interface SpeakersCallBack {

    void onSpeakersLoadedResponse(List<Speaker> speakers, int lastPage);

    void onSpeakersLoadedFailure();
  }
}
