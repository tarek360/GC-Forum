package io.tarek360.gcforum.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.domain.model.Notification;
import io.tarek360.gcforum.ui.notificationCenter.NotificationDetailsActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tarek on 1/28/16.
 */
public class UIHelper {

  private static long lastPressedTime;
  private static int clickedTimes = 0;

  public static int getSpanCount(Activity activity) {
    int spanCount = 1;
    if (activity.getResources().getBoolean(R.bool.isTablet)) {
      spanCount++;
    }
    return spanCount;
  }

  public static DisplayMetrics getDisplayMetrics(Activity activity) {
    /**
     * Get screen dimensions
     */
    DisplayMetrics displayMetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics;
  }

  public static void showSnackbar(View view, String message) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
  }

  public static void showSnackbar(View view, int resId) {
    Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
  }

  public static void setSmallScript(TextView textView, String text, String subScript,
      int subScriptColor) {

    String сolorString = String.format("%X", subScriptColor).substring(2); // !!strip alpha value!!
    textView.setText(Html.fromHtml(
        String.format(text + " <small><font color=\"#%s\">" + subScript + "</font></small>",
            сolorString)), TextView.BufferType.SPANNABLE);
  }

  public static void setSmallScript(TextView textView, String text, String subScript) {
    textView.setText(Html.fromHtml(text + " <small>" + subScript + "</small>"),
        TextView.BufferType.SPANNABLE);
  }

  public static void setStrike(TextView textView, String text) {
    textView.setText(text);
    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
  }

  public static void makeTextInputLayoutEnable(boolean enable,
      TextInputLayout... textInputLayouts) {
    for (TextInputLayout textInputLayout : textInputLayouts) {
      textInputLayout.getEditText().setEnabled(enable);
    }
  }

  public static void clearEditText(TextInputLayout... textInputLayouts) {
    for (TextInputLayout textInputLayout : textInputLayouts) {
      textInputLayout.getEditText().setText("");
    }
  }

  public static void hideViews(View... views) {
    for (View view : views) {
      view.setVisibility(View.GONE);
    }
  }

  public static MaterialDialog showOkDialog(Context context, int titleResId, int content) {
    return new MaterialDialog.Builder(context).title(titleResId)
        .content(content)
        .positiveText(R.string.ok)
        .show();
  }

  public static void showAppInfoDialog(Context context) {
    if (System.currentTimeMillis() - lastPressedTime < 800) {
      clickedTimes++;
      if (clickedTimes == 9) {

        try {
          PackageInfo pInfo =
              context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

          String versionName = pInfo.versionName;
          int versionCode = pInfo.versionCode;
          String packageName = pInfo.packageName;

          new MaterialDialog.Builder(context).title(context.getString(R.string.version_info_title))
              .content(String.format(context.getString(R.string.version_info_body), versionName,
                  versionCode, packageName))
              .cancelable(false)
              .positiveText(R.string.ok)
              .show();
        } catch (PackageManager.NameNotFoundException e) {
          e.printStackTrace();
        }
        clickedTimes = 0;
      }
    }

    lastPressedTime = System.currentTimeMillis();
  }

  public static MaterialDialog.Builder showOkCancelDialog(Context context, int titleResId,
      int content) {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(context).title(titleResId)
        .content(content)
        .positiveText(R.string.ok)
        .negativeText(R.string.cancel);
    builder.show();
    return builder;
  }

  public static MaterialDialog showIndeterminateDialog(Context context, int titleResId) {
    return new MaterialDialog.Builder(context).title(titleResId)
        .content(R.string.please_wait)
        .progress(true, 0)
        .show();
  }

  public static void showContentDialog(Context context, CharSequence title, CharSequence content) {
    new MaterialDialog.Builder(context).title(title)
        .content(content)
        .positiveText(R.string.close)
        .show();
  }

  public static MaterialDialog.Builder showPhoneCallDialog(Context context, int titleResId,
      int content) {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(context).title(titleResId)
        .content(content)
        .positiveText(R.string.call)
        .negativeText(R.string.cancel);
    builder.show();
    return builder;
  }

  // Creates notification based on title and body received
  public static void createCentralNotification(Context context, String message,
      boolean isLoggedIn) {

    final int MESSAGE_NOTIFICATION_ID = 12313;

    // define sound URI, the sound to be played when there's a notification
    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    String id = null;
    String title = null;
    String body = null;
    long sentTime;
    try {
      JSONObject jsonObject = new JSONObject(message);
      // do stuff
      id = jsonObject.get("id").toString();
      title = jsonObject.get("title").toString();
      body = jsonObject.get("body").toString();
      sentTime = Long.parseLong(jsonObject.get("time_sent").toString());
    } catch (NumberFormatException | JSONException e) {
      e.printStackTrace();
      return;
    }

    int requestID =
        (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId

    PendingIntent pIntent;

    Notification notification = new Notification();
    notification.setId(id);
    notification.setTitle(title);
    notification.setBody(body);
    notification.setTime(sentTime);

    Intent intent = NotificationDetailsActivity.buildIntent(context, notification);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    // Adds the back stack
    //stackBuilder.addParentStack(MainActivity.class);
    stackBuilder.addParentStack(NotificationDetailsActivity.class);

    // Adds the Intent to the top of the stack
    stackBuilder.addNextIntent(intent);

    // Gets a PendingIntent containing the entire back stack
    pIntent = stackBuilder.getPendingIntent(requestID, PendingIntent.FLAG_UPDATE_CURRENT);

    // Now we can attach this to the notification using setContentIntent

    // Create the style object with BigTextStyle subclass.
    NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();
    notiStyle.setBigContentTitle(title);
    notiStyle.setSummaryText(context.getString(R.string.app_name));
    notiStyle.bigText(body);

    Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(soundUri)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .setStyle(notiStyle);

    NotificationManager mNotificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
  }  // Creates notification based on title and body received

  public static Bitmap drawableToBitmap(Drawable drawable) {
    Bitmap bitmap = null;

    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if (bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }

    if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
      bitmap = Bitmap.createBitmap(1, 1,
          Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else {
      bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
          Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

  /**
   * TODO Config Notification
   * Create and show a simple notification containing chat_message_item.
   *
   * @param context .
   * @param cls .
   * @param message .
   */

  private void sendNotification(Context context, Class<?> cls, String message) {
    Intent intent = new Intent(context, cls);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
        PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
        //.setSmallIcon(R.drawable.lo)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(message)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }
}
