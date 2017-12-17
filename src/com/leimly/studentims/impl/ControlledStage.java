package com.leimly.studentims.impl;

import com.leimly.studentims.controller.StageController;

/**
 * Created by lizm on 17-12-15.
 */
public interface ControlledStage {
    StageController stageController = null;

    public void setStageController(StageController stageController);

    public StageController getStageController();

    public void init();
}
