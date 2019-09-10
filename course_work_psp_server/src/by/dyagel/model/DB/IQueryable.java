package by.dyagel.model.DB;

import java.sql.ResultSet;

public interface IQueryable<T> {
    void add(T entity);
    ResultSet getAll();
    void remove(int id);
    void update(T entity);
}
