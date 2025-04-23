package uk.gov.hmcts.cp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.cp.openapi.api.DefaultApi;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;
import uk.gov.hmcts.cp.services.CourtHearingCasesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourtHearingCasesController implements DefaultApi {

    private final CourtHearingCasesService courtHearingCasesService;

    @Override
    public ResponseEntity<List<CaseJudiciaryResult>> getCaseLevelResults(String caseId) {
        List<CaseJudiciaryResult> caseLevelResultsList = courtHearingCasesService.getCaseLevelResults(caseId);
        return new ResponseEntity<>(caseLevelResultsList, HttpStatus.OK);
    }

}
