package cn.eggpixel.DisableFlying;


import cn.eggpixel.EggPixel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DFlyingEvent implements Listener {
    public final List<String> DFP = new ArrayList<>();
    @EventHandler
    public void onPlayerFly(PlayerMoveEvent e) {
        if (e.getPlayer().isFlying() && !e.getPlayer().hasPermission("disableflying.admin")) {
            if (Boolean.parseBoolean(new EggPixel("config.yml").getString(e.getPlayer().getWorld().getName()))) {
                e.getPlayer().setAllowFlight(false);
                e.getPlayer().setFlying(false);
                e.getPlayer().sendMessage(Objects.requireNonNull(new EggPixel("config.yml").getString("FLYING_IS_NOT_ENABLED")));
                if (Boolean.parseBoolean(new EggPixel("config.yml").getString("FLY_NO_FALL_DAMAGE"))) {
                    UUID uuid = e.getPlayer().getUniqueId();
                    Entity entity = Bukkit.getEntity(uuid);
                    assert entity != null;
                    DFP.add(entity.getName());
                } else {
                    UUID uuid = e.getPlayer().getUniqueId();
                    Entity entity = Bukkit.getEntity(uuid);
                    assert entity != null;
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
