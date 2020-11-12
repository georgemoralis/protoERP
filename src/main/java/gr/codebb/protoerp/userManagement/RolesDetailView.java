/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 12/11/2020 (georgemoralis) - Initial work
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckListView;

public class RolesDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML private CbbClearableTextField textRolename;
  @FXML private CheckBox checkBoxActive;
  @FXML private CheckListView<PermissionEntity> permCheckList;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    permCheckList.getItems().addAll(PersimssionQueries.getPermissions());
    permCheckList.setCellFactory(
        new Callback<ListView<PermissionEntity>, ListCell<PermissionEntity>>() {
          public ListCell<PermissionEntity> call(ListView<PermissionEntity> listView) {
            return new CheckBoxListCell<PermissionEntity>(
                item -> permCheckList.getItemBooleanProperty(item),
                new StringConverter<PermissionEntity>() {
                  @Override
                  public PermissionEntity fromString(String arg0) {
                    return null;
                  }

                  @Override
                  public String toString(PermissionEntity per) {
                    return per.getPermissionDisplayName();
                  }
                });
          }
        });
  }
}
