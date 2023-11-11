package org.ipav.mines;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.Map;

public class SQL{
    //NO VALUES ARE SANITIZED!!! Make sure to sanitize any input if the user is able to modify it!
    private static final String url = "jdbc:mysql://localhost:3306/mines";
    private static final String user = "root";
    private static final String password = "";
    public static void CreateUserData(String uuid){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO `userdata` (`uuid`, `stoneAmount`, `ironAmount`, `goldAmount`, `diamondAmount`) VALUES (?, '100', '0', '0', '0')";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int[] GetUserData(String uuid) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM userdata WHERE uuid = ? LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new int[]{
                                resultSet.getInt("stoneAmount"),
                                resultSet.getInt("ironAmount"),
                                resultSet.getInt("goldAmount"),
                                resultSet.getInt("diamondAmount")
                        };
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int[] ResetAll(Player player){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE userdata SET stoneAmount = 100, ironAmount = 0, goldAmount = 0, diamondAmount = 0 WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, player.getUniqueId().toString());
                statement.executeUpdate();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0F, 2.0F);
                return GetUserData(player.getUniqueId().toString());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[] ChangeAmount(Player player, int slotNum, int amount) {
        if (slotNum == 22)
            return ResetAll(player);
        Map<Integer, String> slot = Map.of(11, "stoneAmount", 12, "ironAmount", 13, "goldAmount", 14, "diamondAmount");
        int index = slotNum - 11; //Quickest way to do this right now, if you continue to add stuff you'll have to change this.
        int[] oldValues = GetUserData(player.getUniqueId().toString());
        if (oldValues == null || index < 0 || index >= oldValues.length)
            return null;
        int newValue = oldValues[index] + amount;
        if (newValue > 100 || newValue < 0 || oldValues[0] + oldValues[1] + oldValues[2] + oldValues[3] + amount < 100) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
            return oldValues;
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE userdata SET " + slot.get(slotNum) + " = ? WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, newValue);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0F, 1.0F);
                return GetUserData(player.getUniqueId().toString());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

