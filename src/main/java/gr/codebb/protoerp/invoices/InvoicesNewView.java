/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.CbbSearchableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class InvoicesNewView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private TableView<?> selectTable;
  @FXML private TableColumn<?, ?> codeColumn;
  @FXML private TableColumn<?, ?> shortColumn;
  @FXML private TableColumn<?, ?> nameColumn;
  @FXML private TableColumn<?, ?> lastNumColumn;
  @FXML private TableColumn<?, ?> plantColumn;
  @FXML private CbbSearchableTextField searchTextField;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }
}
