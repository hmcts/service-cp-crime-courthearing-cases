package uk.gov.hmcts.cp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CourtHearingCasesRepositoryTest {

    private CourtHearingCasesRepository courtHearingCasesRepository;

    @BeforeEach
    void setUp() {
        courtHearingCasesRepository = new InMemoryCourtHearingCasesRepositoryImpl();
    }

    @Test
    void getCourtScheduleByCaseUrn_shouldReturnCourtScheduleResponse() {
        UUID caseId = UUID.randomUUID();
        CaseJudiciaryResponse response = courtHearingCasesRepository.getCaseLevelResults(
            caseId.toString());

        assertNotNull(response.getCaseResults());
        assertEquals(1, response.getCaseResults().size());

        CaseJudiciaryResult caseJudiciaryResult = response.getCaseResults().get(0);
        assertNotNull(caseJudiciaryResult);
        assertEquals("This is the example outcome of case results", caseJudiciaryResult.getResultText());
    }

}
