/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */

package eu.taxofficer.protoerp.company.queries;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import eu.taxofficer.protoerp.company.entities.CompanyPlantsEntity;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyUtil;
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

  public static CompanyPlantsEntity getPlantByCode(int code) {
    CompanyEntity current = CompanyUtil.getCurrentCompany();
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<CompanyPlantsEntity> result;
    result =
        streams
            .streamAll(em, CompanyPlantsEntity.class)
            .where(p -> p.getCode() == code)
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
