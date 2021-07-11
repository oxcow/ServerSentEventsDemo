package net.iyiguo.html5.serversentevents.web;

import net.iyiguo.html5.serversentevents.domain.Poker;
import net.iyiguo.html5.serversentevents.domain.Room;
import net.iyiguo.html5.serversentevents.dto.PokerEvent;
import net.iyiguo.html5.serversentevents.service.PokerCacheDBService;
import net.iyiguo.html5.serversentevents.service.PokerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@Controller()
@RequestMapping("/poker")
public class PokerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokerController.class);

    private PokerCacheDBService pokerCacheDBService;

    @Autowired
    private PokerMessageService pokerMessageService;

    public PokerController(PokerCacheDBService pokerCacheDBService) {
        this.pokerCacheDBService = pokerCacheDBService;
    }

    @GetMapping("/rooms")
    public String allRooms(Model model) {
        model.addAttribute("rooms", pokerCacheDBService.allRooms());
        return "poker/room_list";
    }

    @GetMapping("/room/{roomId}")
    public String getRoom(@PathVariable("roomId") Long roomId, Model model) {
        Optional<Room> roomOptional = pokerCacheDBService.getRoomById(roomId);
        if (roomOptional.isPresent()) {
            model.addAttribute("room", roomOptional.get());
            return "poker/room_info";
        }
        throw new RuntimeException("Can not found room #" + roomId);
    }

    @GetMapping("/enter_room/{roomId}/pokers/{pokerId}")
    public String enterRoom(@PathVariable("roomId") Long roomId,
                            @PathVariable("pokerId") Long pokerId,
                            Model model) {
        Optional<Room> roomOptional = pokerCacheDBService.getRoomById(roomId);
        Optional<Poker> pokerOptional = pokerCacheDBService.getPokerByRoomIdAndPokerId(roomId, pokerId);
        pokerCacheDBService.enterRoom(roomId, pokerId);
        model.addAttribute("room", roomOptional.get());
        model.addAttribute("user", pokerOptional.get());
        String pokerRole = pokerOptional.get().getRole().name().toLowerCase();
        return String.format("poker/%s_room", pokerRole);
    }

    @RequestMapping(value = "/subscribe/{roomId}/{pokerId}")
    public SseEmitter subscribeRoomMessage(@PathVariable("roomId") Long roomId,
                                           @PathVariable("pokerId") Long pokerId,
                                           @RequestHeader(value = "Last-Event-ID", defaultValue = "0") Long lastEventId) {
        SseEmitter sseEmitter = pokerMessageService.subscribeRoom(pokerId, roomId, lastEventId);
        return sseEmitter;
    }

    @PostMapping("/send")
    @ResponseBody
    public boolean sendMessage(@RequestBody PokerEvent pokerEvent) {
        pokerMessageService.handlePokerEvent(pokerEvent);
        return true;
    }

    @PostMapping("/vote")
    @ResponseBody
    public boolean vote(@RequestParam("roomId") Long roomId,
                        @RequestParam("pokerId") Long pokerId,
                        @RequestParam(name = "vote") Integer votes) {
        pokerMessageService.vote(roomId, pokerId, votes);
        return true;
    }
}
