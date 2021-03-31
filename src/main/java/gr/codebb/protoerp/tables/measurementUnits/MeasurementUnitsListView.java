/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 31/03/2021 (gmoralis) -Initial
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBooleanTableColumn;
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

public class MeasurementUnitsListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<MeasurementUnitsEntity> measurementUnitsTable;

  @ColumnProperty(prefWidth = "80.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<MeasurementUnitsEntity, Long> columnId;

  @ColumnProperty(prefWidth = "100.0d")
  private CbbTableColumn<MeasurementUnitsEntity, Boolean> columnActive;

  @ColumnProperty(prefWidth = "150.0d")
  private CbbTableColumn<MeasurementUnitsEntity, String> columnDescription;

  @ColumnProperty(prefWidth = "140.0d")
  private CbbTableColumn<MeasurementUnitsEntity, String> columnShortName;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnId = new CbbLongTableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnDescription = new CbbStringTableColumn<>("Περιγραφή");
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnShortName = new CbbStringTableColumn<>("Συντ/φία");
    columnShortName.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    columnActive = new CbbBooleanTableColumn<>("Ενεργή");
    columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));

    measurementUnitsTable
        .getColumns()
        .addAll(columnId, columnActive, columnShortName, columnDescription);

    init(this);
    selectWithService();
    measurementUnitsTable.setUserData("measurementUnitsTable"); // for use with savesettings
  }

  @FXML
  private void refreshAction(ActionEvent event) {}

  @FXML
  private void newAction(ActionEvent event) {}

  @FXML
  protected void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}

  @Override
  protected CbbTableView getTableView() {
    return measurementUnitsTable;
  }

  @Override
  protected List getMainQuery() {
    return MeasurementUnitsQueries.getMeasurementsUnitsDatabase(false);
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
