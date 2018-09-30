package net.iyiguo.html5.serversendevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerSendEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSendEventsApplication.class, args);
    }
}
