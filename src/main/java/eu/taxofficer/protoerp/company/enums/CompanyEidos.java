/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */

package eu.taxofficer.protoerp.company.enums;

import gr.codebb.lib.crud.intf.Displayable;

public enum CompanyEidos implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  NATURAL(1, "Φυσικό πρόσωπο"),
  NOTNATURAL(2, "Μη Φυσικό πρόσωπο");

  private int id;
  private String name;

  private CompanyEidos(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static CompanyEidos[] getNames() {
    /*
    return all the itemstype except for the placeholder one (used for combobox fill)
         */
    CompanyEidos[] names = new CompanyEidos[CompanyEidos.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = CompanyEidos.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }

  public static CompanyEidos fromInteger(int x) {
    switch (x) {
      case 1:
        return NATURAL;
      case 2:
        return NOTNATURAL;
    }
    return null;
  }
}
