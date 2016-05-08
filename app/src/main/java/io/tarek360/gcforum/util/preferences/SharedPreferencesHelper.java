package io.tarek360.gcforum.util.preferences;

/**
 * Created by tarek on 12/22/15.
 */
public interface SharedPreferencesHelper {

  String getString(String key);

  void setString(String key, String value);

  int getInt(String key);

  void setInt(String key, int value);

  boolean getBoolean(String key);

  void setBoolean(String key, boolean value);
}
