/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.invoices;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

public class VatCalculation {
  @Getter @Setter private BigDecimal valueNoVat;
  @Getter @Setter private BigDecimal vatCategory;
  @Getter @Setter private BigDecimal vatSum;
}
