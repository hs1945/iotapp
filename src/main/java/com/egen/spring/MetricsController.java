package com.egen.spring;

import com.egen.Application;
import com.egen.Entity.Metric;
import com.egen.Entity.TimeRange;
import com.egen.rules.OverWeightRule;
import com.egen.rules.UnderWeightRule;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.easyrules.api.RulesEngine;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@RestController
@RequestMapping("/")
public class MetricsController {
    final Datastore datastore;

    public MetricsController(){
        Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(Application.mongoClient, "sensorDB");
    }


    @RequestMapping(value="/", method = POST)
    String index(@RequestBody Metric metric ) {
        return create(metric);
    }
    @RequestMapping(value="/metrics/create", method = POST)
    String create(@RequestBody Metric metric ) {
        datastore.save(metric);
        JSONObject response = new JSONObject();
        response.append("HTTP STATUS", HttpStatus.ACCEPTED);
        response.append("Content", metric);

        int base_weight = datastore.createQuery(Metric.class).get().getValue();

        RulesEngine rulesEngine = aNewRulesEngine().build();

        rulesEngine.registerRule(new OverWeightRule(metric.getValue(),base_weight ));
        rulesEngine.registerRule(new UnderWeightRule(metric.getValue(),base_weight ));

        rulesEngine.fireRules();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        return gson.toJson(response);
    }

    @RequestMapping(value="/metrics/read", method = GET)
    List<Metric> read() {
        return datastore.find(Metric.class).asList();
    }

    @RequestMapping(value="/metrics/readByTimeRange", method = POST)
    List<Metric> readByTimeRange(@RequestBody TimeRange timeRange) {
        return datastore.createQuery(Metric.class).filter("timestamp >", timeRange.getMinTime())
                .filter("timestamp <", timeRange.getMaxTime()).asList();

    }


}


