package uk.gov.hmcts.cp.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.repositories.CourtHearingCasesRepository;
import uk.gov.hmcts.cp.repositories.InMemoryCourtHearingCasesRepositoryImpl;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CourtHearingCasesServiceTest {
    private final CourtHearingCasesRepository courtHearingCasesRepository = new InMemoryCourtHearingCasesRepositoryImpl();
    private final CourtHearingCasesService courtHearingCasesService = new CourtHearingCasesService(courtHearingCasesRepository);

    @Test
    void shouldReturnStubbedCourtHearingResult_whenValidCaseIdProvided() {
        // Arrange
        String validCaseId = randomUUID().toString();

        // Act
        CaseJudiciaryResponse response = courtHearingCasesService.getCaseLevelResults(
            validCaseId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getCaseResults()).isNotEmpty();
        assertThat(response.getCaseResults().get(0)).isNotNull();
        assertThat(response.getCaseResults().get(0).getResultText()).isNotEmpty();
        assertThat(response.getCaseResults().get(0).getResultText())
            .isEqualTo("This is the example outcome of case results");
    }

    @Test
    void shouldThrowBadRequestException_whenCaseUrnIsNull() {
        // Arrange
        String nullCaseUrn = null;

        // Act & Assert
        assertThatThrownBy(() -> courtHearingCasesService.getCaseLevelResults(nullCaseUrn))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("400 BAD_REQUEST")
            .hasMessageContaining("caseId is required");
    }

    @Test
    void shouldThrowBadRequestException_whenCaseIdIsEmpty() {
        // Arrange
        String emptyCaseId = "";

        // Act & Assert
        assertThatThrownBy(() -> courtHearingCasesService.getCaseLevelResults(emptyCaseId))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("400 BAD_REQUEST")
            .hasMessageContaining("caseId is required");
    }

}
