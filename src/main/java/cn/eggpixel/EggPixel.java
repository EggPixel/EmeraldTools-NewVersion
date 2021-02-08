package cn.eggpixel;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

public class EggPixel{
    final String FileName;
    YamlConfiguration get = null;
    public EggPixel(String FileName) {
        this.FileName = FileName;
    }
    public String getString(String Key) {
        try {
            YamlConfiguration getting = load();
            return getting.getString(Key);
        } catch (Exception e) {
            Main.plugin.getLogger().log(Level.SEVERE, "=================THIS IS A BUG OR FILE NOT EXIST!=============");
            Main.plugin.getLogger().log(Level.SEVERE, "错误!无法获取" + FileName + "下的" + Key + "键内容!请检查是否存在文件!");
            Main.plugin.getLogger().log(Level.SEVERE, "错误信息:" + e.getMessage());
            Main.plugin.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            Main.plugin.getLogger().log(Level.SEVERE, "                         即将关闭插件!                          ");
            Main.plugin.getLogger().log(Level.SEVERE, "==============================================================");
            Bukkit.getPluginManager().disablePlugin(Main.plugin);
            File antibuild = new File(Main.plugin.getDataFolder(),"antibuild.yml");
            if (antibuild.exists()) {
                antibuild.delete();
            }
            File config = new File(Main.plugin.getDataFolder(),"config.yml");
            if (config.exists()) {
                config.delete();
            }
            return "§a读取文件错误!请查看后台!";
        }
    }
    public void reload() {
        get = null;
        load();
    }
    public void save() {
        try {
            load().save(new File(Main.plugin.getDataFolder(), FileName));
        } catch (Exception e) {
            Main.plugin.getLogger().log(Level.SEVERE, "=================THIS IS A BUG OR FILE NOT EXIST!=============");
            Main.plugin.getLogger().log(Level.SEVERE, "                       错误!无法保存" + FileName);
            Main.plugin.getLogger().log(Level.SEVERE, "错误信息:" + e.getMessage());
            Main.plugin.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            Main.plugin.getLogger().log(Level.SEVERE, "                         即将关闭插件!                          ");
            Main.plugin.getLogger().log(Level.SEVERE, "==============================================================");
            File antibuild = new File(Main.plugin.getDataFolder(),"antibuild.yml");
            if (antibuild.exists()) {
                antibuild.delete();
            }
            File config = new File(Main.plugin.getDataFolder(),"config.yml");
            if (config.exists()) {
                config.delete();
            }
            Bukkit.getPluginManager().disablePlugin(Main.plugin);
        }
    }
    public void set(String old,String news) {
        load().set(old, news);
        save();
    }
    public void set(String old,boolean news) {
        load().set(old, news);
        save();
    }
    public YamlConfiguration load() {
        if (get == null) {
            get = YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(), FileName));
        }
        return get;
    }
}
