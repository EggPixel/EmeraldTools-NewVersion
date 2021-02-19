package cn.eggpixel.AntiDrop;

import cn.eggpixel.EggPixel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;

public class AntiDropEvent implements Listener {
    public static final List<String> AntiDrop = new ArrayList<>();
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        if (AntiDrop.contains(e.getPlayer().getUniqueId().toString())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(new EggPixel("config.yml").getString("ANTI_DROP_CANCELLED"));
        } else {
            e.setCancelled(false);
        }
    }
}
