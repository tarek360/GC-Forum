package io.tarek360.gcforum.ui.sessions;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.EventDay;
import io.tarek360.gcforum.ui.base.BaseFragment;
import io.tarek360.gcforum.util.UIHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class SessionsFragment extends BaseFragment implements SessionsContract.View {
  private static final String TAG = SessionsFragment.class.getSimpleName();

  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
  @Bind(R.id.recycler_view) RecyclerView rvProducts;
  @Bind(R.id.tvMessage) TextView tvMessage;
  @Bind(R.id.toolbar) Toolbar toolbar;

  private SessionsContract.ActionsListener mActionsListener;

  private EventDay mEventDay;
  private RVAdapter mRVAdapter;

  private int eventDayIndex = 1;

  /**
   * Returns a new instance of this fragment for the given section
   * number.
   */
  public static SessionsFragment newInstance(String title) {
    SessionsFragment fragment = new SessionsFragment();
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

    toolbar.setTitle("");
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

    addItemsToSpinner();

    mActionsListener = new SessionsPresenter(this);

    mEventDay = new EventDay();
    mEventDay.setSessions(new ArrayList<>());

    initRV();

    swipeContainer.setOnRefreshListener(() -> {
      // Your code to refresh the list here.
      // Make sure you call swipeContainer.setRefreshing(false)
      // once the network request has completed successfully.
      mActionsListener.loadEventDay(eventDayIndex);
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
    mRVAdapter = new RVAdapter(mEventDay.getSessions(), mActionsListener);
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

  @Override public void showEventDay(EventDay eventDay) {

    mRVAdapter.setProducts(eventDay.getSessions());
    rvProducts.scrollToPosition(0);
    Log.i(TAG, "showEventDay .getItemCount() " + mRVAdapter.getItemCount());
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

  // add items into spinner dynamically
  public void addItemsToSpinner() {

    View spinnerContainer =
        LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_spinner, toolbar, false);
    ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    toolbar.addView(spinnerContainer, lp);

    ArrayList<String> days = new ArrayList<>();
    days.add(getString(R.string.schedule_spinner_day_1));
    days.add(getString(R.string.schedule_spinner_day_2));

    DaySpinnerAdapter spinnerAdapter = new DaySpinnerAdapter();
    spinnerAdapter.addItems(days);

    Spinner spinnerDay = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);

    spinnerDay.setAdapter(spinnerAdapter);

    spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
        eventDayIndex = position + 1;
        mActionsListener.loadEventDay(eventDayIndex);
      }

      @Override public void onNothingSelected(AdapterView<?> arg0) {

      }
    });
  }

  private class DaySpinnerAdapter extends BaseAdapter {
    private List<String> mItems = new ArrayList<>();

    public void clear() {
      mItems.clear();
    }

    public void addItem(String title) {
      mItems.add(title);
    }

    public void addItems(List<String> titles) {
      mItems.addAll(titles);
    }

    @Override public int getCount() {
      return mItems.size();
    }

    @Override public Object getItem(int position) {
      return mItems.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getDropDownView(int position, View view, ViewGroup parent) {
      if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
        view = getActivity().getLayoutInflater()
            .inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);
        view.setTag("DROPDOWN");
      }

      TextView textView = (TextView) view.findViewById(android.R.id.text1);
      textView.setText(getTitle(position));

      return view;
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
      if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
        view = getActivity().getLayoutInflater().inflate(R.layout.
            toolbar_spinner_item_actionbar, parent, false);
        view.setTag("NON_DROPDOWN");
      }
      TextView textView = (TextView) view.findViewById(android.R.id.text1);
      textView.setText(getTitle(position));
      return view;
    }

    private String getTitle(int position) {
      return position >= 0 && position < mItems.size() ? mItems.get(position) : "";
    }
  }
}
