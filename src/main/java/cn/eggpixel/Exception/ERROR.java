/*
 * 仅发出信息
 * 不关闭插件
 */
package cn.eggpixel.Exception;

import cn.eggpixel.Main;

import java.util.logging.Level;

public class ERROR {
    public ERROR(Exception ExceptionInfo) {
        Main.plugin.getLogger().log(Level.SEVERE, "=================THIS IS A BUG OR FILE NOT EXIST!=============");
        Main.plugin.getLogger().log(Level.SEVERE, "错误信息:" + ExceptionInfo.getMessage());
        Main.plugin.getLogger().log(Level.SEVERE, ExceptionInfo.toString());
        Main.plugin.getLogger().log(Level.SEVERE, "反馈:https://www.eggpixel.cn/FeedBack.html");
        Main.plugin.getLogger().log(Level.SEVERE, "==============================================================");
    }
}
