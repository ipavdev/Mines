package org.ipav.mines;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import static org.ipav.mines.SQL.GetUserData;
import static org.ipav.mines.SQL.CreateUserData;
public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getCommand("mine").setExecutor(new Commands());
        this.getCommand("mine").setTabCompleter(new Commands());
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Menu(), this);
    }

    @Override
    public void onDisable() {getLogger().info("Disabled Mines Plugin!");}

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(GetUserData(player.getUniqueId().toString()) == null)
            CreateUserData(player.getUniqueId().toString());
        startMineLoop(player);
    }

    public void startMineLoop(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    if (Mine.countBlocks(player.getWorld(), -950, 110, -194, -900, 200) <= 2)
                        Mine.FillMine(player, player.getWorld(), -950, 110, -194, -900, 200);
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(this, 0L, 1200L);
    }

}
