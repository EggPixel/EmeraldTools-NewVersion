package cn.eggpixel.disableFlying;


import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DFlyingEvent implements Listener {
    public final List<String> DFP = new ArrayList<>();
    @EventHandler
    public void onPlayerFly(PlayerMoveEvent e) {
        FileConfiguration config = Main.plugin.getConfig();
        if (e.getPlayer().isFlying() && !e.getPlayer().hasPermission("disableflying.admin")) {
            if (Boolean.parseBoolean(config.getString(e.getPlayer().getWorld().getName()))) {
                e.getPlayer().setAllowFlight(false);
                e.getPlayer().setFlying(false);
                e.getPlayer().sendMessage(config.getString("FLYING_IS_NOT_ENABLED"));
                if (Boolean.parseBoolean(config.getString("FLY_NO_FALL_DAMAGE"))) {
                    UUID uuid = e.getPlayer().getUniqueId();
                    Entity entity = Bukkit.getEntity(uuid);
                    DFP.add(entity.getName());
                } else {
                    UUID uuid = e.getPlayer().getUniqueId();
                    Entity entity = Bukkit.getEntity(uuid);
                    DFP.remove(entity.getName());
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        org.bukkit.event.entity.EntityDamageEvent.DamageCause cause = e.getCause();
        if (DFP.contains(e.getEntity().getName())) {
            if (cause == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
                DFP.remove(e.getEntity().getName());
            }
        }
    }
}
