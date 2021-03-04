/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 11/01/2020 (gmoralis) - Queries now using current company search
 * 26/12/2019 (gmoralis) - Added getPlantByCode
 * 25/12/2019 (gmoralis) - Added getPlantsDatabase
 */
package gr.codebb.protoerp.settings.company_plants;

import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jinq.jpa.JinqJPAStreamProvider;

public class PlantsQueries {

  public static List<PlantsEntity> getPlantsDatabase(boolean activeonly) {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity company = (CompanyEntity) session.getAttribute("company");
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<PlantsEntity> results;
    if (activeonly) {
      results =
          streams
              .streamAll(em, PlantsEntity.class)
              .where(c -> c.getActive())
              // .where(c -> c.getCompany().equals(company))
              .toList();
    } else {
      results = streams.streamAll(em, PlantsEntity.class).toList();
    }

    em.close();
    return results;
  }

  public static PlantsEntity getPlantByCode(int code) {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity company = (CompanyEntity) session.getAttribute("company");
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<PlantsEntity> result;

    result =
        streams
            .streamAll(em, PlantsEntity.class)
            .where(p -> p.getCode() == code)
            // .where(c -> c.getCompany().equals(company))
            .findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
