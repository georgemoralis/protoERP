/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/05/2021 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class TableViewUtil {

  public static void makeHeaderWrappable(TableColumn col, int sub) {
    Label label = new Label(col.getText());
    label.setStyle("-fx-padding: 8px;");
    label.setWrapText(true);
    label.setAlignment(Pos.CENTER);
    label.setTextAlignment(TextAlignment.CENTER);

    StackPane stack = new StackPane();
    stack.getChildren().add(label);
    stack.prefWidthProperty().bind(col.widthProperty().subtract(sub));
    label.prefWidthProperty().bind(stack.prefWidthProperty());
    col.setGraphic(stack);
  }
}
