package cn.eggpixel.repairServer;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;



public class RepairCommand implements CommandExecutor, Listener {
    private static boolean mode = false;
    public final Main plugin;
    public RepairCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (!sender.hasPermission("emeraldtools.repair")) {
            sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            return true;
        }
        if (args.length == 0) {
            if (!mode) {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.kickPlayer(new EggPixel("config.yml").getString("REPAIR_KICK"));
                }
                mode = true;
                sender.sendMessage(new EggPixel("config.yml").getString("REPAIR_MODE").replace("%STATUS%","开启"));
            } else {
                mode = false;
                sender.sendMessage(new EggPixel("config.yml").getString("REPAIR_MODE").replace("%STATUS%", "关闭"));
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.kickPlayer(new EggPixel("config.yml").getString("REPAIR_KICK"));
                }
                mode = true;
                sender.sendMessage(new EggPixel("config.yml").getString("REPAIR_MODE").replace("%STATUS%","开启"));
            } else if (args[0].equalsIgnoreCase("off")) {
                mode = false;
                sender.sendMessage(new EggPixel("config.yml").getString("REPAIR_MODE").replace("%STATUS%","关闭"));
            } else {
                sender.sendMessage(new EggPixel("config.yml").getString("REPAIR_USAGE"));
            }
        }
        return true;
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (mode) {
            if (!e.getPlayer().hasPermission("emeraldtools.repair.join")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, new EggPixel("config.yml").getString("REPAIR_KICK"));
            }
        }
    }
}
