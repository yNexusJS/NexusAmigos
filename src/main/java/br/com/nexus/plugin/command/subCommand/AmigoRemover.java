package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.api.NexusCoreAPI;
import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.model.PlayerObject;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import lombok.SneakyThrows;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class AmigoRemover implements SubCommand {
    @Override
    public String command() {
        return "remover";
    }

    @Override @SneakyThrows
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(args.length != 2) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cUse o comando da seguinte forma: /amigos remover <username>."));
            return;
        }
        String sendRequestNick = args[1];
        if(!databaseMethod.hasProxiedPlayer(sendRequestNick)) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cEsse jogador não está cadastrado no banco de dados."));
            return;
        }
        if(!AmigosCache.hashMapList.get(proxiedPlayer).getFriendList().contains(sendRequestNick)) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cEsse jogador não é seu amigo."));
            return;
        }

        ProxiedPlayer playerRequest = BungeeCord.getInstance().getPlayer(sendRequestNick);
        if(playerRequest == null) {
            PlayerObject playerObject = AmigosCache.hashMapList.get(proxiedPlayer);
            ArrayList<String> amigos = databaseMethod.getListFriendByName(sendRequestNick);

            playerObject.getFriendList().remove(sendRequestNick);
            amigos.remove(playerObject.getProxiedPlayer().getName());

            databaseMethod.updatePlayerObject(playerObject);
            databaseMethod.updateFrindList(sendRequestNick, amigos);
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê e o jogador §7"+
                    sendRequestNick + " §cnão são mais amigos."));
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
            return;
        } else {
            PlayerObject playerObjectsendedRequest = AmigosCache.hashMapList.get(playerRequest);
            PlayerObject playerObject = AmigosCache.hashMapList.get(proxiedPlayer);

            playerObject.getFriendList().remove(playerObjectsendedRequest.getProxiedPlayer().getName());
            playerObjectsendedRequest.getFriendList().remove(playerObject.getProxiedPlayer().getName());
            databaseMethod.updatePlayerObject(playerObjectsendedRequest);
            databaseMethod.updatePlayerObject(playerObject);

            playerRequest.sendMessage(textComponentUtil.createTextComponent(""));
            playerRequest.sendMessage(textComponentUtil.createTextComponent("§cVocê e o jogador " + new NexusCoreAPI().getTagUtil(proxiedPlayer).getTag() + " " +
                    proxiedPlayer.getName() + " §cnão são mais amigos."));
            playerRequest.sendMessage(textComponentUtil.createTextComponent(""));

            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê e o jogador " + new NexusCoreAPI().getTagUtil(playerRequest).getTag() + " " +
                    playerRequest.getName() + " §cnão são mais amigos."));
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        }
    }
}
