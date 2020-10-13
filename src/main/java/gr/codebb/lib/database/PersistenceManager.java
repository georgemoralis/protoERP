/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/10/2020 (georgemoralis) - Fixed factory return field
 * 13/10/2020 (georgemoralis) - Added getEmf function
 * 09/10/2020 (georgemoralis) - Added databaseProperties
 * 09/10/2020 (georgemoralis) - Initial , added createEntityManager
 */
package gr.codebb.lib.database;

import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

  private static EntityManagerFactory factory = null;

  public static EntityManagerFactory createEntityManager(Map databaseProperties) {
    factory = Persistence.createEntityManagerFactory("jpaData", databaseProperties);
    return factory;
  }

  public static EntityManagerFactory getEmf() {
    return factory;
  }
}
