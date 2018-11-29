package structures;

import persistence.entities.Mail;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailContainer {

    private BlockingQueue<Mail> queue;

    private boolean shouldContinueProducing = true;

    public MailContainer(int capacity) {
        queue = new LinkedBlockingQueue<Mail>(capacity);
    }

    public void stopProcess () {
        shouldContinueProducing = false;
    }

    public boolean getFlag () {return shouldContinueProducing;}

    public int getSize() { return queue.size();}


    public void put(Mail mail) throws InterruptedException {
        queue.put(mail);

    }

    public void putAll (List<Mail> mailList) {

        queue.addAll(mailList);
    }

    public Mail get() throws InterruptedException {
            return queue.take();
    }
}
