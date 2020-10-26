/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 26/10/2020 (georgemoralis) - Added findUserByUsername
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.database.PersistenceManager;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class UserQueries {
  /**
   * findUserByUsername
   *
   * @param username
   * @return userentity
   */
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
