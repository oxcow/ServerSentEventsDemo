package net.iyiguo.html5.sse.demo.poker.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import net.iyiguo.html5.sse.demo.poker.config.PokerCacheProperties;
import net.iyiguo.html5.sse.demo.poker.dao.PokerDao;
import net.iyiguo.html5.sse.demo.poker.dao.PokerVoteDao;
import net.iyiguo.html5.sse.demo.poker.dao.RoomDao;
import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.entity.PokerVote;
import net.iyiguo.html5.sse.demo.poker.entity.Room;
import net.iyiguo.html5.sse.demo.poker.web.dto.PokerVo;
import net.iyiguo.html5.sse.demo.poker.web.dto.PokerVotesVo;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomPokersVo;
import net.iyiguo.html5.sse.demo.poker.web.dto.RoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Service
public class RoomPokersService {

    private SetMultimap<Long, Poker> roomPokersCache;

    @Autowired
    private PokerCacheProperties pokerCacheProperties;

    private RoomDao roomDao;
    private PokerDao pokerDao;
    private PokerVoteDao pokerVoteDao;

    public RoomPokersService(RoomDao roomDao, PokerDao pokerDao, PokerVoteDao pokerVoteDao) {
        this.roomDao = roomDao;
        this.pokerDao = pokerDao;
        this.pokerVoteDao = pokerVoteDao;
    }

    @PostConstruct
    public void init() {
        roomPokersCache = HashMultimap.create();

        pokerCacheProperties.getRooms().forEach(room -> roomDao.save(room));
        pokerCacheProperties.getPokers().forEach(poker -> pokerDao.save(poker));
    }

    public boolean hadEnterRoom(Long pokerId, Long roomNo) {
        Set<Poker> pokers = roomPokersCache.get(roomNo);
        return pokers.stream().anyMatch(poker -> pokerId.equals(poker.getId()));
    }

    public RoomPokersVo pokerEnterRoom(Long pokerId, Long roomNo) {
        RoomPokersVo roomPokersVo = new RoomPokersVo();
        Optional<Room> roomOptional = roomDao.getById(roomNo);
        Optional<Poker> pokerOptional = pokerDao.getById(pokerId);
        if (roomOptional.isPresent() && pokerOptional.isPresent()) {
            roomPokersCache.put(roomNo, pokerOptional.get());

            Room room = roomOptional.get();
            roomPokersVo.setRoom(new RoomVo(room.getId(), room.getName(), room.getType()));

            Map<Long, Integer> pokerVoteMap = getPokerVotes(roomNo);

            List<PokerVo> pokers = Lists.newArrayList();
            roomPokersCache.get(roomNo).forEach(poker -> {
                PokerVo pokerVo = new PokerVo(poker.getId(), poker.getName(), poker.getRole());

                if (pokerVoteMap.containsKey(poker.getId())) {
                    pokerVo.setVotes(new PokerVotesVo(poker.getId(), pokerVoteMap.get(poker.getId())));
                }
                if (poker.getId().equals(pokerId)) {
                    roomPokersVo.setOneself(pokerVo);
                }
                pokers.add(pokerVo);
                roomPokersVo.setPokers(pokers);
            });
        }
        return roomPokersVo;
    }

    private Map<Long, Integer> getPokerVotes(Long roomNo) {
        List<PokerVote> pokerVotes = pokerVoteDao.findByRoomId(roomNo);
        return pokerVotes.stream().collect(Collectors.toMap(PokerVote::getPorkId, PokerVote::getVotes));
    }
}
