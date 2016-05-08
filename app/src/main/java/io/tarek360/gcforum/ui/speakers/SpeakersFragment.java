package io.tarek360.gcforum.ui.speakers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Speaker;
import io.tarek360.gcforum.ui.base.BaseFragment;
import io.tarek360.gcforum.ui.customViews.EndlessRecyclerViewScrollListener;
import io.tarek360.gcforum.util.UIHelper;
import io.tarek360.gcforum.util.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class SpeakersFragment extends BaseFragment implements SpeakersContract.View {
  private static final String TAG = SpeakersFragment.class.getSimpleName();

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.recycler_view) RecyclerView rvProducts;
  @Bind(R.id.tvMessage) TextView tvMessage;
  @Bind(R.id.toolbar) Toolbar toolbar;

  private SpeakersContract.ActionsListener mActionsListener;

  private RVAdapter mRVAdapter;
  private DisplayImageOptions mOptions;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static SpeakersFragment newInstance(String title) {
    SpeakersFragment fragment = new SpeakersFragment();
    fragment.setTitle(title);
    //Bundle args = new Bundle();
    //args.putInt(ARG_CALL_TYPE, callType);
    //fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    setHasOptionsMenu(true);
    initUILOptions();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.i(TAG, "onCreateView");

    View rootView = inflater.inflate(R.layout.toolbar_appbar_coordinator_layout, container, false);

    ButterKnife.bind(this, rootView);

    toolbar.setTitle(getTitle());
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

    mActionsListener = new SpeakersPresenter(this, mNavigator);

    //mSpeakers = new EventDay();
    //mSpeakers.setsponsors(new ArrayList<>());

    initRV();

    mActionsListener.loadSpeakers();

    swipeContainer.setOnRefreshListener(mActionsListener::refreshSpeakers);
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light, android.R.color.holo_orange_light,
        android.R.color.holo_red_light);

    return rootView;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    Log.i(TAG, "onDestroyView");
    mActionsListener.onDestroyView();
    ButterKnife.unbind(this);
  }

  private void initRV() {
    rvProducts.setHasFixedSize(true);

    // Create adapter passing in the sample user data
    mRVAdapter = new RVAdapter(new ArrayList<>(), mActionsListener, mOptions);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    rvProducts.setLayoutManager(layoutManager);
    rvProducts.setAdapter(mRVAdapter);
    rvProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override public void onLoadMore() {
        mActionsListener.loadSpeakers();
        Log.i(TAG, "onLoadMore");
      }
    });
  }

  @Override public void showSwipeContainerIndicator(final boolean refreshing) {
    swipeContainer.post(() -> swipeContainer.setRefreshing(refreshing));
  }

  @Override public void showSpeakers(boolean clearing, List<Speaker> speakers) {

    if (clearing) {
      mRVAdapter.setProducts(speakers);
    } else {
      mRVAdapter.addProducts(speakers);
    }
  }

  @Override public void showSnackBar(int resId) {
    UIHelper.showSnackbar(rvProducts, resId);
  }

  @Override public void showTVMessage(int resId) {
    tvMessage.setVisibility(View.VISIBLE);
    tvMessage.setText(resId);
  }

  @Override public void hideTVMessage() {
    tvMessage.setVisibility(View.INVISIBLE);
  }

  @Override public void openSponsorUrl(String url) {
    if (!Utils.openUrl(getActivity(), url)) {
      UIHelper.showOkDialog(getActivity(), R.string.error, R.string.error_invalid_url);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void initUILOptions() {

    mOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
        .cacheOnDisk(true)
        .considerExifParams(true)
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
        .bitmapConfig(Bitmap.Config.ARGB_8888)
        .showImageOnLoading(R.drawable.ic_account)
        .showImageOnFail(R.drawable.ic_account)
        .showImageForEmptyUri(R.drawable.ic_account)
        .build();
  }
}
