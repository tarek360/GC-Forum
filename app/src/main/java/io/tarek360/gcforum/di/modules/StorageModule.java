package io.tarek360.gcforum.di.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelper;
import io.tarek360.gcforum.util.preferences.SharedPreferencesHelperImpl;
import javax.inject.Singleton;

/**
 * Created by tarek on 12/22/15.
 */
@Module public class StorageModule {

  @Provides @Singleton SharedPreferences provideSharedPreferences(App app) {
    return PreferenceManager.getDefaultSharedPreferences(app);
  }

  @Provides @Singleton SharedPreferencesHelper provideStorageHelper(
      SharedPreferences sharedPreferences) {
    return new SharedPreferencesHelperImpl(sharedPreferences);
  }
}
