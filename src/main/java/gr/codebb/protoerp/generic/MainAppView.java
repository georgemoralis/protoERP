/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 29/12/2020 (georgemoralis) - fix issue in confirm dialog for backup
 * 13/10/2020 (georgemoralis) - Added checkforupdates option
 * 12/10/2020 (georgemoralis) - Added backup and update actions
 * 11/10/2020 (georgemoralis) - Added about button action
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.generic;

import static gr.codebb.lib.util.ThreadUtil.runAndWait;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import gr.codebb.ctl.CbbDetachableTabPane;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.lib.util.StageUtil;
import gr.codebb.lib.util.WebUtil;
import gr.codebb.protoerp.MainSettings;
import gr.codebb.protoerp.about.AboutView;
import gr.codebb.protoerp.settings.SettingsHelper;
import gr.codebb.util.version.VersionUtil;
import gr.codebb.webserv.rest.Util.Calls;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
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
    // check for updates service
    Service<Void> service =
        new Service<Void>() {
          @Override
          protected Task<Void> createTask() {
            return new Task<Void>() {
              @Override
              protected Void call() throws Exception {
                Thread.sleep(10000);
                checkForUpdates(true);
                return null;
              }
            };
          }
        };
    service.start();
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
  private void onBackupButtonPressed(ActionEvent event) {
    ButtonType response =
        AlertDlg.create()
            .message(
                "Προσοχή! Η εκκίνηση της εφαρμογής θα τερματίσει το πρόγραμμα.\nΘέλετε να συνεχίσετε?")
            .title("Εκκίνηση εφαρμογής Backup")
            .owner(masterDetailPane.getScene().getWindow())
            .modality(Modality.APPLICATION_MODAL)
            .showAndWaitConfirm();
    if (response == ButtonType.OK) {
      if (SystemUtils.IS_OS_WINDOWS) { // support only for windows for now
        File f = new File("");
        ProcessBuilder pb =
            new ProcessBuilder(
                "cmd",
                "/c",
                "start",
                "C:\\codebb\\backup\\backup.exe",
                "--application=" + MainSettings.getInstance().getAppName(),
                "--applicationPath=\"" + f.getAbsolutePath() + "\"");
        pb.directory(new File("C:\\codebb\\backup"));
        try {
          Process process = pb.start();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        System.exit(0);
      } else {
        // OS is not windows
        AlertDlg.create()
            .type(AlertDlg.Type.ERROR)
            .message("Δεν υποστηρίζεται το backup σε άλλο περιβάλλον εκτός windows")
            .title("Πρόβλημα")
            .owner(null)
            .modality(Modality.APPLICATION_MODAL)
            .showAndWait();
      }
    }
  }

  @FXML
  private void onUpdateButtonPressed(ActionEvent event) {
    Service<Void> service =
        new Service<Void>() {
          @Override
          protected Task<Void> createTask() {
            return new Task<Void>() {
              @Override
              protected Void call() throws Exception {
                checkForUpdates(false);
                return null;
              }
            };
          }
        };
    service.start();
  }

  @FXML
  private void onHelpButtonPressed(ActionEvent event) {
    Desktop desktop = Desktop.getDesktop();
    try {
      desktop.browse(new URI("https://codebb.gr/documentation/protoERP/protoERP.html"));
    } catch (URISyntaxException | IOException ex) {
      ex.printStackTrace();
    }
  }

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

  public void checkForUpdates(boolean startup) {
    String currentVersion = MainSettings.getInstance().getVersion();
    System.out.println("version : " + currentVersion);

    if (!WebUtil.pingURL("https://clients.codebb.gr", 1000)) {
      if (!startup) {
        try {
          runAndWait(
              () -> {
                AlertDlg.create()
                    .type(AlertDlg.Type.ERROR)
                    .message(
                        "Η υπηρεσία αναβαθμίσεων δεν είναι διαθέσιμη αυτή τη στιγμή.\nΔοκιμάστε αργότερα")
                    .title("Πρόβλημα")
                    .owner(mainDetachPane.getScene().getWindow())
                    .modality(Modality.APPLICATION_MODAL)
                    .showAndWait();
              });
        } catch (InterruptedException | ExecutionException ex) {
          ex.printStackTrace();
        }
      }
      return; // if not found URL then don't go further
    } else {
      System.out.println("webserv seems reachable.");
    }
    if (startup) {
      boolean checkforUpdates = SettingsHelper.loadIntegerSetting("checkUpdates") != 0;
      if (checkforUpdates) {
        String version_string = null;
        try {
          version_string =
              Calls.UpdaterRequest(
                  "K8PBC-HTURS-BH5E9-2B039-56044", MainSettings.getInstance().getAppName());
        } catch (IOException | InterruptedException ex) {
          ex.printStackTrace();
        }
        if (version_string == null) {
          System.out.println("api call seems unreachable");
          return;
        }
        System.out.println("webserv replies that the latest version is " + version_string);
        final String vfinal = version_string;
        if (VersionUtil.isComparedVersionNewer(
            MainSettings.getInstance().getVersion(), version_string)) {
          System.out.println("New version found");
          try {
            runAndWait(
                () -> {
                  FxmlUtil.LoadResult<UpdateInfoView> versionWindow =
                      FxmlUtil.load("/fxml/generic/UpdateInfo.fxml");
                  versionWindow
                      .getController()
                      .setVersions(MainSettings.getInstance().getVersion(), vfinal);
                  Stage stage =
                      StageUtil.setStageSettings(
                          "Νέα έκδοση!",
                          new Scene(versionWindow.getParent()),
                          Modality.APPLICATION_MODAL,
                          mainStackPane.getScene().getWindow(),
                          null,
                          "/img/protoerp.png");
                  stage.setResizable(false);
                  stage.show();
                });
          } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
          }
        }
      } else {
        System.out.println("check for updates disabled");
      }
    } else {
      String version_string = null;
      try {
        version_string =
            Calls.UpdaterRequest(
                "K8PBC-HTURS-BH5E9-2B039-56044", MainSettings.getInstance().getAppName());
      } catch (IOException | InterruptedException ex) {
        ex.printStackTrace();
      }
      if (version_string == null) {
        System.out.println("webserv seems unreachable");
        return;
      }
      final String vfinal = version_string;

      if (VersionUtil.isComparedVersionNewer(
          MainSettings.getInstance().getVersion(), version_string)) {
        System.out.println("New version found");
        try {
          runAndWait(
              () -> {
                FxmlUtil.LoadResult<UpdateInfoView> versionWindow =
                    FxmlUtil.load("/fxml/generic/UpdateInfo.fxml");
                versionWindow
                    .getController()
                    .setVersions(MainSettings.getInstance().getVersion(), vfinal);
                Stage stage =
                    StageUtil.setStageSettings(
                        "Νέα έκδοση!",
                        new Scene(versionWindow.getParent()),
                        Modality.APPLICATION_MODAL,
                        mainStackPane.getScene().getWindow(),
                        null,
                        "/img/protoerp.png");
                stage.setResizable(false);
                stage.show();
              });
        } catch (InterruptedException | ExecutionException ex) {
          ex.printStackTrace();
        }
      } else {
        System.out.println("version is up to date!");
        try {
          runAndWait(
              () -> {
                AlertDlg.create()
                    .type(AlertDlg.Type.INFORMATION)
                    .message("Δεν υπάρχουν διαθέσιμες ενημερώσεις αυτή τη στιγμή")
                    .title("Πληροφόρηση")
                    .owner(mainDetachPane.getScene().getWindow())
                    .modality(Modality.APPLICATION_MODAL)
                    .showAndWait();
              });
        } catch (InterruptedException | ExecutionException ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
