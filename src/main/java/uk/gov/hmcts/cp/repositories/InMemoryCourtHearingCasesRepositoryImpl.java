package uk.gov.hmcts.cp.repositories;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCourtHearingCasesRepositoryImpl implements CourtHearingCasesRepository {

    private final Map<String, CaseJudiciaryResponse> caseJudiciaryResponseMap = new ConcurrentHashMap<>();


    public void saveCaseLevelResults(final String caseId, final CaseJudiciaryResponse courtHouseResponse){
        caseJudiciaryResponseMap.put(caseId, courtHouseResponse);
    }
    public void clearAll(){
        caseJudiciaryResponseMap.clear();
    }


    public CaseJudiciaryResponse getCaseLevelResults(String caseId) {
        if (!caseJudiciaryResponseMap.containsKey(caseId)) {
            saveCaseLevelResults(caseId, createCaseLevelResults());
        }
        return caseJudiciaryResponseMap.get(caseId);
    }

    private CaseJudiciaryResponse createCaseLevelResults() {
        final CaseJudiciaryResult caseJudiciaryResult = CaseJudiciaryResult.builder()
            .resultText("This is the example outcome of case results")
            .caseUrn("DVL12XM5")
            .hearingId("30d23005-f743-434b-a919-c70471b2ef43")
            .build();
        return CaseJudiciaryResponse.builder()
            .caseResults(Arrays.asList(caseJudiciaryResult))
            .build();
    }
}
