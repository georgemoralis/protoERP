/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 02/04/2021 (gmoralis) - Copied from prototype with minimal changes
 */
package gr.codebb.protoerp.tables.vat;

import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.mydata.masterdata.VatmdEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_vat")
public class VatEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String description;
  @Getter @Setter private BigDecimal vatRate;
  @Getter @Setter private Boolean active;

  @ManyToOne
  @JoinColumn(name = "mydata_vat_id")
  @Getter
  @Setter
  private VatmdEntity mydata_vat;

  @Override
  public String getComboDisplayValue() {
    return getDescription() + " (" + getVatRate() + "%)";
  }
}
