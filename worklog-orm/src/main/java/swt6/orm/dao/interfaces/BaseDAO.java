package swt6.orm.dao.interfaces;

import java.util.List;

public interface BaseDAO<T> {
    void insert(T entity);
    T update(T entity);
    void delete(T entity);
    T getById(Class<T> clazz, long id);
    List<T> getAll(Class<T> clazz);
    //List<Object[]> getFromQuery(String query);
}
