package br.com.nexus.plugin;

import br.com.nexus.plugin.command.AmigoCommand;
import br.com.nexus.plugin.listener.EventPlayer;
import br.com.nexus.plugin.storage.HikaridConnect;
import br.com.nexus.plugin.storage.database.DatabaseMethod;
import br.com.nexus.plugin.util.ConfigurationFile;
import br.com.nexus.plugin.util.ListUtil;
import br.com.nexus.plugin.util.TextComponentUtil;
import lombok.SneakyThrows;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public class Main extends Plugin {

    private String prefix = "§e[NexusAmigos] ";
    private final HikaridConnect hikaridConnect = new HikaridConnect();
    private final TextComponentUtil textComponentUtil = new TextComponentUtil();
    private final ListUtil listUtil = new ListUtil();
    private final ConfigurationFile configurationFile = new ConfigurationFile(this);
    private final DatabaseMethod databaseMethod = new DatabaseMethod(hikaridConnect, listUtil);

    @Override @SneakyThrows
    public void onEnable() {
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent(prefix + "§aPlugin iniciado"));
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent(prefix + "§aConfiguraçãos carregadas."));
        configurationFile.loadConfig();
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent(prefix + "§aDatabase carregada."));
        hikaridConnect.MySQLConnectLoad(configurationFile.getConfig().getString("MySQL.host"), configurationFile.getConfig().getString("MySQL.database"),
                configurationFile.getConfig().getString("MySQL.user"), configurationFile.getConfig().getString("MySQL.password"));
        databaseMethod.createTable();
        registerService();
    }

    public void registerService() {
        getProxy().getPluginManager().registerListener(this, new EventPlayer(hikaridConnect, databaseMethod, textComponentUtil));
        getProxy().getPluginManager().registerCommand(this, new AmigoCommand(textComponentUtil, databaseMethod));
    }

    @Override
    public void onDisable() {
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent(prefix + "§cPlugin desligando..."));
        hikaridConnect.closeHikariDataSource();
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent(prefix + "§cDatabase descarregada."));
    }
}
