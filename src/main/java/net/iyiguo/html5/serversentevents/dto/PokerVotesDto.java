package net.iyiguo.html5.serversentevents.dto;

import net.iyiguo.html5.serversentevents.domain.Poker;
import net.iyiguo.html5.serversentevents.enums.PokerVoteStatusEnum;

public class PokerVotesDto {
    private Poker poker;
    private Integer votes;
    private PokerVoteStatusEnum voteStatus = PokerVoteStatusEnum.UN_VOTED;

    public PokerVotesDto() {
    }

    public PokerVotesDto(Poker poker, Integer votes) {
        this.poker = poker;
        this.votes = votes;
    }

    public PokerVotesDto(Poker poker, PokerVoteStatusEnum voteStatus) {
        this.poker = poker;
        this.voteStatus = voteStatus;
    }

    public Poker getPoker() {
        return poker;
    }

    public void setPoker(Poker poker) {
        this.poker = poker;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
