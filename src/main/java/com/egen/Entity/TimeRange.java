package com.egen.Entity;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@Entity("timerange")
public class TimeRange{
    private long minTime;
    private long maxTime;

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = Long.parseLong(minTime);
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = Long.parseLong(maxTime);
    }



}
