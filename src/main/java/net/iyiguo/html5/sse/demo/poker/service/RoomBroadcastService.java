package net.iyiguo.html5.sse.demo.poker.service;

import com.google.common.collect.Sets;
import net.iyiguo.html5.sse.demo.poker.model.PokerEmitter;
import net.iyiguo.html5.sse.demo.poker.model.PokerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RoomBroadcastService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomBroadcastService.class);

    private Set<PokerEmitter> broadcastUsers = Sets.newConcurrentHashSet();

    private ExecutorService broadcastWorker = Executors.newFixedThreadPool(3);

    @PreDestroy
    public void destroy() {
        if (!broadcastUsers.isEmpty()) {
            broadcastUsers.forEach(obj -> obj.getEmitter().complete());
            broadcastUsers = Sets.newConcurrentHashSet();
        }
        LOGGER.debug("完成所有浏览器到服务端的消息链接");
    }

    public Optional<SseEmitter> getRoomBroadcastObject(Long roomId, Long pokerId) {
        Optional<PokerEmitter> object = broadcastUsers.stream()
                .filter(obj -> obj.getRoomId().equals(roomId))
                .filter(obj -> obj.getPokerId().equals(pokerId))
                .findFirst();
        if (object.isPresent()) {
            return Optional.ofNullable(object.get().getEmitter());
        } else {
            return Optional.empty();
        }
    }

    public boolean subscribe(Long roomId, Long pokerId, Long lastEventId, SseEmitter sseEmitter) {

        Optional<PokerEmitter> exist = broadcastUsers.stream()
                .filter(obj -> obj.getRoomId().equals(roomId))
                .filter(obj -> obj.getPokerId().equals(pokerId))
                .findFirst();
        if (exist.isPresent()) {
            LOGGER.info("Poker#{} 已经在Room#{} 中", roomId, pokerId);
            return false;
        }

        // 当前订阅用户是否重新新开页面或者浏览器登录
        // 如果是同一个用户的不同浏览器页面，那么当前新开页面接收消息ID与之前的相同
        Long finalLastEventId = broadcastUsers.stream()
                .filter(obj -> obj.getRoomId().equals(roomId))
                .filter(obj -> obj.getPokerId().equals(pokerId))
                .filter(obj -> obj.getEmitter() != sseEmitter)
                .map(PokerEmitter::getLastEventId)
                .findFirst()
                .orElse(lastEventId);

        boolean isReg = broadcastUsers.add(new PokerEmitter(roomId, pokerId, finalLastEventId, sseEmitter));

        LOGGER.debug("当前Room人数:{}", broadcastUsers.size());
        return isReg;
    }

    public boolean unsubscribe(Long roomId, Long pokerId, SseEmitter sseEmitter) {
        int beforeSize = broadcastUsers.size();
        broadcastUsers.stream()
                .filter(obj -> obj.getRoomId().equals(roomId))
                .filter(obj -> obj.getPokerId().equals(pokerId))
                .filter(obj -> obj.getEmitter() != sseEmitter)
                .findFirst()
                .ifPresent(broadcastUsers::remove);
        LOGGER.debug("移除订阅人【{}】【{}】- {}, 人数从 {} 变为 {}", roomId, pokerId, sseEmitter, beforeSize, broadcastUsers.size());
        return true;
    }

    public void broadcast(Long roomId, PokerMessage pokerMessage) {
        if (Objects.nonNull(pokerMessage)) {
            broadcastUsers.stream()
                    .filter(obj -> obj.getRoomId().equals(roomId))
                    .forEach(obj -> {
                        SseEmitter emitter = obj.getEmitter();
                        try {
                            emitter.send(SseEmitter.event()
                                    .id(pokerMessage.getId().toString())
                                    .name(pokerMessage.getAction().name())
                                    .data(pokerMessage.getMessage(), MediaType.APPLICATION_JSON));
                            LOGGER.debug("[{}] 发送消息 {} 到 Poker#{}(Room#{})",
                                    pokerMessage.getAction(), pokerMessage.getMessage(), obj.getPokerId(), obj.getRoomId());
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    });
        }
    }
}
