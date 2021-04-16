/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.settings.company;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protoerp_company_kad")
public class CompanyKad {

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
}
