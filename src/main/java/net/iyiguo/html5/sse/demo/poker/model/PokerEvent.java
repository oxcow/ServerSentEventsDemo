package net.iyiguo.html5.sse.demo.poker.model;

import net.iyiguo.html5.sse.demo.poker.enums.PokerActionEnum;

public class PokerEvent {

    private PokerActionEnum action;
    private Long roomId;
    private Long pokerId;

    public PokerEvent() {
    }

    public PokerEvent(Long pokerId, Long roomId, PokerActionEnum action) {
        this.action = action;
        this.roomId = roomId;
        this.pokerId = pokerId;
    }

    public PokerActionEnum getAction() {
        return action;
    }

    public void setAction(PokerActionEnum action) {
        this.action = action;
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
}
