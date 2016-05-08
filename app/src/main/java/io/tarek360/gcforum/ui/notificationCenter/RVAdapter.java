package io.tarek360.gcforum.ui.notificationCenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.joanzapata.iconify.widget.IconTextView;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Notification;
import java.util.List;

/**
 * Created by tarek on 4/20/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

  // Store a member variable for the contacts
  private List<Notification> mNotifications;
  // Actions Listener
  private NotificationsContract.ActionsListener mActionsListener;

  // Pass in the contact array into the constructor
  public RVAdapter(List<Notification> notifications,
      NotificationsContract.ActionsListener actionsListener) {
    mNotifications = notifications;
    mActionsListener = actionsListener;
  }

  // Usually involves inflating a layout from XML and returning the holder
  @Override public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate the custom layout
    View contactView = inflater.inflate(R.layout.notification_item, parent, false);

    // Return a new holder instance
    ViewHolder viewHolder = new ViewHolder(contactView);
    return viewHolder;
  }

  // Involves populating data into the item through holder
  @Override public void onBindViewHolder(RVAdapter.ViewHolder viewHolder, int position) {
    // Get the data model based on position
    Notification notification = mNotifications.get(position);

    // Set item views based on the data model
    viewHolder.title.setText(notification.getTitle());
    viewHolder.time.setText(notification.getFormattedTimeAgo());

    String iconText = "{md-notifications}";
    int textColorRes = R.color.md_grey_600;

    viewHolder.icon.setText(iconText);
    viewHolder.icon.setTextColor(viewHolder.icon.getResources().getColor(textColorRes));
  }

  // Return the total count of items
  @Override public int getItemCount() {
    return mNotifications.size();
  }

  public void clear() {
    mNotifications.clear();
    notifyDataSetChanged();
  }

  // Set a list of items
  public void setProducts(List<Notification> notifications) {
    mNotifications.clear();
    mNotifications.addAll(notifications);
    notifyDataSetChanged();
  }

  // Add a list of items
  public void addProducts(List<Notification> notifications) {
    int positionStart = getItemCount();
    mNotifications.addAll(notifications);
    notifyItemRangeInserted(positionStart, notifications.size());
  }

  // Provide a direct reference to each of the views within a data item
  // Used to cache the views within the item layout for fast access
  public class ViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    @Bind(R.id.title) TextView title;
    @Bind(R.id.time) TextView time;
    @Bind(R.id.icon) IconTextView icon;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the context from any ViewHolder instance.
      super(itemView);

      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.notification_item) public void onItemClick() {
      mActionsListener.openNotification(mNotifications.get(getLayoutPosition()));
    }
  }
}