import business_logic.MailConsumer;
import business_logic.MailProducer;
import business_logic.orchestration.MailOrchestrator;
import persistence.dao.WorkerDAO;
import persistence.entities.Mail;
import persistence.entities.Worker;
import services.XMLService;
import structures.MailContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
            if (notSentMails.size() > 0) {
                System.out.println("NOT ALL MESSAGES WERE SENT");
                System.out.println("The list of workers, that didn't recieve mail:");
                for (Worker currWorker: notSentMails)
                    System.out.println(currWorker);
            }
            System.exit(-45);
        }





        //        WorkerDAO workerDAO = new WorkerDAO();
//        List<Worker> workerList = workerDAO.findAll();
//
//
//        for (Worker worker: workerList) {
//            Mail mail = new Mail(worker);
//        }



        //workerDAO.findAll().stream().forEach(System.out::println);


//        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
//            StringBuilder line = new StringBuilder();
//            while ( (message = reader.readLine()) != null) {
//                line.append(message);
//                line.append('\n');
//            }
//            message = line.toString();
//        }
//        catch (IOException ex) {
//            ex.printStackTrace(System.out);
//        }

    }

}