package io.tarek360.gcforum.ui.map;

import io.tarek360.gcforum.domain.model.MapData;

/**
 * Created by tarek on 1/25/16.
 */
public interface MapContract {

  interface View {

    void showIndeterminateDialog();

    void dismissIndeterminateDialog();

    void showSnackBar(int resId);

    void showMap(MapData mapData);

    void showErrorDialog(int titleResId, int contentResId);
  }

  interface ActionsListener {

    void getMapData();

    void onDestroyView();
  }
}
