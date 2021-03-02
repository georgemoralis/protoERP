/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 03/03/2021 (gmoralis) - Added load/saving of common passwords
 * 02/03/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.internetSettings;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.protoerp.settings.SettingsHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

public class MitrooPassView implements Initializable {

  @FXML private CbbClearableTextField textusernamemitroou;
  @FXML private CbbClearableTextField textpasswordmitroou;
  @FXML private CbbClearableTextField texVatrepresentant;
  @FXML private CheckBox commonCheckBox;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  public void commonLoad() {
    commonCheckBox.setSelected(true);
    commonCheckBox.setDisable(true);
    textusernamemitroou.setText(SettingsHelper.loadStringSetting("mitroo_username"));
    textpasswordmitroou.setText(SettingsHelper.loadStringSetting("mitroo_password"));
    texVatrepresentant.setText(SettingsHelper.loadStringSetting("mitroo_reprvat"));
  }

  public void save() {
    if (commonCheckBox.isSelected()) {
      SettingsHelper.updateStringSetting("mitroo_username", textusernamemitroou.getText());
      SettingsHelper.updateStringSetting("mitroo_password", textpasswordmitroou.getText());
      SettingsHelper.updateStringSetting("mitroo_reprvat", texVatrepresentant.getText());
    }
  }
}
