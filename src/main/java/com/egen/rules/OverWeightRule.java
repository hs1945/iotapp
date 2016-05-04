package com.egen.rules;

import com.egen.Application;
import com.egen.Entity.Alert;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@Rule(name = "overWeight",
        description = "Underweight or overweight")
public class OverWeightRule {
    private int value;
    private int base_value;

    public OverWeightRule(int value, int base_value){
        this.value = value;
        this.base_value = base_value;
    }

    @Condition
    public boolean evaluate(){
        return value > 1.1*base_value;
    }

    @Action
    public void execute(){
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(Application.mongoClient, "sensorDB");
        Alert alert = new Alert();
        alert.setAlert("Overweight");
        alert.setTimestamp(System.currentTimeMillis());
        alert.setValue(value);
        datastore.save(alert);
    }
}
