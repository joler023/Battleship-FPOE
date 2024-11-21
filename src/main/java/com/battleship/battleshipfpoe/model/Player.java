package com.battleship.battleshipfpoe.model;

import java.io.Serializable;

public class Player{
    private String nickname;
    private int shipsDestroyed;

    public Player() {
        nickname = "ANONIMO";
        shipsDestroyed = 0;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", shipsDestroyed=" + shipsDestroyed +
                '}';
    }
}
