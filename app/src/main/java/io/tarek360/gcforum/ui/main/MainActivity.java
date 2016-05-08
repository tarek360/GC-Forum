package io.tarek360.gcforum.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.mikepenz.fastadapter.IExpandable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.User;
import io.tarek360.gcforum.gcm.GcmPresenter;
import io.tarek360.gcforum.ui.base.BaseActivity;
import io.tarek360.gcforum.util.UIHelper;

public class MainActivity extends BaseActivity {

  private static final int PROFILE_SETTING = 1;

  // DRAWER ITEM IDENTIFIERS
  private static final int DRAWER_ITEM_IDENTIFIER_HOME = 0;
  private static final int DRAWER_ITEM_IDENTIFIER_SCHEDULE = 1;
  private static final int DRAWER_ITEM_IDENTIFIER_SPEAKERS = 2;
  private static final int DRAWER_ITEM_IDENTIFIER_CHAT = 3;
  private static final int DRAWER_ITEM_IDENTIFIER_SPONSORS = 4;
  private static final int DRAWER_ITEM_IDENTIFIER_LOCATION = 5;
  private static final int DRAWER_ITEM_IDENTIFIER_NOTIFICATIONS = 6;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.app_bar) AppBarLayout appbarLayout;
  IProfile profile;
  //save our header or result
  private AccountHeader headerResult = null;
  private Drawer result = null;
  private DisplayImageOptions options;

  public static Intent buildIntent(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    initHeader();
    initDrawerMenu(savedInstanceState);

    refreshGCMToken();
  }

  private void initHeader() {
    initUILOptions();

    ImageView imageView = (ImageView) findViewById(R.id.imageView);
    String bgPath = "drawable://" + R.drawable.home_header_bg2;
    ImageLoader.getInstance().displayImage(bgPath, imageView, options);
  }

  private void initUILOptions() {
    options = new DisplayImageOptions.Builder().cacheInMemory(true)
        .cacheOnDisk(true)
        .considerExifParams(true)
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
        .bitmapConfig(Bitmap.Config.ARGB_8888)
        .build();
  }

  private void refreshGCMToken() {
    // GCM
    new GcmPresenter(this, mSharedPreferencesHelper).startRegisterGcm();
  }

  private void initDrawerMenu(Bundle savedInstanceState) {
    initDrawerMenuProfile();
    initDrawerMenuHeader(savedInstanceState);

    //Create the drawer
    result = new DrawerBuilder().withActivity(this)
        //.withAccountHeader(headerResult)
        //.withHeaderPadding(true)
        .withSliderBackgroundColorRes(R.color.md_grey_50)
        //.withActionBarDrawerToggle(true)
        .withToolbar(toolbar)
        .withDisplayBelowStatusBar(false)
        .withActionBarDrawerToggleAnimated(true)
        .withRootView(R.id.drawer_layout)

        //set the AccountHeader we created earlier for the header
        .addDrawerItems(

            new PrimaryDrawerItem().withName(R.string.drawer_item_home)
                .withIcon(
                    new IconDrawable(this, MaterialIcons.md_home).colorRes(R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_HOME),

            new PrimaryDrawerItem().withName(R.string.drawer_item_agenda)
                .withSelectable(false)
                .withIcon(
                    new IconDrawable(this, MaterialCommunityIcons.mdi_calendar_multiple).colorRes(
                        R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_SCHEDULE),

            new PrimaryDrawerItem().withName(R.string.drawer_item_speakers)
                .withSelectable(false)
                .withIcon(
                    new IconDrawable(this, MaterialCommunityIcons.mdi_account_multiple).colorRes(
                        R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_SPEAKERS),

            new PrimaryDrawerItem().withName(R.string.drawer_item_sponsors)
                .withSelectable(false)
                .withIcon(
                    new IconDrawable(this, MaterialCommunityIcons.mdi_square_inc_cash).colorRes(
                        R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_SPONSORS),

            new PrimaryDrawerItem().withName(R.string.drawer_item_location)
                .withSelectable(false)
                .withIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).colorRes(
                    R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_LOCATION),

            new DividerDrawerItem(),

            new PrimaryDrawerItem().withName(R.string.drawer_item_chat)
                .withSelectable(false)
                .withIcon(
                    new IconDrawable(this, MaterialIcons.md_comment).colorRes(R.color.md_grey_600))
                .withIdentifier(DRAWER_ITEM_IDENTIFIER_CHAT))

        .withOnDrawerItemClickListener((view, position, drawerItem) -> {

          if (drawerItem != null) {

            int identifier = (int) drawerItem.getIdentifier();

            //if our drawer has collapsible items we check if the clicked items has subItem. if yes we open it
            if (((IExpandable) drawerItem).getSubItems() != null) {
              result.getAdapter().toggleExpandable(position);
              //we consume the event and want no further handling
              return true;
            }

            switch (identifier) {

              case DRAWER_ITEM_IDENTIFIER_SCHEDULE:
                mNavigator.navigateToSessionsActivity();
                break;

              case DRAWER_ITEM_IDENTIFIER_SPEAKERS:
                mNavigator.navigateToSpeakersActivity();

                break;

              case DRAWER_ITEM_IDENTIFIER_CHAT:

                mNavigator.navigateToChatActivity();

                break;

              case DRAWER_ITEM_IDENTIFIER_SPONSORS:
                mNavigator.navigateToSponsorsActivity();

                break;

              case DRAWER_ITEM_IDENTIFIER_LOCATION:
                mNavigator.navigateToMapActivity();
                break;
            }

            if (drawerItem instanceof Nameable) {
              //String title = ((Nameable) drawerItem).getName().getText(ChatActivity.this);
              //toolbar.setTitle(title);
            }
          }

          return false;
        })
        .withSavedInstance(savedInstanceState)
        .build();

    // set the selection to the item with the identifier 5
    if (savedInstanceState == null) {
      result.setSelection(0);
    } else {
      //result.setSelection(savedInstanceState.getLastSelection, false);
    }
  }

  private void initDrawerMenuHeader(Bundle savedInstanceState) {
    // Create the AccountHeader
    headerResult = new AccountHeaderBuilder().withActivity(this)
        .withCompactStyle(false)
        .withSelectionListEnabledForSingleProfile(false)
        .withHeaderBackground(R.drawable.nav_header_bg)
        //.addProfiles(profile)

        .withOnAccountHeaderListener((view, profile1, current) -> {
          //sample usage of the onProfileChanged listener
          //if the clicked item has the identifier 1
          if (profile1 instanceof IDrawerItem) {

          }

          //false if you have not consumed the event and it should close the drawer
          return false;
        })
        .withSavedInstance(savedInstanceState)
        .build();
  }

  private void initDrawerMenuProfile() {

    String name = mSharedPreferencesHelper.getString(User.FLAG_USER_FULL_NAME);
    String email = mSharedPreferencesHelper.getString(User.FLAG_USER_EMAIL);
    String userPhotoUrl = User.getMyPhotoUrl(mSharedPreferencesHelper);
    // Create a profile
    profile = new ProfileDrawerItem().withName(name)
        .withEmail(email)
        .withIcon(userPhotoUrl)
        .withIdentifier(0);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);

    menu.findItem(R.id.action_notification)
        .setIcon(
            new IconDrawable(this, MaterialIcons.md_notifications).colorRes(R.color.md_white_1000)
                .actionBarSize());

    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.

    switch (item.getItemId()) {

      case R.id.action_settings:

        return true;

      case R.id.action_notification:
        mNavigator.navigateToNotificationsActivity();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the drawer to the bundle
    outState = result.saveInstanceState(outState);
    //add the values which need to be saved from the accountHeader to the bundle
    outState = headerResult.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override public void onBackPressed() {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (result != null && result.isDrawerOpen()) {
      result.closeDrawer();
    } else {
      super.onBackPressed();
    }
  }

  @OnClick(R.id.toolbar) public void onToolBarClick() {
    UIHelper.showAppInfoDialog(this);
  }
}

