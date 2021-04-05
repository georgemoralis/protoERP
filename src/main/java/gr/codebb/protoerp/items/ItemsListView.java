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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
  private void newAction(ActionEvent event) {}

  @FXML
  private void openAction(ActionEvent event) {}

  @FXML
  private void deleteAction(ActionEvent event) {}
}
