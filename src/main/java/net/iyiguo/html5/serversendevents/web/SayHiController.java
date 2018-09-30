package net.iyiguo.html5.serversendevents.web;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/html5/sse")
public class SayHiController {
    public static final Logger logger = LoggerFactory.getLogger(SayHiController.class);

    AtomicInteger counter = new AtomicInteger(0);

    @RequestMapping("/quick_start")
    public String quickStart() {
        return "data: hello for server send event！\n\n";
    }

    @RequestMapping("/event_fields")
    public String eventFields() {

        int id = counter.getAndIncrement();

        int retry = RandomUtils.nextInt(6000, 12000);

        StringBuilder messageSb = new StringBuilder();
        messageSb.append("id: ").append(id).append("\n");
        messageSb.append("retry: ").append(retry).append("\n");
        messageSb.append("data: ").append("发送一条ID=").append(id).append("的消息，并设置下次重链接retry=").append(retry).append("ms");
        messageSb.append("\n\n");

        return messageSb.toString();
    }

    @RequestMapping("/customer_event")
    public String customerEvent() {

        StringBuilder message1 = new StringBuilder();
        message1.append("event: ").append("event_one").append("\n");
        message1.append("data: ").append("first event!").append("\n\n");

        StringBuilder message2 = new StringBuilder();
        message2.append("event: ").append("event_two").append("\n");
        message2.append("data: ").append("second event!").append("\n\n");

        int randomInt = RandomUtils.nextInt(1, 4);

        if (randomInt == 1) {
            return message1.toString();
        }

        if (randomInt == 2) {
            return message2.toString();
        }

        return message1.toString() + message2.toString();
    }

    @RequestMapping("/mixed_event")
    public String mixedEvent() {
        return "event: event_one\ndata: first event!\n\n"
                + "event: event_two\ndata: second event!\n\n"
                + "data: this is a not event message(1)\n"
                + "data: this is a not event message(2)\n\n";
    }
}
