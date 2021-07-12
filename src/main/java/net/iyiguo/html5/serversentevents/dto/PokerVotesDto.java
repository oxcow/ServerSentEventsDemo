package net.iyiguo.html5.serversentevents.dto;

import net.iyiguo.html5.serversentevents.enums.PokerVoteStatusEnum;

/**
 * Poker Vote Info
 */
public class PokerVotesDto {
    private Long pokerId;
    private Integer votes;
    private PokerVoteStatusEnum voteStatus = PokerVoteStatusEnum.UN_VOTED;

    public PokerVotesDto() {
    }

    public PokerVotesDto(Long pokerId, Integer votes) {
        this.pokerId = pokerId;
        this.votes = votes;
    }

    public PokerVotesDto(Long pokerId, PokerVoteStatusEnum voteStatus) {
        this.pokerId = pokerId;
        this.voteStatus = voteStatus;
    }

    public PokerVotesDto(Long pokerId, Integer votes, PokerVoteStatusEnum voteStatus) {
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
