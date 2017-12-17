package com.leimly.studentims.jdbc;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.ScoreDAO;
import com.leimly.studentims.db.StudentDAO;
import com.leimly.studentims.entity.Score;
import com.leimly.studentims.entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lizm on 17-12-17.
 */
public class JDBCStudent implements StudentDAO {
    @Override
    public Boolean addStudent(Student student) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "insert into student values(?,?,?,?,?,?,?,?,?);";
        Boolean flag = false;
        PreparedStatement sta = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, student.getCode());
            sta.setString(2, student.getName());
            sta.setString(3, student.getSex());
            sta.setDate(4, java.sql.Date.valueOf(student.getBirth().toString()));
            sta.setString(5, student.getAddress());
            sta.setString(6, student.getPhone());
            sta.setString(7, student.getDepartment());
            sta.setString(8, student.getMajor());
            sta.setString(9, student.getSclass());

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
    public ArrayList<Student> searchStudentByClass(String sclass) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from student where class=?";
        ArrayList<Student> arrayList = new ArrayList<>();
        PreparedStatement sta = null;
        ResultSet rs = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, sclass);
            rs = sta.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setCode(rs.getString(1));
                student.setName(rs.getString(2));
                student.setSex(rs.getString(3));
                student.setBirth(rs.getDate(4));
                student.setAddress(rs.getString(5));
                student.setPhone(rs.getString(6));
                student.setDepartment(rs.getString(7));
                student.setMajor(rs.getString(8));
                student.setSclass(rs.getString(9));
                arrayList.add(student);
            }
            rs.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return arrayList;
        }
    }

    private void getScore(Score score) {
        ScoreDAO scoreDAO = new JDBCScore();
        scoreDAO.searchScoreByCS(score);
    }

    @Override
    public Boolean deleteStudent(Student student) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql2 = "delete from student where code=?";
        String sql = "delete from score where code=?";
        PreparedStatement sta = null;
        Boolean flag = false;
        try {

            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, student.getCode());
            int rows = sta.executeUpdate();
            if (rows > 0) {
                sta = conn.connection.prepareStatement(sql2);
                sta.setString(1, student.getCode());
                rows = sta.executeUpdate();
                if (rows > 0) {
                    flag = true;
                } else {
                    flag = false;
                }
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
    public ObservableList<Student> searchStudentByMsg(Student student) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        ObservableList<Student> studentData = FXCollections.observableArrayList();
        String sql = "select * from student where department=? and major=? and class=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, student.getDepartment());
            sta.setString(2, student.getMajor());
            sta.setString(3, student.getSclass());
            rs = sta.executeQuery();
            while (rs.next()) {
                Student stu = new Student();
                stu.setCode(rs.getString(1));
                stu.setName(rs.getString(2));
                stu.setSex(rs.getString(3));
                stu.setBirth(rs.getDate(4));
                stu.setAddress(rs.getString(5));
                stu.setPhone(rs.getString(6));
                stu.setDepartment(rs.getString(7));
                stu.setMajor(rs.getString(8));
                stu.setSclass(rs.getString(9));

                studentData.add(stu);
            }

            rs.close();
            sta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            return studentData;
        }
    }

    @Override
    public Boolean searchStudentByCode(Student student) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "select * from student where code=?";
        PreparedStatement sta = null;
        ResultSet rs = null;
        Boolean result = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, student.getCode());
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

    @Override
    public Boolean modifyStudent(Student student, Student lastStageValue) {
        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        String sql = "UPDATE student set name=?, sex=?, birth=?, address=?, phone=?, department=?, major=?, class=? WHERE code=?;";
        PreparedStatement sta = null;
        Boolean flag = false;
        try {
            sta = conn.connection.prepareStatement(sql);
            sta.setString(1, student.getName());
            sta.setString(2, student.getSex());
            sta.setDate(3, java.sql.Date.valueOf(student.getBirth().toString()));
            sta.setString(4, student.getAddress());
            sta.setString(5, student.getPhone());
            sta.setString(6, student.getDepartment());
            sta.setString(7, student.getMajor());
            sta.setString(8, student.getSclass());
            sta.setString(9, lastStageValue.getCode());
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

}
