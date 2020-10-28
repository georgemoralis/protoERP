/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 27/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import org.controlsfx.control.SearchableComboBox;

public class LoginView implements Initializable {

  @FXML private SearchableComboBox<?> userCombo;
  @FXML private PasswordField passText;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void onLogin(ActionEvent event) {}

  @FXML
  private void onCancel(ActionEvent event) {}
}
