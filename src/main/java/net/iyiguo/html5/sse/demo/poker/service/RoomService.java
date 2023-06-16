package net.iyiguo.html5.sse.demo.poker.service;

import net.iyiguo.html5.sse.demo.poker.dao.RoomDao;
import net.iyiguo.html5.sse.demo.poker.entity.Room;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomDto;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Service
public class RoomService {
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Optional<RoomVo> getRoomByNo(Long roomNo) {
        Optional<Room> roomOptional = roomDao.getById(roomNo);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            return Optional.of(new RoomVo(room.getId(), room.getName(), room.getType()));
        }
        return Optional.empty();
    }

    public List<RoomVo> findAllRooms() {
        return roomDao.findAll().stream()
                .map(room -> new RoomVo(room.getId(), room.getName(), room.getType()))
                .collect(Collectors.toList());
    }

    public void saveRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setType(roomDto.getType());
        roomDao.save(room);
    }
}
