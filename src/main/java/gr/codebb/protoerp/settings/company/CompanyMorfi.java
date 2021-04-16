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
package gr.codebb.protoerp.settings.company;

import gr.codebb.lib.crud.intf.Displayable;

public enum CompanyMorfi implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  AE(1, "A.E.");

  private int id;
  private String name;

  private CompanyMorfi(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static CompanyMorfi[] getNames() {
    /*
    return all the itemstype except for the placeholder one (used for combobox fill)
         */
    CompanyMorfi[] names = new CompanyMorfi[CompanyMorfi.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = CompanyMorfi.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }

  public static CompanyMorfi fromInteger(int x) {
    switch (x) {
      case 1:
        return AE;
    }
    return null;
  }
}
