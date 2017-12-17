package com.leimly.studentims;

import com.leimly.studentims.controller.StageController;
import com.leimly.studentims.id.UIMessage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private StageController stageController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageController = new StageController();
        stageController.loadStage(UIMessage.loginViewID, UIMessage.loginViewRes, UIMessage.loginTitle, StageStyle.UNDECORATED);
        stageController.loadStage(UIMessage.mainViewID, UIMessage.mainViewRes, UIMessage.mainTitle);
        stageController.loadStage(UIMessage.gradeEnterViewID, UIMessage.gradeEnterViewRes, UIMessage.gradeEnterTitle);
        stageController.loadStage(UIMessage.studentGradeOperationViewID, UIMessage.studentGradeOperationViewRes, UIMessage.studentGradeOperationTitle);
        stageController.loadStage(UIMessage.studentGradeEntryViewID, UIMessage.studentGradeEntryViewRes, UIMessage.studentGradeEntryTitle);
        stageController.loadStage(UIMessage.studentMngViewID, UIMessage.studentMngViewRes, UIMessage.studentMngTitle);
        stageController.loadStage(UIMessage.studentMsgViewID, UIMessage.studentMsgViewRes, UIMessage.studentMsgTitle);
        stageController.loadStage(UIMessage.userMngViewID, UIMessage.userMngViewRes, UIMessage.userMngTitle);
        stageController.setStage(UIMessage.loginViewID);
    }


    public static void main(String[] args) {
//        ConnectMysql conn = new ConnectMysql("./src/com/leimly/studentims/config/conn.ini");
        launch(args);
    }
}
