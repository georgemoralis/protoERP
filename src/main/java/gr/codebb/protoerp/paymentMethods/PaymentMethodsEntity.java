/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.paymentMethods;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.mydata.masterdata.PaymentMethodmdEntity;
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
@Table(name = "protoerp_paymentMethods")
public class PaymentMethodsEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String description;
  @Getter @Setter private Boolean active;

  @ManyToOne
  @JoinColumn(name = "mydata_paymethod_id")
  @Getter
  @Setter
  private PaymentMethodmdEntity mydata_paymethod;

  @Getter @Setter private Boolean autoPayment;

  @ManyToOne
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  @Override
  public String getComboDisplayValue() {
    return description;
  }
}
