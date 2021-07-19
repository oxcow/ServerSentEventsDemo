package net.iyiguo.html5.sse.demo.poker.dao;

import net.iyiguo.html5.sse.demo.poker.entity.PokerVote;

import java.util.List;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public interface PokerVoteDao {
    List<PokerVote> findByRoomId(Long roomId);

    void save(PokerVote pokerVote);

    void delByRoomId(Long roomId);

    void delByRoomIdAndPokerId(Long roomId, Long pokerId);
}
