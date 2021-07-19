package net.iyiguo.html5.sse.demo.poker.entity;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public class PokerVote {
    private Long roomId;
    private Long porkId;
    private Integer votes;

    public PokerVote() {
    }

    public PokerVote(Long roomId, Long porkId, Integer votes) {
        this.roomId = roomId;
        this.porkId = porkId;
        this.votes = votes;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPorkId() {
        return porkId;
    }

    public void setPorkId(Long porkId) {
        this.porkId = porkId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
