package swt6.orm.dao;

import swt6.orm.dao.interfaces.BaseDAO;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseDAOImpl<T> implements BaseDAO<T> {
    @Override
    public void insert(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.merge(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public T update(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            JpaUtil.commit();
            return entity;
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public void delete(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public T get(Class<T> clazz, long id) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            T result = em.find(clazz, id);
            JpaUtil.commit();
            return result;
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public List<Object[]> getFromQuery(String query) {
        List<Object[]> results;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(query, Object[].class).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }
}
