package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 4/17/16.
 */
public class GCM {

  @SerializedName("gcm_id") @Expose String gcmId;
  @SerializedName("device") @Expose String device = "android";

  public void setGcmId(String gcmId) {
    this.gcmId = gcmId;
  }
}
