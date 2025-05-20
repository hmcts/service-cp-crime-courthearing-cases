package uk.gov.hmcts.cp.services;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary;
import uk.gov.hmcts.cp.openapi.model.JudgesJudiciary.RoleEnum;
import java.util.UUID;

@Service
public class JudgesService implements StubbedJudges {

    private static final Logger log = LoggerFactory.getLogger(JudgesService.class);
    private final Boolean stubbedJudges = Boolean.parseBoolean(System.getProperty("STUBBED_JUDGES", "true"));

    public Judges getJudge(UUID courtId) {
        if (stubbedJudges) {
            log.info("System configured to return stubbed Judge details. Ignoring provided courtId : {}", courtId.toString());
            return getStubbedJudge();
        }else {
            log.error("NO REFERENCE DATA SERVICE CONFIGURED AT THIS TIME");
            throw new NotImplementedException("NO REFERENCE DATA SERVICE CONFIGURED");
        }
    }

    public Judges getStubbedJudge() {
        JudgesJudiciary judiciary = JudgesJudiciary.builder()
                .johTitle("His Honour")
                .johNameSurname("John Smith")
                .role(RoleEnum.fromValue("judge"))
                .johKnownAs("His Honour Judge Smith")
                .build();
        Judges judges = new Judges();
        judges.setJudiciary(judiciary);

        return judges;
    }
}
