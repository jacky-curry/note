package com.caiyanjia.notes.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Msg {

    private static String user_id;

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        Msg.user_id = user_id;
    }

    public Msg() {
    }


}
