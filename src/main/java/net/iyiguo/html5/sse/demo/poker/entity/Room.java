package net.iyiguo.html5.sse.demo.poker.entity;

import net.iyiguo.html5.sse.demo.poker.enums.RoomTypeEnum;

public class Room {

    private Long id;
    private String name;
    private transient String pass;
    private RoomTypeEnum type;

    public Room() {
    }

    public Room(Long id, String name, RoomTypeEnum type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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