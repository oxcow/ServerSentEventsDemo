package net.iyiguo.html5.sse.demo.poker.web.dto;

import java.util.List;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public class RoomPokersVo {
    private RoomVo room;
    private PokerVo oneself;
    private List<PokerVo> pokers;

    public RoomVo getRoom() {
        return room;
    }

    public void setRoom(RoomVo room) {
        this.room = room;
    }

    public PokerVo getOneself() {
        return oneself;
    }

    public void setOneself(PokerVo oneself) {
        this.oneself = oneself;
    }

    public List<PokerVo> getPokers() {
        return pokers;
    }

    public void setPokers(List<PokerVo> pokers) {
        this.pokers = pokers;
    }
}
