package com.egen.Entity;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@Entity("alerts")
public class Alert {
    private long timestamp;
    private String alert;
    private int value;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}