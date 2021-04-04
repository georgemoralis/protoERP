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

import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsEntity;
import gr.codebb.protoerp.tables.vat.VatEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_items")
public class ItemsEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String code;
  @Getter @Setter private String barcode;

  @Getter
  @Setter
  @Column(columnDefinition = "TEXT")
  private String description;

  @Getter @Setter private String notes;
  @Getter @Setter private Boolean active;

  @ManyToOne
  @JoinColumn(name = "vatsell_id")
  @Getter
  @Setter
  private VatEntity vatSell;

  @ManyToOne
  @JoinColumn(name = "measunit_id")
  @Getter
  @Setter
  private MeasurementUnitsEntity measureUnit;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private ItemsType itemType;

  @Getter @Setter private BigDecimal sellPrice;
}
