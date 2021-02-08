package cn.eggpixel.antiDrop;

import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static cn.eggpixel.antiDrop.AntiDropEvent.AntiDrop;

public class AntiDropCommand implements CommandExecutor {
    public final Main plugin;
    public AntiDropCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        FileConfiguration config = Main.plugin.getConfig();
        if (sender.hasPermission("emeraldtools.antidrop")) {
            if (sender instanceof Player) {
                Player player = Bukkit.getPlayerExact(sender.getName());
                if (AntiDrop.contains(player.getUniqueId().toString())) {
                    AntiDrop.remove(player.getUniqueId().toString());
                    sender.sendMessage(config.getString("ANTI_DROP_SET").replace("%STATUS%", "能"));
                } else {
                    AntiDrop.add(player.getUniqueId().toString());
                    sender.sendMessage(config.getString("ANTI_DROP_SET").replace("%STATUS%", "不能"));
                }
            } else {
                sender.sendMessage(plugin.getConfig().getString("DO_IN_CONSOLE"));
            }
        } else {
            sender.sendMessage(plugin.getConfig().getString("NO_PERMISSION"));
        }
        return true;
    }
}
