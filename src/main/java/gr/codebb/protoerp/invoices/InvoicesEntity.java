/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_invoices")
public class InvoicesEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
  private List<InvoiceLinesEntity> invoiceLines = new ArrayList<>();

  public void addInvoiceLine(InvoiceLinesEntity line) {
    invoiceLines.add(line);
    line.setInvoice(this);
  }

  public void removeInvoiceLine(InvoiceLinesEntity line) {
    invoiceLines.remove(line);
    line.setInvoice(null);
  }
}
