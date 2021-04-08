/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.tables.InvoiceTypes;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_invoice_types")
public class InvoiceTypesEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String code;
  @Getter @Setter private String name;
  @Getter @Setter private String shortName;
  @Getter @Setter private String seira;
  @Getter @Setter private Integer number;
  @Getter @Setter private Boolean active;

  @Override
  public String getComboDisplayValue() {
    if (seira.length() == 0) {
      return shortName + " - " + name;
    } else {
      return shortName + " - " + name + " (ΣΕΙΡΑ : " + seira + " )";
    }
  }
}
