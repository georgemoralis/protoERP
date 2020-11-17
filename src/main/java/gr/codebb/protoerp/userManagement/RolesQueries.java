/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 17/11/2020 (georgemoralis) - Added findRoleName
 * 07/11/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class RolesQueries {

  public static List<RolesEntity> getRoles() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<RolesEntity> results = streams.streamAll(em, RolesEntity.class).toList();

    em.close();
    return results;
  }

  public static RolesEntity findRoleName(String rolename) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<RolesEntity> result;
    result =
        streams
            .streamAll(em, RolesEntity.class)
            .where(p -> p.getRoleName().equals(rolename))
            .findFirst();

    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
