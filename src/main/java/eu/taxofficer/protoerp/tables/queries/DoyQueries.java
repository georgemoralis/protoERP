/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.tables.queries;

import eu.taxofficer.protoerp.tables.entities.DoyEntity;
import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class DoyQueries {

  public static List<DoyEntity> getDoyDatabase(boolean activeonly) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<DoyEntity> results;
    if (activeonly) {
      results = streams.streamAll(em, DoyEntity.class).where(c -> c.getActive()).toList();
    } else {
      results = streams.streamAll(em, DoyEntity.class).toList();
    }

    em.close();
    return results;
  }

  public static DoyEntity getDoyByCode(String doycode) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<DoyEntity> result;

    result =
        streams.streamAll(em, DoyEntity.class).where(p -> p.getCode().equals(doycode)).findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
