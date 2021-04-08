/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 02/04/2021 (gmoralis) - Προσθήκη συντελεστών Φ.Π.Α.
 * 31/03/2021 (gmoralis) - Προσθήκη μονάδων μέτρησης
 * 29/03/2021 (gmoralis) - Διαχωρισμός μενού απο το κεντρικό παράθυρο
 */
package gr.codebb.protoerp.generic;

import gr.codebb.ctl.CbbDetachableTab;
import gr.codebb.ctl.CbbDetachableTabPane;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.settings.internetSettings.MitrooPassView;
import gr.codebb.protoerp.settings.internetSettings.MyDataPassView;
import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsListView;
import gr.codebb.protoerp.tables.vat.VatListView;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;

/**
 * FXML Controller class
 *
 * @author snow
 */
public class MainMenuView implements Initializable {

  @FXML private MenuBar menuBar;

  private CbbDetachableTabPane mainDetachPane;

  public void setMainDetachPane(CbbDetachableTabPane mainDetachPane) {
    this.mainDetachPane = mainDetachPane;
  }

  private void showAsTab(Node frm, String label) {
    final CbbDetachableTab tab = new CbbDetachableTab(label);
    tab.setClosable(true);
    tab.setContent(frm);
    mainDetachPane.getTabs().add(tab);
    mainDetachPane.getSelectionModel().select(tab);
    /** Workaround for TabPane memory leak */
    tab.setOnClosed(
        new EventHandler<Event>() {

          @Override
          public void handle(Event t) {
            tab.setContent(null);
          }
        });
    mainDetachPane.getSelectionModel().selectLast();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void onMitrooCodes(ActionEvent event) {
    FxmlUtil.LoadResult<MitrooPassView> getDetailView =
        FxmlUtil.load("/fxml/settings/internetServices/MitrooPass.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Κωδικοί Μητρώου", getDetailView.getParent(), menuBar.getScene().getWindow());
    getDetailView.getController().companyLoad();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
      }
    }
  }

  @FXML
  private void onMyDataCodes(ActionEvent event) {
    FxmlUtil.LoadResult<MyDataPassView> getDetailView =
        FxmlUtil.load("/fxml/settings/internetServices/MyDataPass.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Κωδικοί MyData", getDetailView.getParent(), menuBar.getScene().getWindow());
    getDetailView.getController().companyPassLoad();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
      }
    }
  }

  @FXML
  private void onMeasureUnits(ActionEvent event) {
    FxmlUtil.LoadResult<MeasurementUnitsListView> getListView =
        FxmlUtil.load("/fxml/tables/measurementUnits/MeasurementUnitsListView.fxml");
    Node measure = (Node) getListView.getParent();
    showAsTab(measure, "Μονάδες Μέτρησης");
  }

  @FXML
  private void onVat(ActionEvent event) {
    FxmlUtil.LoadResult<VatListView> getListView =
        FxmlUtil.load("/fxml/tables/vat/VatListView.fxml");
    Node measure = (Node) getListView.getParent();
    showAsTab(measure, "Συντελεστές Φ.Π.Α.");
  }

  @FXML
  private void onInvoiceSeries(ActionEvent event) {}
}
