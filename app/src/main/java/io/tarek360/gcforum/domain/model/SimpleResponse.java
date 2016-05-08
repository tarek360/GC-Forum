package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 2/17/16.
 */
public class SimpleResponse {

  @SerializedName("success") @Expose private Boolean success;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }
}
