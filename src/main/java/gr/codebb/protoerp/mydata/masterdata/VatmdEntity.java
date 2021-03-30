/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 30/03/2021 (gmoralis) - Copied from prototype with minimal changes
 */
package gr.codebb.protoerp.mydata.masterdata;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_mydata_vat")
public class VatmdEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private Integer code;
  @Getter @Setter private String description;
  @Getter @Setter private BigDecimal vatRate;
  @Getter @Setter private Boolean active;

  @Override
  public String getComboDisplayValue() {
    return description;
  }
}
