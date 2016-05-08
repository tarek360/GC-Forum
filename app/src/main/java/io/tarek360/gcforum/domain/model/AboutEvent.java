package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 2/17/16.
 */
public class AboutEvent {

  public static final String KEY_ABOUT_TITLE = "about-title";
  public static final String KEY_ABOUT_DESC = "about-desc";
  public static final String KEY_SPEAKERS_COUNT = "speakers_count";
  public static final String KEY_DISCUSSIONS_COUNT = "panels_count";

  @SerializedName(KEY_ABOUT_TITLE) @Expose String Title;
  @SerializedName(KEY_ABOUT_DESC) @Expose String description;
  @SerializedName(KEY_SPEAKERS_COUNT) @Expose int speakersCount;
  @SerializedName(KEY_DISCUSSIONS_COUNT) @Expose int discussionsCount;

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDiscussionsCount() {
    return discussionsCount;
  }

  public void setDiscussionsCount(int discussionsCount) {
    this.discussionsCount = discussionsCount;
  }

  public int getSpeakersCount() {
    return speakersCount;
  }

  public void setSpeakersCount(int speakersCount) {
    this.speakersCount = speakersCount;
  }
}
