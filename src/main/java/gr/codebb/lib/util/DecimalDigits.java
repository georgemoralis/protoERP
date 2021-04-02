/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 02/04/2021 (gmoralis) - Copied from prototype
 */
package gr.codebb.lib.util;

import gr.codebb.protoerp.settings.SettingsHelper;

public enum DecimalDigits {
  VALUES("valuesDecimal"),
  UNIT("unitDecimal"),
  QUANTITY("quantityDecimal"),
  PERCENT_DISC("percentDiscountDecimal"),
  PERCENT_VAT("percentVatDecimal");

  private final String settingname;

  private DecimalDigits(String settingname) {
    this.settingname = settingname;
  }

  public static String getDecimalFormat(String setting) {
    int size = Integer.parseInt(SettingsHelper.loadStringSetting(setting));
    switch (size) {
      case 0:
        return "#,##0";
      case 1:
        return "#,##0.0";
      case 2:
        return "#,##0.00";
      case 3:
        return "#,##0.000";
      case 4:
        return "#,##0.0000";
      default: // just return 2 digits in rare case that happends
        return "#,##0.00";
    }
  }

  public static int getDecimalSize(String setting) {
    int size = Integer.parseInt(SettingsHelper.loadStringSetting(setting));
    return size;
  }

  public String getSettingName() {
    return settingname;
  }
}
