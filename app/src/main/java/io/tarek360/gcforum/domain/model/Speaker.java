package io.tarek360.gcforum.domain.model;

import android.text.Html;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by tarek on 4/9/16.
 */
@Parcel public class Speaker {

  @SerializedName("id") @Expose String id;
  @SerializedName("name") @Expose String name;
  @SerializedName("title") @Expose String title;
  @SerializedName("bio") @Expose String bio;
  @SerializedName("image_url") @Expose String image;

  public CharSequence getBio() {
    if (bio != null) {
      return Html.fromHtml(bio);
    }
    return null;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
