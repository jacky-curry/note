package com.caiyanjia.notes.entity;

public class Administrator {
   private String id;
   private String password;
   private String get_user;
    private String notice;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGet_user() {
        return get_user;
    }

    public void setGet_user(String get_user) {
        this.get_user = get_user;
    }
}
