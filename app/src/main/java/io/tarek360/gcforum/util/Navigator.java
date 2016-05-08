/**
 * Copyright (C) 2015 tarek360 All rights reserved.
 *
 * @author Ahmed Tarek
 */
package io.tarek360.gcforum.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.domain.model.Speaker;
import io.tarek360.gcforum.ui.chat.ChatActivity;
import io.tarek360.gcforum.ui.main.MainActivity;
import io.tarek360.gcforum.ui.map.MapActivity;
import io.tarek360.gcforum.ui.notificationCenter.NotificationDetailsActivity;
import io.tarek360.gcforum.ui.notificationCenter.NotificationsActivity;
import io.tarek360.gcforum.ui.sessions.SessionsActivity;
import io.tarek360.gcforum.ui.speakers.SpeakerDetailsActivity;
import io.tarek360.gcforum.ui.speakers.SpeakersActivity;
import io.tarek360.gcforum.ui.sponsors.SponsorsActivity;

/**
 * Class used to navigate through the application.
 */

public class Navigator {

  private Context context;

  /**
   * Goes to the MainActivity.
   */
  public void navigateToMainActivity() {
    if (context != null) {
      Intent intentToLaunch = MainActivity.buildIntent(context);
      intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the SessionsActivity.
   */
  public void navigateToSessionsActivity() {
    if (context != null) {
      Intent intentToLaunch = SessionsActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the SponsorsActivity.
   */
  public void navigateToSponsorsActivity() {
    if (context != null) {
      Intent intentToLaunch = SponsorsActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the SpeakersActivity.
   */
  public void navigateToSpeakersActivity() {
    if (context != null) {
      Intent intentToLaunch = SpeakersActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the MapActivity.
   */
  public void navigateToMapActivity() {
    if (context != null) {
      Intent intentToLaunch = MapActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the ChatActivity.
   */
  public void navigateToChatActivity() {
    if (context != null) {
      Intent intentToLaunch = ChatActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the NotificationsActivity.
   */
  public void navigateToNotificationsActivity() {
    if (context != null) {
      Intent intentToLaunch = NotificationsActivity.buildIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the NotificationDetailsActivity.
   */
  public void navigateToNotificationDetailsActivity(Notification notification) {
    if (context != null) {
      Intent intentToLaunch = NotificationDetailsActivity.buildIntent(context, notification);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the SpeakerDetailsActivity.
   */
  public void navigateToSpeakerDetailsActivity(Speaker speaker) {
    if (context != null) {
      Intent intentToLaunch = SpeakerDetailsActivity.buildIntent(context, speaker);
      context.startActivity(intentToLaunch);
    }
  }

  public void setContext(@NonNull Context context) {
    this.context = context;
  }
}
