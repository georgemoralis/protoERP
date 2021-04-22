/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 22/04/2020 (gmoralis) - Initial
 */
package gr.codebb.protoerp.tables.InvoiceTypes;

import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class InvoiceTypesQueries {

  public static List<InvoiceTypesEntity> getInvoiceTypesDatabasePerCompany(boolean activeonly) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<InvoiceTypesEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, InvoiceTypesEntity.class)
              .where(c -> c.getActive())
              .where(c -> c.getCompanyplant().getCompany().equals(current))
              .toList();
    } else {
      results =
          streams
              .streamAll(em, InvoiceTypesEntity.class)
              .where(c -> c.getCompanyplant().getCompany().equals(current))
              .toList();
    }

    em.close();
    return results;
  }
}
