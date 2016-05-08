package io.tarek360.gcforum.di.modules;

import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.gcm.NotificationModel;
import io.tarek360.gcforum.util.Navigator;
import javax.inject.Singleton;

/**
 * Created by tarek on 12/22/15.
 */
@Module public class AppModule {

  @NonNull private final App app;

  public AppModule(@NonNull App app) {
    this.app = app;
  }

  @NonNull @Singleton @Provides public App providesApp() {
    return app;
  }

  @Singleton @Provides public Navigator providesNavigator() {
    return new Navigator();
  }

  @Singleton @Provides NotificationModel provideNotificationModel() {
    return new NotificationModel();
  }
}
