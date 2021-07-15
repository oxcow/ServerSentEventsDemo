package net.iyiguo.html5.sse.demo.poker.model;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author leeyee
 * @date 2021/7/15
 */
public class PokerEmitter {
    private Long roomId;
    private Long pokerId;
    private long lastEventId;
    private SseEmitter emitter;

    public PokerEmitter() {
    }

    public PokerEmitter(Long roomId, Long pokerId, long lastEventId, SseEmitter emitter) {
        this.roomId = roomId;
        this.pokerId = pokerId;
        this.lastEventId = lastEventId;
        this.emitter = emitter;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPokerId() {
        return pokerId;
    }

    public void setPokerId(Long pokerId) {
        this.pokerId = pokerId;
    }

    public long getLastEventId() {
        return lastEventId;
    }

    public void setLastEventId(long lastEventId) {
        this.lastEventId = lastEventId;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public String toString() {
        return "PokerEmitter{" +
                "roomId=" + roomId +
                ", pokerId=" + pokerId +
                ", lastEventId=" + lastEventId +
                ", emitter=" + emitter +
                '}';
    }
}
