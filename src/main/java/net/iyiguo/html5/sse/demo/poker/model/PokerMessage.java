package net.iyiguo.html5.sse.demo.poker.model;

import net.iyiguo.html5.sse.demo.poker.enums.PokerActionEnum;

/**
 * @author leeyee
 */
public class PokerMessage {
    private Long id;
    private PokerActionEnum action;
    private String message;

    public PokerMessage() {
    }

    public PokerMessage(Long id, PokerActionEnum action, String message) {
        this.id = id;
        this.action = action;
        this.message = message;
    }

    public PokerActionEnum getAction() {
        return action;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
