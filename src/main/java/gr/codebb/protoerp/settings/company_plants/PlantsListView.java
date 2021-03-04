/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.settings.company_plants;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBooleanTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbIntegerTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbLongTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbStringTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

public class PlantsListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<PlantsEntity> plantsTable;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<PlantsEntity, Long> columnId;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<PlantsEntity, Boolean> columnActive;

  @ColumnProperty(prefWidth = "180.0d")
  CbbTableColumn<PlantsEntity, Integer> columnCode;

  @ColumnProperty(prefWidth = "120.0d")
  CbbTableColumn<PlantsEntity, String> columnDescription;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnId = new CbbLongTableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnCode = new CbbIntegerTableColumn<>("Κωδ.Εγκατάστασης");
    columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
    columnDescription = new CbbStringTableColumn<>("Περιγραφή");
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnActive = new CbbBooleanTableColumn<>("Ενεργή");
    columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));

    plantsTable.getColumns().addAll(columnId, columnActive, columnCode, columnDescription);

    init(this);
    selectWithService();
    plantsTable.setUserData("companyPlantsTable"); // for use with savesettings
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {}

  @FXML
  protected void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}

  @Override
  protected CbbTableView getTableView() {
    return plantsTable;
  }

  @Override
  protected List getMainQuery() {
    return PlantsQueries.getPlantsDatabase(false);
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
