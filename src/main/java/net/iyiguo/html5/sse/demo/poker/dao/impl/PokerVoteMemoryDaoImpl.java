package net.iyiguo.html5.sse.demo.poker.dao.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.iyiguo.html5.sse.demo.poker.dao.PokerVoteDao;
import net.iyiguo.html5.sse.demo.poker.entity.PokerVote;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Repository
public class PokerVoteMemoryDaoImpl implements PokerVoteDao {

    /**
     * Room:Poker:Votes
     */
    private Table<Long, Long, Integer> pokerVotesCache;

    @PostConstruct
    public void init() {
        pokerVotesCache = HashBasedTable.create();
    }

    @PreDestroy
    public void destroy() {
        pokerVotesCache = null;
    }

    @Override
    public List<PokerVote> findByRoomId(Long roomId) {
        List<PokerVote> pokerVotes = Lists.newArrayList();
        Map<Long, Integer> pokerVoteCols = pokerVotesCache.row(roomId);

        if (Objects.nonNull(pokerVoteCols)) {
            for (Map.Entry<Long, Integer> pv : pokerVoteCols.entrySet()) {
                pokerVotes.add(new PokerVote(roomId, pv.getKey(), pv.getValue()));
            }
        }
        return pokerVotes;
    }

    @Override
    public void delByRoomId(Long roomId) {
        Map<Long, Integer> pokerVoteCols = Maps.newHashMap(pokerVotesCache.row(roomId));
        Iterator<Long> pokerIdIter = pokerVoteCols.keySet().iterator();
        if (Objects.nonNull(pokerVoteCols)) {
            while (pokerIdIter.hasNext()) {
                pokerVotesCache.remove(roomId, pokerIdIter.next());
            }
        }
    }

    @Override
    public void delByRoomIdAndPokerId(Long roomId, Long pokerId) {
        pokerVotesCache.remove(roomId, pokerId);
    }

    @Override
    public void save(PokerVote pokerVote) {
        pokerVotesCache.put(pokerVote.getRoomId(), pokerVote.getPorkId(), pokerVote.getVotes());
    }
}
