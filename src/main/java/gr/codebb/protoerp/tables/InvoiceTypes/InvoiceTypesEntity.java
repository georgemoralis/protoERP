/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 13/05/2021 (gmoralis) - Added invoiceType for mydata
 * 22/04/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.tables.InvoiceTypes;

import eu.taxofficer.protoerp.company.PlantsEntity;
import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.mydata.masterdata.InvoiceTypemdEntity;
import java.io.Serializable;
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

  @ManyToOne
  @JoinColumn(name = "company_plant_id")
  @Getter
  @Setter
  private PlantsEntity companyplant;

  @ManyToOne
  @JoinColumn(name = "mydata_invoiceType_id")
  @Getter
  @Setter
  private InvoiceTypemdEntity mydata_invoiceType;

  @Override
  public String getComboDisplayValue() {
    if (seira.length() == 0) {
      return shortName + " - " + name;
    } else {
      return shortName + " - " + name + " (ΣΕΙΡΑ : " + seira + " )";
    }
  }

  public String getPlantS() {
    return companyplant.getComboDisplayValue();
  }

  @Getter @Setter private String printFormVer;
}
