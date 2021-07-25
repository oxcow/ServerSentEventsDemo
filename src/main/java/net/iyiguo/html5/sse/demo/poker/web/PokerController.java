package net.iyiguo.html5.sse.demo.poker.web;

import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.enums.PokerActionEnum;
import net.iyiguo.html5.sse.demo.poker.model.PokerEvent;
import net.iyiguo.html5.sse.demo.poker.service.PokerMessageService;
import net.iyiguo.html5.sse.demo.poker.service.PokerService;
import net.iyiguo.html5.sse.demo.poker.service.PokerVoteService;
import net.iyiguo.html5.sse.demo.poker.service.RoomPokersService;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomPokersVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@RequestMapping("/demo/pokers")
public class PokerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokerController.class);

    private PokerMessageService pokerMessageService;

    private RoomPokersService roomPokersService;

    private PokerVoteService pokerVoteService;

    @Autowired
    private PokerService pokerService;

    public PokerController(PokerMessageService pokerMessageService, RoomPokersService roomPokersService, PokerVoteService pokerVoteService) {
        this.pokerMessageService = pokerMessageService;
        this.roomPokersService = roomPokersService;
        this.pokerVoteService = pokerVoteService;
    }

    @GetMapping("/{pokerId}/enterRoom/{roomId}")
    public String enterRoom(@PathVariable("pokerId") Long pokerId,
                            @PathVariable("roomId") Long roomNo,
                            Model model) {
        boolean hadEnterRoom = roomPokersService.hadEnterRoom(pokerId, roomNo);
        RoomPokersVo roomPokersVo = roomPokersService.pokerEnterRoom(pokerId, roomNo);
        model.addAttribute("roomInfo", roomPokersVo);
        if (!hadEnterRoom) {
            PokerEvent pokerEvent = new PokerEvent(pokerId, roomNo, PokerActionEnum.ONLINE);
            pokerMessageService.handlePokerEvent(pokerEvent);
        }
        return "poker/room";
    }

    @PostMapping("/enterRoom/{roomNo}")
    public String enterRoom(@PathVariable("roomNo") Long roomNo,
                            @RequestParam("name") String name) {
        Poker poker = pokerService.savePoker(name);
        return String.format("redirect:/demo/pokers/%d/enterRoom/%d", poker.getId(), roomNo);
    }

    @PutMapping("/{pokerId}/room/{roomNo}")
    @ResponseBody
    public boolean leaveRoom(@PathVariable("pokerId") Long pokerId,
                             @PathVariable("roomNo") Long roomNo) {
        boolean isLeave = roomPokersService.pokerLeaveRoom(pokerId, roomNo);
        if (isLeave) {
            pokerVoteService.cleanVote(pokerId, roomNo);
            PokerEvent pokerEvent = new PokerEvent(pokerId, roomNo, PokerActionEnum.OFFLINE);
            pokerMessageService.handlePokerEvent(pokerEvent);

            LOGGER.info("Poker【{}】离开房间【{}】。{}", pokerId, roomNo);
        }
        return true;
    }

    @PostMapping("/cmd")
    @ResponseBody
    public boolean sendMessage(@RequestBody PokerEvent pokerEvent) {
        LOGGER.info("发送命令: {}", pokerEvent);
        pokerMessageService.handlePokerEvent(pokerEvent);
        return true;
    }

    @PostMapping("/{pokerId}/vote")
    @ResponseBody
    public boolean vote(@PathVariable("pokerId") Long pokerId,
                        @RequestParam("roomId") Long roomNo,
                        @RequestParam(name = "vote", required = false) Integer votes) {
        LOGGER.info("Poker【{}】在房间【{}】中投了【{}】票", pokerId, roomNo, votes);
        PokerEvent pokerEvent = new PokerEvent(pokerId, roomNo, PokerActionEnum.VOTE);
        if (Objects.nonNull(votes)) {
            pokerVoteService.votes(pokerId, votes, roomNo);
        } else {
            pokerVoteService.cleanVote(pokerId, roomNo);
            pokerEvent = new PokerEvent(pokerId, roomNo, PokerActionEnum.CANCEL);
        }
        pokerMessageService.handlePokerEvent(pokerEvent);
        return true;
    }
}
