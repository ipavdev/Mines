package org.ipav.mines;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

import static org.ipav.mines.SQL.GetUserData;

public class Mine{
    public static void FillMine(Player player, World world, int x1, int y1, int z1, int x2, int y2) {
        long startTime = System.currentTimeMillis();
        int[] chances = GetUserData(player.getUniqueId().toString());
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = z1; z <= z1 + (maxX - minX); z++) {
                    Location location = new Location(world, x, y, z);
                    Material block = generateBlock(chances[0], chances[1], chances[2], chances[3]);
                    world.getBlockAt(location).setType(block);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        player.sendMessage(ChatColor.GREEN + "Created your mine in " + totalTime + " milliseconds");
    }


    public static Material generateBlock(int stone, int iron, int gold, int diamond) {
        int totalBlocks = stone + iron + gold + diamond;
        Random random = new Random();
        int randomNumber = random.nextInt(totalBlocks) + 1;
        if (randomNumber <= stone) {
            return Material.STONE;
        } else if (randomNumber <= (stone + diamond)) {
            return Material.DIAMOND_BLOCK;
        } else if (randomNumber <= (stone + diamond + iron)) {
            return Material.IRON_BLOCK;
        } else {
            return Material.GOLD_BLOCK;
        }
    }

    public static float countBlocks(World world, int x1, int y1, int z1, int x2, int y2) {
        float total = 0;
        float air = 0;
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = z1; z <= z1 + (maxX - minX); z++) {
                    Location location = new Location(world, x, y, z);
                    total++;
                    if (world.getBlockAt(location).getType() == Material.AIR)
                        air++;
                }
            }
        }
        return total/air;
    }
}
