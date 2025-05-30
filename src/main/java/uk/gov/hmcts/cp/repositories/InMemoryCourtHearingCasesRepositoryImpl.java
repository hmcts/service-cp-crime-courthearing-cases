package uk.gov.hmcts.cp.repositories;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;

import java.util.Arrays;

@Component
public class InMemoryCourtHearingCasesRepositoryImpl implements CourtHearingCasesRepository {

    public CaseJudiciaryResponse getCaseLevelResults(String caseId) {
        CaseJudiciaryResult caseJudiciaryResult = CaseJudiciaryResult.builder()
            .resultText("This is the example outcome of case results")
            .build();
        return CaseJudiciaryResponse.builder()
            .caseResults(Arrays.asList(caseJudiciaryResult))
            .build();
    }
}
