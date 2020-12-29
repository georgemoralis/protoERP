/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
 /*
 * Changelog
 * =========
 * 29/12/2020 (georgemoralis) - fix issue in confirm dialog for updater
 * 13/10/2020 (georgemoralis) - Added UpdateSetting
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.generic;

import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.util.WebUtil;
import gr.codebb.protoerp.MainSettings;
import gr.codebb.protoerp.settings.SettingsHelper;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

public class UpdateInfoView implements Initializable {

    @FXML
    private Label oldVersionLabel;
    @FXML
    private Label newVersionLabel;
    @FXML
    private WebView webViewEngine;

    public void setVersions(String old, String newv) {
        oldVersionLabel.setText(old);
        newVersionLabel.setText(newv);
        WebEngine engine = webViewEngine.getEngine();
        String version = newVersionLabel.getText().replace(".", "_");
        String constructUrl
                = "https://deploy.codebb.gr/changelog/"
                + MainSettings.getInstance().getAppName()
                + "/"
                + version
                + ".html";
        if (WebUtil.urlExists(constructUrl)) {
            engine.load(constructUrl);
        } else {
            engine.load(
                    "https://deploy.codebb.gr/changelog/"
                    + MainSettings.getInstance().getAppName()
                    + "/generic.html");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void visitForum(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://forum.codebb.gr/"));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateNowButton(ActionEvent event) {
        ButtonType response
                = AlertDlg.create()
                        .message(
                                "Προσοχή! Η εκκίνηση της εφαρμογής θα τερματίσει το πρόγραμμα.\nΘέλετε να συνεχίσετε?")
                        .title("Εκκίνηση εφαρμογής Update")
                        .owner(webViewEngine.getScene().getWindow())
                        .modality(Modality.APPLICATION_MODAL)
                        .showAndWaitConfirm();
        if (response == ButtonType.OK) {
            if (SystemUtils.IS_OS_WINDOWS) { // support only for windows for now
                File f = new File("");
                ProcessBuilder pb
                        = new ProcessBuilder(
                                "cmd",
                                "/c",
                                "start",
                                "C:\\codebb\\updater\\updater.exe",
                                "--application=" + MainSettings.getInstance().getAppName(),
                                "--os=windows",
                                "--platform=" + System.getProperty("os.arch"),
                                "--applicationPath=\"" + f.getAbsolutePath() + "\"");
                pb.directory(new File("C:\\codebb\\updater"));
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
                        .message("Δεν υποστηρίζεται o updater σε άλλο περιβάλλον εκτός windows")
                        .title("Πρόβλημα")
                        .owner(null)
                        .modality(Modality.APPLICATION_MODAL)
                        .showAndWait();
            }
        }
    }

    @FXML
    private void remindLaterButton(ActionEvent event) {
        Stage stage = (Stage) webViewEngine.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void noUpdatesButton(ActionEvent event) {
        SettingsHelper.updateIntegerSetting("checkUpdates", 0);
        Stage stage = (Stage) webViewEngine.getScene().getWindow();
        stage.close();
    }
}
