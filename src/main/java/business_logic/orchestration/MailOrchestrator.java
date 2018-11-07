package business_logic.orchestration;

import business_logic.MailConsumer;
import business_logic.MailProducer;
import structures.MailContainer;

import java.util.List;
import java.util.concurrent.Executors;


public class MailOrchestrator extends AbstractOrchestrator<MailProducer, MailConsumer> {

    MailContainer mailContainer;

    public MailOrchestrator(MailContainer mailContainer, List<MailProducer> producers, List<MailConsumer> consumers) {
        this.mailContainer = mailContainer;
        this.consumers = consumers;
        this.producers = producers;
        //executorService =  Executors.newFixedThreadPool(4);
    }

    @Override
    public void orchestrate() {


    }
}
