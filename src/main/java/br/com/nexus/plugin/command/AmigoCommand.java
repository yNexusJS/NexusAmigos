package br.com.nexus.plugin.command;

import br.com.nexus.plugin.api.NexusCoreAPI;
import br.com.nexus.plugin.command.subCommand.*;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

public class AmigoCommand extends Command {

    public AmigoCommand(TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod) {
        super("amigos", "", "amigo");
        this.textComponentUtil = textComponentUtil;
        this.databaseMethod = databaseMethod;
    }

    private final TextComponentUtil textComponentUtil;
    private final DatabaseMethod databaseMethod;
    public ArrayList<SubCommand> listCommand = new ArrayList<>();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(textComponentUtil.createTextComponent("§cApenas jogadores podem executar esse comando."));
            return;
        }
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
        if(new NexusCoreAPI().isIgnoreServer(proxiedPlayer.getServer().getInfo().getName())) {
            proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§cVocê precisa para se autenticar para executar esse comando."));
            return;
        }

        if(args.length == 0) {
            sendMessageDefault(proxiedPlayer);
            return;
        }

        registerSubCommand();
        for(SubCommand subcommand : listCommand) {
            if(subcommand.command().equalsIgnoreCase(args[0])) {
                subcommand.execute(proxiedPlayer, args, textComponentUtil, databaseMethod);
                return;
            }
        }
        sendMessageDefault(proxiedPlayer);
    }

    public void registerSubCommand() {
        listCommand.add(new AmigoAdicionar());
        listCommand.add(new AmigoAceitar());
        listCommand.add(new AmigoRemover());
        listCommand.add(new AmigoRecusar());
        listCommand.add(new AmigoPedidos());
        listCommand.add(new AmigoLista());
        listCommand.add(new AmigoNotifcation());
    }

    public void sendMessageDefault(ProxiedPlayer proxiedPlayer) {
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§eComandos amigos:"));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos add <nick> §f§l- §7Adicionar amigos."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos remover <nick> §f§l- §7Remover amigos."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos aceitar <nick> §f§l- §7Aceita pedido de amizade."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos recusar <nick> §f§l- §7Negar pedido de amizade."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos pedidos §f§l- §7Lista de pedidos enviado."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos lista §f§l- §7Listar seus amigos."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent("§e/amigos notificacao §f§l- §7Habilitar as notificações de amigos."));
        proxiedPlayer.sendMessage(textComponentUtil.createTextComponent(""));
    }

}
