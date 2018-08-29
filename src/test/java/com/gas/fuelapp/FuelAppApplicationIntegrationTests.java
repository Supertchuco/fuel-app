package com.gas.fuelapp;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FuelAppApplicationIntegrationTests {

    private static final String requestEndpointBase = "http://localhost:8090/fuelConsumption";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String readJSON(String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    private HttpHeaders buildHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    public void shouldReturn200WhenInsertNewFuelConsumption() {
        String payload = readJSON("request/saveNewFuelConsumptionPayloadWithSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/saveFuelConsumptions", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenTryToSaveEmptyFuelConsumptionJasonArray() {
        String payload = readJSON("request/saveNewFuelConsumptionPayloadWithEmptyFuelConsumptionJasonArray.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/saveFuelConsumptions", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenTryToInsertNewFuelConsumptionWithoutDriverIdOnRequest() {
        String payload = readJSON("request/saveNewFuelConsumptionPayloadWithoutDriverId.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/saveFuelConsumptions", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenSaveDuplicatedFuelConsumption() {
        String payload = readJSON("request/saveNewFuelConsumptionPayloadWithSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/saveFuelConsumptions", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnUnauthorizedWhenTryToDoRequestWithDriverIdWithoutAccess() {
        String payload = readJSON("request/saveNewFuelConsumptionPayloadWithDriverWithoutAccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/saveFuelConsumptions", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateConsumeByMonthAndYearReport() {
        String payload = readJSON("request/generateConsumeByMonthAndYearReportSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeByMonthAndYear", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateConsumeByMonthAndYearReportWithoutYearParam() {
        String payload = readJSON("request/generateConsumeByMonthAndYearReportSuccessWithoutYear.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeByMonthAndYear", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenGenerateConsumeByMonthAndYearReportWithouMonthParam() {
        String payload = readJSON("request/generateConsumeByMonthAndYearReportWithoutMonth.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeByMonthAndYear", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateConsumeGroupedByMonthReport() {
        String payload = readJSON("request/generateConsumeByConsumeGroupedByMonthReportSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeGroupedByMonth", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateConsumeGroupedByMonthReportWithoutYear() {
        String payload = readJSON("request/generateConsumeByConsumeGroupedByMonthReportSuccessWithoutYear.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeGroupedByMonth", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateMonthlyStatisticsReport() {
        String payload = readJSON("request/generateConsumeByMonthlyStatisticsReportSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeGroupedByMonth", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenGenerateMonthlyStatisticsReportWithOutYear() {
        String payload = readJSON("request/generateConsumeByMonthlyStatisticsReportSuccessWithoutYear.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase + "/consumeGroupedByMonth", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}