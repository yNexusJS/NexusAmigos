package br.com.nexus.plugin.command.subCommand;

import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.TextComponentUtil;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface SubCommand {

    String command();
    void execute(ProxiedPlayer proxiedPlayer, String args[], TextComponentUtil textComponentUtil, DatabaseMethod databaseMethod);

}
