/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 25/02/2021 (gmoralis) - Ported from prototype
 * 26/12/2019 (gmoralis) - Added getCountryByCode
 * 26/12/2019 (gmoralis) - Added getCountriesDatabase
 */
package gr.codebb.protoerp.settings.countries;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class CountriesQueries {

  public static List<CountriesEntity> getCountriesDatabase(boolean activeonly) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<CountriesEntity> results;
    if (activeonly) {
      results = streams.streamAll(em, CountriesEntity.class).where(c -> c.getActive()).toList();
    } else {
      results = streams.streamAll(em, CountriesEntity.class).toList();
    }

    em.close();
    return results;
  }

  public static CountriesEntity getCountryByCode(String country) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<CountriesEntity> result;

    result =
        streams
            .streamAll(em, CountriesEntity.class)
            .where(p -> p.getCode().equals(country))
            .findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
