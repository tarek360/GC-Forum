package io.tarek360.gcforum.ui.chat;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * Created by tarek on 4/14/16.
 */
public class MyCustomLayoutManager extends LinearLayoutManager {
  private static final float MILLISECONDS_PER_INCH = 250f;
  private Context mContext;

  public MyCustomLayoutManager(Context context) {
    super(context);
    mContext = context;
  }

  @Override public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
      final int position) {

    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {

      //This controls the direction in which smoothScroll looks
      //for your view
      @Override public PointF computeScrollVectorForPosition(int targetPosition) {
        return MyCustomLayoutManager.this.computeScrollVectorForPosition(targetPosition);
      }

      //This returns the milliseconds it takes to
      //scroll one pixel.
      @Override protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
      }
    };

    smoothScroller.setTargetPosition(position);
    startSmoothScroll(smoothScroller);
  }
}
