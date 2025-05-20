package uk.gov.hmcts.cp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.cp.openapi.api.JudgesApi;
import uk.gov.hmcts.cp.openapi.model.Judges;
import uk.gov.hmcts.cp.services.JudgesService;
import java.util.UUID;

@RestController
public class JudgesController implements JudgesApi {

    private static final Logger log = LoggerFactory.getLogger(JudgesController.class);
    private final JudgesService judgesService;

    public JudgesController(JudgesService judgesService) {
        this.judgesService = judgesService;
    }

    @Override
    public ResponseEntity<Judges> getJudgeById(String courtId) {
        if (courtId == null) {
            log.warn("No court identifier provided: {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
        UUID cId = UUID.fromString(courtId);
        log.debug("Received request to fetch judge details for courtId: {}", cId.toString());
        Judges judges = judgesService.getJudge(cId);
        log.debug("Successfully found judge details : {}", judges);
        return new ResponseEntity<>(judges, HttpStatus.OK);
    }

}
