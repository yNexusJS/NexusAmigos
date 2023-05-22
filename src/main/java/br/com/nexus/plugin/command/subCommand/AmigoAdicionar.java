package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.api.NexusCoreAPI;
import br.com.nexus.plugin.cache.AmigosCache;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AmigoAdicionar implements SubCommand {


    @Override
    public String command() {
        return "add";
    }

    @Override
    public void execute(ProxiedPlayer proxiedPlayer, String args[], TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(args.length != 2) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cUse o comando da seguinte forma: /amigos adicionar <username>."));
            return;
        }
        String sendRequestNick = args[1];
        ProxiedPlayer playerRequest = BungeeCord.getInstance().getPlayer(sendRequestNick);
        if(!AmigosCache.hashMapList.containsKey(playerRequest)) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cO jogador está offline, apenas é permitido enviar solicitação de amizade para jogadores online."));
            return;
        }
        if(new NexusCoreAPI().isIgnoreServer(proxiedPlayer.getServer().getInfo().getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cEsse jogador está se autenticando."));
            return;
        }
        if(AmigosCache.hashMapList.get(playerRequest).getFriendList().contains(proxiedPlayer.getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocês já são amigos."));
            return;
        }
        if(AmigosCache.hashMapList.get(playerRequest).getRequestList().contains(proxiedPlayer.getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê já enviou uma solicitação para esse jogador."));
            return;
        }
        if(AmigosCache.hashMapList.get(proxiedPlayer).getRequestList().contains(proxiedPlayer.getName())) {
            proxiedPlayer.chat("/amigos aceitar " + playerRequest);
            return;
        }

        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§aSua solicitação foi enviada. O jogador "+ new NexusCoreAPI().getTagUtil(playerRequest).getTag() + " " +
                "" + playerRequest.getName() + " §atem cerca de 5 minutos para aceitar sua solicitação."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));

        AmigosCache.hashMapList.get(playerRequest).getRequestList().add(proxiedPlayer.getName());

        if(AmigosCache.hashMapList.get(playerRequest).getNotification()) {
            TextComponent aceitar = new TextComponent(TextComponent.fromLegacyText("§2§lACEITAR"));
            aceitar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§2§lAceitar a solicitação")));
            aceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo aceitar " + proxiedPlayer.getName()));


            TextComponent negar = new TextComponent(TextComponent.fromLegacyText("§c§lRECUSE"));
            negar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§c§lRecusar a solicitação")));
            negar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo recusar " + proxiedPlayer.getName()));

            TextComponent mensagem = new TextComponent(TextComponent.fromLegacyText("§aO jogador " +
                    new NexusCoreAPI().getTagUtil(proxiedPlayer).getTag().replaceAll("&", "§")+ " " + proxiedPlayer + " §aenviou para você uma solicitação de amizade, clique aqui para "));

            mensagem.addExtra(aceitar);
            mensagem.addExtra(" §aou ");
            mensagem.addExtra(negar);
            mensagem.addExtra("§a.");

            playerRequest.sendMessage(textComponentUtil.createTextComponent(""));
            playerRequest.sendMessage(mensagem);
            playerRequest.sendMessage(textComponentUtil.createTextComponent(""));
        }

    }
}
