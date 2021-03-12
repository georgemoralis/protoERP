/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 12/03/2020 (georgemoralis) - Added username as session variable
 * 06/03/2020 (georgemoralis) - Check if username is empty validation
 * 01/11/2020 (georgemoralis) - Request focus to pass TextField
 * 30/10/2020 (georgemoralis) - Added authentication
 * 27/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.controlsfx.control.SearchableComboBox;

public class LoginView implements Initializable {

  @FXML private SearchableComboBox<UserEntity> userCombo;
  @FXML private PasswordField passText;

  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // fill userCombo
    List<UserEntity> users = UserQueries.getUsers();
    new ComboboxService<>(users, userCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(userCombo);
    if (users.size() == 1) {
      userCombo.getSelectionModel().select(users.get(0)); // select user if it's only one
    }
    Platform.runLater(
        () -> {
          passText.requestFocus();
        });
    validator
        .createCheck()
        .dependsOn("username", userCombo.valueProperty())
        .withMethod(
            c -> {
              UserEntity username = c.get("username");
              if (username == null) {
                c.error("Το όνομα χρήστη δεν μπορεί να είναι κενό");
              }
            })
        .decorates(userCombo)
        .immediate();
  }

  @FXML
  private void onLogin(ActionEvent event) {
    validator.validate();
    if (validator.containsErrors()) {
      return;
    }
    Subject currentUser = SecurityUtils.getSubject();
    UsernamePasswordToken token =
        new UsernamePasswordToken(
            userCombo.getSelectionModel().getSelectedItem().getUsername(), passText.getText());
    try {
      if (!currentUser.isAuthenticated()) {
        System.out.println("Current user is not authenticated.");

        try {
          currentUser.login(token);
          System.out.println("Authenticated: " + currentUser.isAuthenticated());
          Session session = currentUser.getSession();
          session.setAttribute(
              "username", userCombo.getSelectionModel().getSelectedItem().getUsername());
          ((Stage) passText.getScene().getWindow()).hide();
        } catch (UnknownAccountException uae) {
          System.out.println(uae.getMessage());
        } catch (IncorrectCredentialsException iae) {
          System.out.println(iae.getMessage());
        } catch (LockedAccountException lae) {
          System.out.println(lae.getMessage());
        } catch (AuthenticationException ae) {
          AlertDlg.create()
              .type(AlertDlg.Type.ERROR)
              .message(ae.getMessage())
              .title("Πρόβλημα")
              .owner(userCombo.getScene().getWindow())
              .modality(Modality.APPLICATION_MODAL)
              .showAndWait();
        } catch (Exception e) {
          System.out.println(e.getStackTrace());
        }
      } else {
        System.out.println("There is a user who is already authenticated...");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @FXML
  private void onCancel(ActionEvent event) {
    System.exit(0); // close application
  }
}
