/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 04/04/2021 - Initial
 */
package gr.codebb.protoerp.items;

import gr.codebb.lib.crud.intf.Displayable;

public enum ItemsType implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  MERCHANDISE(1, "Εμπόρευμα"),
  PRODUCT(2, "Προϊόν"),
  SERVICE(3, "Υπηρεσία");

  private int id;
  private String name;

  private ItemsType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static ItemsType[] getNames() {
    /*
      return all the itemstype except for the placeholder one (used for combobox fill)
    */
    ItemsType[] names = new ItemsType[ItemsType.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = ItemsType.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }
}
