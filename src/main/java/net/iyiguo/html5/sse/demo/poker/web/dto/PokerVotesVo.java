package net.iyiguo.html5.sse.demo.poker.web.dto;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public class PokerVotesVo {
    private Long pokerId;
    private Integer votes;

    public PokerVotesVo() {
    }

    public PokerVotesVo(Long pokerId, Integer votes) {
        this.pokerId = pokerId;
        this.votes = votes;
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
}
