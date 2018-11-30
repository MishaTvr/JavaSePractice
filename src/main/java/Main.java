import business_logic.MailConsumer;
import business_logic.MailProducer;
import business_logic.orchestration.MailOrchestrator;
import persistence.entities.Worker;
import structures.MailContainer;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MailContainer pochtaRossii = new MailContainer(7);
        MailConsumer consumer1 = new MailConsumer(pochtaRossii);
        MailConsumer consumer2 = new MailConsumer(pochtaRossii);
        MailConsumer consumer3 = new MailConsumer(pochtaRossii);
        MailProducer producer = new MailProducer(pochtaRossii);

        List<MailProducer> producerList = new ArrayList<>();
        producerList.add(producer);

        List<MailConsumer> consumerList = new ArrayList<>();
        consumerList.add(consumer1);
        consumerList.add(consumer2);
        consumerList.add(consumer3);

        MailOrchestrator mailOrchestrator = new MailOrchestrator(pochtaRossii, producerList, consumerList);
        try {
            mailOrchestrator.orchestrate();
        }
        catch (Exception ex) {
            List<Worker> notSentMails = mailOrchestrator.getNotSentWorkersList();
            List<Worker> dupEmails = mailOrchestrator.getdupEmailsWorkersList();
            if (notSentMails.size() > 0) {
                System.out.println("NOT ALL MESSAGES WERE SENT");
                System.out.println("The list of workers, that didn't recieve mail:");
                for (Worker currWorker: notSentMails)
                    System.out.println(currWorker);
            }
            if (dupEmails.size() > 0) {
                System.out.println("SOME WORKERS HAVE EQUAL EMAILS");
                System.out.println("The list of those workers:");
                for (Worker currWorker: dupEmails)
                    System.out.println(currWorker);
            }
            System.out.println("\n------------EXCEPTION'S ESSENCE----------------\n");
            ex.printStackTrace(System.out);
            System.exit(-1);
        }

    }
}