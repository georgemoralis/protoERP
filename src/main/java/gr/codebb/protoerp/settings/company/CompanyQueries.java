/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
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
}
