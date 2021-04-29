/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 29/04/2021 - Initial
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.protoerp.items.ItemsEntity;
import gr.codebb.protoerp.mydata.masterdata.VatmdExemptionEntity;
import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsEntity;
import gr.codebb.protoerp.tables.vat.VatEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_invoiceLines")
public class InvoiceLinesEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  @Getter
  @Setter
  private InvoicesEntity invoice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  @Getter
  @Setter
  private ItemsEntity item;
  // part of itemsEntity
  @Getter @Setter private String code;
  @Getter @Setter private String barcode;

  @Getter
  @Setter
  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "measunit_id", nullable = false)
  @Getter
  @Setter
  private MeasurementUnitsEntity measureUnit;

  // part of MeasurementUnitsEntity
  @Getter @Setter private String measureShortName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vat_id", nullable = false)
  @Getter
  @Setter
  private VatEntity vat;

  // part of VatEntity
  @Getter @Setter private BigDecimal vatRate;

  @ManyToOne
  @JoinColumn(name = "vatexemp_id")
  @Getter
  @Setter
  private VatmdExemptionEntity vatExemp;

  @Getter @Setter private Integer posIndex; // line position
  @Getter @Setter private BigDecimal quantity;
  @Getter @Setter private BigDecimal unitPrice;
  @Getter @Setter private BigDecimal total;
  @Getter @Setter private BigDecimal percentDisc;
  @Getter @Setter private BigDecimal discount;
  @Getter @Setter private BigDecimal totalNoDisc;
}
