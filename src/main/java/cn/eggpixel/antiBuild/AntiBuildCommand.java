package cn.eggpixel.antiBuild;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;


public class AntiBuildCommand implements CommandExecutor  {
    public final Main plugin;
    public AntiBuildCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            if (sender.hasPermission("antibuild.admin")) {
                if (args.length == 1 && Objects.equals(args[0], "reload")) {
                    new EggPixel("antibuild.yml").reload();
                    sender.sendMessage("§a[AntiBuild] AntiBuild Module reloaded");
                    return true;
                }
                if (args.length == 1) {
                    String now = Bukkit.getWorld(args[0]).getName();
                    if (Boolean.parseBoolean(new EggPixel("antibuild.yml").getString(now))) {
                        new EggPixel("antibuild.yml").set(now, String.valueOf(false));
                        sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        new EggPixel("antibuild.yml").set(now, String.valueOf(true));
                        sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    return true;
                }
                if (args.length == 2) {
                    String now = Bukkit.getWorld(args[0]).getName();
                    boolean bool = Boolean.parseBoolean(args[1]);
                    if (!bool) {
                        new EggPixel("antibuild.yml").set(now, String.valueOf(false));
                        sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        new EggPixel("antibuild.yml").set(now, String.valueOf(true));
                        sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    return true;
                }
                if (args.length == 0) {
                    if (sender instanceof Player) {
                        String now = ((Player) sender).getWorld().getName();
                        if (Boolean.parseBoolean(new EggPixel("antibuild.yml").getString(now))) {
                            new EggPixel("antibuild.yml").set(now, String.valueOf(false));
                            sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                        } else {
                            new EggPixel("antibuild.yml").set(now, String.valueOf(true));
                            sender.sendMessage(plugin.getConfig().getString("ANTIBUILD_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                        }
                    } else {
                        sender.sendMessage(plugin.getConfig().getString("DO_IN_CONSOLE"));
                    }
                } else {
                    sender.sendMessage(plugin.getConfig().getString("AB_USAGE"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("NO_PERMISSION"));
            }
            return true;
        } catch (Exception e) {
            sender.sendMessage(plugin.getConfig().getString("WORLD_NOT_FOUND"));
            return true;
        }
    }
}
