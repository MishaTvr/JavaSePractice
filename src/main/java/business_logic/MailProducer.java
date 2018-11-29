package business_logic;

import Exceptions.ProducerException;
import persistence.dao.WorkerDAO;
import persistence.entities.Mail;
import persistence.entities.Worker;
import structures.MailContainer;

import java.util.List;
import java.util.concurrent.Callable;
// in future

public class MailProducer implements Callable<Integer> {

    private MailContainer mailContainer;



    public MailProducer(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }

    @Override
    public Integer call() throws ProducerException {
        List<Mail> mailList = Mail.getMailList((new WorkerDAO()).findAll());
        int addCounter = 0;
        try {
            for (Mail currentMail: mailList) {
                mailContainer.put(currentMail);
                addCounter++;
                System.out.println("i added a letter with email :"+ currentMail.getEmail());
                //Thread.sleep(1000);
            }
        }
        catch (InterruptedException ex) {
            throw new ProducerException("mail putting error", ex);
        }
        System.out.println("im here");

        mailContainer.stopProcess();
        return addCounter;
    }
}
