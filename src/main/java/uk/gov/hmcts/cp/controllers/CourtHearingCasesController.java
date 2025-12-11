package uk.gov.hmcts.cp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.api.CasesApi;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.services.CourtHearingCasesService;

@Slf4j
@RestController
public class CourtHearingCasesController implements CasesApi {
    private final CourtHearingCasesService courtHearingCasesService;

    public CourtHearingCasesController(final CourtHearingCasesService courtHearingCasesService) {
        this.courtHearingCasesService = courtHearingCasesService;
    }

    @Override
    public ResponseEntity<CaseJudiciaryResponse> getCaseLevelResults(final String caseId) {
        final String sanitizeCaseId;
        final CaseJudiciaryResponse caseJudiciaryResponse;
        try {
            sanitizeCaseId = sanitizeCaseId(caseId);
            caseJudiciaryResponse = courtHearingCasesService.getCaseLevelResults(sanitizeCaseId);
        } catch (ResponseStatusException e) {
            log.atError().log(e.getMessage());
            throw e;
        }

        log.debug("Found case judiciary response for caseId: {}", sanitizeCaseId);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(caseJudiciaryResponse);
    }

    private String sanitizeCaseId(final String caseId) {
        if (caseId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "caseId is required");
        }
        return StringEscapeUtils.escapeHtml4(caseId);
    }

}
