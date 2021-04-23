/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

public class InvoicesListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<?> invoiceTable;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void refreshAction(ActionEvent event) {}

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
        getDetailView1.getController().setInvoiceType(type);
        Optional<ButtonType> result1 = alert1.showAndWait();
      }
    }
  }

  @FXML
  private void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}
}
