package cn.eggpixel.prefix;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrefixCommand implements CommandExecutor {
    public Main plugin;
    public PrefixCommand(Main plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("emeraldtools.addprefix")) {
            sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            return true;
        }
        if (args.length == 2 && args[1].equals("remove")) {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                return true;
            }
            sender.sendMessage(new EggPixel("config.yml").getString("PREFIX_REMOVE").replace("%PLAYERNAME%",player.getName()));
            if (new EggPixel("prefix.yml").getString(player.getName()) != null) {
                new EggPixel("prefix.yml").set(player.getName(), null);
            }
            return true;
        } else if (args.length == 3 && args[1].equals("add")) {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                return true;
            }
            String prefix = args[2].replace("&","§");
            sender.sendMessage(new EggPixel("config.yml").getString("PREFIX_ADD").replace("%PLAYERNAME%",player.getName()).replace("%PREFIX%",prefix));
            new EggPixel("prefix.yml").set(player.getName(), prefix);
            return true;
        } else {
            sender.sendMessage(new EggPixel("config.yml").getString("PREFIX_USAGE"));
            return true;
        }
    }
}
