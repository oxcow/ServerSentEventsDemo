package net.iyiguo.html5.sse.demo.poker.web;

import net.iyiguo.html5.sse.demo.poker.entity.Room;
import net.iyiguo.html5.sse.demo.poker.service.PokerCacheDBService;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomDto;
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

    private PokerCacheDBService pokerCacheDBService;

    public RoomController(PokerCacheDBService pokerCacheDBService) {
        this.pokerCacheDBService = pokerCacheDBService;
    }

    @GetMapping
    public String rooms(Model model) {
        model.addAttribute("rooms", pokerCacheDBService.allRooms());
        return "poker/room_list";
    }

    @GetMapping("/{roomId}")
    public String room(@PathVariable("roomId") Long roomNo, Model model) {
        Optional<Room> roomOptional = pokerCacheDBService.getRoomById(roomNo);
        if (roomOptional.isPresent()) {
            model.addAttribute("room", roomOptional.get());
            return "room_detail";
        }
        throw new RuntimeException("Can not found room #" + roomNo);
    }

    @GetMapping("/create")
    public String create() {
        return "poker/create_room";
    }

    @PostMapping("/create")
    public String saveCreate(RoomDto room) {
        // TODO: create room
        return "redirect:/demo/rooms";
    }

}
