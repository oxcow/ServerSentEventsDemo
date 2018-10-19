package net.iyiguo.html5.serversentevents.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.iyiguo.html5.serversentevents.domain.Message;
import net.iyiguo.html5.serversentevents.enums.MessageTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private Map<Long, Message> messageMap = Maps.newConcurrentMap();

    private AtomicLong atomicLong = new AtomicLong(1);

    public void addMessage(Message message) {
        messageMap.put(message.getId(), message);
    }

    public void addPublishNotice(String notice, Long ttl) {
        Long id = atomicLong.getAndIncrement();
        Message message = new Message(id, notice, LocalDateTime.now(), ttl, MessageTypeEnum.ADMIN_NOTICE);
        LOGGER.debug("管理员发布消息: {}", message);
        messageMap.put(id, message);
    }

    public Message getCeilingMessage(Long id) {

        if (messageMap.containsKey(id)) return messageMap.get(id);

        Optional<Long> ceilingKey = messageMap.keySet().stream()
                .filter(obj -> obj > id)
                .sorted()
                .findFirst();

        return ceilingKey.isPresent() ? messageMap.get(ceilingKey.get()) : null;
    }

    @Scheduled(cron = "2/15 * * * * ?")
    private void autoProduceMessage() {

        Long id = atomicLong.getAndIncrement();

        String context = RandomStringUtils.randomAlphanumeric(20);

        long ttl = RandomUtils.nextLong(10, 20);

        Message message = new Message(id, context + id, LocalDateTime.now(), ttl, MessageTypeEnum.BROADCAST);

        messageMap.putIfAbsent(id, message);

        LOGGER.info("系统生产消息: {}", message.toString());
    }

    @Scheduled(cron = "5/15 * * * * ?")
    private void cleanExpired() {

        Set<Long> expiredKey = Sets.newHashSet();

        for (Map.Entry<Long, Message> longMessageEntry : messageMap.entrySet()) {
            Message message = longMessageEntry.getValue();
            if (LocalDateTime.now().isAfter(message.getCreateTime().plusSeconds(message.getTtl()))) {
                expiredKey.add(longMessageEntry.getKey());
            }
        }

        LOGGER.info("系统清理过期消息Ids: {}", expiredKey);

        if (!expiredKey.isEmpty()) {
            expiredKey.forEach(messageMap::remove);
        }
    }
}
