package com.ewan.timetable.rest.api;

import java.math.BigDecimal;

public class LocationRequest_JSON {
    public String getLocationRequest(String name, BigDecimal[] coordinates, String destination, BigDecimal speed
                                     ) {
        return "{" + formatJSONValue("name", name)
                + ", " + formatJSONValue("destination", destination)
                + ", \"speed\": " + speed.toString()
                + ", \"coordinates\": [" + coordinates[0] + ", " + coordinates[1] + "]"
                + "}";
    }

    public String getBasicLocationRequest() {
        return getLocationRequest("name", new BigDecimal[] { BigDecimal.valueOf(123.11), BigDecimal.valueOf(122.2)}, "destination", new BigDecimal(333.3));
    }

    private String formatJSONValue(String key, String value) {
        if (value == null) {
            return "\"" + key + "\": null";
        } else {
            return "\"" + key + "\": \"" + value + "\"";
        }
    }
}
