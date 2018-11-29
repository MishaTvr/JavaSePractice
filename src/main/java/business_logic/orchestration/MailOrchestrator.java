package business_logic.orchestration;

import business_logic.MailConsumer;
import business_logic.MailProducer;
import persistence.entities.Worker;
import services.XMLService;
import structures.MailContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MailOrchestrator extends AbstractOrchestrator<MailProducer, MailConsumer> {

    private MailContainer mailContainer;

    public void setThreadAmount (int threadAmount) {
        this.threadAmount = threadAmount;
    }

    public MailOrchestrator(MailContainer mailContainer, List<MailProducer> producers, List<MailConsumer> consumers) {
        this.mailContainer = mailContainer;
        this.consumers = consumers;
        this.producers = producers;
        executorService =  Executors.newFixedThreadPool(threadAmount);
    }


    public List <Worker> getNotSentWorkersList() {
        return notSentWorkers;
    }




    public static String parseException(String input) {
        int index = input.indexOf("Exception: ") + "Exception: ".length();
        return input.substring(index);
    }




    @Override
    public void orchestrate() throws Exception {
        List<String> notSentMails = new ArrayList<>();
        int mailConsumed = 0;
        int mailProduced = 0;

        if (producers.size() != 1) {
            System.out.println(producers.size());
            System.out.println("ERROR: TOO MUCH PRODUCERS");
            System.exit(1);
        }

        Future producerFuture = executorService.submit(producers.get(0));


        List<Future> consFutureList = new ArrayList<>(threadAmount);

        for (MailConsumer mailConsumer: consumers) {
                Future currentFuture = executorService.submit(mailConsumer);
                consFutureList.add(currentFuture);
        }

        mailProduced = Integer.parseInt(producerFuture.get().toString());

        for (Future currentNum: consFutureList) {

            try {
                mailConsumed += Integer.parseInt(currentNum.get().toString());
            }
            catch (ExecutionException ex) {
                if ("sender".equals(ex.getCause().getMessage())) {
                    String report = ex.getCause().getCause().getMessage();
                    notSentMails.add(report);
                }
            }

        }


        if (mailConsumed != mailProduced){
            System.out.println("ERROR SOMETHING WAS WRONG");
            System.out.println("mails consumed: " + mailConsumed +
                                " || mails produced: " + mailProduced);
            executorService.shutdown();
            setWorkersNotSentList(notSentMails);
            throw new Exception("not all mails were sent");
        }

        else {
            System.out.println("All messages were sent");
            executorService.shutdown();
        }

    }

    private void setWorkersNotSentList (List<String> notSentMailsList) {

        for (Worker currWorker : XMLService.getWorkerList()) {
            for (String currEmail: notSentMailsList) {
                if (currEmail.equals(currWorker.getMail()))
                    notSentWorkers.add(currWorker);

            }
        }

    }


}
