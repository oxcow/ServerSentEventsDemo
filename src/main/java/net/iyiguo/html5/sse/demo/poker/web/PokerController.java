package net.iyiguo.html5.sse.demo.poker.web;

import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.entity.Room;
import net.iyiguo.html5.sse.demo.poker.model.PokerEvent;
import net.iyiguo.html5.sse.demo.poker.service.PokerCacheDBService;
import net.iyiguo.html5.sse.demo.poker.service.PokerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller()
@RequestMapping("/demo/pokers")
public class PokerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokerController.class);

    private PokerCacheDBService pokerCacheDBService;

    private PokerMessageService pokerMessageService;

    public PokerController(PokerCacheDBService pokerCacheDBService, PokerMessageService pokerMessageService) {
        this.pokerCacheDBService = pokerCacheDBService;
        this.pokerMessageService = pokerMessageService;
    }


    @GetMapping("/{pokerId}/enterRoom/{roomId}")
    public String enterRoom(@PathVariable("pokerId") Long pokerId,
                            @PathVariable("roomId") Long roomId,
                            Model model) {
        Optional<Room> roomOptional = pokerCacheDBService.getRoomById(roomId);
        Optional<Poker> pokerOptional = pokerCacheDBService.getPokerByRoomIdAndPokerId(roomId, pokerId);
        pokerCacheDBService.enterRoom(roomId, pokerId);
        model.addAttribute("room", roomOptional.get());
        model.addAttribute("user", pokerOptional.get());
        return "room";
    }

    @PostMapping("/cmd")
    @ResponseBody
    public boolean sendMessage(@RequestBody PokerEvent pokerEvent) {
        pokerMessageService.handlePokerEvent(pokerEvent);
        return true;
    }

    @PostMapping("/{pokerId}/vote")
    @ResponseBody
    public boolean vote(@PathVariable("pokerId") Long pokerId,
                        @RequestParam("roomId") Long roomId,
                        @RequestParam(name = "vote") Integer votes) {
        pokerMessageService.vote(roomId, pokerId, votes);
        return true;
    }
}
