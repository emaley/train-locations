package com.ewan.timetable.web;

import com.ewan.timetable.api.LocationRequest;
import com.ewan.timetable.api.LocationResponse;
import com.ewan.timetable.service.TrainTimetableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "TrainLocations", description = "Allows for input of train location")
@RequestMapping(path = "trains", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainResource {
    TrainTimetableService trainTimetableService;

    public TrainResource(TrainTimetableService trainTimetableService) {
        this.trainTimetableService = trainTimetableService;
    }

    @RequestMapping(method = RequestMethod.GET, path="")
    @ApiOperation(value = "Get all train locations",
            notes = "Returns 200 on success or 204 for no content")
    public ResponseEntity<List<LocationResponse>> getLocations() {
        log.info("Get train locations");

        List<LocationResponse> locations = trainTimetableService.getList();
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(locations);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{trainId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get train location",
            notes = "Returns 200 on success")
    public ResponseEntity<LocationResponse> getTrainLocation (
            @Valid @RequestBody
            @PathVariable("trainId")
            @ApiParam(name = "trainId",
                    value = "ID of the train to find the location for",
                    example = "1",
                    required = true)
                    Long trainId) {
        log.debug("Get request for trainId: {}", trainId);

        return ResponseEntity.ok(trainTimetableService.get(trainId));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{trainId}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Input location for trainId",
            notes = "Returns 200 on success")
    public ResponseEntity<Long> updateTrainLocation (
            @Valid @RequestBody
            @PathVariable("trainId")
            @ApiParam(name = "trainId",
                    value = "ID of the train",
                    example = "12",
                    required = true)
            Long trainId,
            @Valid @RequestBody
            @ApiParam(name = "request",
                    value = "Update train location<br>Refer to model for detailed documentation",
                    required = true)
                    LocationRequest locationRequest) {
        log.debug("Location of trainId {} = {}", trainId, locationRequest.toString());

        LocationResponse locationResponse = trainTimetableService.update(trainId, locationRequest);
        return ResponseEntity.ok(locationResponse.getTrainId());
    }
}
