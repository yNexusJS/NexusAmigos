package br.com.nexus.plugin.model;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

@Getter @Setter
public class PlayerObject {

    public PlayerObject(ProxiedPlayer proxiedPlayer, Boolean notification, ArrayList<String> requestList, ArrayList<String> friendList) {
        this.proxiedPlayer = proxiedPlayer;
        this.notification = notification;
        this.requestList = requestList;
        this.friendList = friendList;
    }

    private ProxiedPlayer proxiedPlayer;
    private Boolean notification;
    private ArrayList<String> requestList;
    private ArrayList<String> friendList;

}
