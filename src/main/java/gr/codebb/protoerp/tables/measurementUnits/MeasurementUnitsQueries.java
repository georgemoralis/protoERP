/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 31/03/2021 (gmoralis) - Copied from prototype with minimal changes
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class MeasurementUnitsQueries {

  public static List<MeasurementUnitsEntity> getMeasurementsUnitsDatabase(boolean activeonly) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<MeasurementUnitsEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, MeasurementUnitsEntity.class)
              .where(c -> c.getActive())
              .where(p -> p.getCompany().equals(current))
              .toList();
    } else {
      results =
          streams
              .streamAll(em, MeasurementUnitsEntity.class)
              .where(p -> p.getCompany().equals(current))
              .toList();
    }

    em.close();
    return results;
  }

  public static MeasurementUnitsEntity getMeasurementUnitByShortName(String shortName) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<MeasurementUnitsEntity> result;

    result =
        streams
            .streamAll(em, MeasurementUnitsEntity.class)
            .where(p -> p.getShortName().equals(shortName))
            .where(p -> p.getCompany().equals(current))
            .findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
