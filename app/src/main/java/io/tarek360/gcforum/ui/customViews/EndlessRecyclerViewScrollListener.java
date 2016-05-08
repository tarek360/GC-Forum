package io.tarek360.gcforum.ui.customViews;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by tarek on 1/30/16.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
  RecyclerView.LayoutManager mLayoutManager;
  // The minimum amount of items to have below your current scroll position
  // before loading more.
  private int visibleThreshold = 5;

  public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
  }

  public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
  }

  public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
    visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
  }

  public int getLastVisibleItem(int[] lastVisibleItemPositions) {
    int maxSize = 0;
    for (int i = 0; i < lastVisibleItemPositions.length; i++) {
      if (i == 0) {
        maxSize = lastVisibleItemPositions[i];
      } else if (lastVisibleItemPositions[i] > maxSize) {
        maxSize = lastVisibleItemPositions[i];
      }
    }
    return maxSize;
  }

  // This happens many times a second during a scroll, so be wary of the code you place here.
  // We are given a few useful parameters to help us work out if we need to load some more data,
  // but first we check if we are waiting for the previous load to finish.
  @Override public void onScrolled(RecyclerView view, int dx, int dy) {
    int lastVisibleItemPosition = 0;
    int totalItemCount = mLayoutManager.getItemCount();

    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
      int[] lastVisibleItemPositions =
          ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
      // get maximum element within the list
      lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
    } else if (mLayoutManager instanceof LinearLayoutManager) {
      lastVisibleItemPosition =
          ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    } else if (mLayoutManager instanceof GridLayoutManager) {
      lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    }

    // If it isn’t currently loading, we check to see if we have breached
    // the visibleThreshold and need to reload more data.
    // If we do need to reload some more data, we execute onLoadMore to fetch the data.
    // threshold should reflect how many total columns there are too
    if ((lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
      onLoadMore();
    }
  }

  // Defines the process for actually loading more data based on page
  public abstract void onLoadMore();
}