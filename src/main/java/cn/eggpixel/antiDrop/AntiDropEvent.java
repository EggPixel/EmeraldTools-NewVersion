package cn.eggpixel.antiDrop;

import cn.eggpixel.Main;
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
            e.getPlayer().sendMessage(Main.plugin.getConfig().getString("ANTI_DROP_CANCELLED"));
        } else {
            e.setCancelled(false);
        }
    }
}
