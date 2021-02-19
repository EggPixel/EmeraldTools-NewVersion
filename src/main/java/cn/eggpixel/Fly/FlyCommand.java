package cn.eggpixel.Fly;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    public final Main plugin;
    public FlyCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender.hasPermission("emeraldtools.fly")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = ((Player) sender).getPlayer();
                    assert player != null;
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_USED").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "False"));
                    } else {
                        player.setAllowFlight(true);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_USED").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "True"));
                    }
                } else {
                    sender.sendMessage(new EggPixel("config.yml").getString("DO_IN_CONSOLE"));
                }
                return true;
            } else if (args.length == 1) {
                try {
                    Player player = Bukkit.getPlayer(args[0]);
                    assert player != null;
                    if (player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_USED").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "False"));
                    } else {
                        player.setAllowFlight(true);
                        sender.sendMessage(new EggPixel("config.yml").getString("FLYING_USED").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "True"));
                    }
                } catch (Exception e){
                    sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                }
                return true;
            } else {
                sender.sendMessage(new EggPixel("config.yml").getString("FLYING_USAGE"));
                return true;
            }
        } else {
            sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            return true;
        }
    }
}
