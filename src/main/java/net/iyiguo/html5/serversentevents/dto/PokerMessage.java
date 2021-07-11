package net.iyiguo.html5.serversentevents.dto;

import net.iyiguo.html5.serversentevents.enums.PokerActionEnum;

public class PokerMessage {
    private Long id;
    private PokerActionEnum action;
    private String message;

    public PokerMessage() {
    }

    public PokerMessage(Long id, PokerActionEnum action) {
        this.id = id;
        this.action = action;
    }

    public PokerMessage(Long id, PokerActionEnum action, String message) {
        this.id = id;
        this.action = action;
        this.message = message;
    }

    public PokerActionEnum getAction() {
        return action;
    }

    public void setAction(PokerActionEnum action) {
        this.action = action;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
