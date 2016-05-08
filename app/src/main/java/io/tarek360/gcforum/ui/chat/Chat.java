package io.tarek360.gcforum.ui.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

  @SerializedName("last_message") @Expose private String message;
  @SerializedName("last_sender_id") @Expose private String authorId;
  @SerializedName("last_sender_name") @Expose private String authorName;

  // Required default constructor for Firebase object mapping
  @SuppressWarnings("unused") private Chat() {
  }

  Chat(String message, String authorId, String authorName) {
    this.message = message;
    this.authorId = authorId;
    this.authorName = authorName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public String getMessage() {
    return message;
  }

  public String getAuthorId() {
    return authorId;
  }
}
