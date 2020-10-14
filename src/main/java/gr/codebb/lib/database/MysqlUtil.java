/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 14/10/2020 (georgemoralis) - Added checkDatabaseConnection,checkIfTableExists functions
 */
package gr.codebb.lib.database;

import gr.codebb.util.database.Dbms;
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
}
