package com.example.filetest;

public class NotesData {
    int id;
    String title,des;
    String uri;

    public NotesData(String title, String des, String uri) {
        this.title = title;
        this.des = des;
        this.uri = uri;
    }

    public NotesData(int id, String title, String des, String uri) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
