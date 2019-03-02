package business_logic.orchestration;

import business_logic.MailConsumer;
import business_logic.MailProducer;
import configuration.BeansLoader.BeanBuilder;
import exceptions.OrchestratorException;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.entities.Worker;
import services.XMLService;
import structures.MailContainer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MailOrchestrator extends AbstractOrchestrator<MailProducer, MailConsumer> {

    @Autowired
    MailContainer mailContainer;

    @Autowired
    BeanBuilder<MailConsumer> mailConsumerBeanBuilder;

    @Autowired
    BeanBuilder<MailProducer> mailProducerBeanBuilder;





    public void setThreadAmount (int threadAmount) {
        this.threadAmount = threadAmount;
    }


    public MailOrchestrator(int amount) {
        threadAmount = amount;
        executorService =  Executors.newFixedThreadPool(threadAmount);
    }


    private List <MailConsumer> setConsumers() {
        return mailConsumerBeanBuilder.load();
    }

    private List <MailProducer> setProducers() {
        return mailProducerBeanBuilder.load();
    }



    public List <Worker> getNotSentWorkersList() {
        return notSentWorkers;
    }
    public List <Worker> getdupEmailsWorkersList() {
        return dupEmailsWorkers;
    }



    @Override
    public void orchestrate() throws OrchestratorException {
        consumers = this.setConsumers();
        producers = this.setProducers();


        Set<String> notSentMails = new LinkedHashSet<>();

        int mailConsumed = 0;
        int mailProduced = 0;

        if (producers.size() != 1) {
            System.out.println("producers amount: " + producers.size());
            executorService.shutdown();
            throw new OrchestratorException("TOO MANY PRODUCERS");
        }

        Future producerFuture = executorService.submit(producers.get(0));


        List<Future> consFutureList = new ArrayList<>(threadAmount);

        for (MailConsumer mailConsumer: consumers) {
                Future currentFuture = executorService.submit(mailConsumer);
                consFutureList.add(currentFuture);
        }

        try {
            mailProduced = Integer.parseInt(producerFuture.get().toString());
        }
        catch (Exception ex) {
            if (ex.getMessage().contains("DUP_EMAILS_ERR!")) {
                setdupEmailsWorkersList(producers.get(0).getDupEmails());
            }
            executorService.shutdown();
            throw new OrchestratorException(ex);
        }

        for (Future currentNum: consFutureList) {

            try {
                mailConsumed += Integer.parseInt(currentNum.get().toString());
            }
            catch (Exception ex) {
                executorService.shutdown();
                throw new OrchestratorException(ex);
            }
        }

        for (MailConsumer consumer: consumers) {
            notSentMails.addAll(consumer.getNotSentWorkersNames());
        }


        if (mailConsumed != mailProduced){
            System.out.println("ERROR SOMETHING WAS WRONG");
            System.out.println("mails consumed: " + mailConsumed +
                                " || mails produced: " + mailProduced);
            executorService.shutdown();
            setWorkersNotSentList(notSentMails);
            throw new OrchestratorException("not all mails were sent");
        }

        else {
            System.out.println("All messages were sent");
            executorService.shutdown();
        }

    }

    private void setWorkersNotSentList (Set<String> notSentMailsList) {
        for (Worker currWorker : XMLService.getWorkerList()) {
            for (String currEmail: notSentMailsList) {
                if (currEmail.equals(currWorker.getMail()))
                    notSentWorkers.add(currWorker);
            }
        }
    }

    private void setdupEmailsWorkersList (Set<String> notSentMailsList) {
        for (String dupEmail: notSentMailsList) {
            for (Worker currWorker : XMLService.getWorkerList()) {
                if (dupEmail.equals(currWorker.getMail()))
                    dupEmailsWorkers.add(currWorker);
            }
        }
    }


}
