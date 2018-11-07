package structures;

import persistence.entities.Mail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailContainer {

    private BlockingQueue<Mail> queue;

    private boolean shouldContinueProducing = true;

    public MailContainer(int capacity) {
        queue = new LinkedBlockingQueue<Mail>(capacity);
    }

    public void put(Mail mail){

    }

    public void get(Mail mail){

    }
}
