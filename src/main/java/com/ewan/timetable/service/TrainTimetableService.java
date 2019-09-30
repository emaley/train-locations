package com.ewan.timetable.service;

import com.ewan.timetable.api.LocationRequest;
import com.ewan.timetable.api.LocationResponse;
import com.ewan.timetable.model.Train;
import com.ewan.timetable.repository.TrainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrainTimetableService {
    private TrainRepository trainRepository;
    private SimpMessagingTemplate broker;

    public TrainTimetableService(TrainRepository trainRepository, SimpMessagingTemplate broker) {
        this.trainRepository = trainRepository;
        this.broker = broker;
    }

    public List<LocationResponse> getList() {
        List<LocationResponse> returnList = new ArrayList<>();
        for (Train train : trainRepository.findAll()) {
            returnList.add(buildLocationResponseFromTrainModel(train));
        }
        return returnList;
    }

    public LocationResponse get(Long trainId) {
        Optional<Train> trainOptional = trainRepository.findById(trainId);
        Train train;
        if (trainOptional.isPresent()) {
            train = trainOptional.get();
        } else {
            throw Problem.valueOf(Status.NOT_FOUND);
        }
        return buildLocationResponseFromTrainModel(train);

    }

    public LocationResponse update(Long trainId, LocationRequest locationRequest) {
        Optional<Train> trainOptional = trainRepository.findById(trainId);

        Train trainRequest;
        if (trainOptional.isPresent()) {
            trainRequest = trainOptional.get();
            if (locationRequest.getCoordinates() != null) {
                trainRequest.setGpsLatitude(locationRequest.getCoordinates()[0]);
                trainRequest.setGpsLongitude(locationRequest.getCoordinates()[1]);
            }
            trainRequest.setTrainName(locationRequest.getName());
            trainRequest.setDestination(locationRequest.getDestination());
            trainRequest.setSpeed(locationRequest.getSpeed());
        } else {
            trainRequest = Train.builder()
                    .trainId(trainId)
                    .gpsLatitude(locationRequest.getCoordinates()[0])
                    .gpsLongitude(locationRequest.getCoordinates()[1])
                    .trainName(locationRequest.getName())
                    .destination(locationRequest.getDestination())
                    .speed(locationRequest.getSpeed())
                    .build();
        }

        Train train = trainRepository.save(trainRequest);

        LocationResponse locationResponse = buildLocationResponseFromTrainModel(train);
        sendClientWebSocketMessage(locationResponse);

        return locationResponse;
    }

    private void sendClientWebSocketMessage(LocationResponse locationResponse) {
        log.info("Sending location to websocket for train: {}", locationResponse.toString());
        broker.convertAndSend("/topic/trains", locationResponse);
    }

    private LocationResponse buildLocationResponseFromTrainModel(Train train) {
        return LocationResponse.builder()
                .trainId(train.getTrainId())
                .name(train.getTrainName())
                .coordinates(new BigDecimal[] {train.getGpsLatitude(), train.getGpsLongitude()})
                .destination(train.getDestination())
                .speed(train.getSpeed())
                .build();
    }

}
