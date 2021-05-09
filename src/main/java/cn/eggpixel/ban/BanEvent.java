package cn.eggpixel.ban;

import cn.eggpixel.EggPixel;
import cn.eggpixel.exception.ERROR;
import cn.eggpixel.sqlite.SQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BanEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e){
        try {
            String banEnd = null;
            String banStart = null;
            String reason = null;
            int id = 0;
            ResultSet rs = SQL.rs;
            String uuid = e.getPlayer().getUniqueId().toString();
            while (rs.next()) {
                if (rs.getString("UUID").equals(uuid)) {
                    banStart = rs.getString("BanDate");
                    id = rs.getInt("ID");
                    banEnd = rs.getString("BanEnd");
                    reason = rs.getString("Reason");
                }
            }
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rs.close();
            if (banEnd == null || reason == null || banStart == null) return;
            Date datEnd = formatter.parse(banEnd);
            long end = datEnd.getTime();
            long now = System.currentTimeMillis();
            if (now > end) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED,new EggPixel("config.yml").getString("BAN_FORMAT").replace("%DATETIME%",banStart).replace("%BANENDS%",banEnd).replace("%REASON%",reason).replace("%BANID%", String.valueOf(id)));
            }
        } catch (Exception ea) {
            new ERROR(ea);
        }

    }
}
