package cn.eggpixel.Prefix;

import cn.eggpixel.EggPixel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatEvent implements Listener {
    String mess;
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (new EggPixel("prefix.yml").getString(e.getPlayer().getName()) == null) {
            this.mess = "<" + e.getPlayer().getDisplayName() + "> " + e.getMessage();
        } else {
            this.mess = new EggPixel("prefix.yml").getString(e.getPlayer().getName()) + "§r<" + e.getPlayer().getDisplayName() + "> " + e.getMessage();
        }
        e.setCancelled(true);
        Bukkit.broadcastMessage(this.mess);
    }
}
