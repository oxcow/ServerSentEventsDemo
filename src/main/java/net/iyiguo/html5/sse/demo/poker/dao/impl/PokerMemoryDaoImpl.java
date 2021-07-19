package net.iyiguo.html5.sse.demo.poker.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.iyiguo.html5.sse.demo.poker.dao.PokerDao;
import net.iyiguo.html5.sse.demo.poker.entity.Poker;
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
public class PokerMemoryDaoImpl implements PokerDao {
    private Map<Long, Poker> pokersCache;
    private AtomicLong idAtomic;

    @PostConstruct
    public void init() {
        pokersCache = Maps.newConcurrentMap();
        idAtomic = new AtomicLong(0L);
    }

    @PreDestroy
    public void destroy() {
        pokersCache = null;
        idAtomic = null;
    }

    @Override
    public Optional<Poker> getById(Long id) {
        return Optional.ofNullable(pokersCache.get(id));
    }

    @Override
    public List<Poker> findAll() {
        return Lists.newArrayList(pokersCache.values());
    }

    @Override
    public Poker save(Poker poker) {
        Long id = idAtomic.addAndGet(1L);
        poker.setId(id);
        pokersCache.put(id, poker);
        return poker;
    }

    @Override
    public Poker delById(Long id) {
        return pokersCache.remove(id);
    }
}
