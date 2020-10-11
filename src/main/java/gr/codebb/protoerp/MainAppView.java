/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 11/10/2020 (georgemoralis) - Added about button action
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import gr.codebb.ctl.CbbDetachableTabPane;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.lib.util.StageUtil;
import gr.codebb.protoerp.about.AboutView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.MasterDetailPane;

public class MainAppView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private JFXHamburger hamburgermenu;
  @FXML private CbbDetachableTabPane mainDetachPane;

  HamburgerNextArrowBasicTransition hammenu;

  MasterDetailPane masterDetailPane;

  public CbbDetachableTabPane getMainDetachPane() {
    return mainDetachPane;
  }

  public void setMasterPane(MasterDetailPane masterDetailPane) {
    this.masterDetailPane = masterDetailPane;
  }

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    hammenu = new HamburgerNextArrowBasicTransition(hamburgermenu);
    hammenu.setRate(-1);
  }

  @FXML
  private void hamburgerMenuMouseClicked(MouseEvent event) {
    hammenu.setRate(hammenu.getRate() * -1);
    hammenu.play();
    if (masterDetailPane.isShowDetailNode()) {
      masterDetailPane.setShowDetailNode(false);
    } else {
      masterDetailPane.setShowDetailNode(true);
    }
  }

  @FXML
  private void onBackupButtonPressed(ActionEvent event) {}

  @FXML
  private void onUpdateButtonPressed(ActionEvent event) {}

  @FXML
  private void onHelpButtonPressed(ActionEvent event) {}

  @FXML
  private void onAboutButtonPressed(ActionEvent event) {
    FxmlUtil.LoadResult<AboutView> aboutWindow = FxmlUtil.load("/fxml/about/About.fxml");
    Stage stage =
        StageUtil.setStageSettings(
            "Άδεια Χρήσης",
            new Scene(aboutWindow.getParent()),
            Modality.APPLICATION_MODAL,
            mainStackPane.getScene().getWindow(),
            null,
            "/img/protoerp.png");
    stage.setResizable(false);
    stage.show();
  }
}
