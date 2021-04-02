/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 02/04/2021 (gmoralis) - Copied from prototype with minimal changes
 */
package gr.codebb.protoerp.tables.vat;

import gr.codebb.lib.database.PersistenceManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class VatQueries {

  public static List<VatEntity> getVatDatabase(boolean activeonly) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<VatEntity> results;
    if (activeonly) {
      results = streams.streamAll(em, VatEntity.class).where(c -> c.getActive()).toList();
    } else {
      results = streams.streamAll(em, VatEntity.class).toList();
    }

    em.close();
    return results;
  }

  public static VatEntity getVatFromVatRate(BigDecimal vatRate) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<VatEntity> result;
    result =
        streams
            .streamAll(em, VatEntity.class)
            .where(c -> c.getActive())
            .where(p -> p.getVatRate().equals(vatRate))
            .findFirst();

    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
