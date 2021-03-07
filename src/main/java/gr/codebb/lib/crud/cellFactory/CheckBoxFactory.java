/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/03/2021 (georgemoralis) - Added from prototype
 */
package gr.codebb.lib.crud.cellFactory;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CheckBoxFactory<T> {

  public Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>> cellFactory =
      (final TableColumn<T, Boolean> param) -> {
        final CheckBox checkBox = new CheckBox();
        checkBox.setDisable(true);
        final TableCell<T, Boolean> cell =
            new TableCell<T, Boolean>() {
              @Override
              public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (isEmpty()) {
                  setGraphic(null);
                } else {
                  setGraphic(checkBox);
                  checkBox.setSelected(item);
                }
              }
            };
        cell.setGraphic(checkBox);
        cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        cell.setAlignment(Pos.CENTER);
        return cell;
      };
}
