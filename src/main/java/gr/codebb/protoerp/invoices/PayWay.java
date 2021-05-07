/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 07/05/2021 - Initial
 */
package gr.codebb.protoerp.invoices;

public enum PayWay {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  CASH(1, "Μετρητοίς"),
  CREDIT(2, "Επι Πίστωση");

  private final int id;
  private final String name;

  private PayWay(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
