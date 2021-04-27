/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.cbbDateTimePicker.CbbDateTimePicker;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import gr.codebb.protoerp.trader.TraderEntity;
import gr.codebb.protoerp.trader.TraderPlantsEntity;
import gr.codebb.protoerp.trader.TraderQueries;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.control.SearchableComboBox;

public class Invoice1DetailView implements Initializable {

  @FXML private Label invoiceTypeLabel;
  @FXML private Label plantLabel;
  @FXML private SearchableComboBox<TraderEntity> traderCombo;
  @FXML private CbbDateTimePicker dateTimePicker;

  private InvoiceTypesEntity invoiceType;
  @FXML private ComboBox<TraderPlantsEntity> traderPlantCombo;
  @FXML private Button invoiceLinesNewButton;
  @FXML private Button invoiceLinesEditButton;
  @FXML private Button invoiceLinesDeleteButton;
  @FXML private TableView<?> invoiceLinesTable;
  @FXML private TableColumn<?, ?> linesCodeCol;
  @FXML private TableColumn<?, ?> linesBarcodeCol;
  @FXML private TableColumn<?, ?> linesEidosCol;
  @FXML private TableColumn<?, ?> linesFPACol;
  @FXML private TableColumn<?, ?> linesSeiraEmCol;
  @FXML private TableColumn<?, ?> linesMonCol;
  @FXML private TableColumn<?, ?> linesPosotCol;
  @FXML private TableColumn<?, ?> linesTimMonCol;
  @FXML private TableColumn<?, ?> linesValueBeforeDiscCol;
  @FXML private TableColumn<?, ?> linesDiscPerCol;
  @FXML private TableColumn<?, ?> linesDiscValueCol;
  @FXML private TableColumn<?, ?> linesValueCol;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    new ComboboxService<>(TraderQueries.getTradersPerCompany(), traderCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(traderCombo);

    traderCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (options, oldValue, newValue) -> {
              List<TraderPlantsEntity> tplants = TraderQueries.getTraderPlants(newValue);
              if (newValue != null) {
                new ComboboxService<>(tplants, traderPlantCombo).start();
                DisplayableListCellFactory.setComboBoxCellFactory(traderPlantCombo);
                if (tplants.size() == 1) // select if it is only one
                {
                  traderPlantCombo.getSelectionModel().select(tplants.get(0));
                }
              }
            });
  }

  public void newInvoice(InvoiceTypesEntity invoiceType) {
    setInvoiceType(invoiceType);
    dateTimePicker.setDateTimeValue(LocalDateTime.now());
  }

  public void setInvoiceType(InvoiceTypesEntity invoiceType) {
    this.invoiceType = invoiceType;
    if (invoiceType.getSeira() == null) {
      invoiceTypeLabel.setText(invoiceType.getShortName() + " - " + invoiceType.getName());
    } else {
      invoiceTypeLabel.setText(
          invoiceType.getShortName()
              + " - "
              + invoiceType.getName()
              + " (ΣΕΙΡΑ : "
              + invoiceType.getSeira()
              + " )");
    }
    plantLabel.setText(invoiceType.getPlantS());
  }

  @FXML
  private void invoiceLinesNewAction(ActionEvent event) {}

  @FXML
  private void invoiceLinesEditAction(ActionEvent event) {}

  @FXML
  private void invoiceLinesDeleteAction(ActionEvent event) {}
}
