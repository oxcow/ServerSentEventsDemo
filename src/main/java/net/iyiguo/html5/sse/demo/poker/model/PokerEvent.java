package net.iyiguo.html5.sse.demo.poker.model;

import net.iyiguo.html5.sse.demo.poker.enums.PokerActionEnum;

public class PokerEvent {

    private PokerActionEnum action;
    private Long roomNo;
    private Long pokerId;

    public PokerEvent() {
    }

    public PokerEvent(Long pokerId, Long roomNo, PokerActionEnum action) {
        this.action = action;
        this.roomNo = roomNo;
        this.pokerId = pokerId;
    }

    public PokerActionEnum getAction() {
        return action;
    }

    public void setAction(PokerActionEnum action) {
        this.action = action;
    }

    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public Long getPokerId() {
        return pokerId;
    }

    public void setPokerId(Long pokerId) {
        this.pokerId = pokerId;
    }

    @Override
    public String toString() {
        return "PokerEvent{" +
                "action=" + action +
                ", roomNo=" + roomNo +
                ", pokerId=" + pokerId +
                '}';
    }
}
