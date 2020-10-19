/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 19/10/2020 (georgemoralis) - Create user and grant access
 * 17/10/2020 (georgemoralis) - Added isMysqlRunning
 * 14/10/2020 (georgemoralis) - Added checkDatabaseConnection,checkIfTableExists functions
 */
package gr.codebb.lib.database;

import gr.codebb.util.database.Dbms;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

  public static boolean createDatabase(
      String host, String port, String database, String username, String password) {
    Connection conn = null;
    Statement stmt = null;
    try {
      // STEP 2: Register JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn =
          DriverManager.getConnection(
              "jdbc:mysql://" + host + ":" + port + "/", username, password);

      // STEP 4: Execute a query
      System.out.println("Creating database...");
      stmt = conn.createStatement();

      String sql =
          "CREATE DATABASE IF NOT EXISTS `"
              + database
              + "` /*!40100 DEFAULT CHARACTER SET utf8 */;"; // IF NOT EXISTS
      stmt.executeUpdate(sql);
      System.out.println("Database created successfully...");
    } catch (SQLException se) {
      // Handle errors for JDBC
      se.printStackTrace();
      return false;
    } catch (Exception e) {
      // Handle errors for Class.forName
      e.printStackTrace();
      return false;
    } finally {
      // finally block used to close resources
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException se2) {
      } // nothing we can do
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      } // end finally try
    } // end try
    return true;
  }

  public static boolean createUserAndGrand(
      String host,
      String port,
      String database,
      String username,
      String password,
      String rootuser,
      String rootpass) {
    Connection conn = null;
    Statement stmt = null;
    try {
      // STEP 2: Register JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn =
          DriverManager.getConnection(
              "jdbc:mysql://" + host + ":" + port + "/", rootuser, rootpass);

      // STEP 4: Execute a query
      System.out.println("Creating user...");
      stmt = conn.createStatement();

      String sql =
          "GRANT ALL PRIVILEGES ON "
              + database
              + ".* To '"
              + username
              + "'@'"
              + host
              + "' IDENTIFIED BY '"
              + password
              + "';";
      stmt.executeUpdate(sql);
      System.out.println("User created successfully...");
    } catch (SQLException se) {
      // Handle errors for JDBC
      se.printStackTrace();
      return false;
    } catch (Exception e) {
      // Handle errors for Class.forName
      e.printStackTrace();
      return false;
    } finally {
      // finally block used to close resources
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException se2) {
      } // nothing we can do
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      } // end finally try
    } // end try
    return true;
  }
}
