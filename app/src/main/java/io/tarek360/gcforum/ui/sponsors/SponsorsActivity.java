package io.tarek360.gcforum.ui.sponsors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.ui.base.BaseActivity;

public class SponsorsActivity extends BaseActivity {

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, SponsorsActivity.class);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_framelayout);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container,
            SponsorsFragment.newInstance(getString(R.string.drawer_item_sponsors)), "sponsors")
        .commit();
  }
}
