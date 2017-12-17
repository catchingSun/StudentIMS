package com.leimly.studentims.controller;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.StudentDAO;
import com.leimly.studentims.entity.Student;
import com.leimly.studentims.id.StageFlag;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCStudent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by lizm on 17-12-6.
 */
public class StudentMngController implements ControlledStage {

    public TextField departTF;
    public TextField professionTF;
    public TextField classTF;
    public TableView showStudentMsgTV;
    Student getSItem;
    StageController stageController;

    public void setOnAddStudent(ActionEvent actionEvent) {
        stageController.setStage(UIMessage.studentMsgViewID, null, StageFlag.STUDENTMNG_ADD);
        stageController.getController(UIMessage.studentMsgViewID).init();
    }

    public void setOnExit(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.studentMngViewID);
    }

    public void setOnViewStudent(ActionEvent actionEvent) {
        Student getSItem = getSelectItem();
        setStudentMsg(getSItem, StageFlag.STUDENTMNG_LOOK);
    }

    public void setOnDelStudent(ActionEvent actionEvent) {
        getSItem = getSelectItem();
        deleteStudent(getSItem);
        getSearch();

    }

    public void setOnModifyStudent(ActionEvent actionEvent) {
        getSItem = getSelectItem();
        setStudentMsg(getSItem, StageFlag.STUDENTMNG_MOD);

    }

    public void setOnSearch(ActionEvent actionEvent) throws SQLException {
        getSearch();
    }

    private void getSearch() {
        Student student = new Student();
        student.setDepartment(departTF.getText());
        student.setMajor(professionTF.getText());
        student.setSclass(classTF.getText());
        ObservableList<Student> studentData = searchStudent(student);
        updateTableView(studentData);
    }

    private void setStudentMsg(Student getSItem, int stageFlag) {
        if (getSItem != null) {
            stageController.setStage(UIMessage.studentMsgViewID, getSItem, stageFlag);
            stageController.getController(UIMessage.studentMsgViewID).init();
        }
    }

    private Student getSelectItem() {
        showStudentMsgTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        showStudentMsgTV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                getSItem = (Student) showStudentMsgTV.getSelectionModel().getSelectedItem();
            }
        });
        return getSItem;
    }


    private void deleteStudent( Student student) {
        StudentDAO studentDAO = new JDBCStudent();
        Boolean flag = studentDAO.deleteStudent(student);
        if (flag) {
            JOptionPane.showMessageDialog(null, "删除成功。", "Success", JOptionPane.OK_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "删除失败。", "Success", JOptionPane.OK_OPTION);
        }
    }

    private ObservableList<Student> searchStudent(Student student) {
        StudentDAO studentDAO = new JDBCStudent();
        return studentDAO.searchStudentByMsg(student);
    }

    private void updateTableView(ObservableList<Student> studentData) {
        ObservableList<TableColumn> observableList = showStudentMsgTV.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("code"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("name"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("sex"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("birth"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("address"));
        observableList.get(5).setCellValueFactory(new PropertyValueFactory("phone"));
        observableList.get(6).setCellValueFactory(new PropertyValueFactory("department"));
        observableList.get(7).setCellValueFactory(new PropertyValueFactory("major"));
        observableList.get(8).setCellValueFactory(new PropertyValueFactory("sclass"));

        showStudentMsgTV.setItems(studentData);
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public StageController getStageController() {
        return stageController;
    }

    @Override
    public void init() {

    }
}
