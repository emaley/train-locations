package com.ewan.timetable.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.zalando.problem.Exceptional;
import org.zalando.problem.ProblemModule;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {
    TestRestTemplate restTemplate;
    String url;
    String requestJSON;

    @Getter
    ResponseEntity<String> result;

    @Getter
    ObjectMapper objectMapper = new ObjectMapper();

    public TestHelper(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        objectMapper.registerModule(new ProblemModule());
    }

    public TestHelper setUrl(String url) {
        this.url = url;
        return this;
    }

    public TestHelper setRequestJSON(String requestJSON) {
        this.requestJSON = requestJSON;
        return this;
    }

    public TestHelper runRequest(HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entityObj = new HttpEntity<>(requestJSON, headers);
        result = restTemplate.exchange(url, httpMethod, entityObj, String.class);
        return this;
    }

    public TestHelper expectOKHttpStatus() {
        return expectHttpStatus(HttpStatus.OK);
    }

    public TestHelper expectHttpStatus(HttpStatus httpStatus) {
        assertThat(result.getStatusCode()).isEqualTo(httpStatus);
        return this;
    }

    public TestHelper expectZalandoProblem(int statusCode) throws Exception {
        Exceptional exceptional = objectMapper.readValue(result.getBody(), org.zalando.problem.Exceptional.class);

        assertThat(exceptional).isNotNull();
        assertThat(exceptional.getStatus().getStatusCode()).isEqualTo(statusCode);

        return this;
    }
}
