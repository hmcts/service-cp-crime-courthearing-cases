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
    private static final Logger LOG = LoggerFactory.getLogger(CourtHearingCasesService.class);

    private final CourtHearingCasesRepository courtHearingCasesRepository;

    public CaseJudiciaryResponse getCaseLevelResults(final String caseId) {
        if (StringUtils.isEmpty(caseId)) {
            LOG.atWarn().log("No case id provided");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "caseId is required");
        }
        LOG.atWarn().log("NOTE: System configured to return stubbed Case results details. Ignoring provided caseUrId : {}", caseId);
        final CaseJudiciaryResponse stubbedCaseJudiciaryResponse = courtHearingCasesRepository.getCaseLevelResults(caseId);
        LOG.atDebug().log("Case Result response: {}", stubbedCaseJudiciaryResponse);
        return stubbedCaseJudiciaryResponse;
    }
}
