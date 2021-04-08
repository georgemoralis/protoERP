/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 07/04/2021 (gmoralis) - Columns in tableview now working
 * 06/02/2021 (gmoralis) - Edit and delete actions
 * 06/04/2021 (gmoralis) - Fixed confirm after validation
 * 05/04/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.items;

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

public class ItemsListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<ItemsEntity> itemsTable;

  @ColumnProperty(prefWidth = "100.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<ItemsEntity, Long> columnCode;

  @ColumnProperty(prefWidth = "100.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<ItemsEntity, Long> columnBarcode;

  @ColumnProperty(prefWidth = "100.0d")
  private CbbTableColumn<ItemsEntity, Boolean> columnActive;

  @ColumnProperty(prefWidth = "250.0d")
  private CbbTableColumn<ItemsEntity, String> columnDescription;

  @ColumnProperty(prefWidth = "100.0d")
  private CbbTableColumn<ItemsEntity, String> columnItemTypeS;

  @ColumnProperty(prefWidth = "100.0d")
  private CbbTableColumn<ItemsEntity, String> columnMeasureUnitS;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<ItemsEntity, BigDecimal> columnSellPrice;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<ItemsEntity, BigDecimal> columnSellVatRate;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnCode = new CbbLongTableColumn<>("Κωδικός");
    columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
    columnBarcode = new CbbLongTableColumn<>("Barcode");
    columnBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
    columnDescription = new CbbStringTableColumn<>("Περιγραφή");
    columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    columnActive = new CbbBooleanTableColumn<>("Ενεργή");
    columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));
    columnItemTypeS = new CbbStringTableColumn<>("Τύπος");
    columnItemTypeS.setCellValueFactory(new PropertyValueFactory<>("itemTypeS"));
    columnMeasureUnitS = new CbbStringTableColumn<>("Μ.Μέτρησης");
    columnMeasureUnitS.setCellValueFactory(new PropertyValueFactory<>("measureUnitS"));

    columnSellPrice =
        new CbbBigDecimalTableColumn<>(
            "Τιμή Πώλησης", DecimalDigits.getDecimalFormat(DecimalDigits.UNIT.getSettingName()));
    columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

    columnSellVatRate =
        new CbbBigDecimalTableColumn<>(
            "Φ.Π.Α.", DecimalDigits.getDecimalFormat(DecimalDigits.PERCENT_VAT.getSettingName()));
    columnSellVatRate.setCellValueFactory(new PropertyValueFactory<>("sellVatRate"));

    itemsTable
        .getColumns()
        .addAll(
            columnCode,
            columnActive,
            columnBarcode,
            columnDescription,
            columnItemTypeS,
            columnMeasureUnitS,
            columnSellPrice,
            columnSellVatRate);

    init(this);
    selectWithService();
    itemsTable.setUserData("itemsTable"); // for use with savesettings
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<ItemsDetailView> getDetailView =
        FxmlUtil.load("/fxml/items/ItemsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Είδους", getDetailView.getParent(), mainStackPane.getScene().getWindow());
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
    FxmlUtil.LoadResult<ItemsDetailView> getDetailView =
        FxmlUtil.load("/fxml/items/ItemsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Είδους",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView.getController().fillData(itemsTable.getSelectionModel().getSelectedItem());
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
    int row = itemsTable.getSelectionModel().getSelectedIndex();
    itemsTable.getSelectionModel().select(row);
    Optional<ButtonType> response =
        AlertHelper.deleteConfirm(
            itemsTable.getScene().getWindow(),
            "Είστε σιγουροι ότι θέλετε να διαγράψετε το Είδος : "
                + itemsTable.getSelectionModel().getSelectedItem().getDescription());
    if (response.get() == ButtonType.OK) {
      GenericDao gdao = new GenericDao(ItemsEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(itemsTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        e.printStackTrace();
      }
      selectWithService();
    }
  }

  @Override
  protected CbbTableView getTableView() {
    return itemsTable;
  }

  @Override
  protected List getMainQuery() {
    return ItemsQueries.getItemsDatabase(false);
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
