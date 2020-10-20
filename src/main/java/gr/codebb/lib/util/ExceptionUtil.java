/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 20/10/2020 (georgemoralis) - Added exceptionToString
 */
package gr.codebb.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

  public static String exceptionToString(Throwable e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String exceptionText = sw.toString();
    return exceptionText;
  }
}
