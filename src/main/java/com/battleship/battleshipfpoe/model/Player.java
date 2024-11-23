package com.battleship.battleshipfpoe.model;

import java.io.Serializable;

public class Player implements IPlayer{
    private String nickname;
    private String playerSunken;
    private String machineSunken;

    public Player() {
        nickname = "ANONIMO";
        playerSunken = "0";
        machineSunken = "0";
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public String getPlayerSunken() {
        return playerSunken;
    }
    public void setSunkenPlayer(String playerSunken) {
        this.playerSunken = playerSunken;
    }

    public String getMachineSunken() {
        return machineSunken;
    }
    public void setMachineSunken(String machineSunken) {
        this.machineSunken = machineSunken;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", playerSunken='" + playerSunken + '\'' +
                ", machineSunken='" + machineSunken + '\'' +
                '}';
    }
}
