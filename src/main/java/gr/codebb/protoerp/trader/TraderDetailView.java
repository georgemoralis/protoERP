/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.trader;

import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

public class TraderDetailView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private VBox mainVBox;
  @FXML private TabPane tabPane;
  @FXML private CbbClearableTextField textName;
  @FXML private CbbClearableTextField textJob;
  @FXML private CbbClearableTextField textVatNumber;
  @FXML private CbbClearableTextField textEmail;
  @FXML private CbbClearableTextField textMobilePhone;
  @FXML private SearchableComboBox<?> doyCombo;
  @FXML private TextField textId;
  @FXML private CheckBox checkBoxActive;
  @FXML private StackPane mainStackPane1;
  @FXML private Button newPlantButton;
  @FXML private Button openPlantButton;
  @FXML private Button deletePlantButton;
  @FXML private TableView<?> tablePlants;
  @FXML private TableColumn<?, ?> columnId;
  @FXML private TableColumn<?, ?> columnActive;
  @FXML private TableColumn<?, ?> columnCode;
  @FXML private TableColumn<?, ?> columnDescription;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void onTaxisUpdate(ActionEvent event) {}

  @FXML
  private void newPlantAction(ActionEvent event) {}

  @FXML
  private void openPlantAction(ActionEvent event) {}

  @FXML
  private void deletePlantAction(ActionEvent event) {}
}
