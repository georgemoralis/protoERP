/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 02/03/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.internetSettings;

import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MitrooPassView implements Initializable {

  @FXML private CbbClearableTextField textusernamemitroou;
  @FXML private CbbClearableTextField textpasswordmitroou;
  @FXML private CbbClearableTextField texVatrepresentant;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void onInstructionsPressed(ActionEvent event) {}
}
