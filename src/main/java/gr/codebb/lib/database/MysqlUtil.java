/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 17/10/2020 (georgemoralis) - Added isMysqlRunning
 * 14/10/2020 (georgemoralis) - Added checkDatabaseConnection,checkIfTableExists functions
 */
package gr.codebb.lib.database;

import gr.codebb.util.database.Dbms;
import java.io.IOException;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlUtil {

  public static boolean checkDatabaseConnection(Dbms database) {
    java.sql.Connection con = null;
    try {

      // Getting a connection to the newly started database
      Class.forName("com.mysql.cj.jdbc.Driver");
      try {
        con =
            DriverManager.getConnection(
                database.getUrl(), database.getUsername(), database.getPassword());
      } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
      }

    } catch (ClassNotFoundException ex) {
      return false;
    }
    try {
      con.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return true;
  }

  public static boolean checkIfTableExists(Dbms database, String table) {
    java.sql.Connection con = null;
    try {

      // Getting a connection to the newly started database
      Class.forName("com.mysql.cj.jdbc.Driver");
      try {
        con =
            DriverManager.getConnection(
                database.getUrl(), database.getUsername(), database.getPassword());
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    if (con != null) {

      try {
        try (ResultSet tables =
            con.getMetaData().getTables(database.getDbName(), null, table, null)) {
          while (tables.next()) {
            String currentTableName = tables.getString("TABLE_NAME");
            if (currentTableName.equals(table)) {
              return true;
            }
          }
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    try {
      con.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public static boolean isMysqlRunning(String host, int port) {
    boolean isUp = false;
    try {
      try (Socket socket = new Socket("127.0.0.1", port)) {
        // Server is up
        isUp = true;
      }
    } catch (IOException e) {
      // Server is down
    }
    return isUp;
  }
}
