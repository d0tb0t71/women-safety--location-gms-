package com.example.womensafety.models;

public class RequestModel {

    String requestID;
    String lat;
    String lon;
    String userID;
    String dateTime;


    public RequestModel() {
    }

    public RequestModel(String requestID, String lat, String lon, String userID, String dateTime) {
        this.requestID = requestID;
        this.lat = lat;
        this.lon = lon;
        this.userID = userID;
        this.dateTime = dateTime;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
