package br.com.nexus.plugin.API;

import br.com.nexus.plugin.cache.AmigosCache;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class NexusAmigosAPI {

    public Boolean isFriend(ProxiedPlayer player, ProxiedPlayer request) {
        return AmigosCache.hashMapList.get(player).getFriendList().contains(request.getName());
    }

}
