package com.example.truyenhay.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String id, genreid,name ;
    private String description;
    private String author;

    public String getGenreid() {
        return genreid;
    }

    public Book(String id, String genreid, String name, String description, String author) {
        this.id = id;
        this.genreid = genreid;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public void setGenreid(String genreid) {
        this.genreid = genreid;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
