/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 18/03/2021 (gmoralis) - Initial commit
 */
package gr.codebb.protoerp.settings.internetSettings;

import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.ToggleSwitch;

/**
 * FXML Controller class
 *
 * @author snow
 */
public class MyDataPassView implements Initializable {

  @FXML private CbbClearableTextField textUserMyData;
  @FXML private CbbClearableTextField textPassMyData;
  @FXML private CbbClearableTextField textDemoUserMyData;
  @FXML private CbbClearableTextField textDemoPassMyData;
  @FXML private ToggleSwitch toggleDemoMyDataEnabled;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }
}
