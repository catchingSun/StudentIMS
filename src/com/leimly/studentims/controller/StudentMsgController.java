package com.leimly.studentims.controller;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.StudentDAO;
import com.leimly.studentims.entity.Student;
import com.leimly.studentims.id.StageFlag;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCStudent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


/**
 * Created by lizm on 17-12-6.
 */
public class StudentMsgController implements ControlledStage {
    public TextField studentNumTF;
    public TextField sexTF;
    public TextField departTF;
    public TextField addressTF;
    public TextField nameTF;
    public TextField professionTF;
    public TextField telNumTF;
    public TextField classTF;
    public DatePicker birthDP;

    StageController stageController;

    static Student lastStageValue = null;
    int stageFlag = -1;

    @Override
    public StageController getStageController() {
        return stageController;
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void init() {

        lastStageValue = (Student) stageController.getStageValue();
        this.stageFlag = stageController.getStageFlag();

        if (stageFlag != StageFlag.STUDENTMNG_ADD && stageFlag > 0) {
            try {
                setViewMessage(lastStageValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void setOnOK(ActionEvent actionEvent) throws ParseException {
        Student student;
        switch (this.stageFlag) {
            case StageFlag.STUDENTMNG_ADD:
                student = new Student();
                Boolean bool = setStudent(student);
                if (bool) {
                    addStudent(student);
                    stageController.closeStage(UIMessage.studentMsgViewID);
                }
                break;
            case StageFlag.STUDENTMNG_MOD:
                studentNumTF.setEditable(false);
                student = new Student();
                setStudent(student);
                modifyStudentMessage(student, lastStageValue);
                break;
            default:
                stageController.closeStage(UIMessage.studentMsgViewID);
                break;


        }
    }

    private void modifyStudentMessage(Student student, Student lastStageValue) {
        StudentDAO studentDAO = new JDBCStudent();
        Boolean flag = studentDAO.modifyStudent(student, lastStageValue);
        if (flag) {
            JOptionPane.showMessageDialog(null, "更新学生信息成功", "Success", JOptionPane.OK_OPTION);
            stageController.closeStage(UIMessage.studentMsgViewID);
        } else {
            JOptionPane.showMessageDialog(null, "更新学生信息失败，请重新填写学生信息", "Success", JOptionPane.NO_OPTION);
        }
    }


    public boolean setStudent(Student student) {
        student.setCode(studentNumTF.getText());
        student.setSex(sexTF.getText());
        student.setDepartment(departTF.getText());
        student.setAddress(addressTF.getText());
        student.setName(nameTF.getText());
        student.setBirth(java.sql.Date.valueOf(birthDP.getValue()));
        student.setMajor(professionTF.getText());
        student.setSclass(classTF.getText());
        if (telNumTF.getText().length() == 11) {
            student.setPhone(telNumTF.getText());
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "请输入合法手机号码。", "Success", JOptionPane.NO_OPTION);
            return false;
        }

    }

    public LocalDate transforStrIntoDate(String str) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (str.length() > 0) {
            LocalDate date = LocalDate.parse(str);
            return date;
        }
        return null;
    }

    public void addStudent(Student student) {
        boolean result = searchStudent(student);
        if (result) {
            JOptionPane.showMessageDialog(null, "学生信息已存在。", "Success", JOptionPane.NO_OPTION);
        } else {
            StudentDAO studentDAO = new JDBCStudent();
            Boolean flag = studentDAO.addStudent(student);
            if (flag) {
                JOptionPane.showMessageDialog(null, "添加学生信息成功", "Success", JOptionPane.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "添加学生信息失败，请重新添加", "Success", JOptionPane.NO_OPTION);
            }
        }
    }

    public Boolean searchStudent(Student student) {
        StudentDAO studentDAO = new JDBCStudent();
        return studentDAO.searchStudentByCode(student);
    }

    public void setOnCancel(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.studentMsgViewID);
    }

    public void setViewMessage(Student student) throws ParseException {
        studentNumTF.setText(student.getCode());
        sexTF.setText(student.getSex());
        departTF.setText(student.getDepartment());
        addressTF.setText(student.getAddress());
        nameTF.setText(student.getName());
        professionTF.setText(student.getMajor());
        telNumTF.setText(student.getPhone());
        classTF.setText(student.getSclass());
        birthDP.setValue(transforStrIntoDate(student.getBirth().toString()));
    }


}
