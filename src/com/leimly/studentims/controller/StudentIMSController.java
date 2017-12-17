package com.leimly.studentims.controller;

import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by lizm on 17-12-6.
 */
public class StudentIMSController implements ControlledStage {
    StageController stageController;

    @FXML
    public void setOnStudentGradeEntry(ActionEvent actionEvent) {
        stageController.setStage(UIMessage.studentGradeEntryViewID);
    }

    @FXML
    public void setOnShowAboutIMS(ActionEvent actionEvent) {

    }

    @FXML
    public void setOnUserManager(ActionEvent actionEvent) {
        stageController.setStage(UIMessage.userMngViewID);
    }

    @FXML
    public void setOnStudentManager(ActionEvent actionEvent) {
        stageController.setStage(UIMessage.studentMngViewID);
    }

    @FXML
    public void setOnMenuExit(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.mainViewID);
    }

    public void setOnStudentGradeOperator(ActionEvent actionEvent) {
        stageController.setStage(UIMessage.studentGradeOperationViewID);
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
