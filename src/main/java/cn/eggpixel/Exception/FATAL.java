package cn.eggpixel.Exception;

import cn.eggpixel.Main;
import cn.eggpixel.Message;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URLClassLoader;

public class FATAL {
    /**
     * 制造致命信息
     * */
    public FATAL(Exception ExceptionInfo) {
        new Message("=================THIS IS A BUG OR FILE NOT EXIST!=============").error();
        new Message("错误信息:" + ExceptionInfo.getMessage()).error();
        new Message(ExceptionInfo.toString()).error();
        new Message("反馈:https://www.eggpixel.cn/FeedBack.html").error();
        new Message("插件即将关闭!").error();
        new Message("==============================================================").error();
        ClassLoader plugins = Main.plugin.getClass().getClassLoader();
        try {
            ((URLClassLoader)plugins).close();
        } catch (IOException ignored) {
        }
        System.gc();
        Bukkit.getPluginManager().disablePlugin(Main.plugin);
    }
}
