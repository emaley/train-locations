package com.ewan.timetable.service;

import com.ewan.timetable.api.LocationRequest;
import com.ewan.timetable.api.LocationResponse;
import com.ewan.timetable.model.Train;
import com.ewan.timetable.repository.TrainRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainEntityServiceTest {
    TrainTimetableService trainTimetableService;

    @Autowired
    TrainRepository trainRepository;

    @Mock
    SimpMessagingTemplate broker;

    @Before
    public void setUp() throws Exception {
        trainTimetableService = new TrainTimetableService(trainRepository, broker);
    }

    private LocationRequest createLocationRequest() {
        return LocationRequest.builder()
                .name("name")
                .destination("destination")
                .speed(new BigDecimal(333.3))
                .coordinates(new BigDecimal[] {BigDecimal.valueOf(123.11), BigDecimal.valueOf(122.2)})
                .build();
    }

    private List<Long> populateTrainRepository(int numTrains) {
        List<Long> trainIds = new ArrayList();
        for (int i = 0; i < numTrains; i++) {
            Train train = Train.builder()
                    .trainId(Long.valueOf(i))
                    .trainName("name" + i)
                    .destination("destination" + i)
                    .speed(BigDecimal.valueOf(i))
                    .gpsLatitude(BigDecimal.valueOf(i))
                    .gpsLongitude(BigDecimal.valueOf(i))
                    .build();
            Train savedTrain = trainRepository.save(train);
            trainIds.add(savedTrain.getTrainId());
        }
        return trainIds;
    }

    @Test
    public void shouldCreateATrainAndRetrieveCreatedTrainSuccessfully() throws Exception {
        LocationRequest locationRequest = createLocationRequest();
        LocationResponse createdTrainResponse = trainTimetableService.update(1L, locationRequest);

        LocationResponse train = trainTimetableService.get(createdTrainResponse.getTrainId());
        assertThat(train.getTrainId()).isEqualTo(createdTrainResponse.getTrainId());
    }

    @Test
    public void shouldReturn2TrainsInListRequest() throws Exception {
        populateTrainRepository(2);
        List<LocationResponse> trains = trainTimetableService.getList();
        assertThat(trains).isNotNull();
        assertThat(trains.size()).isEqualTo(2);
    }

    @Test
    public void shouldThrowExceptionForRetrievingTrainNotFound() {
        try {
            trainTimetableService.get(12L);
            fail("Should have thrown exception for train not found");
        } catch (ThrowableProblem ex) {
            assertThat(ex.getStatus()).isEqualTo(Status.NOT_FOUND);
        }
    }

    @Test
    public void shouldUpdateTrain() throws Exception {
        List<Long> trainIds = populateTrainRepository(1);

        LocationResponse locationResponse = trainTimetableService.get(trainIds.get(0));
        LocationRequest locationRequest = LocationRequest.builder()
                .name("updated:" + locationResponse.getName())
                .destination("updated:" + locationResponse.getDestination())
                .build();
        LocationResponse updatedLocationResponse = trainTimetableService.update(locationResponse.getTrainId(), locationRequest);
        assertThat(updatedLocationResponse.getTrainId()).isEqualTo(trainIds.get(0));
        assertThat(updatedLocationResponse.getName()).startsWith("updated");
    }
}
