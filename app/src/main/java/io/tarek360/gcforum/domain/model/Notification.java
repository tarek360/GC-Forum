package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.tarek360.gcforum.util.Utils;
import org.parceler.Parcel;

/**
 * Created by tarek on 2/17/16.
 */
@Parcel public class Notification {

  public static final String FLAG_ID = "id";
  public static final String FLAG_TITLE = "title";
  public static final String FLAG_BODY = "body";
  public static final String FLAG_SENT_TIME = "time_sent";

  @SerializedName(FLAG_ID) @Expose String id;
  @SerializedName(FLAG_TITLE) @Expose String title;
  @SerializedName(FLAG_BODY) @Expose String body;
  @SerializedName(FLAG_SENT_TIME) @Expose long time;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getFormattedTimeAgo() {
    return Utils.getFormattedTimeAgo(time);
  }
}
