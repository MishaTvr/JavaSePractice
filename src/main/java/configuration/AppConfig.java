package configuration;

import business_logic.MailConsumer;
import business_logic.MailProducer;
import business_logic.orchestration.MailOrchestrator;
import configuration.BeansLoader.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import persistence.dao.WorkerDAO;
import persistence.entities.Mail;
import persistence.entities.Property;
import persistence.entities.Worker;
import services.Sender;
import structures.MailContainer;




@Configuration
@PropertySource("file:/home/george/IdeaProjects/Mailer/src/main/resources/mainProps.properties")
public class AppConfig {

    @Value("${dbClassName}")
    private String driverClassName;
    @Value("${dbURL}")
    private String dbURL;
    @Value("${dbUserName}")
    private String dbUserName;
    @Value("${dbPassword}")
    private String dbPassword;
    @Value("${dbURLWorkerDAO}")
    private String dbWorkerDAOURL;
    @Value("${dbUserName}")
    private String user;
    @Value("${dbPassword}")
    private String password;
    @Value("${letterTemplate}")
    private String fileName;
    @Value("${letterSubject}")
    private String subject;





    @Bean
    public DriverManagerDataSource getDriverManager() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(dbURL);
        driverManagerDataSource.setUsername(dbUserName);
        driverManagerDataSource.setPassword(dbPassword);
        return driverManagerDataSource;
    }

    @Bean
    public MailOrchestrator getMailOrchestrator() {
        return new MailOrchestrator(4);
    }

    @Bean
    public WorkerDAO getWorkerDAO() {
        WorkerDAO workerDAO = WorkerDAO.getInstance();
        workerDAO.setDbLength(dbWorkerDAOURL, user, password);
        return workerDAO;
    }



    @Bean
    public BeanBuilder<MailConsumer> mailConsumerContainer() {
        BeanBuilder<MailConsumer> container = new BeanBuilder<MailConsumer>(3);
        container.setBeanFactory(mailConsumerBeanFactory());
        return container;
    }


    @Bean
    public BeanFactory<MailConsumer> mailConsumerBeanFactory() {
        return new BeanFactory<MailConsumer>() {
            public MailConsumer createBean() {
                return getConsumer();
            }
        };
    }

    @Bean
    public BeanBuilder<Mail> mailContainer() {
        BeanBuilder<Mail> container = new BeanBuilder<>(WorkerDAO.getInstance().getDbLength());
        container.setBeanFactory(mailBeanFactory());
        return container;
    }


    @Bean
    public BeanFactory<Mail> mailBeanFactory() {
        return new BeanFactory<Mail>() {
            public Mail createBean() { return new Mail(fileName, subject); }
        };
    }

    @Bean
    public BeanBuilder<MailProducer> mailProducerBeanBuilder() {
        BeanBuilder<MailProducer> container = new BeanBuilder<MailProducer>(1);
        container.setBeanFactory(mailProducerBeanFactory());
        return container;
    }

    @Bean
    public BeanFactory<MailProducer> mailProducerBeanFactory() {
        return () -> getProducer();
    }

    @Bean
    public BeanBuilder<Worker> workerBeanBuilder() {
        BeanBuilder<Worker> container = new BeanBuilder<Worker>(WorkerDAO.getInstance().getDbLength());
        container.setBeanFactory(workerBeanFactory());
        return container;
    }


    @Bean
    public BeanFactory<Worker> workerBeanFactory() {
        return Worker::new;
    }

    @Bean
    public BeanBuilder<Property> propertyBeanBuilder() {
        BeanBuilder<Property> container = new BeanBuilder<>(WorkerDAO.getInstance().getDbLength()*3);
        container.setBeanFactory(propertyBeanFactory());
        return container;
    }

    @Bean
    public BeanFactory<Property> propertyBeanFactory() {
        return new BeanFactory<Property>() {
            public Property createBean() {
                return new Property();
            }
        };
    }


    @Bean
    public MailContainer getContainer() {
        return new MailContainer(7);
    }


    @Bean
    public MailProducer getProducer() {
        return new MailProducer();
    }

    @Bean
    public MailConsumer getConsumer() {
        return new MailConsumer();
    }

    @Bean
    public Sender getSender() {return new Sender();}





    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
