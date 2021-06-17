/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 30/10/2020 (georgemoralis) - Added getUsers
 * 26/10/2020 (georgemoralis) - Added findUserByUsername
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
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
  public static UsersEntity findUserByUsername(String username) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<UsersEntity> result;
    result =
        streams
            .streamAll(em, UsersEntity.class)
            .where(p -> p.getUsername().equals(username))
            .findFirst();

    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  public static List<UsersEntity> getUsers() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<UsersEntity> results = streams.streamAll(em, UsersEntity.class).toList();

    em.close();
    return results;
  }
}
