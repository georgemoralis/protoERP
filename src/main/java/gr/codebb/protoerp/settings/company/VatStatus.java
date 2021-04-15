/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 15/04/2021 - Initial
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.lib.crud.intf.Displayable;

public enum VatStatus implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  NORMAL(1, "Κανονικό");

  private int id;
  private String name;

  private VatStatus(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static VatStatus[] getNames() {
    /*
      return all the itemstype except for the placeholder one (used for combobox fill)
    */
    VatStatus[] names = new VatStatus[VatStatus.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = VatStatus.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }
}
