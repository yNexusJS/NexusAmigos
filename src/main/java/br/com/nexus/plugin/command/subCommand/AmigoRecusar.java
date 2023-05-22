package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AmigoRecusar implements SubCommand {
    @Override
    public String command() {
        return "recusar";
    }

    @Override
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(args.length != 2) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cUse o comando da seguinte forma: /amigos recusar <username>."));
            return;
        }
        String sendRequestNick = args[1];
        if(!AmigosCache.hashMapList.get(proxiedPlayer).getRequestList().contains(sendRequestNick)) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê não tem pedido de amizade desse jogador."));
            return;
        }
        AmigosCache.hashMapList.get(proxiedPlayer).getRequestList().remove(sendRequestNick);
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê recusou o convite de amizade do jogador §7" + sendRequestNick + " §c."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
    }
}
