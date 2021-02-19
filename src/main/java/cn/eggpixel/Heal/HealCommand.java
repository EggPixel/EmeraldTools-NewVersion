package cn.eggpixel.Heal;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HealCommand implements CommandExecutor {
    public final Main plugin;
    public HealCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String s, String[] args) {
        if (sender.hasPermission("emeraldtools.heal")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setFoodLevel(20);
                    player.setSaturation(20);
                    player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
                    player.setFireTicks(0);
                    sender.sendMessage(new EggPixel("config.yml").getString("HEAL_PLAYER").replace("%PLAYERNAME%", player.getName()));
                } else {
                    sender.sendMessage(new EggPixel("config.yml").getString("DO_IN_CONSOLE"));
                }
                return true;
            } else if (args.length == 1) {
                try {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    assert player != null;
                    player.setFoodLevel(20);
                    player.setSaturation(20);
                    player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
                    player.setFireTicks(0);
                    sender.sendMessage(new EggPixel("config.yml").getString("HEAL_PLAYER").replace("%PLAYERNAME%", player.getName()));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                    return true;
                }
            } else {
                sender.sendMessage(new EggPixel("config.yml").getString("HEAL_USAGE"));
                return true;
            }
        } else {
            sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            return true;
        }
    }
}
