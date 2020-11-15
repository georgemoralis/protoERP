/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 12/11/2020 (georgemoralis) - Initial work in Add detail view
 * 09/11/2020 (georgemoralis) - More work on listview
 * 06/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbLongTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbStringTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class RolesView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<RolesEntity> rolesTable;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<RolesEntity, Long> columnId;

  @ColumnProperty(prefWidth = "150.0d")
  CbbTableColumn<RolesEntity, String> columnRoleName;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnId = new CbbLongTableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnRoleName = new CbbStringTableColumn<>("Ρόλος");
    columnRoleName.setCellValueFactory(new PropertyValueFactory<>("roleName"));

    rolesTable.getColumns().addAll(columnId, columnRoleName);

    init(this);
    selectWithService();
  }

  @FXML
  private void deleteAction(ActionEvent event) {}

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<RolesDetailView> getDetailView =
        FxmlUtil.load("/fxml/userManagement/RolesDetail.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη ρόλου - δικαιωμάτων",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        // save data
        selectWithService();
      }
    }
  }

  @FXML
  protected void openAction(ActionEvent event) {}

  @Override
  protected CbbTableView getTableView() {
    return rolesTable;
  }

  @Override
  protected List getMainQuery() {
    return RolesQueries.getRoles();
  }

  @Override
  protected StackPane getMainStackPane() {
    return mainStackPane;
  }

  @Override
  protected Button getDeleteButton() {
    return deleteButton;
  }

  @Override
  protected Button getOpenButton() {
    return openButton;
  }
}
