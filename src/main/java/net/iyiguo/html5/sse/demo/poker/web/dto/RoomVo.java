package net.iyiguo.html5.sse.demo.poker.web.dto;

import net.iyiguo.html5.sse.demo.poker.enums.RoomTypeEnum;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public class RoomVo {
    private Long roomNo;
    private String name;
    private RoomTypeEnum type;

    public RoomVo() {
    }

    public RoomVo(Long roomNo, String name, RoomTypeEnum type) {
        this.roomNo = roomNo;
        this.name = name;
        this.type = type;
    }

    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }
}
