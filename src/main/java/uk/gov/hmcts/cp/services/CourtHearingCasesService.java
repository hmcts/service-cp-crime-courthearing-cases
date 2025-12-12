package uk.gov.hmcts.cp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.repositories.CourtHearingCasesRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtHearingCasesService {

    private final CourtHearingCasesRepository courtHearingCasesRepository;

    public CaseJudiciaryResponse getCaseLevelResults(final String caseId) {
        if (StringUtils.isEmpty(caseId)) {
            log.warn("No case id provided");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "caseId is required");
        }

        log.warn("NOTE: System configured to return stubbed Case results details. " +
                     "Ignoring provided caseUrId: {}", caseId);

        final CaseJudiciaryResponse response = courtHearingCasesRepository.getCaseLevelResults(caseId);

        log.debug("Case Result response: {}", response);

        return response;
    }
}
