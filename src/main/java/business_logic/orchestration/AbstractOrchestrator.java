package business_logic.orchestration;

import persistence.entities.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class AbstractOrchestrator<Producer extends Callable<Integer>,Consumer extends Callable<Integer>>  {
    List<Producer> producers;
    List<Consumer> consumers;
    ExecutorService executorService;
    int threadAmount = 4;
    List<Worker> notSentWorkers = new ArrayList<>();

    public abstract void orchestrate() throws Exception;


}
