package persistence.dao;

import java.util.List;

public interface AbstractDAO<T,U> {

    public List<T> findAll();
}
