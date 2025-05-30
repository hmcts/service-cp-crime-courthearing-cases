package uk.gov.hmcts.cp.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.repositories.CourtHearingCasesRepository;

@Service
@RequiredArgsConstructor
public class CourtHearingCasesService {
    private static final Logger log = LoggerFactory.getLogger(CourtHearingCasesService.class);

    private final CourtHearingCasesRepository courtHearingCasesRepository;

    public CaseJudiciaryResponse getCaseLevelResults(String caseId) {
        if (StringUtils.isEmpty(caseId)) {
            log.warn("No case id provided");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "caseId is required");
        }
        log.warn("NOTE: System configured to return stubbed Case results details. Ignoring provided caseUrId : {}", caseId);
        CaseJudiciaryResponse stubbedCaseJudiciaryResponse = courtHearingCasesRepository.getCaseLevelResults(caseId);
        log.debug("Case Result response: {}", stubbedCaseJudiciaryResponse);
        return stubbedCaseJudiciaryResponse;
    }
}
