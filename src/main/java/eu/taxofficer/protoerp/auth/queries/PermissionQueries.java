/*
 * copyright 2021
 * taxofficer.eu
 * ProtoERP - Open source invocing program
 * protoERP@taxofficer.eu
 */
package eu.taxofficer.protoerp.auth.queries;

import eu.taxofficer.protoerp.auth.entities.PermissionEntity;
import gr.codebb.lib.database.PersistenceManager;
import java.util.List;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

/** @author George Moralis */
public class PermissionQueries {
  public static List<PermissionEntity> getPermissions() {
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    List<PermissionEntity> results = streams.streamAll(em, PermissionEntity.class).toList();
    em.close();
    return results;
  }
}
