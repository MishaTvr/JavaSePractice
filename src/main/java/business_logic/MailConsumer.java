package business_logic;

import exceptions.ConsumerException;
import exceptions.SenderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import persistence.entities.Mail;
import services.Sender;
import structures.MailContainer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class MailConsumer implements Callable<Integer> {
    @Autowired
    MailContainer mailContainer;
    @Autowired
    Sender sender;

    private Set<String> notSentWorkersNames = new LinkedHashSet<>();



    public Set<String> getNotSentWorkersNames() {
        return notSentWorkersNames;
    }

    @Override
    public Integer call() throws ConsumerException {
        int mailCounter = 0;

            while ((mailContainer.getFlag()) || (mailContainer.getSize() > 0)) {
                try {
                    Mail currentMail = mailContainer.get();
                    mailCounter += sender.send(currentMail);
                    System.out.println("current queue size is" + mailContainer.getSize());
                }
                catch (SenderException ex) {
                    notSentWorkersNames.add(ex.getMessage());
                }
                catch (InterruptedException ex) {
                    throw new ConsumerException("unable to get message from container", ex);
                }

            }


        System.out.println("consumer ended");
        return mailCounter;
    }

}
