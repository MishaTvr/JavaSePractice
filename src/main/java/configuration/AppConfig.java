package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.dao.WorkerDAO;
import persistence.entities.Worker;

@Configuration
public class AppConfig {

    @Bean
    public Worker getWorker() {
        return new Worker();
    }


}
