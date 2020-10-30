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
import javafx.util.StringConverter;

public class DisplayableStringFactory<T extends Displayable> extends StringConverter<T> {

  @Override
  public T fromString(String string) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public String toString(T object) {
    if (object == null) {
      return null;
    } else {
      return object.getComboDisplayValue();
    }
  }
}
