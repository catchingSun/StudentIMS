package com.leimly.studentims.jdbc;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.UserDAO;
import com.leimly.studentims.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lizm on 17-12-17.
 */
public class JDBCUser implements UserDAO {
    @Override
    public Boolean searchUser(User user) {
        boolean flag = false;
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from user where user=? and passwd=?";
        PreparedStatement sta = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, user.getUserName());
            sta.setString(2, user.getUserPassword());
            ResultSet rs = sta.executeQuery();
            if (rs.next()) {
                user.setAdmin(rs.getString(3));
                flag = true;

            } else {
                flag = false;

            }
            rs.close();
            sta.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return flag;
        }
    }

    @Override
    public Boolean deleteUser(User user) {
        Boolean flag = false;
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "delete from user where user=?;";
        PreparedStatement sta = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, user.getUserName());
            int rows = sta.executeUpdate();
            if (rows > 0) {
                flag = true;
            } else {
                flag = false;
            }

            sta.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return flag;
        }
    }

    @Override
    public Boolean addUser(User user) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "insert into user values(?,?,?);";
        PreparedStatement sta = null;
        Boolean flag = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, user.getUserName());
            sta.setString(2, user.getUserPassword());
            sta.setString(3, user.getAdmin());
            int rows = sta.executeUpdate();
            if (rows > 0) {
                flag = true;
            } else {
                flag = false;
            }
            sta.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            conn.close();
            return flag;
        }
    }

    @Override
    public Boolean searchUserByUserName(User user) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from user where user=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, user.getUserName());
            rs = sta.executeQuery();


            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
            rs.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return result;
        }
    }
}
