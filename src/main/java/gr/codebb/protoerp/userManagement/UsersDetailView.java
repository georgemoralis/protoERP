/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/12/2020 (georgemoralis) - Improved change password method
 * 04/12/2020 (georgemoralis) - save-edit works . Validation should be ok
 * 01/12/2020 (georgemoralis) - More WIP work
 * 29/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import net.synedra.validatorfx.Validator;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.controlsfx.control.CheckListView;

public class UsersDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML private CheckBox checkActive;
  @FXML private CbbClearableTextField textName;
  @FXML private CbbClearableTextField textUsername;
  @FXML private PasswordField textPassword;
  @FXML private PasswordField textRepeatPassword;
  @FXML private CheckListView<RolesEntity> roleCheckList;
  @FXML private CheckBox checkNoPass;

  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    roleCheckList.getItems().addAll(RolesQueries.getRoles());
    roleCheckList.setCellFactory(
        (ListView<RolesEntity> listView) ->
            new CheckBoxListCell<RolesEntity>(
                item -> roleCheckList.getItemBooleanProperty(item),
                new StringConverter<RolesEntity>() {
                  @Override
                  public RolesEntity fromString(String arg0) {
                    return null;
                  }

                  @Override
                  public String toString(RolesEntity rol) {
                    return rol.getRoleName();
                  }
                }));
    checkActive.setSelected(true); // user is active by default on new entry

    validator
        .createCheck()
        .dependsOn("username", textUsername.textProperty())
        .withMethod(
            c -> {
              String username = c.get("username");
              if (username.isEmpty()) {
                c.error("Το username δεν μπορεί να είναι κενό");
              }
            })
        .decorates(textUsername)
        .immediate();

    validator
        .createCheck()
        .withMethod(
            c -> {
              if (!c.get("password").equals(c.get("passwordConfirmation"))) {
                c.error("Οι κωδικοί δεν είναι ίδιοι");
              }
            })
        .dependsOn("password", textPassword.textProperty())
        .dependsOn("passwordConfirmation", textRepeatPassword.textProperty())
        .decorates(textPassword)
        .decorates(textRepeatPassword);

    validator
        .createCheck()
        .dependsOn("username", textUsername.textProperty())
        .withMethod(
            c -> {
              String username = c.get("username");
              UserEntity user = UserQueries.findUserByUsername(username);
              if (user != null) // if exists
              {
                if (!textId.getText().isEmpty()) { // if it is not a new entry
                  if (user.getId()
                      != Long.parseLong(textId.getText())) // check if found id is the same
                  {
                    c.error("Υπάρχει ήδη χρήστης με όνομα " + username);
                  }
                } else {
                  c.error("Υπάρχει ήδη χρήστης με όνομα " + username);
                }
              }
            })
        .decorates(textUsername);

    checkNoPass
        .selectedProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observable,
                  Boolean oldValue,
                  Boolean newValue) {
                if (newValue) {
                  textPassword.setText("");
                  textRepeatPassword.setText("");
                  textPassword.setPromptText("");
                  textRepeatPassword.setPromptText("");
                  textPassword.setDisable(true);
                  textRepeatPassword.setDisable(true);
                } else {
                  textPassword.setDisable(false);
                  textRepeatPassword.setDisable(false);
                }
              }
            });
  }

  public void fillData(UserEntity user) {
    textId.setText(user.getId().toString());
    textName.setText(user.getName());
    textUsername.setText(user.getUsername());
    // we don't copy password they are encrypted anyway
    checkActive.setSelected(user.getActive());
    for (RolesEntity rol : roleCheckList.getItems()) {
      for (RolesEntity roleExist : user.getRoleList()) {
        if (roleExist.getRoleName().matches(rol.getRoleName())) {
          roleCheckList.getCheckModel().check(rol);
        }
      }
    }
    if (!user.getPassword().isEmpty()) {
      textPassword.setPromptText("Ο κωδικός δεν εμφανίζεται");
      textRepeatPassword.setPromptText("Ο κωδικός δεν εμφανίζεται");
    } else {
      checkNoPass.setSelected(true);
    }
  }

  public boolean save() {
    GenericDao gdao = new GenericDao(UserEntity.class, PersistenceManager.getEmf());
    UserEntity user = new UserEntity();
    user.setActive(checkActive.isSelected());
    user.setName(textName.getText());
    user.setUsername(textUsername.getText());
    if (checkNoPass.isSelected()) {
      user.setPassword("");
    } else {
      DefaultPasswordService passwordService = new DefaultPasswordService();
      user.setPassword(passwordService.encryptPassword(textPassword.getText()));
    }

    for (RolesEntity role : roleCheckList.getCheckModel().getCheckedItems()) {
      user.getRoleList().add(role);
    }
    gdao.createEntity(user);
    return true;
  }

  public boolean saveEdit() {
    GenericDao gdao = new GenericDao(UserEntity.class, PersistenceManager.getEmf());
    UserEntity user = (UserEntity) gdao.findEntity(Long.valueOf(textId.getText()));
    user.setActive(checkActive.isSelected());
    user.setName(textName.getText());
    user.setUsername(textUsername.getText());
    DefaultPasswordService passwordService = new DefaultPasswordService();
    if (checkNoPass.isSelected()) {
      user.setPassword("");
    } else {
      if (!textPassword.getText().isEmpty()) {
        // if stored password is not the same than entered password
        if (!passwordService.passwordsMatch(textPassword.getText(), user.getPassword())) {
          user.setPassword(passwordService.encryptPassword(textPassword.getText()));
        }
      }
    }
    user.getRoleList().clear();
    for (RolesEntity role : roleCheckList.getCheckModel().getCheckedItems()) {
      user.getRoleList().add(role);
    }
    gdao.updateEntity(user);
    return true;
  }

  public boolean validateControls() {
    validator.validate();
    if (validator.containsErrors()) {
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message("Ελέξτε την φόρμα για λάθη")
          .title("Πρόβλημα")
          .owner(textName.getScene().getWindow())
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return false;
    }
    return true;
  }
}
