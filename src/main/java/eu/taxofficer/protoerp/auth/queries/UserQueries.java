/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.queries;

import eu.taxofficer.protoerp.auth.entities.UserEntity;
import gr.codebb.lib.database.PersistenceManager;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

/** @author George Moralis */
public class UserQueries {

  public static UserEntity findUserByUsername(String username) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<UserEntity> result;
    result =
        streams
            .streamAll(em, UserEntity.class)
            .where(p -> p.getUsername().equals(username))
            .findFirst();

    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
