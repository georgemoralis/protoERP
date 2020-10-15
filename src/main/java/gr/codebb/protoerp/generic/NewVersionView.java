/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 15/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.generic;

import gr.codebb.lib.util.WebUtil;
import gr.codebb.protoerp.MainSettings;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class NewVersionView implements Initializable {

  @FXML private WebView webViewEngine;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    WebEngine engine = webViewEngine.getEngine();
    String version = MainSettings.getInstance().getVersion().replace(".", "_");
    String constructUrl =
        "https://deploy.codebb.gr/changelog/"
            + MainSettings.getInstance().getAppName()
            + "/installed_"
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

  @FXML
  private void closeButton(ActionEvent event) {
    Stage stage = (Stage) webViewEngine.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void visitForum(ActionEvent event) {
    Desktop desktop = Desktop.getDesktop();
    try {
      desktop.browse(new URI("https://forum.codebb.gr/"));
    } catch (URISyntaxException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
