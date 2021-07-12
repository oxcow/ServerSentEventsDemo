package net.iyiguo.html5.serversentevents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import net.iyiguo.html5.serversentevents.dto.PokerEvent;
import net.iyiguo.html5.serversentevents.dto.PokerMessage;
import net.iyiguo.html5.serversentevents.dto.PokerVotesDto;
import net.iyiguo.html5.serversentevents.enums.PokerActionEnum;
import net.iyiguo.html5.serversentevents.enums.PokerVoteStatusEnum;
import net.iyiguo.html5.serversentevents.web.MessageController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PokerMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private Table<Long, Long, Integer> pokerVoteTable = HashBasedTable.create();

    private AtomicLong atomicLong = new AtomicLong(1);

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private RoomBroadcastService roomBroadcastService;

    @Autowired
    private PokerCacheDBService pokerCacheDBService;

    public SseEmitter subscribeRoom(@PathVariable Long pokerId,
                                    @PathVariable Long roomId,
                                    @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId) {
        LOGGER.debug("Poker【{}】请求订阅【{}】广播消息，最后接收消息ID: {}", pokerId, roomId, lastEventId);

        Optional<SseEmitter> object = roomBroadcastService.getRoomBroadcastObject(roomId, pokerId);
        if (object.isPresent()) {
            LOGGER.info("Poker#{} 已经在Room#{} 中", roomId, pokerId);
            return object.get();
        }

        final SseEmitter sseEmitter = new SseEmitter(-1L);
        roomBroadcastService.subscribe(roomId, pokerId, lastEventId, sseEmitter);
        LOGGER.debug("【{}】加入到【{}】广播消息队列.{},{}", pokerId, roomId, lastEventId, sseEmitter);
        return sseEmitter;
    }

    public void handlePokerEvent(PokerEvent pokerEvent) {
        if (pokerEvent.getAction().equals(PokerActionEnum.FLOP)) {
            List<PokerVotesDto> pokerVotesDtoList = getPokerVotesInfoByRoomId(pokerEvent.getRoomId());
            if (!pokerVotesDtoList.isEmpty()) {
                String text = writeValueAsString(pokerVotesDtoList);
                PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), PokerActionEnum.FLOP, text);
                roomBroadcastService.broadcast(pokerEvent.getRoomId(), message);
            }
        } else if (pokerEvent.getAction().equals(PokerActionEnum.SHUFFLE)) {
            pokerVoteTable.clear();
            PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), PokerActionEnum.SHUFFLE, "shuffle");
            roomBroadcastService.broadcast(pokerEvent.getRoomId(), message);
        }

    }

    public void vote(Long roomId, Long pokerId, Integer votes) {
        pokerVoteTable.put(roomId, pokerId, votes);

        List<PokerVotesDto> pokerVotesDto = getPokerVotesInfoByRoomId(roomId);
        if (!pokerVotesDto.isEmpty()) {
            String text = writeValueAsString(pokerVotesDto);
            PokerMessage message = new PokerMessage(atomicLong.getAndIncrement(), PokerActionEnum.VOTE, text);
            roomBroadcastService.broadcast(roomId, message);
        }
    }

    private List<PokerVotesDto> getPokerVotesInfoByRoomId(Long roomId) {
        Map<Long, Integer> pokerVote = pokerVoteTable.row(roomId);
        List<PokerVotesDto> pokerVotesDtoList = Lists.newArrayList();
        for (Map.Entry<Long, Integer> pv : pokerVote.entrySet()) {
            pokerVotesDtoList.add(new PokerVotesDto(pv.getKey(), pv.getValue(), PokerVoteStatusEnum.VOTED));
        }
        return pokerVotesDtoList;
    }

    private String writeValueAsString(Object object) {
        try {
            return jacksonObjectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("对象转换JSON对象异常. {}", e.getMessage(), e);
            throw new RuntimeException("对象转换JSON对象异常." + e.getMessage());
        }
    }

}
