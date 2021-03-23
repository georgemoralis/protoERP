/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 23/03/2021 (gmoralis) - Startup work on adding synallasomeno
 * 06/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.trader;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.lib.util.AlertDlgHelper;
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

public class TraderListView implements Initializable {

  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button deleteButton;
  @FXML private Button openButton;
  @FXML private CbbTableView<TraderEntity> transactorTable;
  @FXML private StackPane mainStackPane;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void refreshAction(ActionEvent event) {}

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<TraderDetailView> getDetailView =
        FxmlUtil.load("/fxml/trader/TraderDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Συναλλασσόμενου",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          /* if (!getDetailView.getController().validateControls()) {
            event1.consume();
          }*/
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        /*getDetailView.getController().save();
        selectWithService();*/
      }
    }
  }

  @FXML
  private void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}
}
