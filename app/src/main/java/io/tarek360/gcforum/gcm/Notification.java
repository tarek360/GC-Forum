package io.tarek360.gcforum.gcm;

/**
 * Created by tarek on 4/30/16.
 */
public class Notification {

  private String type;
  private String message;

  public Notification() {

  }

  public Notification(String type, String message) {
    this.type = type;
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
