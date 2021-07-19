package net.iyiguo.html5.sse.demo.poker.dao;

import net.iyiguo.html5.sse.demo.poker.entity.Room;

import java.util.List;
import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public interface RoomDao {
    Optional<Room> getById(Long id);

    List<Room> findAll();

    Room save(Room room);

    Room delById(Long id);
}
