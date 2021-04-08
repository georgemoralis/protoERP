/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/04/2021 (gmoralis) - Can't delete measureunit if it has reference elsewhere. Stored it as inactive
 * 06/04/2021 (gmoralis) - Fixed confirm after validation
 * 02/04/2021 (gmoralis) - Added delete action
 * 01/04/2021 (gmoralis) - Added edit
 * 01/04/2021 (gmoralis) - Added save new entry and confirm dialog before save
 * 31/03/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBooleanTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbLongTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbStringTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.AlertHelper;
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
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<MeasurementUnitsDetailView> getDetailView =
        FxmlUtil.load("/fxml/tables/measurementUnits/MeasurementUnitsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Μονάδας Μέτρησης",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          } else {
            if (!(AlertHelper.saveConfirm(
                        getDetailView.getController().getMainStackPane().getScene().getWindow())
                    .get()
                == ButtonType.OK)) {
              event1.consume();
            }
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
        selectWithService();
      }
    }
  }

  @FXML
  protected void openAction(ActionEvent event) {
    FxmlUtil.LoadResult<MeasurementUnitsDetailView> getDetailView =
        FxmlUtil.load("/fxml/tables/measurementUnits/MeasurementUnitsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Μονάδας Μέτρησης",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView
        .getController()
        .fillData(measurementUnitsTable.getSelectionModel().getSelectedItem());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          } else {
            if (!(AlertHelper.editConfirm(
                        getDetailView.getController().getMainStackPane().getScene().getWindow())
                    .get()
                == ButtonType.OK)) {
              event1.consume();
            }
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().SaveEdit();
        selectWithService();
      }
    }
  }

  @FXML
  private void deleteAction(ActionEvent event) {
    int row = measurementUnitsTable.getSelectionModel().getSelectedIndex();
    measurementUnitsTable.getSelectionModel().select(row);
    Optional<ButtonType> response =
        AlertHelper.deleteConfirm(
            measurementUnitsTable.getScene().getWindow(),
            "Είστε σιγουροι ότι θέλετε να διαγράψετε την Μονάδα Μέτρησης : "
                + measurementUnitsTable.getSelectionModel().getSelectedItem().getDescription());
    if (response.get() == ButtonType.OK) {
      GenericDao gdao = new GenericDao(MeasurementUnitsEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(measurementUnitsTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        AlertHelper.errorDialog(
            measurementUnitsTable.getScene().getWindow(),
            "Η μονάδα μέτρησης δεν μπορεί να διαγραφεί γιατί υπάρχουν άλλες εγγραφές που την χρησιμοποιούν\nΑποθηκεύτηκε σαν ανενεργή");
        MeasurementUnitsEntity k =
            (MeasurementUnitsEntity)
                gdao.findEntity(
                    measurementUnitsTable.getSelectionModel().getSelectedItem().getId());
        k.setActive(false);
        gdao.updateEntity(k);
      }
      selectWithService();
    }
  }

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
