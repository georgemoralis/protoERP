/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 16/04/2021 - Initial
 */
package gr.codebb.protoerp.settings.kad;

import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.settings.company.*;

public enum KadType implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  MAIN(1, "Κύρια"),
  EXTRA(2, "Δευτερεύουσα");

  private int id;
  private String name;

  private KadType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static KadType[] getNames() {
    /*
    return all the itemstype except for the placeholder one (used for combobox fill)
       */
    KadType[] names = new KadType[KadType.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = KadType.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }

  public static KadType fromInteger(int x) {
    switch (x) {
      case 1:
        return MAIN;
      case 2:
        return EXTRA;
    }
    return null;
  }
}
