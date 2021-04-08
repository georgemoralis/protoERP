/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/04/2021 (gmoralis) - Can't delete vat if it has reference elsewhere. Stored it as inactive
 * 06/04/2021 (gmoralis) - Fixed confirm after validation
 * 02/04/2021 (gmoralis) - Initial full implementation
 */
package gr.codebb.protoerp.tables.vat;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBigDecimalTableColumn;
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
import gr.codebb.lib.util.DecimalDigits;
import gr.codebb.lib.util.FxmlUtil;
import java.math.BigDecimal;
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

public class VatListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<VatEntity> VatTable;

  @ColumnProperty(prefWidth = "80.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<VatEntity, Long> columnId;

  @ColumnProperty(prefWidth = "100.0d")
  private CbbTableColumn<VatEntity, Boolean> columnActive;

  @ColumnProperty(prefWidth = "150.0d")
  private CbbTableColumn<VatEntity, String> columnDescription;

  @ColumnProperty(prefWidth = "120.0d")
  CbbTableColumn<VatEntity, BigDecimal> columnVatRate;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnId = new CbbLongTableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnDescription = new CbbStringTableColumn<>("Περιγραφή");
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnActive = new CbbBooleanTableColumn<>("Ενεργή");
    columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));
    columnVatRate =
        new CbbBigDecimalTableColumn<>(
            "Ποσοστό Φ.Π.Α.",
            DecimalDigits.getDecimalFormat(DecimalDigits.PERCENT_VAT.getSettingName()));
    columnVatRate.setCellValueFactory(new PropertyValueFactory<>("vatRate"));

    VatTable.getColumns().addAll(columnId, columnActive, columnDescription, columnVatRate);

    init(this);
    selectWithService();
    VatTable.setUserData("VatTable"); // for use with savesettings
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<VatDetailView> getDetailView =
        FxmlUtil.load("/fxml/tables/vat/VatDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Συντελεστή Φ.Π.Α.",
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
    FxmlUtil.LoadResult<VatDetailView> getDetailView =
        FxmlUtil.load("/fxml/tables/vat/VatDetailView.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Συντελεστών Φ.Π.Α.",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView.getController().fillData(VatTable.getSelectionModel().getSelectedItem());
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
        getDetailView.getController().saveEdit();
        selectWithService();
      }
    }
  }

  @FXML
  private void deleteAction(ActionEvent event) {
    int row = VatTable.getSelectionModel().getSelectedIndex();
    VatTable.getSelectionModel().select(row);
    Optional<ButtonType> response =
        AlertHelper.deleteConfirm(
            VatTable.getScene().getWindow(),
            "Είστε σιγουροι ότι θέλετε να διαγράψετε τον Συντελεστή Φ.Π.Α. : "
                + VatTable.getSelectionModel().getSelectedItem().getDescription());
    if (response.get() == ButtonType.OK) {
      GenericDao gdao = new GenericDao(VatEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(VatTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        AlertHelper.errorDialog(
            VatTable.getScene().getWindow(),
            "Η κατηγορία Φ.Π.Α. δεν μπορεί να διαγραφεί γιατί υπάρχουν άλλες εγγραφές που την χρησιμοποιούν\nΑποθηκεύτηκε σαν ανενεργή");
        VatEntity k =
            (VatEntity) gdao.findEntity(VatTable.getSelectionModel().getSelectedItem().getId());
        k.setActive(false);
        gdao.updateEntity(k);
      }
      selectWithService();
    }
  }

  @Override
  protected CbbTableView getTableView() {
    return VatTable;
  }

  @Override
  protected List getMainQuery() {
    return VatQueries.getVatDatabase(false);
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
