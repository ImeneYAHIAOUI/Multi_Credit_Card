package fr.univcotedazur.multicredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class MultiCreditServer {

    public static void main(String[] args) {
        SpringApplication.run(MultiCreditServer.class, args);
    }
}
