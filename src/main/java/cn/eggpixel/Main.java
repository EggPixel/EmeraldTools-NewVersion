package cn.eggpixel;

import cn.eggpixel.antiBuild.AntiBuildCommand;
import cn.eggpixel.antiBuild.AntiBuildEvents;
import cn.eggpixel.antiDrop.AntiDropCommand;
import cn.eggpixel.antiDrop.AntiDropEvent;
import cn.eggpixel.disableFlying.DFlyingCommand;
import cn.eggpixel.disableFlying.DFlyingEvent;
import cn.eggpixel.fly.FlyCommand;
import cn.eggpixel.group.GroupCommand;
import cn.eggpixel.group.GroupEvents;
import cn.eggpixel.group.GroupTabList;
import cn.eggpixel.heal.HealCommand;
import cn.eggpixel.prefix.ChatEvent;
import cn.eggpixel.prefix.PrefixCommand;
import cn.eggpixel.repairServer.RepairCommand;
import cn.eggpixel.ban.BanCommand;
import cn.eggpixel.ban.BanEvent;
import cn.eggpixel.sqlite.SQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new EggPixel("config.yml").saveDefault();
        new EggPixel("antibuild.yml").saveDefault();
        new EggPixel("prefix.yml").saveDefault();
        new EggPixel("disableflying.yml").saveDefault();
        new EggPixel("groups.yml").saveDefault();
        new EggPixel("player.yml").saveDefault();
        new EggPixel("wait.yml").saveDefault();

        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] *     插件已启动!     *");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        Objects.requireNonNull(this.getCommand("df")).setExecutor(new DFlyingCommand(this));
        Objects.requireNonNull(this.getCommand("ab")).setExecutor(new AntiBuildCommand(this));
        Objects.requireNonNull(this.getCommand("heal")).setExecutor(new HealCommand(this));
        Objects.requireNonNull(this.getCommand("ad")).setExecutor(new AntiDropCommand(this));
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new FlyCommand(this));
        Objects.requireNonNull(this.getCommand("prefix")).setExecutor(new PrefixCommand(this));
        Objects.requireNonNull(this.getCommand("repair")).setExecutor(new RepairCommand(this));
        Objects.requireNonNull(this.getCommand("group")).setExecutor(new GroupCommand(this));
        Objects.requireNonNull(this.getCommand("reload")).setExecutor(new ReloadCommands());
        Objects.requireNonNull(this.getCommand("ban")).setExecutor(new BanCommand(this));
        Objects.requireNonNull(this.getCommand("eban")).setExecutor(new BanCommand(this));
        Objects.requireNonNull(this.getCommand("unban")).setExecutor(new BanCommand(this));

        Bukkit.getPluginManager().registerEvents(new DFlyingEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiDropEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiBuildEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RepairCommand(this), this);
        Bukkit.getPluginManager().registerEvents(new GroupEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BanEvent(), this);

        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("group")).setTabCompleter(new GroupTabList());
        new Update("https://www.eggpixel.cn/update/up.txt");
        //new MySQLMain().CreateTable();

        new GroupTabList();
        List<String> group1 = GroupTabList.SC;
        group1.add("create");
        group1.add("join");
        group1.add("chat");
        group1.add("leave");
        group1.add("destroy");
        group1.add("reload");
        group1.add("allow");
        group1.add("deny");
        new SQL().connect_to_sql();
        new SQL().init();
    }
    public static Main plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }
    @Override
    public void onDisable() {
        if (ReloadCommands.reload) {
            new Message("你为什么要这么做呢?/reload重启后的服务器是极不稳定的,发生任何错误插件不接受反馈").error();
        } else {
            Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
            Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] *     插件已关闭!     *");
            Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        }
        new SQL().close();
    }

}
