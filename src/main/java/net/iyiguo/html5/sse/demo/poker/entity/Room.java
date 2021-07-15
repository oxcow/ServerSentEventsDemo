package net.iyiguo.html5.sse.demo.poker.entity;

import com.google.common.collect.Lists;
import net.iyiguo.html5.sse.enums.RoomTypeEnum;

import java.util.List;

public class Room {

    private Long id;
    private String name;
    private transient String pass;
    private RoomTypeEnum type;
    private List<Poker> pokers = Lists.newArrayList();

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

    public List<Poker> getPokers() {
        return pokers;
    }

    public void setPokers(List<Poker> pokers) {
        this.pokers = pokers;
    }
}
