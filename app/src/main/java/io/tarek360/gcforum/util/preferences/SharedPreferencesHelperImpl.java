package io.tarek360.gcforum.util.preferences;

import android.content.SharedPreferences;

/**
 * Created by tarek on 12/30/15.
 */
public class SharedPreferencesHelperImpl implements SharedPreferencesHelper {

  private final SharedPreferences mSharedPreferences;

  public SharedPreferencesHelperImpl(SharedPreferences sharedPreferences) {
    mSharedPreferences = sharedPreferences;
  }

  @Override public String getString(String key) {
    return mSharedPreferences.getString(key, "");
  }

  @Override public void setString(String key, String value) {
    mSharedPreferences.edit().putString(key, value).apply();
  }

  @Override public int getInt(String key) {
    return mSharedPreferences.getInt(key, 0);
  }

  @Override public void setInt(String key, int value) {
    mSharedPreferences.edit().putInt(key, value).apply();
  }

  @Override public boolean getBoolean(String key) {
    return mSharedPreferences.getBoolean(key, false);
  }

  @Override public void setBoolean(String key, boolean value) {
    mSharedPreferences.edit().putBoolean(key, value).apply();
  }
}
