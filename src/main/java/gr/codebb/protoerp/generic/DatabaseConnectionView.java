/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.generic;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.MysqlUtil;
import gr.codebb.protoerp.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Modality;


public class DatabaseConnectionView implements Initializable {

    @FXML
    private CbbClearableTextField hostText;
    @FXML
    private CbbClearableTextField portText;
    @FXML
    private CbbClearableTextField databaseText;
    @FXML
    private CbbClearableTextField userText;
    @FXML
    private CbbClearableTextField passText;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    hostText.setText(App.currentdatabase.getHost());
    portText.setText(App.currentdatabase.getPort());
    databaseText.setText(App.currentdatabase.getDbname());
    userText.setText(App.currentdatabase.getUsername());
    passText.setText(App.currentdatabase.getPassword());
  }

    @FXML
    private void saveAction(ActionEvent event) {
    }

    @FXML
    private void checkAction(ActionEvent event) {
        //check if mysql is up
        if(!MysqlUtil.isMysqlRunning(hostText.getText(),Integer.parseInt(portText.getText())))
        {
            AlertDlg.create()
                                    .type(AlertDlg.Type.ERROR)
                                    .message("Δεν τρέχει η υπηρεσία MYSQL στην πόρτα " + portText.getText() + " \nΕλέξτε την εγκατάσταση της MYSQL")
                                    .title("Πρόβλημα")
                                    .owner(null)
                                    .modality(Modality.APPLICATION_MODAL)
                                    .showAndWait();
            return;
        }
    }
}
