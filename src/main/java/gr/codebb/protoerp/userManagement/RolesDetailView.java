/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 17/11/2020 (georgemoralis) - Validation works
 * 16/11/2020 (georgemoralis) - Editing now working ok as well
 * 16/11/2020 (georgemoralis) - More progress in loading/saving
 * 15/11/2020 (georgemoralis) - Progress in loading/saving form
 * 12/11/2020 (georgemoralis) - Initial work
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.codebblib.validatorfx.Validator;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckListView;

public class RolesDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML private CbbClearableTextField textRoleΝame;
  @FXML private CheckListView<PermissionsEntity> permCheckList;

  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    permCheckList.getItems().addAll(PersimssionsQueries.getPermissions());
    permCheckList.setCellFactory(
        (ListView<PermissionsEntity> listView) ->
            new CheckBoxListCell<PermissionsEntity>(
                item -> permCheckList.getItemBooleanProperty(item),
                new StringConverter<PermissionsEntity>() {
                  @Override
                  public PermissionsEntity fromString(String arg0) {
                    return null;
                  }

                  @Override
                  public String toString(PermissionsEntity per) {
                    return per.getPermissionDisplayName();
                  }
                }));
    validator
        .createCheck()
        .dependsOn("rolename", textRoleΝame.textProperty())
        .withMethod(
            c -> {
              String rolename = c.get("rolename");
              if (rolename.isEmpty()) {
                c.error("Το όνομα του ρόλου δεν μπορεί να είναι κενό");
              }
            })
        .decorates(textRoleΝame)
        .immediate();

    validator
        .createCheck()
        .dependsOn("rolename", textRoleΝame.textProperty())
        .withMethod(
            c -> {
              String rolename = c.get("rolename");
              RolesEntity rolef = RolesQueries.findRoleName(rolename);
              if (rolef != null) // if exists
              {
                if (!textId.getText().isEmpty()) { // if it is not a new entry
                  if (rolef.getId()
                      != Long.parseLong(textId.getText())) // check if found id is the same
                  {
                    c.error("Υπάρχει ήδη ρόλος με όνομα " + rolename);
                  }
                } else {
                  c.error("Υπάρχει ήδη ρόλος με όνομα " + rolename);
                }
              }
            })
        .decorates(textRoleΝame);
    /*permCheckList
    .getCheckModel()
    .getCheckedItems()
    .addListener(
        new ListChangeListener<PermissionsEntity>() {
          @Override
          public void onChanged(ListChangeListener.Change<? extends PermissionsEntity> change) {
            while (change.next()) {
              if (change.wasAdded()) {
                for (PermissionsEntity perm : change.getAddedSubList()) {
                  System.out.println(perm.getPermissionName());
                }
              }
              if (change.wasRemoved()) {
                for (PermissionsEntity perm : change.getRemoved()) {
                  System.out.println(perm.getPermissionName());
                }
              }
            }
          }
        });*/
  }

  public void fillData(RolesEntity role) {
    textId.setText(role.getId().toString());
    textRoleΝame.setText(role.getRoleName());
    for (PermissionsEntity perm : permCheckList.getItems()) {
      for (PermissionsEntity permExist : role.getPermissionList()) {
        if (permExist.getPermissionName().matches(perm.getPermissionName())) {
          permCheckList.getCheckModel().check(perm);
        }
      }
    }
  }

  public boolean save() {
    GenericDao gdao = new GenericDao(RolesEntity.class, PersistenceManager.getEmf());
    RolesEntity role = new RolesEntity();
    role.setRoleName(textRoleΝame.getText());
    for (PermissionsEntity perm : permCheckList.getCheckModel().getCheckedItems()) {
      role.getPermissionList().add(perm);
    }
    gdao.createEntity(role);
    return true;
  }

  public boolean validateControls() {
    validator.validate();
    if (validator.containsErrors()) {
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message("Ελέξτε την φόρμα για λάθη")
          .title("Πρόβλημα")
          .owner(textRoleΝame.getScene().getWindow())
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return false;
    }
    return true;
  }

  public boolean saveEdit() {
    GenericDao gdao = new GenericDao(RolesEntity.class, PersistenceManager.getEmf());
    RolesEntity role = (RolesEntity) gdao.findEntity(Long.valueOf(textId.getText()));
    role.setRoleName(textRoleΝame.getText());
    role.getPermissionList().clear();
    for (PermissionsEntity perm : permCheckList.getCheckModel().getCheckedItems()) {
      role.getPermissionList().add(perm);
    }
    gdao.updateEntity(role);
    return true;
  }
}
