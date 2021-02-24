package cn.eggpixel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class Update{
    /**
     * https://www.eggpixel.cn/update/up.txt
     * */
    public Update(String URL,String Version) {
        try {
            URL url = new URL(URL);
            InputStream in =url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bur = new BufferedReader(isr);
            String str;
            str = bur.readLine();
            bur.close();
            isr.close();
            in.close();
            if (Objects.equals(Version, str)) {
                //为最新版本
                new Message("你现在运行的插件是最新版本!").info();
                new Message("版本:" + str).info();
                new Message("当前运行在" + Main.plugin.getServer().getBukkitVersion() + "版本").info();
            } else {
                //不是最新版本
                new Message("你现在运行的插件不是最新版本!").warn();
                new Message("最新版本:" + str).warn();
                new Message("下载链接: https://www.eggpixel.cn/").warn();
            }
            if (!Main.plugin.getServer().getBukkitVersion().startsWith("1.16")) {
                new Message("当前版本并不在支持版本内，发生任何错误不会获得任何支持").error();
            }
        } catch (Exception e) {
            new Message("当前无法连接到更新检测系统").error();
            new Message("请检查当前网络是否正常!").error();
            if (!Main.plugin.getServer().getBukkitVersion().startsWith("1.16")) {
                new Message("当前版本并不在支持版本内，发生任何错误不会获得任何支持").error();
            }
        }
    }
}
