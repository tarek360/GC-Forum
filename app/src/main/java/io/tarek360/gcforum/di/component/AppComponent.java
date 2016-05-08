package io.tarek360.gcforum.di.component;

import dagger.Component;
import io.tarek360.gcforum.di.modules.AppModule;
import io.tarek360.gcforum.di.modules.StorageModule;
import io.tarek360.gcforum.gcm.GcmMessageHandler;
import io.tarek360.gcforum.ui.base.BaseActivity;
import io.tarek360.gcforum.ui.base.BaseFragment;
import javax.inject.Singleton;

/**
 * Created by tarek on 12/22/15.
 */
@Singleton @Component(modules = { AppModule.class, StorageModule.class })
public interface AppComponent {

  void inject(BaseFragment baseFragment);

  void inject(BaseActivity baseActivity);

  void inject(GcmMessageHandler Service);
}
