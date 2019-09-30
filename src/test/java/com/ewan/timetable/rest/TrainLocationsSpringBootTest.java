package com.ewan.timetable.rest;

import com.ewan.timetable.rest.api.LocationRequest_JSON;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrainLocationsSpringBootTest {
    static final String TRAINS_URL = "/trains";

    @Autowired
    TestRestTemplate restTemplate;

    TestHelper testHelper;
    LocationRequest_JSON locationRequest_json = new LocationRequest_JSON();

    @Before
    public void setUp() throws Exception {
        testHelper = new TestHelper(restTemplate);
    }

    @Test
    public void a_shouldReturnNoContent() throws Exception {
        testHelper.setUrl(TRAINS_URL)
                .runRequest(HttpMethod.GET)
                .expectHttpStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    public void b_shouldReturnSuccessfully() throws Exception {
        testHelper.setUrl(TRAINS_URL + "/12/location")
                .setRequestJSON(locationRequest_json.getBasicLocationRequest())
                .runRequest(HttpMethod.PUT)
                .expectOKHttpStatus();
    }

    @Test
    public void shouldThrowBadRequestForNoRequestObject() throws Exception {
        testHelper.setUrl(TRAINS_URL + "/1/location")
                .setRequestJSON(null)
                .runRequest(HttpMethod.PUT)
                .expectHttpStatus(HttpStatus.BAD_REQUEST)
                .expectZalandoProblem(400);
    }
}
