package com.leimly.studentims.controller;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.ScoreDAO;
import com.leimly.studentims.entity.Score;
import com.leimly.studentims.id.StageFlag;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCScore;
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

import javax.swing.*;

/**
 * Created by lizm on 17-12-6.
 */
public class StudentGradeOperationController implements ControlledStage {

    public TextField sclassTF;
    public TextField typeTF;
    public TextField timeTF;
    public TextField subjectTF;
    public TableView showGradeTV;
    StageController stageController;
    public static Score scoreItem = null;

    public void setOnModify(ActionEvent actionEvent) {
        scoreItem = getSelectItem();
        if (scoreItem != null) {
            stageController.setStage(UIMessage.gradeEnterViewID, scoreItem, StageFlag.STUDENTGRADE_MOD);
            stageController.getController(UIMessage.gradeEnterViewID).init();
        }
        getSearch();
    }

    public void setOnExit(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.studentGradeOperationViewID);
    }

    public void setOnDelete(ActionEvent actionEvent) {
        scoreItem = getSelectItem();
        deleteScore(scoreItem);
        getSearch();
    }

    public void setOnSearch(ActionEvent actionEvent) {
        getSearch();

    }

    private void getSearch() {
        ObservableList<Score> scoreData = FXCollections.observableArrayList();
        scoreData = searchScore();
        updateTableView(scoreData);
    }


    private Score getSelectItem() {

        showGradeTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        showGradeTV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                scoreItem = (Score) showGradeTV.getSelectionModel().getSelectedItem();
            }
        });
        return scoreItem;
    }

    private void deleteScore(Score score) {
        ScoreDAO scoreDAO = new JDBCScore();
        Boolean flag = scoreDAO.deleteScore(score);
        if (flag) {
            JOptionPane.showMessageDialog(null, "删除成功。", "Success", JOptionPane.OK_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "删除失败。", "Success", JOptionPane.OK_OPTION);

        }
    }

    private Score setScore() {
        Score tmp = new Score();
        tmp.setSclass(sclassTF.getText());
        tmp.setTime(timeTF.getText());
        tmp.setType(typeTF.getText());
        tmp.setSubject(subjectTF.getText());
        return tmp;
    }

    private ObservableList<Score> searchScore() {
        ScoreDAO scoreDAO = new JDBCScore();
        Score tmp = setScore();
        return scoreDAO.searchScore(tmp);
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
