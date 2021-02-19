package cn.eggpixel;

import java.util.logging.Level;

public class Message {
    /**
     * 制造信息
     * */
    String message;
    public Message(String message) {
        this.message = message;
    }
    public void info() {
        Main.plugin.getLogger().log(Level.INFO, message);
    }
    public void warn() {
        Main.plugin.getLogger().log(Level.WARNING, message);
    }
    public void error() {
        Main.plugin.getLogger().log(Level.SEVERE, message);
    }
    public void debug() {
        Main.plugin.getLogger().log(Level.CONFIG, message);
    }
}
