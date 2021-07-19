package net.iyiguo.html5.sse.demo.poker.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.iyiguo.html5.sse.demo.poker.dao.RoomDao;
import net.iyiguo.html5.sse.demo.poker.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Repository
public class RoomMemoryDaoImpl implements RoomDao {

    private Map<Long, Room> roomsCache;
    private AtomicLong idAtomic;

    @PostConstruct
    public void init() {
        roomsCache = Maps.newConcurrentMap();
        idAtomic = new AtomicLong(0L);
    }

    @PreDestroy
    public void destroy() {
        roomsCache = null;
        idAtomic = null;
    }

    @Override
    public Optional<Room> getById(Long id) {
        return Optional.ofNullable(roomsCache.get(id));
    }

    @Override
    public List<Room> findAll() {
        return Lists.newArrayList(roomsCache.values());
    }

    @Override
    public Room save(Room room) {
        Long id = idAtomic.addAndGet(1L);
        room.setId(id);
        roomsCache.put(id, room);
        return room;
    }

    @Override
    public Room delById(Long id) {
        return roomsCache.remove(id);
    }

}
