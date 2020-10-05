/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/10/2020 (georgemoralis) - Added load function
 */
package gr.codebb.lib.util;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FxmlUtil {

  /**
   * Load a Result
   *
   * @param <T>
   */
  public static class LoadResult<T> {

    Parent parent;
    private T controller;

    public T getController() {
      return controller;
    }

    public void setController(T controller) {
      this.controller = controller;
    }

    public Parent getParent() {
      return parent;
    }
  }

  /**
   * load me from the given fxml fileName
   *
   * @param <T>
   * @param fxmlFileName
   * @return the load Result
   */
  public static <T> LoadResult<T> load(String fxmlFileName) {
    try {
      LoadResult<T> loadResult = new LoadResult<>();
      URL fxml = FxmlUtil.class.getResource(fxmlFileName);
      FXMLLoader fxmlLoader = new FXMLLoader(fxml);
      loadResult.parent = fxmlLoader.load();
      loadResult.setController(fxmlLoader.getController());
      return loadResult;
    } catch (IOException th) {
      th.printStackTrace();
    }
    return null;
  }
}
