/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.ctl.CbbClearableTextField;
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
import org.controlsfx.control.CheckListView;

/**
 * FXML Controller class
 *
 * @author giorg
 */
public class UsersDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML private CheckBox checkActive;
  @FXML private CbbClearableTextField textName;
  @FXML private CbbClearableTextField textUsername;
  @FXML private PasswordField textPassword;
  @FXML private PasswordField textRepeatPassword;
  @FXML private CheckListView<RolesEntity> roleCheckList;

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

  public boolean save() {
    return true;
  }

  public boolean validateControls() {
    return true;
  }
}
