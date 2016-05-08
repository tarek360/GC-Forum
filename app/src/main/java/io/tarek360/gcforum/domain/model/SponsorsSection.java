package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by tarek on 4/9/16.
 */
public class SponsorsSection {

  @SerializedName("title") @Expose String title;
  @SerializedName("sponsors") @Expose List<Sponsor> sponsors;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Sponsor> getSponsors() {
    return sponsors;
  }

  public void setSponsors(List<Sponsor> sponsors) {
    this.sponsors = sponsors;
  }
}
