package cn.eggpixel.group;

import cn.eggpixel.EggPixel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GroupEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        String name = e.getPlayer().getName();
        EggPixel wait = new EggPixel("wait.yml");
        EggPixel groups = new EggPixel("groups.yml");
        EggPixel player = new EggPixel("player.yml");
        EggPixel config = new EggPixel("config.yml");
        String groupName1 = player.getString(name);
        if (groupName1 != null) {
            String groupName = groupName1.split("\\|")[1];
            if (wait.getString(groupName).equals("false")) {
                String var3 = player.getString(e.getPlayer().getName()).split("\\|")[1];
                if (wait.getString("all").contains("|" + var3)) {
                    wait.set("all", wait.getString("all").replace("|" + var3, ""));
                } else if (wait.getString("all").contains(var3 + "|")) {
                    wait.set("all", wait.getString("all").replace(var3 + "|", ""));
                } else {
                    wait.set("all", null);
                }
                player.set(name,null);
                wait.set(groupName,null);
                e.getPlayer().sendMessage(config.getString("GROUP_BY_DENY"));
            } else if (wait.getString(groupName).equals("ok")) {
                wait.set(groupName,null);
                e.getPlayer().sendMessage(config.getString("GROUP_BY_ALLOW"));
            }
        }
    }
}
