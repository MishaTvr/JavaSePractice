package business_logic;

import exceptions.ProducerException;
import persistence.dao.WorkerDAO;
import persistence.entities.Mail;
import structures.MailContainer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
// in future

public class MailProducer implements Callable<Integer> {

    private MailContainer mailContainer;
    private Set<String> dupEmails = new LinkedHashSet<>();



    public MailProducer(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }

    public Set<String> getDupEmails() {
        return dupEmails;
    }

    @Override
    public Integer call() throws ProducerException {
        List<Mail> mailList = Mail.getMailList(WorkerDAO.extractWorkers());
        Set<String> emailSet = new LinkedHashSet<>();
        for (Mail mail: mailList) {
            if (!emailSet.add(mail.getEmail())) {
                this.dupEmails.add(mail.getEmail());
            }
        }

        if (emailSet.size() != mailList.size())
            throw new ProducerException("DUP_EMAILS_ERR!");


        int addCounter = 0;
        try {
            for (Mail currentMail: mailList) {
                mailContainer.put(currentMail);
                addCounter++;
                System.out.println("i added a letter with email :"+ currentMail.getEmail());
            }
        }
        catch (InterruptedException ex) {
            throw new ProducerException("mail putting error", ex);
        }

        mailContainer.stopProcess();
        return addCounter;
    }
}
