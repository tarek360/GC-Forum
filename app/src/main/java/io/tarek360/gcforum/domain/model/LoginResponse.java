package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 2/17/16.
 */
public class LoginResponse extends SimpleResponse {

  public static final int ERROR_EMAIL_TAKEN = 1;

  @SerializedName("token") @Expose private String token;
  @SerializedName(User.FLAG_USER_ID) @Expose private String userId;
  @SerializedName("server_only_error") @Expose private int serverOnlyError;

  public String getUserId() {
    return userId;
  }

  public boolean isEmailTaken() {
    return serverOnlyError == ERROR_EMAIL_TAKEN;
  }

  public void setServerOnlyError(int serverOnlyError) {
    this.serverOnlyError = serverOnlyError;
  }

  public String getToken() {

    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
