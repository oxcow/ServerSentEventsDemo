package net.iyiguo.html5.sse.demo.poker.dao;

import net.iyiguo.html5.sse.demo.poker.entity.Poker;

import java.util.List;
import java.util.Optional;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public interface PokerDao {
    Optional<Poker> getById(Long id);

    List<Poker> findAll();

    Poker save(Poker poker);

    Poker delById(Long id);
}
