/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 19/02/2021 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.settings.tableSettings;

import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.IPreferencesStore;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.jinq.jpa.JinqJPAStreamProvider;

public class TableSaveSettings implements IPreferencesStore {

  private static TableSaveSettings instance = null;

  public static TableSaveSettings getInstance() {
    if (instance == null) {
      instance = new TableSaveSettings();
    }
    return instance;
  }

  @Override
  public String get(String key, String defaultValue) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void put(String key, String value) {
    GenericDao gdaoi1 = new GenericDao(TableSettingsEntity.class, PersistenceManager.getEmf());
    JinqJPAStreamProvider streams = new JinqJPAStreamProvider(PersistenceManager.getEmf());
    EntityManager em = PersistenceManager.getEmf().createEntityManager();
    Optional<TableSettingsEntity> result =
        streams
            .streamAll(em, TableSettingsEntity.class)
            .where(c -> c.getTableKey().equals(key))
            .findFirst();
    em.close();

    if (result.isPresent()) {
      TableSettingsEntity tmp3 = (TableSettingsEntity) result.get();
      tmp3.setTableValue(value);
      gdaoi1.updateEntity(tmp3);
    } else {
      GenericDao gdaoi2 = new GenericDao(TableSettingsEntity.class, PersistenceManager.getEmf());
      TableSettingsEntity t = new TableSettingsEntity();
      t.setTableKey(key);
      t.setTableValue(value);
      gdaoi2.createEntity(t);
    }
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void remove(String key) {
    System.out.println("remove " + key);
  }

  @Override
  public void exportSubtree(OutputStream os) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void importPreferences(InputStream is) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }
}
