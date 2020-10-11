/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 10/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.preloader;

import gr.codebb.protoerp.MainSettings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PreloaderView implements Initializable {

  @FXML private ImageView imgLoading;
  @FXML private VBox vboxBottom;
  @FXML private Text labelWelcome;
  @FXML private Text labelSoftware;
  @FXML private Label labelClose;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    labelClose.setOnMouseClicked(
        (MouseEvent event) -> {
          Platform.exit();
          System.exit(0);
        });
    labelSoftware.setText(MainSettings.getInstance().getAppNameWithVersion());
  }

  public VBox getVboxBottom() {
    return vboxBottom;
  }

  public Text getLabelWelcome() {
    return labelWelcome;
  }

  public Text getLabelSoftware() {
    return labelSoftware;
  }
}
