package org.ipav.mines;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

import static org.ipav.mines.SQL.ChangeAmount;
public class Menu implements Listener {
    public static Inventory inventory;
    public static String inventoryName = ChatColor.GRAY.toString() + ChatColor.BOLD + "         Mine Settings";
    public static void openInventory(Player player) {
        inventory = Bukkit.createInventory(null, 36, inventoryName);
        for (int i = 0; i < 36; i++)
            inventory.setItem(i, CreateItem(Material.GRAY_STAINED_GLASS_PANE, 1, " ", Arrays.asList()));
        int[] values = SQL.GetUserData(String.valueOf(player.getUniqueId()));
        inventory.setItem(11, CreateItem(Material.STONE, 1, ChatColor.GRAY + "Stone", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD+ "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[0] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(12, CreateItem(Material.IRON_BLOCK, 1, ChatColor.WHITE + "Iron", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[1] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(13, CreateItem(Material.GOLD_BLOCK, 1, ChatColor.GOLD + "Gold", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[2] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(14, CreateItem(Material.DIAMOND_BLOCK, 1, ChatColor.AQUA + "Diamond", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[3] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(15, CreateItem(Material.BARRIER, 1, ChatColor.RED + "COMING SOON", Arrays.asList()));
        inventory.setItem(22, CreateItem(Material.BARRIER, 1, ChatColor.BOLD.toString()  + ChatColor.RED + "DEFAULT", Arrays.asList(ChatColor.DARK_GRAY + "Reset ALL values to default!")));
        player.openInventory(inventory);
    }
    private static ItemStack CreateItem(Material material, int Amount, String Name, List<String> Lore){
        ItemStack item = new ItemStack(material, Amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Name);
            if (Lore != null)
                meta.setLore(Lore);
            item.setItemMeta(meta);
        }
        return item;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equalsIgnoreCase(inventoryName)))
            return;
        event.setCancelled(true);
        if (!(event.getSlot() == 11 || event.getSlot() == 12 || event.getSlot() == 13 || event.getSlot() == 14 || event.getSlot() == 22))
            return;
        int amount = (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.DOUBLE_CLICK) ? 1 : (event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) ? -1 : 0;
        if (amount == 0) return;
        int[] values = ChangeAmount(((Player) event.getWhoClicked()), event.getSlot(), amount);
        inventory.setItem(11, CreateItem(Material.STONE, 1, ChatColor.GRAY + "Stone", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD+ "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[0] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(12, CreateItem(Material.IRON_BLOCK, 1, ChatColor.WHITE + "Iron", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[1] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(13, CreateItem(Material.GOLD_BLOCK, 1, ChatColor.GOLD + "Gold", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[2] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
        inventory.setItem(14, CreateItem(Material.DIAMOND_BLOCK, 1, ChatColor.AQUA + "Diamond", Arrays.asList(ChatColor.DARK_GRAY + "Left Click to Increase", ChatColor.DARK_GRAY + "Right Click to Decrease", "", ChatColor.GOLD + "Current Amount" + ChatColor.GRAY + ": " + ChatColor.DARK_GREEN + values[3] + ChatColor.GRAY + "/" + ChatColor.RED + "100")));
    }
}