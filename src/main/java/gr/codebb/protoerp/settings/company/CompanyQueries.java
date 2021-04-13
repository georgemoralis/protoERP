/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 13/03/2021 (gmoralis) - getCompanies doesn't include demo companies
 * 09/04/2021 (gmoralis) - getCompanies can return active or not active as well
 * 08/04/2021 (gmoralis) - Added getCompaniesWithMitrooCodes function
 * 26/02/2021 (gmoralis) - Added getCompanies method
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class CompanyQueries {

  public static List<CompanyEntity> getCompanies(boolean activeonly) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<CompanyEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, CompanyEntity.class)
              .where(c -> c.getActive())
              .where(c -> !c.getDemoCompany())
              .toList();
    } else {
      results = streams.streamAll(em, CompanyEntity.class).where(c -> !c.getDemoCompany()).toList();
    }
    em.close();
    return results;
  }

  public static List<CompanyEntity> getCompaniesWithMitrooCodes(boolean active) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    if (current != null) {
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
    } else {
      List<CompanyEntity> results =
          streams
              .streamAll(em, CompanyEntity.class)
              .where(c -> c.getMitroo_username() != null)
              .where(c -> c.getMitroo_password() != null)
              .where(c -> c.getActive())
              .toList();
      em.close();
      return results;
    }
  }

  public static CompanyEntity getCompanyById(long id) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<CompanyEntity> result;
    result = streams.streamAll(em, CompanyEntity.class).where(p -> p.getId() == id).findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
