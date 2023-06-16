package net.iyiguo.html5.serversentevents.service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.common.collect.Sets;
import net.iyiguo.html5.serversentevents.domain.Message;

@Component
public class BroadcastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastService.class);

    private Set<BroadcastObject> broadcastUsers = Sets.newConcurrentHashSet();

    private final ExecutorService broadcastWorker = Executors.newFixedThreadPool(3);

    private final MessageService messageService;

    public BroadcastService(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreDestroy
    public void destroy() {
        if (!broadcastUsers.isEmpty()) {
            broadcastUsers.forEach(obj -> obj.emitter.complete());
            broadcastUsers = Sets.newConcurrentHashSet();
        }
        LOGGER.debug("完成所有浏览器到服务端的消息链接");
    }

    /**
     * 订阅广播消息
     *
     * @param name        用户
     * @param lastEventId 消息开始ID
     * @param sseEmitter  通道
     * @return
     */
    public boolean subscribe(String name, Long lastEventId, SseEmitter sseEmitter) {

        // 当前订阅用户是否重新新开页面或者浏览器登录
        Optional<Long> optional = broadcastUsers.stream()
                .filter(obj -> name.equals(obj.name) && sseEmitter != obj.emitter)
                .map(BroadcastObject::getLastEventId)
                .findFirst();

        // 如果是同一个用户的不同浏览器页面，那么当前新开页面接收消息ID与之前的相同
        if (optional.isPresent()) {
            return broadcastUsers.add(new BroadcastObject(name, optional.get(), sseEmitter));
        }

        boolean isReg = broadcastUsers.add(new BroadcastObject(name, lastEventId, sseEmitter));

        LOGGER.debug("当前订阅消息人数:{}", broadcastUsers.size());

        return isReg;
    }

    /**
     * 移除指定用户指定通道（一个用户可能打开多个浏览器）
     *
     * @param name
     * @param sseEmitter
     * @return
     */
    private boolean remove(String name, SseEmitter sseEmitter) {

        int beforeCount = broadcastUsers.size();

        Optional<BroadcastObject> optional = broadcastUsers.stream()
                .filter(obj -> name.equals(obj.name) && obj.emitter == sseEmitter)
                .findFirst();

        boolean isRemove = false;

        if (optional.isPresent()) {
            isRemove = broadcastUsers.remove(optional.get());
        }

        LOGGER.debug("移除订阅人【{}】- {}, 人数从 {} 变为 {}", name, sseEmitter, beforeCount, broadcastUsers.size());

        return isRemove;
    }

    /**
     * 监听用户退出事件。从广播队列中移除该用户的所有通道
     *
     * @param name
     */
    @EventListener
    public void listenUserLoginOutEvent(String name) {

        int beforeCount = broadcastUsers.size();

        Set<BroadcastObject> objects = broadcastUsers.stream()
                .filter(obj -> name.equals(obj.name))
                .collect(Collectors.toSet());

        for (BroadcastObject object : objects) {
            if (object.emitter != null) {
                object.emitter.complete();
            }
            broadcastUsers.remove(object);
        }

        LOGGER.debug("收到用户【{}】登出系统消息, 订阅人数从 {} 变为 {}", name, beforeCount, broadcastUsers.size());
    }

    /**
     * 广播消息
     */
    public void broadcast() {

        broadcastUsers.forEach(subscriber -> {

            Message message = messageService.getCeilingMessage(subscriber.lastEventId);

            if (message != null) {

                subscriber.lastEventId = message.getId() + 1;

                broadcastWorker.execute(() -> {

                    SseEmitter emitter = subscriber.emitter;

                    try {
                        LOGGER.debug("系统推送消息: {}->【{}】.{},{}", message.getId(), subscriber.name, message, subscriber);
                        emitter.send(SseEmitter.event()
                                .id(message.getId().toString())
                                .name(message.getType().name().toLowerCase())
                                .data(message, MediaType.APPLICATION_JSON_UTF8));

                    } catch (IOException e) {
                        LOGGER.error("系统推送消息异常: {}->【{}】.{}", message.getId(), subscriber.name, e.getMessage(), e);
                        emitter.completeWithError(e);

                        // 移除当前订阅人信息
                        // 不考虑并发情况。移除时另一个线程进来做send操作时会发生异常，但不影响业务逻辑。
                        remove(subscriber.name, emitter);
                    }

                });
            }
        });
    }

    private static class BroadcastObject {

        private final String name;
        private long lastEventId;
        private final SseEmitter emitter;

        BroadcastObject(String name, long lastEventId, SseEmitter emitter) {
            this.name = name;
            this.lastEventId = lastEventId;
            this.emitter = emitter;
        }

        long getLastEventId() {
            return lastEventId;
        }

        @Override
        public String toString() {
            return "BroadcastObject{" +
                    "name='" + name + '\'' +
                    ", lastEventId=" + lastEventId +
                    ", emitter=" + emitter +
                    '}';
        }
    }
}
