package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.bean.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class userDate {
    private final SimpleStringProperty id = new SimpleStringProperty();

    private final SimpleStringProperty name = new SimpleStringProperty();
//    private final SimpleBooleanProperty black = new SimpleBooleanProperty();


//    public boolean isBlack() {
//        return black.get();
//    }
//
//    public SimpleBooleanProperty blackProperty() {
//        return black;
//    }
//
//    public void setBlack(boolean black) {
//        this.black.set(black);
//    }

    public userDate() {
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * 用于返回将note转换为noteDate
     * @param n
     * @return
     */
    public userDate userChange(User n) {
        userDate userdate = new userDate();
        userdate.setId(n.getId());
        userdate.setName(n.getName());
//        userdate.setBlack(n.getBlacklist());

        return userdate;
    }

}


