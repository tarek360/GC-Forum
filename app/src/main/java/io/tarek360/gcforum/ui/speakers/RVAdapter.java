package io.tarek360.gcforum.ui.speakers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Speaker;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

  // Store a member variable for the sponsors sections
  private List<Speaker> mSpeakers;
  // Actions Listener
  private SpeakersContract.ActionsListener mActionsListener;
  private DisplayImageOptions mOptions;

  // Pass in the contact array into the constructor
  public RVAdapter(List<Speaker> speakers, SpeakersContract.ActionsListener actionsListener,
      DisplayImageOptions options) {
    mSpeakers = speakers;
    mActionsListener = actionsListener;
    mOptions = options;
  }

  // Usually involves inflating a layout from XML and returning the holder
  @Override public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new ViewHolder(inflater.inflate(R.layout.speaker_item, parent, false));
  }

  @Override public void onBindViewHolder(RVAdapter.ViewHolder viewHolder, int position) {

    // Get the data model based on position
    Speaker speaker = mSpeakers.get(position);

    // Set item views based on the data model
    viewHolder.name.setText(speaker.getName());
    viewHolder.title.setText(speaker.getTitle());
    ImageLoader.getInstance().displayImage(speaker.getImage(), viewHolder.imageView, mOptions);
  }

  @Override public int getItemCount() {
    return mSpeakers.size();
  }

  public void clear() {
    mSpeakers.clear();
    notifyDataSetChanged();
  }

  // Set a list of items
  public void setProducts(List<Speaker> speakers) {
    mSpeakers.clear();
    mSpeakers.addAll(speakers);
    notifyDataSetChanged();
  }

  // Add a list of items
  public void addProducts(List<Speaker> speakers) {
    int positionStart = getItemCount();
    mSpeakers.addAll(speakers);
    notifyItemRangeInserted(positionStart, mSpeakers.size());
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.imageView) ImageView imageView;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.title) TextView title;

    public ViewHolder(View itemView) {

      super(itemView);

      ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.speaker_item) public void onItemClick() {
      mActionsListener.openSpeaker(mSpeakers.get(getLayoutPosition()));
    }
  }
}