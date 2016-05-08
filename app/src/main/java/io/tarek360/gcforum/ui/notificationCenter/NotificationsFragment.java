package io.tarek360.gcforum.ui.notificationCenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.ui.base.BaseFragment;
import io.tarek360.gcforum.util.UIHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 4/20/16.
 */
public class NotificationsFragment extends BaseFragment implements NotificationsContract.View {
  private static final String TAG = NotificationsFragment.class.getSimpleName();

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.recycler_view) RecyclerView rvProducts;
  @Bind(R.id.tvMessage) TextView tvMessage;
  @Bind(R.id.toolbar) Toolbar toolbar;

  private NotificationsContract.ActionsListener mActionsListener;

  private RVAdapter mRVAdapter;
  //Create a broadcast receiver to handle change in time
  BroadcastReceiver tickReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equalsIgnoreCase(Intent.ACTION_TIME_TICK)) {
        mRVAdapter.notifyDataSetChanged();
      }
    }
  };

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static NotificationsFragment newInstance(String title) {
    NotificationsFragment fragment = new NotificationsFragment();
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

    mActionsListener = new NotificationsPresenter(this, mNavigator);

    initRV();

    mActionsListener.loadNotifications();

    swipeContainer.setOnRefreshListener(() -> {
      // Your code to refresh the list here.
      // Make sure you call swipeContainer.setRefreshing(false)
      // once the network request has completed successfully.
      mActionsListener.loadNotifications();
    });
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
    mRVAdapter = new RVAdapter(new ArrayList<>(), mActionsListener);
    // Attach the adapter to the recyclerview to populate items
    rvProducts.setAdapter(mRVAdapter);

    // Create adapter passing in the sample user data
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    // Set layout manager to position the items
    rvProducts.setLayoutManager(layoutManager);
  }

  @Override public void showSwipeContainerIndicator(final boolean refreshing) {
    swipeContainer.post(() -> swipeContainer.setRefreshing(refreshing));
  }

  @Override public void showNotifications(List<Notification> notifications) {
    Log.i(TAG, "showNotifications .getItemCount() " + mRVAdapter.getItemCount());

    mRVAdapter.setProducts(notifications);
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

  @Override public void showContentDialog(CharSequence title, CharSequence content) {
    UIHelper.showContentDialog(getActivity(), title, content);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onResume() {
    super.onResume();
    //Register the broadcast receiver to receive TIME_TICK
    getActivity().registerReceiver(tickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
  }

  @Override public void onPause() {
    super.onPause();
    getActivity().unregisterReceiver(tickReceiver);
  }
}
