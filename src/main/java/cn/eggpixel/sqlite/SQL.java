package cn.eggpixel.sqlite;

import cn.eggpixel.exception.ERROR;
import cn.eggpixel.exception.FATAL;
import cn.eggpixel.Main;
import cn.eggpixel.Message;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;

public class SQL {
    int try_count = 1;
    public Connection c;
    Statement stmt;
    File db = new File(Main.plugin.getDataFolder(), "emeraldtools.db");
    public void connect_to_sql() {
        File jar = new File(Main.plugin.getDataFolder(),"sqlite.jar");
        if (loadJar(jar)) {
            //加载成功
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + db.getCanonicalPath());
                stmt = c.createStatement();
                rs = this.stmt.executeQuery(sql);
                new Message("已成功加载SQL驱动!").info();
            } catch (Exception e) {
                new ERROR(e);
            }
        } else {
            //生成文件
            if (!jar.exists())
                Main.plugin.saveResource("sqlite.jar",false);
            if (try_count < 4) {
                new Message(MessageFormat.format("连接失败!正在尝试重新连接,已尝试 {0} 次" ,try_count)).warn();
                new SQL();
                try_count++;
            } else {
                new Message("已尝试多次仍未连接驱动,SQLite支持加载失败").error();
                new Message("即将关闭插件(SQLite是必要组件)").error();
                new FATAL(new Exception("SQL连接失败!"));
            }
        }
    }
    private boolean loadJar(File jarFile) {
        Method method;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
        boolean accessible = method.isAccessible();     // 获取方法的访问权限
        try {
            if (!accessible) {
                method.setAccessible(true);     // 设置方法的访问权限
            }
            URL url;
            try {
                url = jarFile.toURI().toURL();
            } catch (MalformedURLException e) {
                return false;
            }
            try {
                    method.invoke(getClass().getClassLoader(), url);
                } catch (Exception e) {
                    return false;
                }
        } finally {
            method.setAccessible(accessible);
        }
        return true;
    }
    /**
     * 初始化emeraldtools.db
     *
     * 使用前请加上
     *
     * if (!new File(Main.plugin.getDataFolder(),"emeraldtools.db").exists())
     *
     *      new SQL().init();
     *
     * 以确保表未存在
     *
     * 否则将抛出异常
     *
     * @author EmeraldsEgg
     * */
    public void init() {
        try {
            String sql = "CREATE TABLE BANNED " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " UUID           TEXT    NOT NULL, " +
                        " BanDate        TEXT     NOT NULL, " +
                        " BanEnd         TEXT, " +
                        " Reason         TEXT)";
            stmt.executeUpdate(sql);
        } catch (Exception ignored) {
        }
    }
    public static ResultSet rs;

    String sql="Select * from BANNED;";
    public void add(String UUID,String BanDate,String BanEnd,String Reason) {
        int id = 1;
        try {
            if (this.stmt != null) {
                while (rs.next()) {
                    id++;
                }
                sql = "INSERT INTO BANNED (ID,UUID,BanDate,BanSecond,Reason) " +
                        MessageFormat.format("VALUES ({0}, '{1}', '{2}', '{3}', '{4}' );", id, UUID, BanDate, BanEnd, Reason);
                this.stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            new ERROR(e);
        }
    }

    public void close() {
        try {
            this.stmt.close();
            this.c.close();
        } catch (Exception ignored) {

        }
    }
}
