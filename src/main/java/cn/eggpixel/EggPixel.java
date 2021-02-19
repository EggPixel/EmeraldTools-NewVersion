package cn.eggpixel;

import cn.eggpixel.Exception.FATAL;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class EggPixel{
    /**
     * 获取yml文件中的内容
     * */
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
            new FATAL(e);
            if (new File(Main.plugin.getDataFolder(),"antibuild.yml").delete() || new File(Main.plugin.getDataFolder(),"config.yml").delete()) {
                new Message("已删除所有配置文件!").debug();
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
            new FATAL(e);
            if (Main.plugin.getDataFolder().delete()) {
                new Message("已删除所有配置文件!").debug();
            }
        }
    }
    public void set(String Key,String news) {
        load().set(Key, news);
        save();
    }
    public void set(String Key,boolean news) {
        load().set(Key, news);
        save();
    }
    public YamlConfiguration load() {
        if (get == null) {
            get = YamlConfiguration.loadConfiguration(new File(Main.plugin.getDataFolder(), FileName));
        }
        return get;
    }
}