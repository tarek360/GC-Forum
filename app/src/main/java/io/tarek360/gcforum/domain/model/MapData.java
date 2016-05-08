package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 4/10/16.
 */
public class MapData {

  @SerializedName("title") @Expose private String title;
  @SerializedName("short_address") @Expose private String shortAddress;
  @SerializedName("latitude") @Expose private double latitude;
  @SerializedName("longitude") @Expose private double longitude;
  @SerializedName("phone_number") @Expose private String phoneNumber;

  public String getTitle() {
    return title;
  }

  public String getShortAddress() {
    return shortAddress;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
