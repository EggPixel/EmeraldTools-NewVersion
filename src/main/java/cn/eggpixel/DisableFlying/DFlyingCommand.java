package cn.eggpixel.DisableFlying;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DFlyingCommand implements CommandExecutor {
    public final Main plugin;
    public DFlyingCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        try {
            if (sender.hasPermission("disableflying.fly")) {
                if (Objects.equals(args[0], "reload") && args.length == 1) {
                    new EggPixel("config.yml").reload();
                    sender.sendMessage("§a[DisableFlying] Plugin reloaded.");
                    return true;
                }
                if (args.length == 1) {
                    String now = Objects.requireNonNull(Bukkit.getWorld(args[0])).getName();
                    if (Boolean.parseBoolean(new EggPixel("config.yml").getString(now))) {
                        new EggPixel("config.yml").set(now, false);
                        sender.sendMessage(Objects.requireNonNull(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD")).replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        new EggPixel("config.yml").set(now, true);
                        sender.sendMessage(Objects.requireNonNull(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD")).replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    new EggPixel("config.yml").save();
                    return true;
                }
                if (args.length == 2) {
                    String now = Objects.requireNonNull(Bukkit.getWorld(args[0])).getName();
                    boolean bool = Boolean.parseBoolean(args[1]);
                    if (!bool) {
                        new EggPixel("config.yml").set(now, false);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                    } else {
                        new EggPixel("config.yml").set(now, true);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                    }
                    new EggPixel("config.yml").save();
                    return true;
                } else {
                    if (sender instanceof Player) {
                        String now = ((Player) sender).getWorld().getName();
                        if (Boolean.parseBoolean(new EggPixel("config.yml").getString(now))) {
                            new EggPixel("config.yml").set(now, false);
                            sender.sendMessage(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "False"));
                        } else {
                            new EggPixel("config.yml").set(now, true);
                            sender.sendMessage(new EggPixel("config.yml").getString("FLYING_ADD_OR_REMOVE_WORLD").replace("%WORLD%", now).replace("%STATUS%", "True"));
                        }
                        new EggPixel("config.yml").save();
                    } else {
                        sender.sendMessage(new EggPixel("config.yml").getString("DO_IN_CONSOLE"));
                    }
                }
            } else {
                sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            }
            return true;
        } catch (Exception e) {
            sender.sendMessage(new EggPixel("config.yml").getString("WORLD_NOT_FOUND"));
            return true;
        }
    }
}
