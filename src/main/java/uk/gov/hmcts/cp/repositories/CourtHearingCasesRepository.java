package uk.gov.hmcts.cp.repositories;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;

@Repository
public interface CourtHearingCasesRepository {

    CaseJudiciaryResponse getCaseLevelResults(String caseId);
    void saveCaseLevelResults(String caseUrn, CaseJudiciaryResponse courtHouseResponse);
    void clearAll();
}
