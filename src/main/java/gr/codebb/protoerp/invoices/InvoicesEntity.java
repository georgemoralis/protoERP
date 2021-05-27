/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.protoerp.paymentMethods.PaymentMethodsEntity;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import gr.codebb.protoerp.trader.TraderPlantsEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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

  @Getter @Setter private LocalDateTime dateCreated; // imerominia ekdosis tou parastatikou

  @ManyToOne
  @JoinColumn(name = "invoiceType_id")
  @Getter
  @Setter
  private InvoiceTypesEntity invoiceType;

  @Getter @Setter private Integer invoiceNumber;
  // sxetika parastatika
  @Getter @Setter private String relativeInvoices;

  @ManyToOne
  @JoinColumn(name = "traderPlant_id")
  @Getter
  @Setter
  private TraderPlantsEntity traderPlant;

  @ManyToOne
  @JoinColumn(name = "paymethod_id")
  @Getter
  @Setter
  private PaymentMethodsEntity payMethod;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private InvoiceStatus invoiceStatus;

  @ManyToOne
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  @Getter @Setter private BigDecimal totalNoVatValue;
  @Getter @Setter private BigDecimal totalDiscount;
  @Getter @Setter private BigDecimal totalNoVatAfterDiscValue;
  @Getter @Setter private BigDecimal totalVatValue;
  @Getter @Setter private BigDecimal totalValue;
  @Getter @Setter private BigDecimal totalPayValue;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
  @Getter
  private List<InvoiceLinesEntity> invoiceLines = new ArrayList<>();

  public void addInvoiceLine(InvoiceLinesEntity line) {
    invoiceLines.add(line);
    line.setInvoice(this);
  }

  public void removeInvoiceLine(InvoiceLinesEntity line) {
    invoiceLines.remove(line);
    line.setInvoice(null);
  }

  @Transient
  public String getTypeShortNameS() {
    return invoiceType.getShortName();
  }

  @Transient
  public String getSeiraS() {
    return invoiceType.getSeira();
  }

  @Transient
  public String getSynalName() {
    return traderPlant.getTrader().getName();
  }

  @Transient
  public String getSynalVatNumber() {
    return traderPlant.getTrader().getVatNumber();
  }

  @Transient
  public LocalDate getDateOnly() {
    return dateCreated.toLocalDate();
  }

  @Transient
  public String getInvoiceStatusS() {
    return invoiceStatus.toString();
  }

  // for printing
  @Transient
  public String getClientName() {
    return traderPlant.getTrader().getName();
  }

  @Transient
  public String getClientVat() {
    return traderPlant.getTrader().getVatNumber();
  }

  @Transient
  public String getClientOccupation() {
    return traderPlant.getTrader().getJob();
  }

  @Transient
  public String getClientAddress() {
    return traderPlant.getAddress();
  }

  @Transient
  public String getClientCity() {
    return traderPlant.getCity();
  }

  @Transient
  public String getClientDoy() {
    return traderPlant
        .getTrader()
        .getDoy()
        .getName(); // fix me! each plant might have different doy
  }

  @Transient
  public String getClientPostCode() {
    return traderPlant.getTk();
  }

  @Transient
  public String getPayMethodS() {
    return payMethod.getDescription();
  }

  @Transient
  public String getComments() {
    return ""; // TODO;
  }

  @Transient
  public String getPrintType() {
    return "";
  }
}