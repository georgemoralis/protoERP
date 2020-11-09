/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 07/11/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class RolesQueries {

  public static List<RoleEntity> getRoles() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<RoleEntity> results = streams.streamAll(em, RoleEntity.class).toList();

    em.close();
    return results;
  }
}
