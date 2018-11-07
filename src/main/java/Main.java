import business_logic.MailConsumer;
import business_logic.orchestration.MailOrchestrator;
import business_logic.MailProducer;
import business_logic.orchestration.Orchestrator;
import structures.MailContainer;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MailContainer mailContainer = new MailContainer(12);
        Orchestrator orchestrator = new MailOrchestrator(mailContainer, Arrays.asList(new MailProducer(mailContainer)), Arrays.asList(new MailConsumer(mailContainer)));
        orchestrator.orchestrate();
    }
}
