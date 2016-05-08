package io.tarek360.gcforum.domain.model;

import android.text.Html;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by tarek on 4/5/16.
 */
public class Session {

  @SerializedName("id") @Expose String id;
  @SerializedName("day") @Expose String day;
  @SerializedName("title") @Expose String title;
  @SerializedName("subtitle") @Expose String subtitle;
  @SerializedName("start_time") @Expose long startTime;
  @SerializedName("end_time") @Expose long endTime;
  @SerializedName("type") @Expose String type;
  @SerializedName("details") @Expose String details;
  private String formattedStartTime;
  private String formattedEndTime;
  private String formattedStartEndTime;

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getFormattedStartTime() {

    if (formattedStartTime == null) {
      SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("h:mm a");
      SimpleDateFormat.setTimeZone(TimeZone.getDefault());
      formattedStartTime = SimpleDateFormat.format(startTime * 1000);
    }
    return formattedStartTime;
  }

  public String getFormattedEndTime() {

    if (formattedEndTime == null) {
      SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("h:mm a");
      SimpleDateFormat.setTimeZone(TimeZone.getDefault());
      formattedEndTime = SimpleDateFormat.format(endTime * 1000);
    }
    return formattedEndTime;
  }

  public String getFormattedStartEndTime() {
    if (formattedStartEndTime == null) {
      formattedStartEndTime = getFormattedStartTime() + " - " + getFormattedEndTime();
    }
    return formattedStartEndTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CharSequence getDetails() {
    if (details != null) {
      return Html.fromHtml(details);
    }
    return null;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
