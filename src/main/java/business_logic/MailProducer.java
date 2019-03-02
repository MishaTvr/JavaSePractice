package business_logic;

import configuration.BeansLoader.BeanBuilder;
import exceptions.ProducerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import persistence.dao.WorkerDAO;
import persistence.entities.Mail;
import persistence.entities.Worker;
import structures.MailContainer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
// in future

public class MailProducer implements Callable<Integer> {
    @Autowired
    BeanBuilder<Mail> mailBeanBuilder;
    @Autowired
    MailContainer mailContainer;
    @Autowired
    WorkerDAO workerDAO;
    private Set<String> dupEmails = new LinkedHashSet<>();



    public Set<String> getDupEmails() {
        return dupEmails;
    }

    private List<Mail> getMailList() {
        List<Mail> mailList = mailBeanBuilder.load();
        List<Worker> workerList = workerDAO.extractWorkers();
        int counter = 0;
        for (Mail mail:mailList) {
            mail.createMail(workerList.get(counter));
            counter++;
        }
        return mailList;
    }


    @Override
    public Integer call() throws ProducerException {
        List<Mail> mailList = getMailList();
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
