package com.leimly.studentims.controller;

import com.leimly.studentims.db.UserDAO;
import com.leimly.studentims.entity.User;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

/**
 * Created by lizm on 17-12-6.
 */
public class LoginController implements ControlledStage {

    StageController stageController;

    @FXML
    public PasswordField userPasswordPF;
    public TextField userNameTF;

    User user = new User();

    @FXML
    private void setOnUserLogin(ActionEvent actionEvent) {
        user.setUserName(userNameTF.getText());
        user.setUserPassword(userPasswordPF.getText());
        searchUser(user);

    }

    public void searchUser(User user) {
        UserDAO userDAO = new JDBCUser();
        boolean result = userDAO.searchUser(user);
        if (result) {
            stageController.setStage("MainView", "LoginView");
        } else {
            JOptionPane.showMessageDialog(null, "用户名或密码错误,请重新输入", "Success", JOptionPane.NO_OPTION);
        }
    }

    @FXML
    public void setOnUserExit(ActionEvent actionEvent) {
        stageController.closeStage("LoginView");
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
