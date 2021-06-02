/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.company;

import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.countries.CountriesEntity;
import gr.codebb.protoerp.settings.doy.DoyEntity;
import java.io.Serializable;
import javax.persistence.Entity;
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
@Table(name = "company_plants")
public class PlantsEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private Integer code;
  @Getter @Setter private String description;
  /** Στοιχεία εγκατάστασης */
  @Getter @Setter private String address;

  @Getter @Setter private String area;
  @Getter @Setter private String tk;
  @Getter @Setter private String city;
  @Getter @Setter private String phone;
  @Getter @Setter private String fax;
  /** References */
  @ManyToOne
  @JoinColumn(name = "country_id")
  @Getter
  @Setter
  private CountriesEntity country;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  @ManyToOne
  @JoinColumn(name = "doy_id")
  @Getter
  @Setter
  private DoyEntity doy;

  @Getter @Setter private Boolean active;

  @Override
  public String getComboDisplayValue() {
    return getCode() + " - " + getDescription();
  }
}
