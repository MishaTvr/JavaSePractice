package persistence.dao;

import persistence.entities.Worker;

import java.util.Collections;
import java.util.List;

public class WorkerDAO<T,U> implements AbstractDAO<Worker, String> {

    public List<Worker> findAll() {
        return Collections.emptyList();
    }
}
