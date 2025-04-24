package uk.gov.hmcts.cp.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;

import java.util.Arrays;
import java.util.List;

@Service
public class CourtHearingCasesService {

    public List<CaseJudiciaryResult> getCaseLevelResults(String caseId) {
        return Arrays.asList(new CaseJudiciaryResult("Sample1 CaseJudiciaryResult"),
                      new CaseJudiciaryResult("Sample2 CaseJudiciaryResult"));

    }
}
