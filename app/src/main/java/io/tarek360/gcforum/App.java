package io.tarek360.gcforum;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import io.tarek360.gcforum.di.component.AppComponent;
import io.tarek360.gcforum.di.component.DaggerAppComponent;
import io.tarek360.gcforum.di.modules.AppModule;
import io.tarek360.gcforum.di.modules.StorageModule;
import java.io.File;

/**
 * Created by tarek on 1/28/16.
 */
public class App extends Application {

  public static Typeface HELVETICANEUELT_ARABIC_75_BOLD;

  AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    //Fabric.with(this, new Crashlytics());
    Firebase.setAndroidContext(this);

    Iconify.with(new MaterialModule()).with(new MaterialCommunityModule());
    //TODO
    //HELVETICANEUELT_ARABIC_75_BOLD = Typeface.createFromAsset(getAssets(), "HELVETICANEUELT_ARABIC_75_BOLD.TTF");

    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule(this))
        .storageModule(new StorageModule())
        .build();

    //initialize Facebook SDK
    FacebookSdk.sdkInitialize(getApplicationContext());

    initImageLoader();
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  private void initImageLoader() {
    File cacheDir = StorageUtils.getCacheDirectory(this);
    ImageLoaderConfiguration config =
        new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480,
            800) // default = device screen dimensions
            .diskCacheExtraOptions(480, 800, null)
            .threadPoolSize(3) // default
            .threadPriority(Thread.NORM_PRIORITY - 2) // default
            .tasksProcessingOrder(QueueProcessingType.FIFO) // default
            .denyCacheImageMultipleSizesInMemory()
            .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
            .memoryCacheSize(2 * 1024 * 1024)
            .memoryCacheSizePercentage(13) // default
            .diskCache(new UnlimitedDiscCache(cacheDir)) // default
            .diskCacheSize(50 * 1024 * 1024)
            .diskCacheFileCount(100)
            .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
            .imageDownloader(new BaseImageDownloader(this)) // default
            .imageDecoder(new BaseImageDecoder(true)) // default
            .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
            .writeDebugLogs()
            .build();
    ImageLoader.getInstance().init(config);
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
