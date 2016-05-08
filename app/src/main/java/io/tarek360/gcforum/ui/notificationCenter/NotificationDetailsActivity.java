package io.tarek360.gcforum.ui.notificationCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.ui.base.BaseActivity;
import org.parceler.Parcels;

/**
 * Created by tarek on 4/21/16.
 */
public class NotificationDetailsActivity extends BaseActivity {

  private final static String KEY_NOTIFICATION = "notification";
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.timeLine) View timeLine;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.time) TextView time;
  @Bind(R.id.body) TextView body;
  private Notification mNotification;

  public static Intent buildIntent(Context context, Notification notification) {
    Intent intent = new Intent(context, NotificationDetailsActivity.class);
    intent.putExtra(KEY_NOTIFICATION, Parcels.wrap(notification));
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notification_details);

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    timeLine.setVisibility(View.INVISIBLE);

    mNotification = Parcels.unwrap(getIntent().getParcelableExtra(KEY_NOTIFICATION));

    title.setText(mNotification.getTitle());
    time.setText(mNotification.getFormattedTimeAgo());
    body.setText(mNotification.getBody());
  }
}