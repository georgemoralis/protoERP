/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/04/2021 - Initial
 */
package gr.codebb.protoerp.items;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.AlertHelper;
import gr.codebb.lib.util.FxmlUtil;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

public class ItemsListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<?> measurementUnitsTable;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void refreshAction(ActionEvent event) {}

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
          }
          if (!(AlertHelper.SaveConfirm(
                      getDetailView.getController().getMainStackPane().getScene().getWindow())
                  .get()
              == ButtonType.OK)) {
            event1.consume();
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
        // selectWithService();
      }
    }
  }

  @FXML
  private void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}
}
