package business_logic.orchestration;

import java.util.List;
import java.util.concurrent.ExecutorService;

public abstract class AbstractOrchestrator<Producer extends Runnable,Consumer extends Runnable> {
    List<Producer> producers;
    List<Consumer> consumers;
    ExecutorService executorService;


    public abstract void orchestrate();


}
