package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.entity.Note;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public final class noteDate {


    private final SimpleStringProperty title = new SimpleStringProperty();

    private final SimpleStringProperty author = new SimpleStringProperty();
    private final SimpleStringProperty post_date = new SimpleStringProperty();
    private final SimpleStringProperty like = new SimpleStringProperty();
    private final SimpleStringProperty content = new SimpleStringProperty();
    private final SimpleBooleanProperty black = new SimpleBooleanProperty();

    public boolean isBlack() {
        return black.get();
    }

    public SimpleBooleanProperty blackProperty() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black.set(black);
    }

    public noteDate(String title, String author, String content, String post_date, Integer like) {
        setContent(content);
        setPost_date(post_date);
        setTitle(title);
        setAuthor(author);
        setLike(String.valueOf(like));
    }

    public noteDate(){

    }

    /**
     * 用于返回将note转换为noteDate
     * @param n
     * @return
     */
    public noteDate noteChange(Note n) {
        noteDate notedate = new noteDate();
        notedate.setTitle(n.getLabel());
        notedate.setAuthor(n.getAuthor());
        notedate.setLike(String.valueOf(n.getLike()));
        notedate.setContent(n.getContent());
        notedate.setPost_date(String.valueOf(n.getDate()));

        return notedate;
    }




    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getPost_date() {
        return post_date.get();
    }

    public SimpleStringProperty post_dateProperty() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date.set(post_date);
    }

    public String getLike() {
        return like.get();
    }

    public SimpleStringProperty likeProperty() {
        return like;
    }

    public void setLike(String like) {
        this.like.set(like);
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }





}
