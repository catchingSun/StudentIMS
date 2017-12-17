package com.leimly.studentims.controller;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.ScoreDAO;
import com.leimly.studentims.db.StudentDAO;
import com.leimly.studentims.entity.Score;
import com.leimly.studentims.entity.Student;
import com.leimly.studentims.id.StageFlag;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCScore;
import com.leimly.studentims.jdbc.JDBCStudent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lizm on 17-12-6.
 */
public class StudentGradeEntryController implements ControlledStage {

    public TextField sclassTF;
    public TextField typeTF;
    public TextField timeTF;
    public TextField subjectTF;
    public TableView showGradeTV;
    StageController stageController = null;
    public static Score scoreItem = null;

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

    public void setOnExit(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.studentGradeEntryViewID);
    }

    public void setOnEnterGrade(ActionEvent actionEvent) throws SQLException {
        ObservableList<Score> scoreData = searchStudent();
        updateTableView(scoreData);
        scoreItem = getSelectItem();

        if (scoreItem != null) {
            System.out.println("66666");

        }
    }

    private Score getSelectItem() {

        showGradeTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        showGradeTV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                scoreItem = (Score) showGradeTV.getSelectionModel().getSelectedItem();
                if (scoreItem != null) {
                    stageController.setStage(UIMessage.gradeEnterViewID, scoreItem, StageFlag.STUDENTGRADE_INPUT);
                    stageController.getController(UIMessage.gradeEnterViewID).init();
                }
            }
        });
        return scoreItem;
    }


    public void addStudentMsg(Score score) {

        boolean result = searchScore(score);
        if (result) {

        } else {
            ScoreDAO scoreDAO = new JDBCScore();
            scoreDAO.addScoreStudent(score);
        }
    }

    private boolean searchScore(Score score) {
        ScoreDAO scoreDAO = new JDBCScore();
        return scoreDAO.searchScoreByCCS(score);
    }

    private Score setScore(Student student) {
        Score score = new Score();
        score.setSclass(sclassTF.getText());
        score.setType(typeTF.getText());
        score.setTime(timeTF.getText());
        score.setSubject(subjectTF.getText());
        score.setCode(student.getCode());
        score.setName(student.getName());
        addStudentMsg(score);
        return score;
    }

    private ObservableList<Score> searchStudent() {
        ObservableList<Score> scoreData = FXCollections.observableArrayList();
        ArrayList<Student> arrayList = null;
        StudentDAO studentDAO = new JDBCStudent();
        arrayList = studentDAO.searchStudentByClass(sclassTF.getText());
        for (Student i : arrayList) {
            Score score = setScore(i);
            getScore(score);
            scoreData.add(score);
        }
        return scoreData;
    }

    private void getScore(Score score) {
        ScoreDAO scoreDAO = new JDBCScore();
        scoreDAO.searchScoreByCS(score);
    }

    private void updateTableView(ObservableList<Score> scoreData) {
        ObservableList<TableColumn> observableList = showGradeTV.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("code"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("name"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("subject"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("score"));
        observableList.get(5).setCellValueFactory(new PropertyValueFactory("makeup_score"));
        observableList.get(6).setCellValueFactory(new PropertyValueFactory("absent"));

        showGradeTV.setItems(scoreData);
    }

}
