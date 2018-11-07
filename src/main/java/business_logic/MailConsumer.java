package business_logic;

import persistence.entities.Mail;
import structures.MailContainer;

public class MailConsumer implements Runnable {
    private MailContainer mailContainer;

    public MailConsumer(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }

    @Override
    public void run() {

    }
}
