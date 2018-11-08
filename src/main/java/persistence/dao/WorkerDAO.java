package persistence.dao;

import persistence.entities.Worker;
import services.XMLService;


import java.util.List;


public class WorkerDAO<T,U> implements AbstractDAO<Worker, String> {


    public List<Worker> findAll() {
        return XMLService.createWorkerList();
    }
}
