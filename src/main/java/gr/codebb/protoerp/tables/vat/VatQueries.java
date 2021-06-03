/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/04/2021 (gmoralis) - Added company to query results
 * 02/04/2021 (gmoralis) - Copied from prototype with minimal changes
 */
package gr.codebb.protoerp.tables.vat;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class VatQueries {

  public static List<VatEntity> getVatDatabase(boolean activeonly) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<VatEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, VatEntity.class)
              .where(c -> c.getActive())
              .where(p -> p.getCompany().equals(current))
              .toList();
    } else {
      results =
          streams
              .streamAll(em, VatEntity.class)
              .where(p -> p.getCompany().equals(current))
              .toList();
    }

    em.close();
    return results;
  }

  public static VatEntity getVatFromVatRate(BigDecimal vatRate) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<VatEntity> result;
    result =
        streams
            .streamAll(em, VatEntity.class)
            .where(c -> c.getActive())
            .where(p -> p.getVatRate().equals(vatRate))
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
