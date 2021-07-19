package net.iyiguo.html5.sse.demo.poker.web;

import net.iyiguo.html5.sse.demo.poker.service.PokerService;
import net.iyiguo.html5.sse.demo.poker.service.RoomService;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomDto;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/7/15
 */
@Controller
@RequestMapping("/demo/rooms")
public class RoomController {

    private RoomService roomService;
    private PokerService pokerService;

    public RoomController(RoomService roomService, PokerService pokerService) {
        this.roomService = roomService;
        this.pokerService = pokerService;
    }

    @GetMapping
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAllRooms());
        model.addAttribute("pokers", pokerService.findAllPokers());
        return "poker/room_list";
    }

    @GetMapping("/{roomId}")
    public String room(@PathVariable("roomId") Long roomNo, Model model) {
        Optional<RoomVo> roomOptional = roomService.getRoomByNo(roomNo);
        if (roomOptional.isPresent()) {
            model.addAttribute("room", roomOptional.get());
            model.addAttribute("pokers", pokerService.findAllPokers());
            return "poker/room_detail";
        }
        throw new RuntimeException("Can not found room #" + roomNo);
    }

    @GetMapping("/create")
    public String create() {
        return "poker/create_room";
    }

    @PostMapping("/create")
    public String saveCreate(RoomDto room) {
        roomService.saveRoom(room);
        return "redirect:/demo/rooms";
    }

}
