/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 01/12/2020 (georgemoralis) - More WIP work
 * 29/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.StringConverter;
import net.synedra.validatorfx.Validator;
import org.controlsfx.control.CheckListView;

public class UsersDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML private CheckBox checkActive;
  @FXML private CbbClearableTextField textName;
  @FXML private CbbClearableTextField textUsername;
  @FXML private PasswordField textPassword;
  @FXML private PasswordField textRepeatPassword;
  @FXML private CheckListView<RolesEntity> roleCheckList;

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
  }

  public boolean save() {
    GenericDao gdao = new GenericDao(UserEntity.class, PersistenceManager.getEmf());
    return true;
  }

  public boolean saveEdit() {
    return true;
  }

  public boolean validateControls() {
    return true;
  }
}
