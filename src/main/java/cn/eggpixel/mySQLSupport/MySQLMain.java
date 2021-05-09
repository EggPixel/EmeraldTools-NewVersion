package cn.eggpixel.mySQLSupport;

import cn.eggpixel.EggPixel;
import cn.eggpixel.exception.ERROR;
import cn.eggpixel.Message;

import java.sql.*;
import java.text.SimpleDateFormat;

public class MySQLMain {
    static Connection con = null;
    static String Prefix = "MYSQL.";
    public MySQLMain() {
        if (con == null) {
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    new ERROR(e);
                    return;
                }
                System.out.println("正在连接数据库");
                try {
                    con = DriverManager.getConnection(new EggPixel("config.yml").getString(Prefix + "IP")
                                    + new EggPixel("config.yml").getString(Prefix + "DATABASE"),
                            new EggPixel("config.yml").getString(Prefix + "USER"),
                            new EggPixel("config.yml").getString(Prefix + "PASSWORD")
                            );
                    if (con == null) {
                        new Message("连接失败!").error();
                        return;
                    }
                    new Message("成功连接到数据库!").info();
                } catch (Exception e) {
                    new ERROR(e);
                }
            } catch (Exception e) {
                new ERROR(e);
            }
        }
    }
    public void CreateTable() {
        try {
            con.createStatement().execute("CREATE TABLE IF NOT EXISTS GROUP(\n" +
                    "id int,\n" +
                    "Name text,\n" +
                    "date date,\n" +
                    "Group text,\n" +
                    "PRIMARY KEY (id)\n" +
                    ")ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            con.createStatement().execute("CREATE TABLE IF NOT EXISTS GROUPS(\n" +
                    "id int,\n" +
                    "Group text,\n" +
                    "PRIMARY KEY (id)\n" +
                    ")ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        } catch (Exception e) {
            new ERROR(e);
        }
    }
    public String getDate() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
        return sdf1.format(date);
    }
    public void insertData(String UUID,String name,String date,String GroupName) {
        try {
            int id = 1;
            ResultSet var2 = con.createStatement().executeQuery("select * from GROUP");
            while (var2.next()) {
                id++;
            }
            con.createStatement().execute("insert into GROUP values(" + id + ",\"" + UUID + "\",\"" + name + "\""
                    + ",'" + date + "',\"" + GroupName + "\"" +")");
        } catch (Exception e) {
            new ERROR(e);
        }
    }
    public void searchData(String key) {
        try {
            ResultSet a = con.createStatement().executeQuery("select * from GROUP");
            while (a.next()) {
                System.out.println("ID:" + a.getString("id") + ",用户名:" + a.getString("name") + ",时间:"
                        + a.getString("date") + ",加入小组:" + a.getString("Group"));
            }
        } catch (Exception e){
            new ERROR(e);
        }
    }
    public Connection getMysql() {
        return con;
    }
    public void reload() {
        con = null;
        new MySQLMain();
    }
}
