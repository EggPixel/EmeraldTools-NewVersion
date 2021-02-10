/*
 * 发出信息
 * 并关闭插件
 */
package cn.eggpixel.Exception;

import cn.eggpixel.Main;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.logging.Level;

public class FATAL {
    public FATAL(Exception ExceptionInfo) {
        Main.plugin.getLogger().log(Level.SEVERE, "=================THIS IS A BUG OR FILE NOT EXIST!=============");
        Main.plugin.getLogger().log(Level.SEVERE, "错误信息:" + ExceptionInfo.getMessage());
        Main.plugin.getLogger().log(Level.SEVERE, ExceptionInfo.toString());
        Main.plugin.getLogger().log(Level.SEVERE, "反馈:https://www.eggpixel.cn/FeedBack.html");
        Main.plugin.getLogger().log(Level.SEVERE, "插件即将关闭!");
        Main.plugin.getLogger().log(Level.SEVERE, "==============================================================");
        ClassLoader plugins = Main.plugin.getClass().getClassLoader();
        try {
            ((URLClassLoader)plugins).close();
        } catch (IOException ignored) {
        }
        System.gc();
        Bukkit.getPluginManager().disablePlugin(Main.plugin);
    }
}
