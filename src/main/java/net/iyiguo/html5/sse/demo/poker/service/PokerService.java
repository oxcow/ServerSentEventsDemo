package net.iyiguo.html5.sse.demo.poker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.iyiguo.html5.sse.demo.poker.dao.PokerDao;
import net.iyiguo.html5.sse.demo.poker.entity.Poker;
import net.iyiguo.html5.sse.demo.poker.enums.PokerRoleEnum;
import net.iyiguo.html5.sse.demo.poker.web.dto.PokerVo;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Service
public class PokerService {
    private PokerDao pokerDao;

    public PokerService(PokerDao pokerDao) {
        this.pokerDao = pokerDao;
    }

    public List<PokerVo> findAllPokers() {
        return pokerDao.findAll().stream()
                .map(poker -> new PokerVo(poker.getId(), poker.getName(), poker.getRole()))
                .collect(Collectors.toList());
    }

    public Poker savePoker(String name) {
        Poker poker = new Poker();
        poker.setName(name);
        poker.setRole(PokerRoleEnum.POKER);
        return pokerDao.save(poker);
    }
}
