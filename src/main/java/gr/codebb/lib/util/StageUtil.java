/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 11/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class StageUtil {

  public static Stage setStageSettings(
      String title, Scene scene, Modality modality, Window owner, StageStyle style) {
    Stage stage = new Stage();
    stage.setTitle(title);
    stage.setScene(scene);
    if (modality != null) {
      stage.initModality(modality);
    }
    if (style != null) {
      stage.initStyle(style);
    }
    if (owner != null) {
      stage.initOwner(owner);
    }

    return stage;
  }

  public static Stage setStageSettings(
      String title, Scene scene, Modality modality, Window owner, StageStyle style, String icon) {
    Stage stage = setStageSettings(title, scene, modality, owner, style);
    stage.getIcons().add(new Image(StageUtil.class.getResource(icon).toString()));
    return stage;
  }
}
