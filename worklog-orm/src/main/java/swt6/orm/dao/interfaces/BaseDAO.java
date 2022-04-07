package swt6.orm.dao.interfaces;

public interface BaseDAO<T> {
    void insert(T entity);
    T update(T entity);
    void delete(T entity);
    T get(Class<T> clazz, long id);
}
