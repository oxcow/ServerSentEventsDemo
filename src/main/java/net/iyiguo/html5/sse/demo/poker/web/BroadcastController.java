package net.iyiguo.html5.sse.demo.poker.web;

import net.iyiguo.html5.sse.demo.poker.service.PokerMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author leeyee
 * @date 2021/7/15
 */
@Controller
@RequestMapping("/demo/broadcast")
public class BroadcastController {

    private final PokerMessageService pokerMessageService;

    public BroadcastController(PokerMessageService pokerMessageService) {
        this.pokerMessageService = pokerMessageService;
    }

    /**
     * 订阅房间消息
     *
     * @param roomId
     * @param pokerId
     * @param lastEventId
     * @return
     */
    @RequestMapping("/subscribe/{pokerId}/{roomId}")
    public SseEmitter subscribe(@PathVariable("roomId") Long roomId,
                                @PathVariable("pokerId") Long pokerId,
                                @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId) {
        return pokerMessageService.subscribeRoom(pokerId, roomId, lastEventId);
    }
}
