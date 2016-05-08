package io.tarek360.gcforum.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.format.DateFormat;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by tarek on 2/17/16.
 */
public class Utils {

  public static boolean checkEmail(String email) {
    final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+");
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
  }

  // Checking Network
  public static boolean getNetworkState(Context ctx) {
    final ConnectivityManager connMgr =
        (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

    final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

    //should check null because in air plan mode it will be null
    if ((wifi.isAvailable() || mobile.isAvailable()) & (netInfo != null && netInfo.isConnected())) {
      return true;
    }
    return false;
  }

  public static String getDate(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);
    String date = DateFormat.format("dd/MM/yyyy", cal).toString();
    return date;
  }

  public static String getTime(long time) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTimeInMillis(time);
    String date = DateFormat.format("HH:mm", cal).toString();
    return date;
  }

  public static boolean openUrl(Context context, String url) {

    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      url = "http://" + url;
    }
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    try {
      context.startActivity(browserIntent);
      return true;
    } catch (ActivityNotFoundException e) {
      return false;
    }
  }

  //Max 3 MB
  public static boolean isValidFileSize(Uri uri) {
    File file = new File(uri.getPath());
    if (file.length() < 3.1 * 1024 * 1024) {
      return true;
    }
    return false;
  }

  public static String getFormattedTimeAgo(long time) {

    Logger.debug("time ", "time " + System.currentTimeMillis() / 1000);
    long diff = System.currentTimeMillis() / 1000 - time;

    if (diff > 6 * 24 * 60 * 60) {
      String format = "h:mm a dd MMM yyyy";
      SimpleDateFormat SimpleDateFormat = new SimpleDateFormat(format);
      SimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      return SimpleDateFormat.format(time * 1000);
    } else if (diff > 24 * 60 * 60) {

      int day = (int) diff / (24 * 60 * 60);

      if (day > 1) {
        return day + " days ago";
      } else {
        return "1 day ago";
      }
    } else if (diff > 60 * 60) {

      int hour = (int) diff / (60 * 60);

      if (hour > 1) {
        return hour + " hours ago";
      } else {
        return "1 hour ago";
      }
    } else if (diff > 60) {

      int min = (int) diff / 60;

      if (min > 1) {
        return min + " minutes ago";
      } else {
        return "1 minute ago";
      }
    } else if (diff > 1) {

      return "less than minute ago";
    }

    return "";
  }
}
