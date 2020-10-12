/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 12/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import org.apache.commons.lang3.SystemUtils;

public class SystemUtil {

  public static final String LOCAL_APP_DATA_PATH_WINDOWS =
      System.getProperty("user.home") + "/AppData/Local";
  public static final String LOCAL_APP_DATA_PATH_MAC =
      System.getProperty("user.home") + "/Library/Application Support/";
  public static final String LOCAL_APP_DATA_PATH_LINUX = System.getProperty("user.home");

  /**
   * Windows: ("user.home") + "/AppData/Local" OSX: ("user.home") +"/Library/Application Support/"
   * Linux: ("user.home")
   *
   * @return The OS specific local app data path
   */
  public static String getSystemLocalAppDataPath() {
    if (SystemUtils.IS_OS_WINDOWS) {
      return LOCAL_APP_DATA_PATH_WINDOWS;
    } else if (SystemUtils.IS_OS_LINUX) {
      return LOCAL_APP_DATA_PATH_LINUX;
    } else if (SystemUtils.IS_OS_MAC_OSX) {
      return LOCAL_APP_DATA_PATH_MAC;
    }
    return System.getProperty("user.home");
  }
}
