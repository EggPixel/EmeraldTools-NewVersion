package cn.eggpixel.ban;

import cn.eggpixel.EggPixel;
import cn.eggpixel.Main;
import cn.eggpixel.sqlite.SQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BanCommand implements CommandExecutor {
    Main plugin;
    public BanCommand(Main Plugin) {
        this.plugin = Plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String a, String[] args) {
        int DNE = args.length;
        switch (DNE){
            case 0:
                sender.sendMessage(new EggPixel("config.yml").getString("BAN_USAGE"));
                return true;
            case 1:
                Player banPlayer;
                try {
                    banPlayer = Bukkit.getPlayerExact(args[0]);
                    assert banPlayer != null;
                    String PU = Objects.requireNonNull(banPlayer.getPlayer()).getUniqueId().toString();
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String now_time = formatter.format(date);

                    new SQL().add(PU,now_time,"p",new EggPixel("config.yml").getString("BAN_DEFAULT_REASON"));

                    banPlayer.kickPlayer(new EggPixel("config.yml").getString("BAN_DEFAULT_REASON"));
                    sender.sendMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",new EggPixel("config.yml").getString("BAN_DEFAULT_REASON")).replace("%BANENDS%","永远不"));
                    Bukkit.broadcastMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",new EggPixel("config.yml").getString("BAN_DEFAULT_REASON")).replace("%BANENDS%","永远不").replace("%ADMIN%", sender.getName()));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                    return true;
                }
            case 2:
                try {
                    banPlayer = Bukkit.getPlayerExact(args[0]);
                    assert banPlayer != null;
                    String PU = Objects.requireNonNull(banPlayer.getPlayer()).getUniqueId().toString();
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String now_time = formatter.format(date);
                    long totalSeconds = System.currentTimeMillis() / 1000;
                    totalSeconds = totalSeconds + Integer.parseInt(args[1]);
                    String end_time = formatter.format(new Date(totalSeconds * 1000));

                    new SQL().add(PU,now_time,end_time,new EggPixel("config.yml").getString("BAN_DEFAULT_REASON"));

                    banPlayer.kickPlayer(new EggPixel("config.yml").getString("BAN_DEFAULT_REASON"));
                    sender.sendMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",new EggPixel("config.yml").getString("BAN_DEFAULT_REASON")).replace("%BANENDS%",end_time));
                    Bukkit.broadcastMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",new EggPixel("config.yml").getString("BAN_DEFAULT_REASON")).replace("%BANENDS%",end_time).replace("%ADMIN%", sender.getName()));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                    return true;
                }
            case 3:
                try {
                    banPlayer = Bukkit.getPlayerExact(args[0]);
                    assert banPlayer != null;
                    String PU = Objects.requireNonNull(banPlayer.getPlayer()).getUniqueId().toString();
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    String now_time = formatter.format(date);
                    long totalSeconds = System.currentTimeMillis() / 1000;
                    totalSeconds = totalSeconds + Integer.parseInt(args[1]);
                    String end_time = formatter.format(new Date(totalSeconds * 1000));

                    new SQL().add(PU,now_time,end_time,args[2]);

                    banPlayer.kickPlayer(args[2]);
                    sender.sendMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",args[2]).replace("%BANENDS%",end_time));
                    Bukkit.broadcastMessage(new EggPixel("config.yml").getString("BAN_PLAYER").replace("%PLAYERNAME%",banPlayer.getName()).replace("%REASON%",args[2]).replace("%BANENDS%",end_time).replace("%ADMIN%", sender.getName()));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(new EggPixel("config.yml").getString("PLAYER_NOT_FOUND"));
                    return true;
                }
        }
        sender.sendMessage(new EggPixel("config.yml").getString("BAN_USAGE"));
        return true;
    }
}
