/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.queries;

import eu.taxofficer.protoerp.auth.entities.RoleEntity;
import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

/** @author George Moralis */
public class RoleQueries {
  public static List<RoleEntity> getRoles() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<RoleEntity> results = streams.streamAll(em, RoleEntity.class).toList();

    em.close();
    return results;
  }

  public static RoleEntity findRoleName(String rolename) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<RoleEntity> result;
    result =
        streams
            .streamAll(em, RoleEntity.class)
            .where(p -> p.getName().equals(rolename))
            .findFirst();

    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
