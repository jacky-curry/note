package com.caiyanjia.notes.bean;

public class Group {
    //四个个属性
    private String name;
    private String user_id;


    public Group(String name, String user_id) {
        this.name = name;
        this.user_id = user_id;
    }


    public Group() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}