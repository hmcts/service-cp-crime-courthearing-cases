package uk.gov.hmcts.cp.repositories;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;

@Repository
@FunctionalInterface
public interface CourtHearingCasesRepository {

    CaseJudiciaryResponse getCaseLevelResults(String caseId);

}
