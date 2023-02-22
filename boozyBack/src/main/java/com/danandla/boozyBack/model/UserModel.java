package com.danandla.boozyBack.model;

public class UserModel {
    private Long id;
    private String nickname;
    private String realname;

    public UserModel(Long id, String nickname, String realname) {
        this.id = id;
        this.nickname = nickname;
        this.realname = realname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
