/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 04/03/2021 (gmoralis) - Added interDescription
 * 25/02/2021 (gmoralis) - Ported from prototype
 * 05/01/2020 (gmoralis) - Added countryType (to seperate countries of E.E. and others
 * 26/12/2019 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.countries;

import gr.codebb.lib.crud.intf.Displayable;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "countries")
public class CountriesEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String code;
  @Getter @Setter private String description;
  @Getter @Setter private String interDescription;

  @Enumerated(EnumType.ORDINAL)
  @Getter
  @Setter
  private CountryType countryType;

  @Getter @Setter private Boolean active;

  @Override
  public String getComboDisplayValue() {
    return getCode() + " - " + getDescription();
  }
}
