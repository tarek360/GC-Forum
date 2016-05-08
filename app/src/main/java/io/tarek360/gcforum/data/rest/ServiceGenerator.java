package io.tarek360.gcforum.data.rest;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

public class ServiceGenerator {

  // Base Url must end with "/"

  public static final String API_BASE_URL = "http://androidlab.byethost7.com/";

  private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

  private static LoginClient loginClient;
  private static FileUploadClient fileUploadClient;
  private static AboutEventClient aboutEventClient;
  private static SessionsClient sessionsClient;
  private static SponsorsClient sponsorsClient;
  private static SpeakersClient speakersClient;
  private static AttendanceClient attendanceClient;
  private static ContactUsClient contactUsClient;
  private static MapDataClient mapDataClient;
  private static ChatClient chatClient;
  private static GCMUpdateClient mGCMUpdateClient;
  private static NotificationsClient notificationsClient;

  public static <S> S createService(Class<S> serviceClass) {

    if (httpClient.interceptors().size() == 0) {
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      httpClient.addInterceptor(loggingInterceptor);
    }

    httpClient.readTimeout(60, TimeUnit.SECONDS);
    httpClient.connectTimeout(60, TimeUnit.SECONDS);

    Retrofit retrofit = builder.client(httpClient.build()).build();
    return retrofit.create(serviceClass);
  }

  public static LoginClient getLoginClient() {
    if (loginClient == null) {
      loginClient = ServiceGenerator.createService(LoginClient.class);
    }
    return loginClient;
  }

  public static FileUploadClient getFileUploadClient() {
    if (fileUploadClient == null) {
      fileUploadClient = ServiceGenerator.createService(FileUploadClient.class);
    }
    return fileUploadClient;
  }

  public static AboutEventClient getAboutEventClient() {
    if (aboutEventClient == null) {
      aboutEventClient = ServiceGenerator.createService(AboutEventClient.class);
    }
    return aboutEventClient;
  }

  public static SessionsClient getSessionsClient() {
    if (sessionsClient == null) {
      sessionsClient = ServiceGenerator.createService(SessionsClient.class);
    }
    return sessionsClient;
  }

  public static SponsorsClient getSponsorsClient() {
    if (sponsorsClient == null) {
      sponsorsClient = ServiceGenerator.createService(SponsorsClient.class);
    }
    return sponsorsClient;
  }

  public static SpeakersClient getSpeakersClient() {
    if (speakersClient == null) {
      speakersClient = ServiceGenerator.createService(SpeakersClient.class);
    }
    return speakersClient;
  }

  public static AttendanceClient getAttendanceClient() {
    if (attendanceClient == null) {
      attendanceClient = ServiceGenerator.createService(AttendanceClient.class);
    }
    return attendanceClient;
  }

  public static ContactUsClient getContactUsClient() {
    if (contactUsClient == null) {
      contactUsClient = ServiceGenerator.createService(ContactUsClient.class);
    }
    return contactUsClient;
  }

  public static MapDataClient getMapDataClient() {
    if (mapDataClient == null) {
      mapDataClient = ServiceGenerator.createService(MapDataClient.class);
    }
    return mapDataClient;
  }

  public static ChatClient getChatClient() {
    if (chatClient == null) {
      chatClient = ServiceGenerator.createService(ChatClient.class);
    }
    return chatClient;
  }

  public static GCMUpdateClient getGCMUpdateClient() {
    if (mGCMUpdateClient == null) {
      mGCMUpdateClient = ServiceGenerator.createService(GCMUpdateClient.class);
    }
    return mGCMUpdateClient;
  }

  public static NotificationsClient getNotificationsClient() {
    if (notificationsClient == null) {
      notificationsClient = ServiceGenerator.createService(NotificationsClient.class);
    }
    return notificationsClient;
  }
}
