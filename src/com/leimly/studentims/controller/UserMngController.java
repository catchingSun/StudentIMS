package com.leimly.studentims.controller;

import com.leimly.studentims.db.ConnectMysql;
import com.leimly.studentims.db.UserDAO;
import com.leimly.studentims.entity.User;
import com.leimly.studentims.id.UIMessage;
import com.leimly.studentims.impl.ControlledStage;
import com.leimly.studentims.jdbc.JDBCUser;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

/**
 * Created by lizm on 17-12-6.
 */
public class UserMngController implements ControlledStage {

    StageController stageController;

    public TextField userNameTF;
    public PasswordField userPasswordPF;
    public PasswordField userPasswordAgainPF;
    public CheckBox isAdminCB;
    private String userName;
    private String userPass;
    private String userPassAgain;
    private String isAdmin;
    private static String operator = "";
    User user = new User();


    public void setOnDelUser(ActionEvent actionEvent) {
        operator = "del";
        userNameTF.setText("");
        userPasswordPF.setText("");
        userPasswordAgainPF.setText("");
        userPasswordPF.setEditable(false);
        userPasswordAgainPF.setEditable(false);
    }

    public void setOnAddUser(ActionEvent actionEvent) {
        operator = "add";
        userNameTF.setText("");
        userPasswordPF.setText("");
        userPasswordAgainPF.setText("");
        userPasswordPF.setEditable(true);
        userPasswordAgainPF.setEditable(true);
    }

    public void setOnExitUserMng(ActionEvent actionEvent) {
        stageController.closeStage(UIMessage.userMngViewID);

    }

    public void setOnSave(ActionEvent actionEvent) {

        userName = userNameTF.getText();
        user.setUserName(userName);
        userPass = userPasswordPF.getText();
        userPassAgain = userPasswordAgainPF.getText();
        if (isAdminCB.isSelected()) {
            isAdmin = "Y";
        } else {
            isAdmin = "N";
        }
        if (!userName.equals("") && !userPass.equals("") && !userPassAgain.equals("")) {
            if (userPass.equals(userPassAgain)) {
                user.setUserName(userName);
                user.setUserPassword(userPass);
                user.setAdmin(isAdmin);
            } else {
                JOptionPane.showMessageDialog(null, "密码不一致，请重新输入。", "Success", JOptionPane.NO_OPTION);
            }
        }
        switch (operator) {
            case "del":
                delUser(user);
                break;
            case "add":
                addUser(user);
                break;
        }
    }

    public void setOnCancel(ActionEvent actionEvent) {
        userNameTF.setText("");
        userPasswordPF.setText("");
        userPasswordAgainPF.setText("");
        isAdminCB.setSelected(false);
    }

    public void delUser(User user) {
        UserDAO userDAO = new JDBCUser();
        Boolean flag = userDAO.deleteUser(user);
        if (flag) {
            JOptionPane.showMessageDialog(null, "删除成功", "Success", JOptionPane.OK_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "用户不存在,请重新输入", "Success", JOptionPane.NO_OPTION);

        }
    }

    public void addUser(User user) {
        Boolean result = searchUser(user);
        if (result) {
            JOptionPane.showMessageDialog(null, "用户已存在。", "Success", JOptionPane.NO_OPTION);
        } else {
            UserDAO userDAO = new JDBCUser();
            Boolean flag = userDAO.addUser(user);
            if (flag) {
                JOptionPane.showMessageDialog(null, "添加用户成功", "Success", JOptionPane.OK_OPTION);

            } else {
                JOptionPane.showMessageDialog(null, "添加用户失败，请重新输入", "Success", JOptionPane.NO_OPTION);

            }
        }
    }

    public Boolean searchUser(User user) {
        UserDAO userDAO = new JDBCUser();
        return userDAO.searchUserByUserName(user);
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
