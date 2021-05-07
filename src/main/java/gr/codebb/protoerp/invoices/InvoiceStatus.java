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

public enum InvoiceStatus {
  PLACEHOLDER_NO_USE(0, "NOUSE"),
  TEMP(1, "Προσωρινό"),
  COMPLETE(2, "Ολοκληρωμένο");

  private final int id;
  private final String name;

  private InvoiceStatus(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
