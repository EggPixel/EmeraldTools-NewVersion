//工会组件指令
package cn.eggpixel.group;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GroupCommand implements CommandExecutor {
    //初始化
    public final Main plugin;
    public GroupCommand(Main plugin) {
        this.plugin = plugin;
    }
    EggPixel wait = new EggPixel("wait.yml");
    EggPixel groups = new EggPixel("groups.yml");
    EggPixel player = new EggPixel("player.yml");
    EggPixel config = new EggPixel("config.yml");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, String[] args) {
        //工会创建
        if (!(sender instanceof Player)) {
            sender.sendMessage(config.getString("DO_IN_CONSOLE"));
            return true;
        }
        if (args.length == 2) {
            switch (args[0]) {
                case "create":
                    if (sender.hasPermission("emeraldtools.group.create_op")) {
                        if (player.getString(sender.getName()) == null) {
                            createGroup(args[1].toLowerCase(), sender.getName(), true, (Player) sender);
                        } else {
                            sender.sendMessage(config.getString("GROUP_ALREADY_JOIN"));
                        }
                    } else if (sender.hasPermission("emeraldtools.group.create")) {
                        if (player.getString(sender.getName()) == null) {
                            createGroup(args[1].toLowerCase(), sender.getName(), false, (Player) sender);
                        } else {
                            sender.sendMessage(config.getString("GROUP_ALREADY_JOIN"));
                        }
                    } else {
                        sender.sendMessage(config.getString("NO_PERMISSION"));
                    }//加入小组
                    break;
                case "join":
                    if (sender.hasPermission("emeraldtools.group.join")) {
                        if (player.getString(sender.getName()) == null) {
                            joinGroup(args[1].toLowerCase(), sender.getName(), (Player) sender);
                        } else {
                            sender.sendMessage(config.getString("GROUP_ALREADY_JOIN"));
                        }
                    } else {
                        sender.sendMessage(config.getString("NO_PERMISSION"));
                    }
                    break;
                case "chat":
                    String name = sender.getName();
                    if (getPlayerInGroup(name) == null) {
                        sender.sendMessage(config.getString("GROUP_NOT_JOIN"));
                        break;
                    }
                    String a = player.getString(name);
                    String[] var1 = a.split("\\|");
                    String messages = config.getString("GROUP_CHAT_FORMAT").replace("%GroupName%", var1[1])
                            .replace("%PlayerName%", name).replace("%Message%", args[1]);
                    for (Player temp : Bukkit.getOnlinePlayers()) {
                        if (Objects.equals(player.getString(temp.getName()).split("\\|")[1], var1[1])) {
                            temp.sendMessage(messages);
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(messages);
                    break;
                case "allow":
                    if (getApplyGroup().contains(args[1])) {
                        wait.set(args[1],"ok");
                        createGroup(args[1],groups.getString(args[1]));
                        sender.sendMessage(config.getString("GROUP_ALLOW"));
                        this.wait = new EggPixel("wait.yml");
                        this.groups = new EggPixel("groups.yml");
                        this.player = new EggPixel("player.yml");
                        this.config = new EggPixel("config.yml");
                    } else {
                        sender.sendMessage(config.getString("GROUP_NOT_FOUND"));
                    }
                    break;
                case "deny":
                    if (getApplyGroup().contains(args[1])) {
                        wait.set(args[1],"false");
                        groups.set(args[1],null);
                        sender.sendMessage(config.getString("GROUP_DENY"));
                    } else {
                        sender.sendMessage(config.getString("GROUP_NOT_FOUND"));
                    }
                    this.wait = new EggPixel("wait.yml");
                    this.groups = new EggPixel("groups.yml");
                    this.player = new EggPixel("player.yml");
                    this.config = new EggPixel("config.yml");
                    break;
                default:
                    sender.sendMessage(config.getString("GROUP_USAGE"));
                    break;
            }
        } else if (args.length == 1) {
            switch (args[0]) {
                case "leave":
                    if (getPlayerInGroup(sender.getName()) == null) {
                        sender.sendMessage(config.getString("GROUP_NOT_JOIN"));
                    } else {
                        if (!Objects.equals(player.getString(sender.getName()).split("\\|")[0], "1")) {
                            leaveGroup(sender.getName(), (Player) sender);
                        } else {
                            sender.sendMessage(config.getString("GROUP_CANNOT_LEAVE"));
                        }
                    }
                    break;
                case "destroy":
                    if (getPlayerInGroup(sender.getName()) == null) {
                        sender.sendMessage(config.getString("GROUP_NOT_JOIN"));
                    } else if (Objects.equals(player.getString(sender.getName()).split("\\|")[0], "1")) {
                        sender.sendMessage(config.getString("GROUP_DESTROY").replace("%PLAYERNAME%", sender.getName()));
                        String var3 = player.getString(sender.getName()).split("\\|")[1];
                        if (groups.getString("groups").contains("|" + var3)) {
                            groups.set("groups", groups.getString("groups").replace("|" + var3, ""));
                        } else if (groups.getString("groups").contains(var3 + "|")) {
                            groups.set("groups", groups.getString("groups").replace(var3 + "|", ""));
                        } else {
                            groups.set("groups", null);
                        }
                        String[] var4 = groups.getString(var3).split("\\|");
                        for (String var5: var4) {
                            player.set(var5,null);
                        }
                        groups.set(var3,null);
                    } else {
                        sender.sendMessage(config.getString("NO_PERMISSION"));
                    }
                    break;
                case "reload":
                    this.wait = new EggPixel("wait.yml");
                    this.groups = new EggPixel("groups.yml");
                    this.player = new EggPixel("player.yml");
                    this.config = new EggPixel("config.yml");
                    sender.sendMessage(config.getString("GROUP_RELOADED"));
                    break;
                default:
                    sender.sendMessage(config.getString("GROUP_USAGE"));
                    break;
            }
        } else {
                sender.sendMessage(config.getString("GROUP_USAGE"));
        }
        return true;
    }

    /**
     * 获取小组列表
     * */
    public List<String> getGroups() {
        EggPixel groups = new EggPixel("groups.yml");
        List<String> group = new ArrayList<>();
        if (groups.getString("groups") != null) {
            String[] var1 = groups.getString("groups").split("\\|");
            group.addAll(Arrays.asList(var1));
        }
        return group;
    }

    /**
     * 获取玩家在哪一个小组
     * */
    public String getPlayerInGroup(String playerName) {
        EggPixel groups = new EggPixel("player.yml");
        if (groups.getString(playerName) == null) {
            return null;
        }
        else {
            return groups.getString(playerName);
        }
    }

    /**
     * 获取待审核小组列表
     * */
    public List<String> getApplyGroup() {
        EggPixel groups = new EggPixel("wait.yml");
        List<String> group2 = new ArrayList<>();
        if (groups.getString("all") != null) {
            String[] var1 = groups.getString("all").split("\\|");

            for (String var2 : var1) {
                if (groups.getString(var2)!=null && groups.getString(var2).equals("true")) {
                    group2.add(var2);
                }
            }
        }
        return group2;
    }

    /**
     * 创建小组
     * */
    public void createGroup(String groupName,String playerName,boolean isOP,Player players) {
        if (!(getApplyGroup().contains(groupName) || getGroups().contains(groupName))) {
            if (isOP) {
                player.set(playerName,1 + "|" + groupName);
                if (groups.getString("groups") == null) {
                    groups.set("groups", groupName);
                } else {
                    groups.set("groups", groups.getString("groups") + "|" + groupName);
                }
                players.sendMessage(config.getString("GROUP_CREATE").replace("%GROUPNAME%",groupName));
                groups.set(groupName, playerName);
            } else {
                player.set(playerName, 1 + "|" + groupName);
                wait.set(groupName, true);
                if (wait.getString("all") == null) {
                    wait.set("all", groupName);
                } else {
                    wait.set("all", wait.getString("all") + "|" + groupName);
                }
                players.sendMessage(config.getString("GROUP_WAIT"));
                groups.set(groupName, playerName);
            }
        } else {
            players.sendMessage(config.getString("GROUP_EXIST"));
        }
    }
    public void createGroup(String groupName,String playerName) {
        if (!(getApplyGroup().contains(groupName) && getGroups().contains(groupName))) {
            if (groups.getString("groups") == null) {
                groups.set("groups", groupName);
            } else {
                groups.set("groups", groups.getString("groups") + "|" + groupName);
            }
            groups.set(groupName, playerName);
        }
    }

    /**
     * 加入小组
     * */
    public void joinGroup(String groupName, String playerName, Player players) {
        if (getGroups().contains(groupName)) {
            int n = groups.getString(groupName).split("\\|").length;
            player.set(playerName, n + "|" + groupName);
            groups.set(groupName, groups.getString(groupName) + "|" + playerName);
            players.sendMessage(config.getString("GROUP_JOIN").replace("%GROUPNAME%",groupName));
        } else {
            players.sendMessage(config.getString("GROUP_NOT_FOUND"));
        }
    }

    /**
     * 离开小组
     * */
    public void leaveGroup(String playerName, Player players) {
        player.set(playerName,null);
        players.sendMessage(config.getString("GROUP_LEAVE"));
    }
}
