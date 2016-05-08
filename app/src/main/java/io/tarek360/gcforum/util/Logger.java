package io.tarek360.gcforum.util;

import android.util.Log;

/**
 * Created by mohamedfarouk on 7/28/15.
 */
public class Logger {
  private static boolean enabled = true;

  public static boolean isEnabled() {
    return enabled;
  }

  public static void setEnabled(boolean enabled) {
    Logger.enabled = enabled;
  }

  /**
   * A log method to handle logs without crashes and with A default tag by class name and loglevel
   *
   * @param o this object to get the class name as a tag
   * @param message the chat_message_item to be logged
   * @param logLevel one of the following VERBOSE, INFO, DEBUG, WARN, ERROR, WTF
   */
  public static void log(Object o, String message, LogLevel logLevel) {
    try {
      if (isEnabled()) {

        switch (logLevel) {
          case VERBOSE:
            Log.v(o.getClass().getSimpleName(), message);
            break;
          case INFO:
            Log.i(o.getClass().getSimpleName(), message);
            break;
          case DEBUG:
            Log.d(o.getClass().getSimpleName(), message);
            break;
          case WARN:
            Log.w(o.getClass().getSimpleName(), message);
            break;
          case ERROR:
            Log.e(o.getClass().getSimpleName(), message);
            break;
          case WTF:
            Log.wtf(o.getClass().getSimpleName(), message);
            break;
        }
      } else {
        Log.wtf(o.getClass().getSimpleName(), "Log is Disabled");
      }
    } catch (Exception e) {
      wtf(new Logger(), e.toString());
    }
  }

  public static void verbose(Object o, String message) {
    log(o, message, LogLevel.VERBOSE);
  }

  public static void info(Object o, String message) {
    log(o, message, LogLevel.INFO);
  }

  public static void debug(Object o, String message) {
    log(o, message, LogLevel.DEBUG);
  }

  public static void warn(Object o, String message) {
    log(o, message, LogLevel.WARN);
  }

  public static void error(Object o, String message) {
    log(o, message, LogLevel.ERROR);
  }

  public static void wtf(Object o, String message) {
    log(o, message, LogLevel.WTF);
  }

  public enum LogLevel {
    VERBOSE, INFO, DEBUG, WARN, ERROR, WTF
  }
}
