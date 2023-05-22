package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.model.PlayerObject;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import lombok.SneakyThrows;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AmigoNotifcation implements SubCommand{


    @Override
    public String command() {
        return "notificacao";
    }

    @Override @SneakyThrows
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        PlayerObject playerObject = AmigosCache.hashMapList.get(proxiedPlayer);
        if(playerObject.getNotification()) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cSua notificação foi desabilitada."));
            databaseMethod.updatePlayerObject(playerObject);
            playerObject.setNotification(false);
        } else {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§aSua notificação foi habilitada."));
            databaseMethod.updatePlayerObject(playerObject);
            playerObject.setNotification(true);
        }
    }
}
