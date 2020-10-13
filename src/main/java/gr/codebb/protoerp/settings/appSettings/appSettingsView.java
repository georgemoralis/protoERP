/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.settings.appSettings;

import gr.codebb.dlg.AlertDlg;
import gr.codebb.protoerp.settings.SettingsHelper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import org.controlsfx.control.ToggleSwitch;

public class appSettingsView implements Initializable {

  @FXML private Button btnSave;
  @FXML private ToggleSwitch checkForUpdatesCheck;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    checkForUpdatesCheck.setSelected((SettingsHelper.loadIntegerSetting("checkUpdates") != 0));
  }

  @FXML
  private void SaveAction(ActionEvent event) {
    SettingsHelper.updateIntegerSetting("checkUpdates", checkForUpdatesCheck.isSelected() ? 1 : 0);
    AlertDlg.create()
        .type(AlertDlg.Type.INFORMATION)
        .message("Οι ρυθμίσεις αποθηκεύτηκαν με επιτυχία.")
        .title("Επιτυχία")
        .owner(checkForUpdatesCheck.getScene().getWindow())
        .modality(Modality.APPLICATION_MODAL)
        .showAndWait();
  }
}
