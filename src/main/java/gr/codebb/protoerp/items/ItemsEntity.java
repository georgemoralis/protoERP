/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 07/04/2021 - Added transient parameters for use in tableviews
 * 05/04/2021 - Added company and vatExemption variables
 * 04/04/2021 - Initial
 */
package gr.codebb.protoerp.items;

import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.mydata.masterdata.VatmdExemptionEntity;
import gr.codebb.protoerp.settings.company.CompanyEntity;
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
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_items")
public class ItemsEntity implements Serializable, Displayable {

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
  @JoinColumn(name = "vatexemp_id")
  @Getter
  @Setter
  private VatmdExemptionEntity vatExemp;

  @ManyToOne
  @JoinColumn(name = "measunit_id")
  @Getter
  @Setter
  private MeasurementUnitsEntity measureUnit;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private ItemsType itemType;

  @ManyToOne
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  @Getter @Setter private BigDecimal sellPrice;

  /*
  Used for easy access in tableview
  */
  @Transient
  public String getItemTypeS() {
    return itemType.getComboDisplayValue();
  }

  @Transient
  public String getMeasureUnitS() {
    return measureUnit.getDescription();
  }

  @Transient
  public BigDecimal getSellVatRate() {
    return vatSell.getVatRate();
  }

  @Override
  public String getComboDisplayValue() {
    return description;
  }
}
