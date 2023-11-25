package com.example.truyenhay.model;

import java.io.Serializable;

public class Chap implements Serializable {
    String id, name, number;

    public String getId() {
        return id;
    }

    public Chap(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Chap(String name) {
        this.name = name;
    }

    public Chap() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
