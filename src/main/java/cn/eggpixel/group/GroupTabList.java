package cn.eggpixel.group;

import cn.eggpixel.EggPixel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupTabList implements TabExecutor, CommandExecutor {
    //建立List列表
    public static List<String> SC = new ArrayList<>();
    public static List<String> SC2 = new ArrayList<>();
    public static List<String> EDIT = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> List = new ArrayList<>();
        if (args.length == 1) {
            SC.forEach(s -> {
                if (sender.hasPermission("emeraldtools.true") || sender.isOp()) List.add(s);
            });
            return List;
        } else if (args.length == 2) {
            switch (args[0]) {
                case "create":
                case "chat":
                case "leave":
                case "destroy":
                    return List;
                case "join":
                    SC2 = getGroups();
                    SC2.forEach(s -> {
                        if (sender.hasPermission("emeraldtools.true") || sender.isOp()) List.add(s);
                    });
                    return List;
                case "deny":
                case "allow":
                    SC2 = getApplyGroup();
                    SC2.forEach(s -> {
                        if (sender.hasPermission("emeraldtools.true") || sender.isOp()) List.add(s);
                    });
                    return List;
            }
        } else {
            return List;
        }
        return List;
    }

    public List<String> getGroups() {
        EggPixel groups = new EggPixel("groups.yml");
        List<String> group = new ArrayList<>();
        if (groups.getString("groups") != null) {
            String[] var1 = groups.getString("groups").split("\\|");
            group.addAll(Arrays.asList(var1));
        }
        return group;
    }

    public String getPlayerInGroup(String playerName) {
        EggPixel groups = new EggPixel("player.yml");
        if (groups.getString(playerName) == null) {
            return null;
        }
        else {
            return groups.getString(playerName);
        }
    }

    public List<String> getApplyGroup() {
        EggPixel groups = new EggPixel("wait.yml");
        List<String> group2 = new ArrayList<>();
        if (groups.getString("all") != null) {
            String[] var1 = groups.getString("all").split("\\|");
            for (String var2 : var1) {
                if (groups.getString(var2).equals("true")) {
                    group2.add(var2);
                }
            }
        }
        return group2;
    }
}
