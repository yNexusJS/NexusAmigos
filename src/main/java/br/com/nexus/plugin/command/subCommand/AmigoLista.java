package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.api.NexusCoreAPI;
import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AmigoLista implements SubCommand{


    @Override
    public String command() {
        return "lista";
    }

    @Override
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(AmigosCache.hashMapList.get(proxiedPlayer).getFriendList().isEmpty()) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cPara executar esse comando você precisa ter amigos, disque: 188. Força amigo!"));
            return;
        }
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§eA sua lista de amigos:"));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        for(String nick : AmigosCache.hashMapList.get(proxiedPlayer).getFriendList()) {
            ProxiedPlayer proxiedPlayerNick = BungeeCord.getInstance().getPlayer(nick);
            if(proxiedPlayerNick != null) proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("  §e§l➥ " + new NexusCoreAPI().getTagUtil(proxiedPlayerNick).getTag() + " " +
                    proxiedPlayerNick.getName() + " " +
                    "§8("+new NexusCoreAPI().getPrefixServer(proxiedPlayerNick.getServer().getInfo().getName())+")§e."));
            else proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("  §e§l➥ §c" + nick + " §c(Offline)"));
        }
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
    }
}
