package net.iyiguo.html5.sse.demo.poker.web.dto;

import net.iyiguo.html5.sse.demo.poker.enums.PokerRoleEnum;

/**
 * @author leeyee
 * @date 2021/7/19
 */
public class PokerVo {
    private Long id;
    private String name;
    private PokerRoleEnum role;

    private PokerVotesVo votes;

    public PokerVo() {
    }

    public PokerVo(Long id, String name, PokerRoleEnum role) {
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

    public PokerVotesVo getVotes() {
        return votes;
    }

    public void setVotes(PokerVotesVo votes) {
        this.votes = votes;
    }
}
