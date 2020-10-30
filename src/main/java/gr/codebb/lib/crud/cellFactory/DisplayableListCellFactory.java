/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 30/10/2020 (georgemoralis) - Added from prototype
 */
package gr.codebb.lib.crud.cellFactory;

import gr.codebb.lib.crud.intf.Displayable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * A generic cell factory for classes that implement Displayable. Does not show any icon.
 *
 * @param <T> The Displayable implementing type.
 */
public class DisplayableListCellFactory<T extends Displayable>
    implements Callback<ListView<T>, ListCell<T>> {

  @Override
  public ListCell<T> call(ListView<T> p) {
    return new ListCell<T>() {
      @Override
      protected void updateItem(T displayable, boolean empty) {
        super.updateItem(displayable, empty);
        if (displayable != null) {
          setText(displayable.getComboDisplayValue());
        } else {
          setText(null);
        }
      }
    };
  }

  /**
   * Configure a ComboBox with elements implementing Displayable with a cell factory to render the
   * display value.
   *
   * @param comboBox The combo box. Required.
   * @throws NullPointerException If comboBox is null.
   */
  public static void setComboBoxCellFactory(ComboBox<? extends Displayable> comboBox) {
    DisplayableListCellFactory cellFactory = new DisplayableListCellFactory();
    DisplayableStringFactory coverter = new DisplayableStringFactory();
    comboBox.setCellFactory(cellFactory);
    comboBox.setConverter(coverter);
    comboBox.setButtonCell(cellFactory.call(null));
  }
}
