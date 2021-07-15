package net.iyiguo.html5.sse.demo.poker.model;

import net.iyiguo.html5.sse.demo.poker.enums.PokerVoteStatusEnum;

/**
 * Poker Vote Info
 */
public class PokerVotes {
    private Long pokerId;
    private Integer votes;
    private PokerVoteStatusEnum voteStatus = PokerVoteStatusEnum.UN_VOTED;

    public PokerVotes() {
    }

    public PokerVotes(Long pokerId, Integer votes) {
        this.pokerId = pokerId;
        this.votes = votes;
    }

    public PokerVotes(Long pokerId, PokerVoteStatusEnum voteStatus) {
        this.pokerId = pokerId;
        this.voteStatus = voteStatus;
    }

    public PokerVotes(Long pokerId, Integer votes, PokerVoteStatusEnum voteStatus) {
        this.pokerId = pokerId;
        this.votes = votes;
        this.voteStatus = voteStatus;
    }

    public Long getPokerId() {
        return pokerId;
    }

    public void setPokerId(Long pokerId) {
        this.pokerId = pokerId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public PokerVoteStatusEnum getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(PokerVoteStatusEnum voteStatus) {
        this.voteStatus = voteStatus;
    }
}
