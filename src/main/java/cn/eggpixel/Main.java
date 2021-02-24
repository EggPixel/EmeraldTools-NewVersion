package cn.eggpixel;

import cn.eggpixel.AntiBuild.AntiBuildCommand;
import cn.eggpixel.AntiBuild.AntiBuildEvents;
import cn.eggpixel.AntiDrop.AntiDropCommand;
import cn.eggpixel.AntiDrop.AntiDropEvent;
import cn.eggpixel.DisableFlying.DFlyingCommand;
import cn.eggpixel.DisableFlying.DFlyingEvent;
import cn.eggpixel.Fly.FlyCommand;
import cn.eggpixel.Heal.HealCommand;
import cn.eggpixel.Prefix.ChatEvent;
import cn.eggpixel.Prefix.PrefixCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new EggPixel("config.yml").save();
        new EggPixel("antibuild.yml").save();
        new EggPixel("prefix.yml").save();
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] *     插件已启动!     *");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        Objects.requireNonNull(this.getCommand("df")).setExecutor(new DFlyingCommand(this));
        Objects.requireNonNull(this.getCommand("ab")).setExecutor(new AntiBuildCommand(this));
        Objects.requireNonNull(this.getCommand("heal")).setExecutor(new HealCommand(this));
        Objects.requireNonNull(this.getCommand("ad")).setExecutor(new AntiDropCommand(this));
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new FlyCommand(this));
        Objects.requireNonNull(this.getCommand("prefix")).setExecutor(new PrefixCommand(this));

        Bukkit.getPluginManager().registerEvents(new DFlyingEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiDropEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiBuildEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        new Update("https://www.eggpixel.cn/update/up.txt", "2.0.0");
    }
    public static Main plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] *     插件已关闭!     *");
        Bukkit.getConsoleSender().sendMessage("§a[EmeraldTools] ***********************");
    }
}
