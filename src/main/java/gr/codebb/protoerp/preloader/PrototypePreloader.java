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

import gr.codebb.fxanimations.FadeInLeftTransition;
import gr.codebb.fxanimations.FadeInRightTransition;
import gr.codebb.fxanimations.FadeInTransition;
import java.io.IOException;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrototypePreloader extends Preloader {

  private Stage preloaderStage;
  private Scene scene;

  PreloaderView controller;

  public PrototypePreloader() {
    // Constructor is called before everything.
  }

  @Override
  public void init() throws Exception {
    Parent root = null;
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/fxml/generic/Preloader.fxml"));
      root = (Parent) fxmlLoader.load();
      controller = fxmlLoader.<PreloaderView>getController();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    scene = new Scene(root);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.preloaderStage = primaryStage;

    // Set preloader scene and show stage.
    preloaderStage.setScene(scene);
    preloaderStage.initStyle(StageStyle.UNDECORATED);
    // Application icons
    Image image = new Image("/img/protoerp.png");
    preloaderStage.getIcons().addAll(image);
    preloaderStage.show();
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification info) {
    // Handle state change notifications.
    StateChangeNotification.Type type = info.getType();
    switch (type) {
      case BEFORE_LOAD:
        // Called after MyPreloader#start is called.
        new FadeInLeftTransition(controller.getLabelWelcome()).play();
        new FadeInRightTransition(controller.getLabelSoftware()).play();
        new FadeInTransition(controller.getVboxBottom()).play();
        break;
      case BEFORE_INIT:
        // Called before MyApplication#init is called.
        break;
      case BEFORE_START:
        // Called after MyApplication#init and before MyApplication#start is called.
        preloaderStage.hide();
        break;
    }
  }
}
