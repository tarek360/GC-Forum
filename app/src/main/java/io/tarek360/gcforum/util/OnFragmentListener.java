package io.tarek360.gcforum.util;

import android.support.v4.app.Fragment;

/**
 * Created by tarek on 4/1/16.
 */
public interface OnFragmentListener {
  void addFragment(Fragment fragment);

  void replaceFragment(Fragment fragment);
}
