package business_logic;

import structures.MailContainer;

public class MailProducer implements Runnable {

    private MailContainer mailContainer;

    public MailProducer(MailContainer mailContainer) {
        this.mailContainer = mailContainer;
    }
    @Override
    public void run() {

    }
}
