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

public class AmigoPedidos implements SubCommand {
    @Override
    public String command() {
        return "pedidos";
    }

    @Override
    public void execute(ProxiedPlayer proxiedPlayer, String[] args, TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        if(AmigosCache.hashMapList.get(proxiedPlayer).getRequestList().isEmpty()) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê não tem solicitação de amizade."));
            return;
        }
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§eLista de pedido de amizade feito á você."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        for(String playerRequest : AmigosCache.hashMapList.get(proxiedPlayer).getRequestList()) {
            ProxiedPlayer proxiedPlayerRequest = BungeeCord.getInstance().getPlayer(playerRequest);
            StringBuilder prefix = new StringBuilder();
            prefix.append("  §f§l➥");

            if(proxiedPlayerRequest != null) prefix.append(" ").append(new NexusCoreAPI().getTagUtil(proxiedPlayerRequest).getTag().replaceAll("&", "§")).append(" ").append(proxiedPlayerRequest.getName());
            else prefix.append(" §7").append(playerRequest);

            TextComponent aceitar = new TextComponent(TextComponent.fromLegacyText("§2§l[ACEITAR]"));
            aceitar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§2§lAceitar a solicitação")));
            aceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo aceitar " + playerRequest));


            TextComponent negar = new TextComponent(TextComponent.fromLegacyText("§c§l[RECUSAR]"));
            negar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§c§lRecusar a solicitação")));
            negar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo recusar " + playerRequest));

            TextComponent msg = new TextComponent(TextComponent.fromLegacyText(prefix+" "));
            msg.addExtra(aceitar);
            msg.addExtra(" ");
            msg.addExtra(negar);

            proxiedPlayer.sendMessage(msg);
        }
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
    }
}
