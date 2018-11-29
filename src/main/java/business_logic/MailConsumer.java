package business_logic;

import Exceptions.ConsumerException;
import Exceptions.SenderException;
import persistence.entities.Mail;
import services.Sender;
import structures.MailContainer;

import java.util.concurrent.Callable;

public class MailConsumer implements Callable<Integer> {
    private MailContainer mailContainer;

    public MailConsumer(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }

    @Override
    public Integer call() throws ConsumerException {
        int mailCounter = 0;

            while ((mailContainer.getFlag()) || (mailContainer.getSize() > 0)) {
                try {
                    Mail currentMail = mailContainer.get();
                    if (Sender.send(currentMail))
                        mailCounter++;
                    System.out.println("current queue size is" + mailContainer.getSize());
                }
                catch (SenderException ex) {
                    throw new ConsumerException("sender", ex);
                }
                catch (InterruptedException ex) {
                    throw new ConsumerException("unable to get message from container", ex);
                }

                //Thread.sleep(7000);
            }


        System.out.println("consumer ended");
        return mailCounter;
    }

}
