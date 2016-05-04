package com.egen.Entity;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@Entity("metrics")
public class Metric {
    private long timestamp;
    private int value;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
