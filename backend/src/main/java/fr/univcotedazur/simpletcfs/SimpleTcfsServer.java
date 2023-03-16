package fr.univcotedazur.simpletcfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class SimpleTcfsServer {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTcfsServer.class, args);
    }

}
