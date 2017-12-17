package com.leimly.studentims.controller;

import com.leimly.studentims.db.ScoreDAO;
import com.leimly.studentims.entity.Score;
import com.leimly.studentims.id.StageFlag;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCScore;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.swing.*;

public class GradeEnterController implements ControlledStage {
    public CheckBox absentTF;
    public TextField makeUpScoreTF;
    public TextField gradeTF;
    StageController stageController;
    Score lastStageValue = null;
    static int stageFlag;

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
        lastStageValue = (Score) stageController.getStageValue();
        this.stageFlag = stageController.getStageFlag();
        if (lastStageValue != null) {
            gradeTF.setText(lastStageValue.getScore());
            makeUpScoreTF.setText(lastStageValue.getMakeup_score());
            if (lastStageValue.getAbsent() == "是") {
                absentTF.setSelected(true);
            } else {
                absentTF.setSelected(false);
            }
        }

    }

    public void setOnOk(ActionEvent actionEvent) {

        switch (stageFlag) {
            case StageFlag.STUDENTGRADE_INPUT:
                lastStageValue.setScore(gradeTF.getText());
                lastStageValue.setMakeup_score(makeUpScoreTF.getText());
                if (absentTF.isSelected()) {
                    lastStageValue.setAbsent("是");
                } else {
                    lastStageValue.setAbsent("否");
                }
                updateScore(lastStageValue);
                stageController.closeStage(UIMessage.gradeEnterViewID);
                break;
            case StageFlag.STUDENTGRADE_MOD:

                lastStageValue.setScore(gradeTF.getText());
                lastStageValue.setMakeup_score(makeUpScoreTF.getText());
                if (absentTF.isSelected()) {
                    lastStageValue.setAbsent("是");
                } else {
                    lastStageValue.setAbsent("否");
                }
                updateScore(lastStageValue);
                stageController.closeStage(UIMessage.gradeEnterViewID);
                break;
            default:
                break;
        }
    }

    public void setOnCancel(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.gradeEnterViewID);
    }

    private void updateScore(Score score) {
        ScoreDAO scoreDAO = new JDBCScore();
        Boolean flag = scoreDAO.updateScore(score);
        if (flag) {
            JOptionPane.showMessageDialog(null, "更新学生信息成功", "Success", JOptionPane.OK_OPTION);

        } else {
            JOptionPane.showMessageDialog(null, "更新学生信息失败，请重新填写学生信息", "Success", JOptionPane.NO_OPTION);

        }
    }
}
