/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.trader;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class TraderView implements Initializable {

  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button deleteButton;
  @FXML private Button openButton;
  @FXML private CbbTableView<TraderEntity> transactorTable;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void DeleteAction(ActionEvent event) {}
}
