package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.api.NexusCoreAPI;
import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.model.PlayerObject;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import lombok.SneakyThrows;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AmigoAceitar implements SubCommand {


    @Override
    public String command() {
        return "aceitar";
    }

    @Override @SneakyThrows
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(args.length != 2) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cUse o comando da seguinte forma: /amigos aceitar <username>."));
            return;
        }
        String sendRequestNick = args[1];
        ProxiedPlayer playerRequest = BungeeCord.getInstance().getPlayer(sendRequestNick);
        if(!AmigosCache.hashMapList.containsKey(playerRequest)) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cO jogador está offline, apenas é permitido enviar solicitação de amizade para jogadores online, ou aceitar solicitação de jogadores online."));
            return;
        }
        if(AmigosCache.hashMapList.get(playerRequest).getFriendList().contains(proxiedPlayer.getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocês já são amigos."));
            return;
        }
        if(!AmigosCache.hashMapList.get(proxiedPlayer).getRequestList().contains(playerRequest.getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê não tem uma solicitação desse jogador."));
            return;
        }

        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§aA solicitação do jogador " + new NexusCoreAPI().getTagUtil(playerRequest).getTag()+ " " +
                playerRequest.getName()+ " §afoi aceita. Agora vocês são amigos."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));

        playerRequest.sendMessage(textComponentUtil.createTextComponent(""));
        playerRequest.sendMessage(textComponentUtil.createTextComponent("§aA sua solicitação enviada para o jogador " + new NexusCoreAPI().getTagUtil(proxiedPlayer).getTag() + " " +
                proxiedPlayer.getName() + " §afoi aceita. Agora vocês são amigos."));
        playerRequest.sendMessage(textComponentUtil.createTextComponent(""));

        PlayerObject playerObjectsendedRequest = AmigosCache.hashMapList.get(playerRequest);
        PlayerObject playerObject = AmigosCache.hashMapList.get(proxiedPlayer);

        playerObject.getRequestList().remove(playerObjectsendedRequest.getProxiedPlayer().getName());
        playerObject.getFriendList().add(playerObjectsendedRequest.getProxiedPlayer().getName());
        playerObjectsendedRequest.getFriendList().add(playerObject.getProxiedPlayer().getName());
        databaseMethod.updatePlayerObject(playerObjectsendedRequest);
        databaseMethod.updatePlayerObject(playerObject);
    }
}
