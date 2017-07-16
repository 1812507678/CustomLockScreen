package com.example.mywebsocket.bean;

/**
 * Created by hai on 7/15/2017.
 */
public class OnlineUser {
    private String iconUrl;
    private String username;
    private int onlinestate;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOnlinestate() {
        return onlinestate;
    }

    public void setOnlinestate(int onlinestate) {
        this.onlinestate = onlinestate;
    }

    public OnlineUser(String iconUrl, String username, int onlinestate) {
        this.iconUrl = iconUrl;
        this.username = username;
        this.onlinestate = onlinestate;
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
                "iconUrl='" + iconUrl + '\'' +
                ", username='" + username + '\'' +
                ", onlinestate=" + onlinestate +
                '}';
    }
}
