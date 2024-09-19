package au.com.telstra.simcardactivator.rest;

import au.com.telstra.simcardactivator.model.SimCardActivation;
import au.com.telstra.simcardactivator.request.SimCardRequest;
import au.com.telstra.simcardactivator.service.SimCardActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sim-cards")
public class SimCardController {

    @Autowired
    private SimCardActivationService service;

    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestBody SimCardRequest request) {
        boolean activationSuccess = service.activateSimCard(request.getIccid(), request.getCustomerEmail());

        if (activationSuccess) {
            return ResponseEntity.ok("Activation Successful");
        } else {
            return ResponseEntity.status(500).body("Activation Failed");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<SimCardActivation> getStatus(@RequestParam("simCardId") Long id) {
        SimCardActivation activation = service.getStatus(id);
        if (activation != null) {
            return ResponseEntity.ok(activation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
