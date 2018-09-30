package net.iyiguo.html5.serversendevents.web;

import com.google.common.collect.Maps;
import net.iyiguo.html5.serversendevents.domain.Message;
import net.iyiguo.html5.serversendevents.service.CacheDBService;
import net.iyiguo.html5.serversendevents.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private CacheDBService cacheDBService;

    private Map<String, Long> userLastMessageIdMap = Maps.newConcurrentMap();

    @RequestMapping("/broadcast/{name}")
    @ResponseBody
    public String broadcast(@PathVariable String name) {
        Long lastEventId = userLastMessageIdMap.getOrDefault(name, -1L);
        Long nextEventId = lastEventId + 1;
        Message message = messageService.getCeilingMessage(nextEventId);
        if (message == null) {
            return ": no auto produce broadcast need to be sent. ";
        }

        logger.info("「{}」Get produce auto. fetch_ID:{}, message: {}", name, nextEventId, message.toString());

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

        userLastMessageIdMap.put(name, message.getId());

        return stringBuilder.toString();
    }

    @RequestMapping("/publish")
    public boolean publish(String notice) {
        messageService.addPublishNotice(notice);
        return true;
    }

}
