/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/10/2020 (georgemoralis) - Set application title
 * 05/10/2020 (georgemoralis) - Creation of main window
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp;

import static javafx.application.Application.launch;

import gr.codebb.lib.util.FxmlUtil;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.MasterDetailPane;

public class App extends Application {

  @Override
  public void init() throws Exception {
    super.init();
  }

  @Override
  public void start(Stage stage) throws Exception {
    FxmlUtil.LoadResult<MainAppView> getMainView = FxmlUtil.load("/fxml/generic/MainApp.fxml");
    FxmlUtil.LoadResult<LeftSideMenuView> getSideMenuView =
        FxmlUtil.load("/fxml/generic/LeftSideMenu.fxml");
    BorderPane menu = new BorderPane();
    menu.setCenter(getSideMenuView.getParent());
    getSideMenuView
        .getController()
        .setMainDetachPane(getMainView.getController().getMainDetachPane());

    MasterDetailPane masterDetailPane =
        new MasterDetailPane(Side.LEFT, getMainView.getParent(), menu, true);
    masterDetailPane.setDividerPosition(0.191);
    masterDetailPane
        .getStylesheets()
        .add(App.class.getResource("/styles/bootstrap3.css").toExternalForm());

    getMainView.getController().setMasterPane(masterDetailPane);

    masterDetailPane.setMinSize(1168.0, 784.0);

    final Scene scene = new Scene(masterDetailPane);

    stage.setTitle(MainSettings.getInstance().getAppNameWithVersion());
    // Application icons
    Image image = new Image("/img/protoerp-logo.png");
    stage.getIcons().addAll(image);
    scene.getStylesheets().add("/styles/bootstrap3.css");
    stage.setScene(scene);
    stage.sizeToScene();
    stage.toFront();

    stage.setFullScreenExitHint("Πατήστε ESC για να βγείτε από την κατάσταση πλήρης οθόνης");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
