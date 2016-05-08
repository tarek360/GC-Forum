package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 2/17/16.
 */
public class ChatGroupCreationResponse extends SimpleResponse {

  @SerializedName("group_id") @Expose private String groupId;

  public String getGroupId() {
    return groupId;
  }
}