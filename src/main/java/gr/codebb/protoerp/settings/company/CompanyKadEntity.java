/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 20/04/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.company;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.protoerp.settings.kad.KadEntity;
import gr.codebb.protoerp.settings.kad.KadType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "protoerp_company_kad")
public class CompanyKadEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private KadType kadType;

  @ManyToOne
  @JoinColumn(name = "kad_id")
  @Getter
  @Setter
  private KadEntity kad;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  /*
  Used for easy access in tableview
  */
  @Transient
  public String getKadTypeS() {
    return kadType.getComboDisplayValue();
  }

  @Transient
  public String getKadCodeS() {
    return kad.getCode();
  }

  @Transient
  public String getKadDescriptionS() {
    return kad.getDescription();
  }
}
