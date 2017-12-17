package com.leimly.studentims.jdbc;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.ScoreDAO;
import com.leimly.studentims.entity.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lizm on 17-12-17.
 */
public class JDBCScore implements ScoreDAO {

    @Override
    public void addScoreStudent(Score score) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "insert into score (class, time, code, name, subject, type) values(?,?,?,?,?,?);";

        PreparedStatement sta = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, score.getSclass());
            sta.setString(2, score.getTime());
            sta.setString(3, score.getCode());
            sta.setString(4, score.getName());
            sta.setString(5, score.getSubject());
            sta.setString(6, score.getType());

            int rows = sta.executeUpdate();
            if (rows > 0) {
                // JOptionPane.showMessageDialog(null, "添加用户成功", "Success", JOptionPane.OK_OPTION);
            } else {
                // JOptionPane.showMessageDialog(null, "添加用户失败，请重新输入", "Success", JOptionPane.NO_OPTION);
            }
            sta.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

    }

    @Override
    public Boolean updateScore(Score score) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "UPDATE score set score=?, makeup_score=?, absent=? WHERE code=? and subject=?;";
        PreparedStatement sta = null;
        Boolean flag = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, score.getScore());
            sta.setString(2, score.getMakeup_score());
            sta.setString(3, score.getAbsent());
            sta.setString(4, score.getCode());
            sta.setString(5, score.getSubject());
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
    public void searchScoreByCS(Score score) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from score where code=? and subject=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, score.getCode());
            sta.setString(2, score.getSubject());
            rs = sta.executeQuery();
            while (rs.next()) {
                score.setId(rs.getInt("id"));
                score.setScore(rs.getString("score"));
                score.setMakeup_score(rs.getString("makeup_score"));
                score.setAbsent(rs.getString("absent"));
            }
            rs.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Override
    public Boolean searchScoreByCCS(Score score) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from score where code=? and class=? and subject=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, score.getCode());
            sta.setString(2, score.getSclass());
            sta.setString(3, score.getSubject());

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
            return result;
        }
    }

    @Override
    public Boolean deleteScore(Score score) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "delete from score where id=?";
        PreparedStatement sta = null;
        Boolean flag = false;
        try {

            sta = conn.connection.prepareStatement(sql);
            sta.setInt(1, score.getId());
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
            return flag;
        }
    }

    @Override
    public ObservableList<Score> searchScore(Score tmp) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        ObservableList<Score> scoreData = FXCollections.observableArrayList();
        String sql = "select * from score where class=? and time=? and type=? and subject=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, tmp.getSclass());
            sta.setString(2, tmp.getTime());
            sta.setString(3, tmp.getType());
            sta.setString(4, tmp.getSubject());
            rs = sta.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setId(rs.getInt(1));
                score.setSclass(rs.getString(2));
                score.setTime(rs.getString(3));
                score.setCode(rs.getString(4));
                score.setName(rs.getString(5));
                score.setSubject(rs.getString(6));
                score.setType(rs.getString(7));
                score.setScore(rs.getString(8));
                score.setMakeup_score(rs.getString(9));
                score.setAbsent(rs.getString(10));
                scoreData.add(score);
            }
            rs.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return scoreData;
        }
    }
}
