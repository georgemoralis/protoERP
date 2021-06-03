/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 07/05/2021 - Initial
 */
package gr.codebb.protoerp.invoices;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class InvoicesQueries {

  /**
   * Return a list of all invoices (except cancelled ones) sorted default by date field desc
   *
   * @param activeonly
   * @return
   */
  public static List<InvoicesEntity> getInvoicesDatabaseDateSortedDesc() {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<InvoicesEntity> results;
    results =
        streams
            .streamAll(em, InvoicesEntity.class)
            .where(p -> p.getCompany().equals(current))
            // .where(c -> !c.getCancelled())
            .sortedDescendingBy(p -> p.getDateCreated())
            .toList();

    em.close();
    return results;
  }
}
