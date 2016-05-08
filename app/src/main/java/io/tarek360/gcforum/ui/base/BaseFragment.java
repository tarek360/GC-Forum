package io.tarek360.gcforum.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.util.Navigator;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import javax.inject.Inject;

/**
 * Created by tarek on 1/25/16.
 */
public class BaseFragment extends Fragment {

  private static String title;
  @Inject public Navigator mNavigator;
  @Inject public SharedPreferencesHelper mSharedPreferencesHelper;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((App) getActivity().getApplication()).getAppComponent().inject(this);
    mNavigator.setContext(getActivity());
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
