package com.example.womensafety.models;

public class NumberModel {

    String numberID;
    String name;
    String number;


    public NumberModel() {
    }

    public NumberModel(String numberID, String name, String number) {
        this.numberID = numberID;
        this.name = name;
        this.number = number;
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}