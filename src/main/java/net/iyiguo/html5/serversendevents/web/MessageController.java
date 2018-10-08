package net.iyiguo.html5.serversendevents.web;

import net.iyiguo.html5.serversendevents.domain.Message;
import net.iyiguo.html5.serversendevents.service.CacheDBService;
import net.iyiguo.html5.serversendevents.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private CacheDBService cacheDBService;

    @RequestMapping("/broadcast/{name}")
    public String broadcast(@PathVariable String name, @RequestHeader(value = "Last-Event-ID", required = false) Long lastEventId) {

        if (lastEventId == null) lastEventId = -1L;

        Long nextEventId = lastEventId + 1;

        logger.info("「{}」get broadcast message last event id is: {}", name, lastEventId);

        Message message = messageService.getCeilingMessage(nextEventId);

        if (message == null) {
            logger.warn("「{}」, no broadcast message for you!", name);
            return ": No auto produce broadcast message need to be sent."; // comment message
        }

        logger.info("「{}」get broadcast message. next event id is: {}, message: {}", name, nextEventId, message.toString());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id: ").append(message.getId()).append("\n");
        stringBuilder.append("event: ").append(message.getType().name().toLowerCase()).append("\n");
        // 设置客户端每6秒请求一次
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

    @RequestMapping("/publish")
    public boolean publish(String notice) {
        messageService.addPublishNotice(notice);
        return true;
    }

}
