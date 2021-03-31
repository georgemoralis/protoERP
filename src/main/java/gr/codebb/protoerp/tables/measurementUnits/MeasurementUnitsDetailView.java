/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 31/03/2021 (gmoralis) -Initial
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class MeasurementUnitsDetailView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private TextField textId;
  @FXML private CbbClearableTextField textShortName;
  @FXML private CbbClearableTextField textDescription;
  @FXML private CheckBox checkBoxActive;
  @FXML private ComboBox<?> mydataCombo;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }
}
