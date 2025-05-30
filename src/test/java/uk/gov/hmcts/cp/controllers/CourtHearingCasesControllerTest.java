package uk.gov.hmcts.cp.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;
import uk.gov.hmcts.cp.repositories.CourtHearingCasesRepository;
import uk.gov.hmcts.cp.repositories.InMemoryCourtHearingCasesRepositoryImpl;
import uk.gov.hmcts.cp.services.CourtHearingCasesService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourtHearingCasesControllerTest {
    private static final Logger log = LoggerFactory.getLogger(CourtHearingCasesControllerTest.class);

    private CourtHearingCasesRepository courtHearingCasesRepository;
    private CourtHearingCasesService courtHearingCasesService;
    private CourtHearingCasesController courtHearingCasesController;

    @BeforeEach
    void setUp() {
        courtHearingCasesRepository = new InMemoryCourtHearingCasesRepositoryImpl();
        courtHearingCasesService = new CourtHearingCasesService(courtHearingCasesRepository);
        courtHearingCasesController = new CourtHearingCasesController(courtHearingCasesService);
    }

    @Test
    void getCaseLevelResults_ShouldReturnResultsWithOkStatus() {
        UUID caseId = UUID.randomUUID();
        log.info("Calling courtHearingCasesController.getCaseLevelResults with caseId: {}", caseId);
        ResponseEntity<?> response = courtHearingCasesController.getCaseLevelResults(caseId.toString());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        CaseJudiciaryResponse responseBody = (CaseJudiciaryResponse) response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getCaseResults());
        assertEquals(1, responseBody.getCaseResults().size());

        CaseJudiciaryResult schedule = responseBody.getCaseResults().get(0);
        assertNotNull(schedule.getResultText());
        assertEquals("This is the example outcome of case results", schedule.getResultText());
    }

    @Test
    void getCaseLevelResults_ShouldSanitizeCaseId() {
        String unsanitizedCaseId = "<script>alert('xss')</script>";
        log.info("Calling courtHearingCasesController.getCaseLevelResults with unsanitized caseId: {}", unsanitizedCaseId);

        ResponseEntity<?> response = courtHearingCasesController.getCaseLevelResults(unsanitizedCaseId);
        assertNotNull(response);
        log.debug("Received response: {}", response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCaseResults_ShouldReturnBadRequestStatus() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            courtHearingCasesController.getCaseLevelResults(null);
        });
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getReason()).isEqualTo("caseId is required");
        assertThat(exception.getMessage()).isEqualTo("400 BAD_REQUEST \"caseId is required\"");
    }
}
