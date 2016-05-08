package io.tarek360.gcforum.ui.sponsors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.SponsorsSection;
import java.util.List;

/**
 * Created by tarek on 4/5/16.
 */
public class RVAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

  // Store a member variable for the sponsors sections
  private List<SponsorsSection> mSponsorsSections;
  // Actions Listener
  private SponsorsContract.ActionsListener mActionsListener;
  private DisplayImageOptions mOptions;

  // Pass in the contact array into the constructor
  public RVAdapter(List<SponsorsSection> sponsorsSections,
      SponsorsContract.ActionsListener actionsListener, DisplayImageOptions options) {
    mSponsorsSections = sponsorsSections;
    mActionsListener = actionsListener;
    mOptions = options;
  }

  @Override public int getItemViewType(int section, int relativePosition, int absolutePosition) {
    //if (section == 1) {
    //  return 0; // VIEW_TYPE_HEADER is -2, VIEW_TYPE_ITEM is -1. You can return 0 or greater.
    //}
    return super.getItemViewType(section, relativePosition, absolutePosition);
  }

  // Usually involves inflating a layout from XML and returning the holder
  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    switch (viewType) {
      case VIEW_TYPE_HEADER:
        return new SectionViewHolder(
            inflater.inflate(R.layout.sponsors_section_item_header, parent, false));
      case VIEW_TYPE_ITEM:
        return new SponsorViewHolder(inflater.inflate(R.layout.sponsor_item, parent, false));
    }

    return null;
  }

  @Override public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int section) {
    SectionViewHolder sectionViewHolder = (SectionViewHolder) viewHolder;
    sectionViewHolder.title.setText(mSponsorsSections.get(section).getTitle());
  }

  // Involves populating data into the item through holder
  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int section,
      int relativePosition, int absolutePosition) {
    // Get the data model based on position
    SponsorViewHolder sponsorViewHolder = (SponsorViewHolder) viewHolder;

    ImageLoader.getInstance()
        .displayImage(mSponsorsSections.get(section).getSponsors().get(relativePosition).getImage(),
            sponsorViewHolder.imageView, mOptions);

    sponsorViewHolder.imageView.setOnClickListener(view -> mActionsListener.openSponsorUrl(
        mSponsorsSections.get(section).getSponsors().get(relativePosition).getUrl()));
  }

  @Override public int getSectionCount() {
    return mSponsorsSections.size();
  }

  @Override public int getItemCount(int section) {
    return mSponsorsSections.get(section).getSponsors().size();
  }

  public void clear() {
    mSponsorsSections.clear();
    notifyDataSetChanged();
  }

  // Set a list of items
  public void setProducts(List<SponsorsSection> sponsorsSections) {
    mSponsorsSections.clear();
    mSponsorsSections.addAll(sponsorsSections);
    notifyDataSetChanged();
  }

  // Add a list of items
  public void addProducts(List<SponsorsSection> sponsorsSections) {
    int positionStart = getItemCount();
    mSponsorsSections.addAll(sponsorsSections);
    notifyItemRangeInserted(positionStart, sponsorsSections.size());
  }

  public class SectionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title) TextView title;

    public SectionViewHolder(View itemView) {

      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }

  public class SponsorViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.imageView) ImageView imageView;

    public SponsorViewHolder(View itemView) {

      super(itemView);

      ButterKnife.bind(this, itemView);
    }
  }
}