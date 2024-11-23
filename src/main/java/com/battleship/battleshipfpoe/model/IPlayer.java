package com.battleship.battleshipfpoe.model;

public interface IPlayer {
    void setNickname(String nickname);
    String getPlayerSunken();
    String getMachineSunken();
    void setMachineSunken(String machineSunken);
}
