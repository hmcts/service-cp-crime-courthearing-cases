package uk.gov.hmcts.cp.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResponse;
import uk.gov.hmcts.cp.openapi.model.CaseJudiciaryResult;
import uk.gov.hmcts.cp.repositories.CourtHearingCasesRepository;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class, PactVerificationInvocationContextProvider.class})
@Provider("CPHearingCasesProvider")
@PactBroker(
    url = "${pact.broker.url}",
    authentication = @PactBrokerAuth(token = "${pact.broker.token}")
)
@Tag("pact")
public class CourtHearingCasesProviderPactTest {
    private static final Logger LOG = LoggerFactory.getLogger(CourtHearingCasesProviderPactTest.class);

    @Autowired
    private CourtHearingCasesRepository courtHearingCasesRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupTarget(PactVerificationContext context) {
        LOG.atDebug().log("Running test on port: " + port);
        context.setTarget(new HttpTestTarget("localhost", port));
        LOG.atDebug().log("pact.verifier.publishResults: " + System.getProperty("pact.verifier.publishResults"));
    }

    @State("case with ID 123 exists")
    public void setupCaseLevelResults() {
        courtHearingCasesRepository.clearAll();
        final CaseJudiciaryResult caseJudiciaryResult = CaseJudiciaryResult.builder()
            .resultText("This is the example outcome of case results")
            .caseUrn("DVL12XM5")
            .hearingId("30d23005-f743-434b-a919-c70471b2ef43")
            .build();
        CaseJudiciaryResponse caseJudiciaryResponse = CaseJudiciaryResponse.builder()
            .caseResults(asList(caseJudiciaryResult))
            .build();

        courtHearingCasesRepository.saveCaseLevelResults("456789", caseJudiciaryResponse);
    }

    @TestTemplate
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

}
