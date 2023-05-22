package br.com.nexus.plugin.storage.database;

import br.com.nexus.plugin.model.PlayerObject;
import br.com.nexus.plugin.storage.HikaridConnect;
import br.com.nexus.plugin.util.ListUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class DatabaseMethod {

    private final HikaridConnect hikaridConnect;
    private final ListUtil listUtil;

    public Boolean hasProxiedPlayer(ProxiedPlayer proxiedPlayer) throws SQLException {
        ResultSet resultSet = null;
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `NexusAmigos` WHERE `Player` = ?;")) {
            preparedStatement.setString(1, proxiedPlayer.getName());
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {
            if(resultSet != null) resultSet.close();
        }
    }

    public Boolean hasProxiedPlayer(String proxiedPlayer) throws SQLException {
        ResultSet resultSet = null;
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `NexusAmigos` WHERE `Player` = ?;")) {
            preparedStatement.setString(1, proxiedPlayer);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {
            if(resultSet != null) resultSet.close();
        }
    }

    public void updatePlayerObject(PlayerObject playerObject) throws SQLException {
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `NexusAmigos` SET `FriendList` = ? WHERE `Player` = ?")) {
            preparedStatement.setString(1, listUtil.convertArrayListInString(playerObject.getFriendList()));
            preparedStatement.setString(2, playerObject.getProxiedPlayer().getName());
            preparedStatement.executeUpdate();
        }
    }

    public void updateFrindList(String name, ArrayList<String> amigos) throws SQLException {
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `NexusAmigos` SET `FriendList` = ? WHERE `Player` = ?")) {
            preparedStatement.setString(1, listUtil.convertArrayListInString(amigos));
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }
    }

    public void setProxiedPlayer(ProxiedPlayer proxiedPlayer) throws SQLException {
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `NexusAmigos`(`Player`,`FriendList`, `Notification`) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, proxiedPlayer.getName());
            preparedStatement.setString(2, "");
            preparedStatement.setBoolean(3, true);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<String> getListFriendByName(String name) throws SQLException {
        if (!hasProxiedPlayer(name)) new ArrayList<String>();
        ResultSet resultSet = null;
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `NexusAmigos` WHERE `Player` = ?;")) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return listUtil.convertStringInArrayList(resultSet.getString("FriendList"));
        }
        finally {
            if(resultSet != null) resultSet.close();
        }
    }

    public PlayerObject getPlayerObjectByPorxiedPlayer(ProxiedPlayer proxiedPlayer) throws SQLException {
        if (!hasProxiedPlayer(proxiedPlayer)) setProxiedPlayer(proxiedPlayer);

        ResultSet resultSet = null;
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `NexusAmigos` WHERE `Player` = ?;")) {
            preparedStatement.setString(1, proxiedPlayer.getName());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new PlayerObject(proxiedPlayer, resultSet.getBoolean("Notification"), new ArrayList<>(), listUtil.convertStringInArrayList(resultSet.getString("FriendList")));
        } finally {
            if(resultSet != null) resultSet.close();
        }

    }

    public void createTable() throws SQLException {
        try(Connection connection = hikaridConnect.hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `NexusAmigos`(`Player` VARCHAR(24), `FriendList` LONGTEXT, `Notification` BOOLEAN)")) {
            preparedStatement.executeUpdate();
        }
    }

}
