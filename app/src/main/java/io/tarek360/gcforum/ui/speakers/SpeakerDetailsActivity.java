package io.tarek360.gcforum.ui.speakers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Speaker;
import io.tarek360.gcforum.ui.base.BaseActivity;
import org.parceler.Parcels;

/**
 * Created by tarek on 4/21/16.
 */
public class SpeakerDetailsActivity extends BaseActivity {

  private final static String KEY_SPEAKER = "speaker";
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.imageView) ImageView imageView;
  @Bind(R.id.name) TextView name;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.bio) TextView bio;
  private Speaker mSpeaker;
  private DisplayImageOptions mOptions;

  public static Intent buildIntent(Context context, Speaker speaker) {
    Intent intent = new Intent(context, SpeakerDetailsActivity.class);
    intent.putExtra(KEY_SPEAKER, Parcels.wrap(speaker));
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_speaker_details);

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    initUILOptions();

    mSpeaker = Parcels.unwrap(getIntent().getParcelableExtra(KEY_SPEAKER));

    ImageLoader.getInstance().displayImage(mSpeaker.getImage(), imageView, mOptions);
    title.setText(mSpeaker.getTitle());
    name.setText(mSpeaker.getName());
    bio.setText(mSpeaker.getBio());
  }

  private void initUILOptions() {

    mOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
        .considerExifParams(true)
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
        .bitmapConfig(Bitmap.Config.ARGB_8888)
        .showImageOnLoading(R.drawable.ic_account)
        .showImageOnFail(R.drawable.ic_account)
        .showImageForEmptyUri(R.drawable.ic_account)
        .build();
  }
}