package com.ewan.timetable.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationRequest {
    @ApiModelProperty(value = "Train coordinates", required = true, example = "[123.11, 122.2]")
    @NotNull
    BigDecimal[] gpsCoordinates;

    @ApiModelProperty(value = "Train name", required = true, example = "Thomas")
    @NotNull
    String name;

    @ApiModelProperty(value = "Train destination", required = true, example = "Timbuktwo")
    @NotNull
    String destination;

    @ApiModelProperty(value = "Train speed", required = true, example = "333.3")
    @NotNull
    BigDecimal speed;

    @Tolerate
    LocationRequest() {
    }
}
