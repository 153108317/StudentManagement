package com.example.my.studenmanagement.tools;

/**
 * 保存学生信息的实体类
 *
 */
public class Student {
    private String name;
    private String sex;
    private String id;//学号
    private String password;//学生登录密码
    private String number;//手机号
    private int classScore;
    private int workScore;
    private int dayScore;
    private int order;//名次
    private int head;//头像类型
    private int dutyCount;//出勤次数
    private int unDutyCount;//出勤次数

    public Student(String name, String sex, String id, String password, String number, int classScore, int workScore, int dayScore, int order, int head, int dutyCount, int unDutyCount) {
        this.name = name;
        this.sex = sex;
        this.id = id;
        this.password = password;
        this.number = number;
        this.classScore = classScore;
        this.workScore = workScore;
        this.dayScore = dayScore;
        this.order = order;
        this.head = head;
        this.dutyCount = dutyCount;
        this.unDutyCount = unDutyCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClassScore(int classScore) {
        this.classScore = classScore;
    }

    public void setWorkScore(int workScore) {
        this.workScore = workScore;
    }

    public void setDayScore(int dayScore) {
        this.dayScore = dayScore;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public void setDutyCount(int dutyCount) {
        this.dutyCount = dutyCount;
    }

    public void setUnDutyCount(int unDutyCount) {
        this.unDutyCount = unDutyCount;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }

    public int getClassScore() {
        return classScore;
    }

    public int getWorkScore() {
        return workScore;
    }

    public int getDayScore() {
        return dayScore;
    }

    public int getOrder() {
        return order;
    }

    public int getHead() {
        return head;
    }

    public int getDutyCount() {
        return dutyCount;
    }

    public int getUnDutyCount() {
        return unDutyCount;
    }
}
