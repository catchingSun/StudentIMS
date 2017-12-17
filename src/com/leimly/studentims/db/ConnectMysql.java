package com.leimly.studentims.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectMysql {

    public static Connection connection = null;

    private String driver = null;
    private String url = null;
    private String user = null;
    private String pass = null;

    private void initParam(String paramFile) throws FileNotFoundException,
            IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(paramFile));
        driver = props.getProperty("driver");
        url = props.getProperty("url");
        user = props.getProperty("user");
        pass = props.getProperty("pass");
    }

    public ConnectMysql(String file_path) {

        try {
            initParam(file_path);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            if (connection != null) {
                System.out
                        .println("ConnectMysql ConnectMysql() : Connected successfully,Connection built.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultSet runSql(String sql) throws SQLException {
        Statement sta;
        if (connection != null) {
            sta = connection.createStatement();
            return sta.executeQuery(sql);
        } else {
            System.out.println("连接数据库错误。");
        }
        return null;

    }

    public void runInsertSql(String sql) throws SQLException {
        if (connection != null) {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
        } else {
            System.out.println("连接数据库错误。");
        }
    }

    public void getResultSet(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.print(rs.getString(3) + "\t");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (connection != null || !connection.isClosed()) {
            connection.close();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        try {
            //conn.runInsertSql("insert into user values ('000', '123', 'Y');");
            conn.getResultSet(conn.runSql("select * from user;"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}