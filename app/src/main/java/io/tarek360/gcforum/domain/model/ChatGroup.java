package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashSet;
import java.util.Set;
import org.parceler.Parcel;

/**
 * Created by tarek on 4/5/16.
 */
@Parcel public class ChatGroup {

  @SerializedName("id") @Expose String id;
  @SerializedName("owner_id") @Expose String ownerId;
  @SerializedName("name") @Expose String name;
  @SerializedName("last_message") @Expose String lastMessage;
  @SerializedName("group_image") @Expose String groupImage;
  @SerializedName("members_num") @Expose int membersCount;
  @SerializedName("members") @Expose Set<String> membersIds = new HashSet<>();

  public int getMembersCount() {
    return membersCount;
  }

  public void setMembersCount(int membersCount) {
    this.membersCount = membersCount;
  }

  public String getGroupImage() {
    return groupImage;
  }

  public void setGroupImage(String groupImage) {
    this.groupImage = groupImage;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getMembersIds() {
    return membersIds;
  }

  public void setMembersIds(Set<String> membersIds) {
    this.membersIds = membersIds;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }
}
