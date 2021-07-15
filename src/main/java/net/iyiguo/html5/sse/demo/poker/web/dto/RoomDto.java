package net.iyiguo.html5.sse.demo.poker.web.dto;

import net.iyiguo.html5.sse.enums.RoomTypeEnum;

/**
 * @author leeyee
 * @date 2021/7/14
 */
public class RoomDto {
    private String name;
    private RoomTypeEnum type;
    private String description;
    private boolean persist;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }
}
