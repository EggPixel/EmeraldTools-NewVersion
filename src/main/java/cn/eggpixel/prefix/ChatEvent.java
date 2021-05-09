package cn.eggpixel.prefix;

import cn.eggpixel.EggPixel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatEvent implements Listener {
    String mess;
    public static boolean cancelled = false;
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        cancelled = e.isCancelled();
        if (!cancelled) {
            if (new EggPixel("prefix.yml").getString(e.getPlayer().getName()) == null) {
                this.mess = "<" + e.getPlayer().getDisplayName() + "> " + e.getMessage();
            } else {
                this.mess = new cn.eggpixel.EggPixel("prefix.yml").getString(e.getPlayer().getName()) + "§r<" + e.getPlayer().getDisplayName() + "> " + e.getMessage();
            }
            e.setCancelled(true);
            Bukkit.broadcastMessage(this.mess);
        }
    }
}
