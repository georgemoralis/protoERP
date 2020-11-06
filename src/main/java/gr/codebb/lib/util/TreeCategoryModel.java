/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import javafx.scene.Parent;
import lombok.Getter;

public class TreeCategoryModel<T> {

  private String name;
  @Getter private Class<T> viewClass;
  @Getter private String fxmlPath;

  public TreeCategoryModel(String name, Class<T> viewClass, String fxmlPath) {
    this.name = name;
    this.viewClass = viewClass;
    this.fxmlPath = fxmlPath;
  }

  public Parent loadFxml() {
    FxmlUtil.LoadResult<T> getWindow = FxmlUtil.load(fxmlPath);
    return getWindow.getParent();
  }

  @Override
  public String toString() {
    return name;
  }
}
