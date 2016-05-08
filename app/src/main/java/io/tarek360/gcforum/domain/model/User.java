package io.tarek360.gcforum.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.tarek360.gcforum.data.rest.ServiceGenerator;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;

/**
 * Created by tarek on 2/17/16.
 */
public class User {

  public static final String FLAG_USER_TOKEN_DEFAULT = "user_token_ignore_last";
  public static final String FLAG_USER_TOKEN_FACEBOOK = "user_token_facebook";
  public static final String FLAG_USER_FULL_NAME = "name";
  public static final String FLAG_USER_EMAIL = "email";
  public static final String FLAG_USER_PASSWORD = "password";
  public static final String FLAG_USER_TITLE = "title";
  public static final String FLAG_USER_COMPANY = "company";
  public static final String FLAG_USER_ID = "user_id";
  public static final String FLAG_USER_FACEBOOK_DATA = "facebook_data";

  @SerializedName("access_token") @Expose String faceBookAccessToken;
  @SerializedName("gcm_id") @Expose String gcmId;
  @SerializedName("device") @Expose String device = "android";

  @SerializedName(FLAG_USER_ID) @Expose String id;
  @SerializedName(FLAG_USER_FULL_NAME) @Expose String fullName;
  @SerializedName(FLAG_USER_TITLE) @Expose String jobTitle;
  @SerializedName(FLAG_USER_COMPANY) @Expose String company;
  @SerializedName(FLAG_USER_EMAIL) @Expose String email;
  @SerializedName(FLAG_USER_PASSWORD) @Expose String password;
  @SerializedName("image_url") @Expose String imageUrl;
  private boolean selected;

  public User() {

  }

  public User(String faceBookAccessToken, String gcmId) {
    this.faceBookAccessToken = faceBookAccessToken;
    this.gcmId = gcmId;
  }

  public static String getUserPhotoUrl(String userId) {
    StringBuilder url = new StringBuilder(ServiceGenerator.API_BASE_URL);
    url.append("user/image?user_id");
    url.append("=");
    url.append(userId);
    return url.toString();
  }

  public static String getMyPhotoUrl(SharedPreferencesHelper sharedPreferencesHelper) {
    return User.getUserPhotoUrl(sharedPreferencesHelper.getString(FLAG_USER_ID));
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setGcmId(String gcmId) {
    this.gcmId = gcmId;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
