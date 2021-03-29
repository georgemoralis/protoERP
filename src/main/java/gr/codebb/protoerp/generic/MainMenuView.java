/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 29/03/2021 (gmoralis) - Διαχωρισμός μενού απο το κεντρικό παράθυρο
 */
package gr.codebb.protoerp.generic;

import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.settings.internetSettings.MitrooPassView;
import gr.codebb.protoerp.settings.internetSettings.MyDataPassView;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;

/**
 * FXML Controller class
 *
 * @author snow
 */
public class MainMenuView implements Initializable {

  @FXML private MenuBar menuBar;

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void onMitrooCodes(ActionEvent event) {
    FxmlUtil.LoadResult<MitrooPassView> getDetailView =
        FxmlUtil.load("/fxml/settings/internetServices/MitrooPass.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Κωδικοί Μητρώου", getDetailView.getParent(), menuBar.getScene().getWindow());
    getDetailView.getController().companyLoad();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
      }
    }
  }

  @FXML
  private void onMyDataCodes(ActionEvent event) {
    FxmlUtil.LoadResult<MyDataPassView> getDetailView =
        FxmlUtil.load("/fxml/settings/internetServices/MyDataPass.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Κωδικοί MyData", getDetailView.getParent(), menuBar.getScene().getWindow());
    getDetailView.getController().companyPassLoad();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
      }
    }
  }
}
