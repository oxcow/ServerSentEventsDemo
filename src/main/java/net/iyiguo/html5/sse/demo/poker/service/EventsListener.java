package net.iyiguo.html5.sse.demo.poker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.iyiguo.html5.sse.demo.poker.model.PokerEmitter;
import net.iyiguo.html5.sse.demo.poker.model.PokerEvent;

/**
 * @author leeyee
 * @date 2021/8/4
 */
@Service
public class EventsListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsListener.class);

    @EventListener
    @Async
    public void loginEventListener(EntityCreatedEvent<PokerEmitter> subscribeEvent) {
        LOGGER.info("event listener: subscribe event: {}", subscribeEvent);
    }

    @EventListener
    @Async
    public void cmdEventListener(EntityCreatedEvent<PokerEvent> cmdEvent) {
        LOGGER.info("event listener: cmd event: {}", cmdEvent);
    }

}
