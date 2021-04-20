/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 20/04/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.kad;

import gr.codebb.lib.database.PersistenceManager;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class KadQueries {
  public static KadEntity getKadByCode(String code) {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<KadEntity> result;

    result =
        streams.streamAll(em, KadEntity.class).where(p -> p.getCode().equals(code)).findFirst();
    em.close();
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }
}
