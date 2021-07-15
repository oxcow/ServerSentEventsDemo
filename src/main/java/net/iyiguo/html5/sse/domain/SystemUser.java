package net.iyiguo.html5.sse.domain;

import net.iyiguo.html5.sse.enums.RoleEnum;

public class SystemUser {
    private Integer id;
    private String name;
    private String pass;
    private RoleEnum role;

    public SystemUser() {
    }

    public SystemUser(Integer id, String name, String pass, RoleEnum role) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.role = role;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
