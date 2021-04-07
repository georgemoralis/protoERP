/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 23/10/2020 (georgemoralis) - Προσθήκη validation
 * 19/10/2020 (georgemoralis) - Initial working version
 */
package gr.codebb.protoerp.generic;

import gr.codebb.codebblib.validatorfx.Validator;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.MysqlUtil;
import gr.codebb.protoerp.App;
import gr.codebb.util.database.DatabaseDefaultFile;
import gr.codebb.util.database.Dbms;
import gr.codebb.util.database.Mysql;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Modality;

public class DatabaseConnectionView implements Initializable {

  @FXML private CbbClearableTextField hostText;
  @FXML private CbbClearableTextField portText;
  @FXML private CbbClearableTextField databaseText;
  @FXML private CbbClearableTextField userText;
  @FXML private CbbClearableTextField passText;
  @FXML private Button saveButton;
  @FXML private CbbClearableTextField userRootText;
  @FXML private CbbClearableTextField passRootText;

  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    hostText.setText(App.currentdatabase.getHost());
    portText.setText(App.currentdatabase.getPort());
    databaseText.setText(App.currentdatabase.getDbname());
    userText.setText(App.currentdatabase.getUsername());
    passText.setText(App.currentdatabase.getPassword());
    userRootText.setText("root");

    validator
        .createCheck()
        .dependsOn("host", hostText.textProperty())
        .withMethod(
            c -> {
              String host = c.get("host");
              if (host.isEmpty()) {
                c.error("Ο διακομιστής δεν μπορεί να είναι κενός");
              }
            })
        .decorates(hostText)
        .immediate();
    validator
        .createCheck()
        .dependsOn("port", portText.textProperty())
        .withMethod(
            c -> {
              String port = c.get("port");
              if (port.isEmpty()) {
                c.error("Η πόρτα δεν μπορεί να είναι κενή");
              }
            })
        .decorates(portText)
        .immediate();
    validator
        .createCheck()
        .dependsOn("database", databaseText.textProperty())
        .withMethod(
            c -> {
              String database = c.get("database");
              if (database.isEmpty()) {
                c.error("Η Βάση δεν μπορεί να είναι κενή");
              }
            })
        .decorates(databaseText)
        .immediate();
    validator
        .createCheck()
        .dependsOn("user", userText.textProperty())
        .withMethod(
            c -> {
              String user = c.get("user");
              if (user.isEmpty()) {
                c.error("Το όνομα χρήστη δεν μπορεί να είναι κενό");
              }
            })
        .decorates(userText)
        .immediate();
    validator
        .createCheck()
        .dependsOn("pass", passText.textProperty())
        .withMethod(
            c -> {
              String pass = c.get("pass");
              if (pass.isEmpty()) {
                c.error("Ο Κωδικός δεν μπορεί να είναι κενός");
              }
            })
        .decorates(passText)
        .immediate();
    validator
        .createCheck()
        .dependsOn("userRoot", userRootText.textProperty())
        .withMethod(
            c -> {
              String userRoot = c.get("userRoot");
              if (userRoot.isEmpty()) {
                c.error("Το όνομα χρήστη δεν μπορεί να είναι κενό");
              }
            })
        .decorates(userRootText)
        .immediate();
  }

  @FXML
  private void saveAction(ActionEvent event) {
    DatabaseDefaultFile.get_instance()
        .writeDatabaseFile(
            hostText.getText(),
            portText.getText(),
            databaseText.getText(),
            userText.getText(),
            passText.getText());
    AlertDlg.create()
        .type(AlertDlg.Type.INFORMATION)
        .message("Οι ρυθμίσεις αποθηκεύτηκαν.\nΕπανεκκινήστε το πρόγραμμα")
        .title("Επιτυχία")
        .owner(null)
        .modality(Modality.APPLICATION_MODAL)
        .showAndWait();
    System.exit(0);
  }

  @FXML
  private void checkAction(ActionEvent event) {
    if (validator.containsErrors()) {
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message("Ελέξτε την φόρμα για λάθη")
          .title("Πρόβλημα")
          .owner(hostText.getScene().getWindow())
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return;
    }
    // check if mysql is up
    if (!MysqlUtil.isMysqlRunning(hostText.getText(), Integer.parseInt(portText.getText()))) {
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message(
              "Δεν τρέχει η υπηρεσία MYSQL στην πόρτα "
                  + portText.getText()
                  + " \nΕλέξτε την εγκατάσταση της MYSQL")
          .title("Πρόβλημα")
          .owner(null)
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return;
    }
    Dbms database =
        new Mysql(
            hostText.getText(),
            portText.getText(),
            databaseText.getText(),
            userText.getText(),
            passText.getText());
    java.sql.Connection con = null;
    try {

      // Getting a connection to the newly started database
      Class.forName("com.mysql.cj.jdbc.Driver");
      try {
        con =
            DriverManager.getConnection(
                database.getUrl(), userRootText.getText(), passRootText.getText());
      } catch (SQLException ex) {
        switch (ex.getErrorCode()) {
            // database not exists
          case 1049:
            {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message(
                      "Δεν υπάρχει η βάση "
                          + database.getDbName()
                          + "\nΤο πρόγραμμα θα προσπαθήσει να δημιουργήσει την βάση και τον χρήστη")
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
              if (!MysqlUtil.createDatabase(
                  hostText.getText(),
                  portText.getText(),
                  databaseText.getText(),
                  userRootText.getText(),
                  passRootText.getText())) {
                AlertDlg.create()
                    .type(AlertDlg.Type.ERROR)
                    .message("H βάση " + database.getDbName() + " δεν μπορεί να δημιουργηθεί")
                    .title("Πρόβλημα")
                    .owner(null)
                    .modality(Modality.APPLICATION_MODAL)
                    .showAndWait();
                return;
              }
              if (!MysqlUtil.createUserAndGrand(
                  hostText.getText(),
                  portText.getText(),
                  databaseText.getText(),
                  userText.getText(),
                  passText.getText(),
                  userRootText.getText(),
                  passRootText.getText())) {
                AlertDlg.create()
                    .type(AlertDlg.Type.ERROR)
                    .message("Ο χρήστης " + userText.getText() + " δεν μπορεί να δημιουργηθεί")
                    .title("Πρόβλημα")
                    .owner(null)
                    .modality(Modality.APPLICATION_MODAL)
                    .showAndWait();
                return;
              }
              // everything went ok enable save button
              AlertDlg.create()
                  .type(AlertDlg.Type.INFORMATION)
                  .message("Η βάση και ο χρήστης δημιουργήθηκαν\nΑποθηκεύστε τις ρυθμίσεις")
                  .title("Επιτυχία")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();

              saveButton.setDisable(false);
            }
            break;

            // user doesn't exist or doesn't have rights to database
          case 1045:
            {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message("Δεν έχετε πρόσβαση για τον χρήστη " + database.getUsername())
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
            }
            break;
          default:
            {
              AlertDlg.create()
                  .type(AlertDlg.Type.ERROR)
                  .message("Δεν έχετε πρόσβαση για τον χρήστη " + database.getUsername())
                  .title("Πρόβλημα")
                  .owner(null)
                  .modality(Modality.APPLICATION_MODAL)
                  .showAndWait();
            }
            break;
        }
        return;
      }

    } catch (ClassNotFoundException ex) {
      return;
    }
    try {
      con.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
