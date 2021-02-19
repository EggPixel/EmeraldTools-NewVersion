package cn.eggpixel.Vanish;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class VanishCommand implements CommandExecutor {
    public final Main plugin;
    public VanishCommand(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender.hasPermission("emeraldtools.vanish")) {
            try {
                if (args.length == 1) {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    PotionEffect potionEffect = PotionEffectType.INVISIBILITY.createEffect(1000000000, 2);
                    PotionEffectType potionType = PotionEffectType.INVISIBILITY;
                    Iterator var2 = Bukkit.getOnlinePlayers().iterator();
                    assert player != null;
                    if (new VanishEnabledOr().Vanishes(player.getName())) {
                        player.addPotionEffect(potionEffect);
                        sender.sendMessage(new EggPixel("config.yml").getString("VANISH_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", ""));
                        player.sendMessage(new EggPixel("config.yml").getString("VANISH_WAS_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", ""));
                    } else {
                        player.removePotionEffect(potionType);
                        sender.sendMessage(new EggPixel("config.yml").getString("VANISH_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "没有"));
                        player.sendMessage(new EggPixel("config.yml").getString("VANISH_WAS_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "没有"));
                    }
                    while (var2.hasNext()) {
                        Player user = (Player) var2.next();
                        if (!user.hasPermission("emeraldtools.vanish.see")) {
                            if (new VanishEnabledOr().Vanish(player.getName())) {
                                user.hidePlayer(plugin, player);
                            } else {
                                user.showPlayer(plugin, player);
                            }
                        }
                    }
                    return true;
                }
                if (args.length == 0) {
                    if (sender instanceof Player) {
                        Player player = ((Player) sender).getPlayer();
                        PotionEffect potionEffect = PotionEffectType.INVISIBILITY.createEffect(1000000000, 2);
                        PotionEffectType potionType = PotionEffectType.INVISIBILITY;
                        Iterator var2 = Bukkit.getOnlinePlayers().iterator();
                        assert player != null;
                        if (new VanishEnabledOr().Vanish(player.getName())) {
                            player.addPotionEffect(potionEffect);
                            player.sendMessage(new EggPixel("config.yml").getString("VANISH_WAS_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", ""));
                        } else {
                            player.removePotionEffect(potionType);
                            player.sendMessage(new EggPixel("config.yml").getString("VANISH_WAS_USE").replace("%PLAYERNAME%", player.getName()).replace("%STATUS%", "没有"));
                        }
                        while (var2.hasNext()) {
                            Player user = (Player) var2.next();
                            if (!user.hasPermission("emeraldtools.vanish.see")) {
                                if (!new VanishEnabledOr().Vanishes(player.getName())) {
                                    user.hidePlayer(plugin, player);
                                } else {
                                    user.showPlayer(plugin, player);
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(new EggPixel("config.yml").getString("DO_IN_CONSOLE"));
                    }
                    return true;
                }
                sender.sendMessage(new EggPixel("config.yml").getString("VANISH_USAGE"));
                return true;
            } catch (Exception e) {
                sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                return true;
            }
        } else {
            sender.sendMessage(new EggPixel("config.yml").getString("NO_PERMISSION"));
            return true;
        }
    }
    public static class VanishEnabledOr {
        private final List<String> vanishes = new ArrayList<>();
        public boolean Vanish(String player) {
            if (vanishes.contains(player)) {
                vanishes.remove(player);
                return false;
            }
            else {
                vanishes.add(player);
                return true;
            }
        }
        public boolean Vanishes(String player) {
            return !vanishes.contains(player);
        }
        public boolean remove(String player) {
            return vanishes.contains(player);
        }
    }
}