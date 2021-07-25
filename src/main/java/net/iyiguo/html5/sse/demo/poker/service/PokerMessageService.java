package net.iyiguo.html5.sse.demo.poker.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.enums.PokerActionEnum;
import net.iyiguo.html5.sse.demo.poker.model.PokerEmitter;
import net.iyiguo.html5.sse.demo.poker.model.PokerEvent;
import net.iyiguo.html5.sse.demo.poker.model.PokerMessage;
import net.iyiguo.html5.sse.demo.poker.web.dto.PokerVotesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PokerMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokerMessageService.class);

    private AtomicLong atomicLong = new AtomicLong(1);

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private RoomBroadcastService roomBroadcastService;

    @Autowired
    private PokerVoteService pokerVoteService;

    @Autowired
    private PokerService pokerService;

    public SseEmitter subscribeRoom(@PathVariable Long pokerId,
                                    @PathVariable Long roomId,
                                    @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId) {
        LOGGER.info("Poker【{}】请求进入房间【{}】。最后接收消息ID: {}", pokerId, roomId, lastEventId);

        Optional<PokerEmitter> object = roomBroadcastService.getRoomBroadcastObject(roomId, pokerId);
        if (object.isPresent()) {
            LOGGER.info("Poker#{} 已经在Room#{} 中. last id: {}", pokerId, roomId, object.get().getLastEventId());
            return object.get().getEmitter();
        }

        final SseEmitter sseEmitter = new SseEmitter(-1L);
        roomBroadcastService.subscribe(roomId, pokerId, lastEventId, sseEmitter);
        LOGGER.info("Poker【{}】成功进入房间【{}】。广播消息队列.{},{}", pokerId, roomId, lastEventId, sseEmitter);
        return sseEmitter;
    }

    public void handlePokerEvent(PokerEvent pokerEvent) {
        Set<Long> excludePokers = Sets.newHashSet(pokerEvent.getPokerId());
        switch (pokerEvent.getAction()) {
            case FLOP:
                List<PokerVotesVo> pokerVotesDtoList = pokerVoteService.findAllVotes(pokerEvent.getRoomId());
                if (!pokerVotesDtoList.isEmpty()) {
                    String text = writeValueAsString(pokerVotesDtoList);
                    PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), PokerActionEnum.FLOP, text);
                    roomBroadcastService.broadcast(pokerEvent.getRoomId(), message);
                }
                break;
            case SHUFFLE:
                pokerVoteService.cleanAllVote(pokerEvent.getRoomId());
                PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), PokerActionEnum.SHUFFLE, "shuffle");
                roomBroadcastService.broadcast(pokerEvent.getRoomId(), message);
                break;
            case VOTE:
            case CANCEL:
            case OFFLINE:
                String text = writeValueAsString(pokerEvent);
                message = new PokerMessage(atomicLong.getAndIncrement(), pokerEvent.getAction(), text);
                roomBroadcastService.broadcast(pokerEvent.getRoomId(), message, excludePokers);
                break;
            case ONLINE:
                Poker onlinePoker = pokerService.getPokerById(pokerEvent.getPokerId()).orElse(null);
                String pokerInfo = writeValueAsString(onlinePoker);
                message = new PokerMessage(atomicLong.getAndIncrement(), pokerEvent.getAction(), pokerInfo);
                roomBroadcastService.broadcast(pokerEvent.getRoomId(), message, excludePokers);
                break;

            default:
                LOGGER.info("Do Nothing!!");
        }

    }


    private String writeValueAsString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return jacksonObjectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("对象转换JSON对象异常. {}", e.getMessage(), e);
            throw new RuntimeException("对象转换JSON对象异常." + e.getMessage());
        }
    }

}
