package com.todoservice.todoservice.datamodel;


public class Todo {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getDttm() {
        return dttm;
    }

    public void setDttm(long dttm) {
        this.dttm = dttm;
    }

    String title;
    String description;
    int status;
    int id;
    long dttm;
}
