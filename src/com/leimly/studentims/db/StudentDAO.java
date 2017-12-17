package com.leimly.studentims.db;

import com.leimly.studentims.entity.Student;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by lizm on 17-12-17.
 */
public interface StudentDAO {
    public Boolean addStudent(Student student);

    public ArrayList<Student> searchStudentByClass(String sclass);

    public Boolean deleteStudent(Student student);

    public ObservableList<Student> searchStudentByMsg(Student student);

    public Boolean searchStudentByCode(Student student);

    public Boolean modifyStudent(Student student, Student lastStageValue);
}
