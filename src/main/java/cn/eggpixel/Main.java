package cn.eggpixel;

import cn.eggpixel.antiBuild.AntiBuildCommand;
import cn.eggpixel.antiBuild.AntiBuildEvents;
import cn.eggpixel.antiDrop.AntiDropCommand;
import cn.eggpixel.antiDrop.AntiDropEvent;
import cn.eggpixel.disableFlying.DFlyingCommand;
import cn.eggpixel.disableFlying.DFlyingEvent;
import cn.eggpixel.heal.HealCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        File config = new File(getDataFolder(), "config.yml");
        new EggPixel("antibuild.yml").load();
        if (!config.exists()) {
            saveResource("config.yml",false);
            getLogger().log(Level.SEVERE,"没有找到配置文件!正在生成配置文件!");
        }
        File antibuild = new File(getDataFolder(), "antibuild.yml");
        if (!antibuild.exists()) {
            saveResource("antibuild.yml",false);
            getLogger().log(Level.SEVERE,"没有找到antibuild.yml文件!正在生成!");
        }
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] ***********************");
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] *     插件已启动!     *");
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] ***********************");
        Objects.requireNonNull(this.getCommand("df")).setExecutor(new DFlyingCommand(this));
        Objects.requireNonNull(this.getCommand("ab")).setExecutor(new AntiBuildCommand(this));
        Objects.requireNonNull(this.getCommand("heal")).setExecutor(new HealCommand(this));
        Objects.requireNonNull(this.getCommand("ad")).setExecutor(new AntiDropCommand(this));

        Bukkit.getPluginManager().registerEvents(new DFlyingEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiDropEvent(),this);
        Bukkit.getPluginManager().registerEvents(new AntiBuildEvents(), this);
    }
    public static Main plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] ***********************");
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] *     插件已关闭!     *");
        Bukkit.getConsoleSender().sendMessage("§a[DisableFlying] ***********************");
    }
}
