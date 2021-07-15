package net.iyiguo.html5.sse.demo.poker.entity;

import net.iyiguo.html5.sse.demo.poker.enums.PokerRoleEnum;

public class Poker {

    private Long id;
    private String name;
    private PokerRoleEnum role;

    public Poker() {
    }

    public Poker(Long id, String name, PokerRoleEnum role) {
        this.id = id;
        this.name = name;
        this.role = role;
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

    public PokerRoleEnum getRole() {
        return role;
    }

    public void setRole(PokerRoleEnum role) {
        this.role = role;
    }
}
