package cn.eggpixel.DisableFlying;

import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DFlyingCommand implements CommandExecutor {
    public final Main plugin;
    public DFlyingCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            FileConfiguration config = plugin.getConfig();
            if (sender.hasPermission("disableflying.fly")) {
                if (Objects.equals(args[0], "reload") && args.length == 1) {
                    plugin.reloadConfig();
                    sender.sendMessage("§a[DisableFlying] Plugin reloaded.");
                    return true;
                }
                if (args.length == 1) {
                    String now = Bukkit.getWorld(args[0]).getName();
                    if (Boolean.parseBoolean(config.getString(now))) {
                        config.set(now, false);
                        sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        config.set(now, true);
                        sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    plugin.saveConfig();
                    return true;
                }
                if (args.length == 2) {
                    String now = Bukkit.getWorld(args[0]).getName();
                    boolean bool = Boolean.parseBoolean(args[1]);
                    if (!bool) {
                        config.set(now, false);
                        sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        config.set(now, true);
                        sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    plugin.saveConfig();
                    return true;
                } else {
                    if (sender instanceof Player) {
                        String now = ((Player) sender).getWorld().getName();
                        if (Boolean.parseBoolean(config.getString(now))) {
                            config.set(now, false);
                            sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                        } else {
                            config.set(now, true);
                            sender.sendMessage(config.getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                        }
                        plugin.saveConfig();
                    } else {
                        sender.sendMessage(config.getString("DO_IN_CONSOLE"));
                    }
                }
            } else {
                sender.sendMessage(config.getString("NO_PERMISSION"));
            }
            return true;
        } catch (Exception e) {
            FileConfiguration config = plugin.getConfig();
            sender.sendMessage(config.getString("WORLD_NOT_FOUND"));
            return true;
        }
    }
}
