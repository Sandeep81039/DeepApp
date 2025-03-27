package DeepApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        System.out.println("Hello");
        SpringApplication.run(JournalApplication.class, args);

    }

    @Bean
    public PlatformTransactionManager manages(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }



}
