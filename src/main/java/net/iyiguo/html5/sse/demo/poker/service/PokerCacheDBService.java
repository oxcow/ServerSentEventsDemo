package net.iyiguo.html5.sse.demo.poker.service;

import com.google.common.collect.Maps;
import net.iyiguo.html5.sse.demo.poker.config.PokerCacheProperties;
import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PokerCacheDBService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokerCacheDBService.class);

    private Map<Long, Poker> loggedPokers;

    private final PokerCacheProperties pokerCacheProperties;

    public PokerCacheDBService(PokerCacheProperties pokerCacheProperties) {
        this.pokerCacheProperties = pokerCacheProperties;
    }

    @PostConstruct
    public void init() {
        loggedPokers = Maps.newConcurrentMap();
    }

    public boolean enterRoom(Long roomId, Long pokerId) {
        Optional<Poker> pokerOptional = getPokerByRoomIdAndPokerId(roomId, pokerId);
        if (pokerOptional.isPresent()) {
            loggedPokers.put(roomId, pokerOptional.get());
        } else {
            throw new RuntimeException("Can not found Poker #" + pokerId + " from Room #" + roomId);
        }
        return true;
    }

    public List<Room> allRooms() {
        return pokerCacheProperties.getRooms();
    }

    public Optional<Room> getRoomById(Long id) {
        return pokerCacheProperties.getRooms().stream()
                .filter(room -> room.getId().equals(id))
                .findFirst();
    }

    public Optional<Poker> getPokerByRoomIdAndPokerId(Long roomId, Long pokerId) {
        Optional<Room> roomOptional = getRoomById(roomId);
        if (roomOptional.isPresent()) {
            return roomOptional.get().getPokers().stream()
                    .filter(p -> p.getId().equals(pokerId))
                    .findFirst();
        }
        throw new RuntimeException("Can not found Room #" + roomId);
    }

    public boolean addPokerToRoom(Poker poker, Long roomId) {
        Optional<Room> roomOptional = getRoomById(roomId);
        if (roomOptional.isPresent()) {
            if (Objects.isNull(poker.getId())) {
                roomOptional.get().getPokers().add(poker);
            } else {
                boolean exist = roomOptional.get().getPokers().stream()
                        .anyMatch(p -> p.getId().equals(poker.getId()));
                if (!exist) {
                    roomOptional.get().getPokers().add(poker);
                } else {
                    LOGGER.error("Poker #{} had existed in Room #{}", poker.getId(), roomId);
                }
            }
        } else {
            LOGGER.error("Can not found Room #{}", roomId);
            throw new RuntimeException("Can not found Room #" + roomId);
        }
        return true;
    }

}
