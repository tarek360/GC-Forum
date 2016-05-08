package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tarek on 4/24/16.
 */
public class UserForgetPassword {

  public static final String FLAG_USER_EMAIL = "email";
  public static final String FLAG_USER_NEW_PASSWORD = "new_password";
  public static final String FLAG_USER_RESET_CODE = "reset_code";

  @SerializedName(FLAG_USER_EMAIL) @Expose String email;
  @SerializedName(FLAG_USER_NEW_PASSWORD) @Expose String newPassword;
  @SerializedName(FLAG_USER_RESET_CODE) @Expose String resetCode;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getResetCode() {
    return resetCode;
  }

  public void setResetCode(String resetCode) {
    this.resetCode = resetCode;
  }
}
