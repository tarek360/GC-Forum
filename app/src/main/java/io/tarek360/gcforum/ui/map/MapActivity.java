package io.tarek360.gcforum.ui.map;

/**
 * Created by tarek on 4/10/16.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.MapData;
import io.tarek360.gcforum.ui.base.BaseActivity;
import io.tarek360.gcforum.util.Logger;
import io.tarek360.gcforum.util.UIHelper;

public class MapActivity extends BaseActivity implements MapContract.View {

  @Bind(R.id.container) ViewGroup container;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.progressBar) ProgressBar progressBar;

  @Bind(R.id.mapLayout) ViewGroup mapLayout;
  @Bind(R.id.infoLayout) ViewGroup infoLayout;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.short_address) TextView shortAddress;
  @Bind(R.id.phone_number) TextView phoneNumber;

  private MapContract.ActionsListener mActionsListener;
  private SupportMapFragment mapFragment;
  private GoogleMap map;
  private MapData mMapData;

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, MapActivity.class);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_activity);

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

    mActionsListener = new MapPresenter(this, null);
    mActionsListener.getMapData();
  }

  protected void getMapAsync() {
    if (mapFragment != null) {
      mapFragment.getMapAsync(this::loadMap);
    } else {
      Toast.makeText(this, "Error - MapData Fragment was null!!", Toast.LENGTH_SHORT).show();
    }
  }

  protected void loadMap(GoogleMap googleMap) {
    map = googleMap;
    if (map != null) {
      // MapData is ready
      Logger.debug(this, "MapData Fragment was loaded properly!");

      addMapMarker();
    } else {
      Toast.makeText(this, "Error - MapData was null!!", Toast.LENGTH_SHORT).show();
    }
  }

  private void addMapMarker() {

    // Set the color of the marker to green
    BitmapDescriptor defaultMarker =
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
    // listingPosition is a LatLng point
    //30.608114, 32.308886
    LatLng latLng = new LatLng(mMapData.getLatitude(), mMapData.getLongitude());
    // Add marker on the map
    map.addMarker(new MarkerOptions().position(latLng)
        .title(mMapData.getTitle())
        .snippet(mMapData.getShortAddress())
        .icon(defaultMarker));

    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
    map.animateCamera(cameraUpdate);
  }

  @Override public void showIndeterminateDialog() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void dismissIndeterminateDialog() {
    progressBar.setVisibility(View.GONE);
  }

  @Override public void showSnackBar(int resId) {
    UIHelper.showSnackbar(container, resId);
  }

  @Override public void showMap(MapData mapData) {
    mMapData = mapData;
    mapLayout.setVisibility(View.VISIBLE);
    getMapAsync();
    showInfoLayout();
  }

  private void showInfoLayout() {

    infoLayout.setVisibility(View.VISIBLE);
    title.setText(mMapData.getTitle());
    shortAddress.setText(mMapData.getShortAddress());
    phoneNumber.setText(mMapData.getPhoneNumber());
  }

  @Override public void showErrorDialog(int titleResId, int contentResId) {
    UIHelper.showOkDialog(this, titleResId, contentResId).setOnDismissListener(dialog -> finish());
  }

  @OnClick({ R.id.phone_number, R.id.phone_number_icon }) public void onPhoneNumberClick() {

    UIHelper.showPhoneCallDialog(this, R.string.phone_call, R.string.phone_call_us_now)
        .onAny((dialog, which) -> {
          if (which == DialogAction.POSITIVE) {
            makePhoneCall();
          }
        });
  }

  public void makePhoneCall() {

    String phoneNumber = mMapData.getPhoneNumber();

    Logger.debug(this, "phoneNumber=" + phoneNumber);

    Uri number = Uri.parse("tel:" + phoneNumber);
    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
    try {

      startActivity(callIntent);
    } catch (ActivityNotFoundException e) {

    }
  }
}
