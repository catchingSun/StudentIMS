package com.leimly.studentims.entity;


import java.sql.Date;

/**
 * Created by lizm on 17-12-12.
 */
public class Student {
    private String code;
    private String name;
    private String sex;
    private Date birth;
    private String address;
    private String phone;
    private String department;
    private String major;
    private String sclass;

    public Student() {
    }

    public Student(String code, String name, String sex, Date birth, String address, String phone, String department, String major, String sclass) {
        this.code = code;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.department = department;
        this.major = major;
        this.sclass = sclass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }


}
