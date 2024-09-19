package au.com.telstra.simcardactivator.service.impl;

import au.com.telstra.simcardactivator.model.SimCardActivation;
import au.com.telstra.simcardactivator.repository.SimCardActivationRepository;
import au.com.telstra.simcardactivator.request.SimCardRequest;
import au.com.telstra.simcardactivator.response.SimCardActivationResponse;
import au.com.telstra.simcardactivator.service.SimCardActivationService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SimCardActivationServiceImpl implements SimCardActivationService {
    private final RestTemplate restTemplate;

    private final SimCardActivationRepository repository;

    public SimCardActivationServiceImpl(RestTemplate restTemplate, SimCardActivationRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Override
    public boolean activateSimCard(String iccid, String customerEmail) {
        SimCardRequest request = new SimCardRequest();
        request.setIccid(iccid);
        request.setCustomerEmail(customerEmail);

        String actuatorUrl = "http://localhost:8444/actuate";
        SimCardActivationResponse response = restTemplate.postForObject(actuatorUrl, request, SimCardActivationResponse.class);

        boolean activationSuccess = response != null && response.isSuccess();

        SimCardActivation activation = new SimCardActivation();
        activation.setIccid(iccid);
        activation.setCustomerEmail(customerEmail);
        activation.setActive(activationSuccess);
        repository.save(activation);

        return activationSuccess;
    }

    @Override
    public SimCardActivation getStatus(Long id) {
        return repository.findById(id).orElse(null);
    }
}


