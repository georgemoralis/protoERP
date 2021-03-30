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
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.lib.crud.intf.Displayable;
import gr.codebb.protoerp.mydata.masterdata.MeasureUnitmdEntity;
import gr.codebb.protoerp.settings.company.CompanyEntity;
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
@Table(name = "protoerp_measurement_units")
public class MeasurementUnitsEntity implements Serializable, Displayable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;

  @Getter @Setter private String description;
  @Getter @Setter private String shortName;
  @Getter @Setter private Boolean active;

  @ManyToOne
  @JoinColumn(name = "mydata_measunit_id")
  @Getter
  @Setter
  private MeasureUnitmdEntity mydata_measureUnit;

  @ManyToOne
  @JoinColumn(name = "company_id")
  @Getter
  @Setter
  private CompanyEntity company;

  @Override
  public String getComboDisplayValue() {
    return getDescription() + " (" + getShortName() + ")";
  }
}
