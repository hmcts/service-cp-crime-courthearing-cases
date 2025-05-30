package uk.gov.hmcts.cp.controllers;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.api.CasesApi;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.services.CourtHearingCasesService;

@RestController
public class CourtHearingCasesController implements CasesApi {
    private static final Logger log = LoggerFactory.getLogger(CourtHearingCasesController.class);
    private final CourtHearingCasesService courtHearingCasesService;

    public CourtHearingCasesController(CourtHearingCasesService courtHearingCasesService) {
        this.courtHearingCasesService = courtHearingCasesService;
    }

    @Override
    public ResponseEntity<CaseJudiciaryResponse> getCaseLevelResults(String caseId) {
        String sanitizeCaseId;
        CaseJudiciaryResponse caseJudiciaryResponse;
        try {
            sanitizeCaseId = sanitizeCaseId(caseId);
            caseJudiciaryResponse = courtHearingCasesService.getCaseLevelResults(sanitizeCaseId);
        } catch (ResponseStatusException e) {
            log.error(e.getMessage());
            throw e;
        }
        log.debug("Found case judiciary response for caseId: {}", sanitizeCaseId);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(caseJudiciaryResponse);
    }

    private String sanitizeCaseId(String caseId) {
        if (caseId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "caseId is required");
        return StringEscapeUtils.escapeHtml4(caseId);
    }

}
