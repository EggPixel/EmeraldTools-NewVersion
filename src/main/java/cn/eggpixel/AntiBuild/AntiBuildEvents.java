package cn.eggpixel.AntiBuild;

import cn.eggpixel.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;

public class AntiBuildEvents implements Listener {
    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent p) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(), "antibuild.yml"));
        Player player = p.getPlayer();
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName())) && !player.hasPermission("antibuild.place")) {
            p.setCancelled(true);
            player.sendMessage(Main.plugin.getConfig().getString("AB_BUILD"));
        }
    }
    @EventHandler
    public void onPlayerDestroy(BlockBreakEvent b) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(), "antibuild.yml"));
        Player player = b.getPlayer();
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName())) && !player.hasPermission("antibuild.destroy")) {
            b.setCancelled(true);
            player.sendMessage(Main.plugin.getConfig().getString("AB_BUILD"));
        }
    }
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent a) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(), "antibuild.yml"));
        Entity player = a.getDamager();
        if (Boolean.parseBoolean(config.getString(player.getWorld().getName())) && !player.hasPermission("antibuild.attack")) {
            a.setCancelled(true);
            player.sendMessage(Main.plugin.getConfig().getString("AB_BUILD"));
        }
    }
}
