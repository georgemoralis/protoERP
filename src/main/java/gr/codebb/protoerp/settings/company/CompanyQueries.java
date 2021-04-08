/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 08/04/2021 (gmoralis) - Added getCompaniesWithMitrooCodes function
 * 26/02/2021 (gmoralis) - Added getCompanies method
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class CompanyQueries {

  public static List<CompanyEntity> getCompanies() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<CompanyEntity> results = streams.streamAll(em, CompanyEntity.class).toList();
    em.close();
    return results;
  }

  public static List<CompanyEntity> getCompaniesWithMitrooCodes(boolean active) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<CompanyEntity> results =
        streams
            .streamAll(em, CompanyEntity.class)
            .where(c -> c.getMitroo_username() != null)
            .where(c -> c.getMitroo_password() != null)
            .where(c -> c.getActive())
            .where(c -> !c.equals(current)) // exclude current company from list
            .toList();
    em.close();
    return results;
  }
}
