package com.caiyanjia.notes.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppModel {
    private StringProperty text = new SimpleStringProperty();

    private StringProperty title = new SimpleStringProperty();
    private StringProperty like = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty author = new SimpleStringProperty();


    public AppModel()

    {
        this.text = new SimpleStringProperty();
        this.title = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.like = new SimpleStringProperty();

    }

    public StringProperty textProperty() {
        return text;
    }

    public StringProperty titleProperty(){
        return title;
    }

    public StringProperty authorProperty(){
        return author;
    }
    public StringProperty likeProperty(){
        return like;
    }
    public StringProperty dateProperty(){
        return date;
    }

    public final String getText() {
        return textProperty().get();

    }

    public final void setText(String text) {
        textProperty().set(text);

    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getLike() {
        return like.get();
    }

    public void setLike(String like) {
        this.like.set(like);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }
}