/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 12/10/2020 (georgemoralis) - Added urlExists
 * 12/10/2020 (georgemoralis) - Initial added pingURL
 */
package gr.codebb.lib.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtil {

  /**
   * Check if we can ping a url
   *
   * @param url
   * @param timeout
   * @return true if we can
   */
  public static boolean pingURL(String url, int timeout) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setConnectTimeout(timeout);
      connection.setReadTimeout(timeout);
      connection.setRequestMethod("HEAD");
      int responseCode = connection.getResponseCode();
      return (200 <= responseCode && responseCode <= 399);
    } catch (IOException exception) {
      return false;
    }
  }
  /**
   * Check if url exists
   *
   * @param URLName
   * @return true if exists
   */
  public static boolean urlExists(String URLName) {
    try {
      HttpURLConnection.setFollowRedirects(false);
      // note : you may also need
      //        HttpURLConnection.setInstanceFollowRedirects(false)
      HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
      con.setRequestMethod("HEAD");
      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
