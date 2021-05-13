/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBigDecimalTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbIntegerTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbLocalDateTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbStringTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.AlertHelper;
import gr.codebb.lib.util.DecimalDigits;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class InvoicesListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;

  @ColumnProperty(prefWidth = "160.0d")
  private CbbTableColumn<InvoicesEntity, LocalDate> columnDateOnly;

  @ColumnProperty(prefWidth = "90.0d")
  private CbbTableColumn<InvoicesEntity, String> columnInvoiceStatusS;

  @ColumnProperty(prefWidth = "90.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<InvoicesEntity, String> columnTypeShortNameS;

  @ColumnProperty(prefWidth = "90.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<InvoicesEntity, String> columnSeiraS;

  @ColumnProperty(prefWidth = "90.0d", align = ColumnProperty.Align.RIGHT)
  private CbbTableColumn<InvoicesEntity, Integer> columnInvoiceNumber;

  @ColumnProperty(prefWidth = "280.0d")
  private CbbTableColumn<InvoicesEntity, String> columnSynalName;

  @ColumnProperty(prefWidth = "90.0d")
  private CbbTableColumn<InvoicesEntity, String> columnSynalVatNumber;

  @ColumnProperty(prefWidth = "80.0d")
  private CbbTableColumn<InvoicesEntity, BigDecimal> columnTotalNoVatValue;

  @ColumnProperty(prefWidth = "80.0d")
  private CbbTableColumn<InvoicesEntity, BigDecimal> columnTotalDiscount;

  @ColumnProperty(prefWidth = "80.0d")
  private CbbTableColumn<InvoicesEntity, BigDecimal> columnTotalNoVatAfterDiscValue;

  @ColumnProperty(prefWidth = "80.0d")
  private CbbTableColumn<InvoicesEntity, BigDecimal> columnTotalVatValue;

  @ColumnProperty(prefWidth = "80.0d")
  private CbbTableColumn<InvoicesEntity, BigDecimal> columnTotalValue;

  @FXML private CbbTableView<InvoicesEntity> invoiceTable;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnDateOnly = new CbbLocalDateTableColumn<>("Ημερομηνία");
    columnDateOnly.setCellValueFactory(new PropertyValueFactory<>("dateOnly"));
    columnInvoiceStatusS = new CbbStringTableColumn<>("Κατάσταση");
    columnInvoiceStatusS.setCellValueFactory(new PropertyValueFactory<>("invoiceStatusS"));
    columnTypeShortNameS = new CbbStringTableColumn<>("Τύπος");
    columnTypeShortNameS.setCellValueFactory(new PropertyValueFactory<>("typeShortNameS"));
    columnSeiraS = new CbbStringTableColumn<>("Σειρά");
    columnSeiraS.setCellValueFactory(new PropertyValueFactory<>("seiraS"));
    columnInvoiceNumber = new CbbIntegerTableColumn<>("Αριθμός");
    columnInvoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
    columnSynalVatNumber = new CbbStringTableColumn<>("Α.Φ.Μ.");
    columnSynalVatNumber.setCellValueFactory(new PropertyValueFactory<>("synalVatNumber"));
    columnSynalName = new CbbStringTableColumn<>("Συναλλασσόμενος");
    columnSynalName.setCellValueFactory(new PropertyValueFactory<>("synalVatNumber"));
    columnTotalNoVatValue =
        new CbbBigDecimalTableColumn<>(
            "Συν. Αξία", DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName()));
    columnTotalNoVatValue.setCellValueFactory(new PropertyValueFactory<>("totalNoVatValue"));

    columnTotalDiscount =
        new CbbBigDecimalTableColumn<>(
            "Συν. Έκπτωση", DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName()));
    columnTotalDiscount.setCellValueFactory(new PropertyValueFactory<>("totalDiscount"));

    columnTotalNoVatAfterDiscValue =
        new CbbBigDecimalTableColumn<>(
            "Καθ. Αξία", DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName()));
    columnTotalNoVatAfterDiscValue.setCellValueFactory(
        new PropertyValueFactory<>("totalNoVatAfterDiscValue"));

    columnTotalVatValue =
        new CbbBigDecimalTableColumn<>(
            "Αξία Φ.Π.Α.", DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName()));
    columnTotalVatValue.setCellValueFactory(new PropertyValueFactory<>("totalVatValue"));

    columnTotalValue =
        new CbbBigDecimalTableColumn<>(
            "Τελ. Αξία", DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName()));
    columnTotalValue.setCellValueFactory(new PropertyValueFactory<>("τotalValue"));

    invoiceTable
        .getColumns()
        .addAll(
            columnDateOnly,
            columnInvoiceStatusS,
            columnTypeShortNameS,
            columnSeiraS,
            columnInvoiceNumber,
            columnSynalVatNumber,
            columnSynalName,
            columnTotalNoVatValue,
            columnTotalDiscount,
            columnTotalNoVatAfterDiscValue,
            columnTotalVatValue,
            columnTotalValue);

    init(this);
    selectWithService();
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<InvoicesNewView> getDetailView =
        FxmlUtil.load("/fxml/invoices/InvoicesNewSelector.fxml");
    Alert alert =
        AlertDlgHelper.selectDialog(
            "Επιλογή Παραστατικού",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.setDisable(true);
    okbutton
        .disableProperty()
        .bind(
            Bindings.isEmpty(
                getDetailView
                    .getController()
                    .getTableView()
                    .getSelectionModel()
                    .getSelectedItems()));
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      InvoiceTypesEntity type =
          (InvoiceTypesEntity)
              getDetailView.getController().getTableView().getSelectionModel().getSelectedItem();
      if (getDetailView.getController() != null) {
        FxmlUtil.LoadResult<Invoice1DetailView> getDetailView1 =
            FxmlUtil.load("/fxml/invoices/Invoice1DetailView.fxml");
        Alert alert1 =
            AlertDlgHelper.saveDialog(
                "Δημιουργία παραστατικού",
                getDetailView1.getParent(),
                mainStackPane.getScene().getWindow());
        getDetailView1.getController().newInvoice(type);
        Button okbutton1 = (Button) alert1.getDialogPane().lookupButton(ButtonType.OK);
        okbutton1.addEventFilter(
            ActionEvent.ACTION,
            (event1) -> {
              if (!getDetailView1.getController().validateControls()) {
                event1.consume();
              } else {
                if (!(AlertHelper.saveConfirm(
                            getDetailView1
                                .getController()
                                .getInvoiceTypeLabel()
                                .getScene()
                                .getWindow())
                        .get()
                    == ButtonType.OK)) {
                  event1.consume();
                } else // save but not close the window
                {
                  getDetailView1.getController().saveNewInvoice();
                  okbutton1.setDisable(true);
                  getDetailView1
                      .getController()
                      .getPrintButton()
                      .setDisable(false); // enable print button
                  event1.consume();
                }
              }
            });
        alert1
            .getDialogPane()
            .addEventHandler(
                KeyEvent.KEY_PRESSED,
                event1 -> {
                  if (event1.getCode() == KeyCode.ENTER) { // disable default enter press
                    event1.consume();
                  }
                });
        Optional<ButtonType> result1 = alert1.showAndWait();
        if (result1.get() == ButtonType.OK) {
          if (getDetailView1.getController() != null) {
            selectWithService();
          }
        } else {
          selectWithService();
        }
      }
    }
  }

  @FXML
  protected void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}

  @Override
  protected CbbTableView getTableView() {
    return invoiceTable;
  }

  @Override
  protected List getMainQuery() {
    return InvoicesQueries.getInvoicesDatabaseDateSortedDesc();
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
