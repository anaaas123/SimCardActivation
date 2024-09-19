package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.model.SimCardActivation;

import java.util.Optional;

public interface SimCardActivationService {
    boolean activateSimCard(String iccid, String customerEmail);
    SimCardActivation getStatus(Long id);
}
