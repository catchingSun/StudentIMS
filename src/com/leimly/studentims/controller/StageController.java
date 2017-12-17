package com.leimly.studentims.controller;

import com.leimly.studentims.impl.ControlledStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

/**
 * Created by lizm on 17-12-6.
 */
public class StageController {

    private static HashMap<String, Stage> stages = new HashMap<String, Stage>();
    private static HashMap<String, ControlledStage> controllers = new HashMap<String, ControlledStage>();
    public static Object stageValue = new Object();
    private static int stageFlag = -1;

    public int getStageFlag() {
        return stageFlag;
    }

    public void setStageFlag(int stageFlag) {
        StageController.stageFlag = stageFlag;
    }


    public Object getStageValue() {
        return stageValue;
    }

    public void setStageValue(Object stageValue) {
        this.stageValue = stageValue;
    }


    public ControlledStage getController(String name) {
        return controllers.get(name);
    }

    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    public void addController(String name, ControlledStage controller) {
        controllers.put(name, controller);
    }

    public Stage getStage(String name) {
        return stages.get(name);
    }


    public void setPrimaryStage(String primaryStageName, Stage primaryStage) {
        this.addStage(primaryStageName, primaryStage);
    }


    public boolean loadStage(String name, String resources, String title, StageStyle... styles) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resources));
            Pane tempPane = (Pane) loader.load();
            ControlledStage controlledStage;
            controlledStage = (ControlledStage) loader.getController();
            controlledStage.setStageController(this);

            Scene tempScene = new Scene(tempPane);
            Stage tempStage = new Stage();
            tempStage.setScene(tempScene);
            tempStage.setTitle(title);
            tempStage.setResizable(false);
            this.addStage(name, tempStage);
            this.addController(name, controlledStage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setStage(String name) {
        this.getStage(name).show();
        return true;
    }

    public boolean setStage(String name, Object obj, int stageFlag) {
        this.setStageValue(obj);
        this.setStageFlag(stageFlag);
        this.getStage(name).show();

        return true;
    }

    public boolean setStage(String name, Object obj) {
        this.setStageValue(obj);
        this.getStage(name).show();
        return true;
    }

    public boolean closeStage(String name) {
        this.getStage(name).close();
        return true;
    }

    public boolean setStage(String show, String close) {
        getStage(close).close();
        setStage(show);
        return true;
    }

    public boolean unloadStage(String name) {
        if (stages.remove(name) == null) {
            System.out.println("窗口不存在，请检查名称");
            return false;
        } else {
            System.out.println("窗口移除成功");
            return true;
        }
    }
}
