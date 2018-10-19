package net.iyiguo.html5.serversentevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerSentEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSentEventsApplication.class, args);
    }
}
