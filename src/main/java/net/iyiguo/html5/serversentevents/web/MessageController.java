package net.iyiguo.html5.serversentevents.web;

import net.iyiguo.html5.serversentevents.domain.Message;
import net.iyiguo.html5.serversentevents.service.BroadcastService;
import net.iyiguo.html5.serversentevents.service.CacheDBService;
import net.iyiguo.html5.serversentevents.service.MessageService;
import net.iyiguo.html5.serversentevents.util.ThreadUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/message")
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    private MessageService messageService;

    @Autowired
    private CacheDBService cacheDBService;

    @Autowired
    private BroadcastService broadcastService;


    @PostConstruct
    public void init() {
        singleThreadExecutor.execute(() -> {
            LOGGER.debug("启动用户订阅广播消息推送线程");
            while (true) {
                ThreadUtils.sleep(2000);
                broadcastService.broadcast();
            }
        });
    }

    @RequestMapping(value = "/subscribe/{name}")
    public SseEmitter subscribe(@PathVariable String name, @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId) {

        LOGGER.debug("【{}】请求订阅广播消息，最后接收消息ID: {}", name, lastEventId);

        final SseEmitter sseEmitter = new SseEmitter(-1L);

        broadcastService.subscribe(name, lastEventId, sseEmitter);

        LOGGER.debug("【{}】加入到广播消息队列.{},{}", name, lastEventId, sseEmitter);

        return sseEmitter;
    }

    @RequestMapping("/publish")
    @ResponseBody
    public boolean publish(String notice, Long ttl) {
        messageService.addPublishNotice(notice, ttl);
        return true;
    }


    //@RequestMapping("/broadcast/{name}")
    public String broadcast1(@PathVariable String name, @RequestHeader(value = "Last-Event-ID", required = false) Long lastEventId) {

        if (lastEventId == null) {
            lastEventId = -1L;
        }

        Long nextEventId = lastEventId + 1;

        LOGGER.debug("「{}」get broadcast message last event id is: {}", name, lastEventId);

        Message message = messageService.getCeilingMessage(nextEventId);

        if (message == null) {
            LOGGER.warn("「{}」, no broadcast message for you!", name);

            // comment message
            return ": No auto produce broadcast message need to be sent.";
        }

        LOGGER.debug("「{}」get broadcast message. next event id is: {}, message: {}", name, nextEventId, message.toString());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id: ").append(message.getId()).append("\n");
        stringBuilder.append("event: ").append(message.getType().name().toLowerCase()).append("\n");

        // 设置客户端链接超时或异常后重新发起链接的间隔时间
        stringBuilder.append("retry: ").append(6000).append("\n");

        stringBuilder.append("data: ").append(message.getType()).append(",\n");
        stringBuilder.append("data: ").append(message.getId()).append(",\n");
        stringBuilder.append("data: ").append(message.getCreateTime()).append(",\n");
        stringBuilder.append("data: ").append(message.getTtl()).append(",\n");

        // 简单处理自带换行的消息
        for (String s : message.getContext().split("\n")) {
            stringBuilder.append("data: ").append(s).append("\n");
        }

        stringBuilder.append("\n\n");

        return stringBuilder.toString();
    }


}
