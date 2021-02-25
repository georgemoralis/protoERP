/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/**
 * changelog ========= 25/02/2021 (gmoralis) - Ported from prototype 05/01/2020 (gmoralis) - Initial
 * commit
 */
package gr.codebb.protoerp.settings.countries;

import gr.codebb.lib.crud.intf.Displayable;

/** @author shadow */
public enum CountryType implements Displayable {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  EUROPE(1, "Εντός ευρωπαϊκής ένωσης"),
  THIRDCOUNTRY(2, "Τρίτη χώρα");

  private int id;
  private String name;

  private CountryType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static CountryType[] getNames() {
    /*
      return all the itemstype except for the placeholder one (used for combobox fill)
    */
    CountryType[] names = new CountryType[CountryType.values().length - 1];
    for (int i = 0; i < names.length; i++) {
      names[i] = CountryType.values()[i + 1];
    }
    return names;
  }

  @Override
  public String getComboDisplayValue() {
    return name;
  }
}
