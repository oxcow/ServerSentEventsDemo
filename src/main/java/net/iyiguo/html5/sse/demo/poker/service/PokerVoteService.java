package net.iyiguo.html5.sse.demo.poker.service;

import net.iyiguo.html5.sse.demo.poker.dao.PokerVoteDao;
import net.iyiguo.html5.sse.demo.poker.entity.PokerVote;
import net.iyiguo.html5.sse.demo.poker.web.dto.PokerVotesVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leeyee
 * @date 2021/7/19
 */
@Service
public class PokerVoteService {
    private PokerVoteDao pokerVoteDao;

    public PokerVoteService(PokerVoteDao pokerVoteDao) {
        this.pokerVoteDao = pokerVoteDao;
    }

    public void votes(Long porkId, Integer votes, Long roomNo) {
        PokerVote pokerVote = new PokerVote(roomNo, porkId, votes);
        pokerVoteDao.save(pokerVote);
    }

    public List<PokerVotesVo> findAllVotes(Long roomNo) {
        return pokerVoteDao.findByRoomId(roomNo).stream()
                .map(pv -> new PokerVotesVo(pv.getPorkId(), pv.getVotes()))
                .collect(Collectors.toList());
    }

    public void cleanVote(Long porkerId, Long roomNo) {
        pokerVoteDao.delByRoomIdAndPokerId(roomNo, porkerId);
    }

    public void cleanAllVote(Long roomNo) {
        pokerVoteDao.delByRoomId(roomNo);
    }
}
