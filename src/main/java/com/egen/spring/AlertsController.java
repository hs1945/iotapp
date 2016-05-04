package com.egen.spring;

import com.egen.Application;
import com.egen.Entity.Alert;
import com.egen.Entity.TimeRange;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@RestController
@RequestMapping("/alerts")
public class AlertsController {
        final Datastore datastore;

        public AlertsController(){
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(Application.mongoClient, "sensorDB");
        }

        @RequestMapping(value="/read", method = GET)
        List<Alert> read() {
            return datastore.find(Alert.class).asList();
        }

        @RequestMapping(value="/readByTimeRange", method = POST)
        List<Alert> readByTimeRange(@RequestBody TimeRange timeRange) {
            return datastore.createQuery(Alert.class).filter("timestamp >", timeRange.getMinTime())
                    .filter("timestamp <", timeRange.getMaxTime()).asList();
        }

}
