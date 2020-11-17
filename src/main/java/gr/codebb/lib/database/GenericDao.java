/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
 /*
 * Changelog
 * =========
 * 17/11/2020 (georgemoralis) - Added delete functions
 * 15/10/2020 (georgemoralis) - Added find functions
 * 13/10/2020 (georgemoralis) - Added update functions
 * 13/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.lib.database;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class GenericDao<T> implements Serializable {

    @PersistenceContext
    private EntityManager em;
    EntityManagerFactory emf;
    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass, EntityManagerFactory emf) {
        this.entityClass = entityClass;
        this.emf = emf;
    }

    public T createEntity(T entity) {
        beginTransaction();
        create(entity);
        commitAndCloseTransaction();
        return entity;
    }

    public void beginTransaction() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
    }

    private void create(T entity) {
        em.persist(entity);
    }

    public void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    private void commit() {
        em.getTransaction().commit();
    }

    private void closeTransaction() {
        em.close();
    }

    public T updateEntity(T entity) {
        beginTransaction();
        update(entity);
        commitAndCloseTransaction();
        return entity;
    }

    private T update(T entity) {
        return em.merge(entity);
    }

    private T find(long entityID) {
        return em.find(entityClass, entityID);
    }

    public T findEntity(long entityId) {
        beginTransaction();
        T user = find(entityId);
        commitAndCloseTransaction();
        return user;
    }

    private T findReferenceOnly(long entityID) {
        return em.getReference(entityClass, entityID);
    }

    public void deleteEntity(long id) {
        beginTransaction();
        T persistedEntity = findReferenceOnly(id);
        em.remove(persistedEntity);
        commitAndCloseTransaction();
    }
}
