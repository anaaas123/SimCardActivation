package stepDefinitions;

import au.com.telstra.simcardactivator.response.SimCardActivationResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
@Component
public class SimCardActivatorStepDefinitions {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String iccid;

    @Given("a SIM card with ICCID {string}")
    public void a_SIM_card_with_ICCID(String iccid) {
        this.iccid = iccid;
    }

    @When("I activate the SIM card")
    public void i_activate_the_SIM_card() {
        String url = "http://localhost:8080/activate";
        response = restTemplate.postForEntity(url, iccid, String.class);
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Success"));
    }

    @Then("the activation should fail")
    public void the_activation_should_fail() {
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Failed"));
    }

    @Then("the SIM card should be active")
    public void the_SIM_card_should_be_active() {
        ResponseEntity<SimCardActivationResponse> queryResponse =
                restTemplate.getForEntity("http://localhost:8080/query?simCardId=1", SimCardActivationResponse.class);
        assertTrue(queryResponse.getBody().isActive());
    }

    @Then("the SIM card should not be active")
    public void the_SIM_card_should_not_be_active() {
        ResponseEntity<SimCardActivationResponse> queryResponse =
                restTemplate.getForEntity("http://localhost:8080/query?simCardId=2", SimCardActivationResponse.class);
        assertFalse(queryResponse.getBody().isActive());
    }

}
