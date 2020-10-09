/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 09/10/2020 (georgemoralis) - Initial , added createEntityManager
 */
package gr.codebb.lib.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersistenceManager {

  private static EntityManagerFactory factory = null;

  public static EntityManagerFactory createEntityManager() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpaData");
    return factory;
  }
}
