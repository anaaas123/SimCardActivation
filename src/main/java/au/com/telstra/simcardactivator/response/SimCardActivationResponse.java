package au.com.telstra.simcardactivator.response;

import lombok.Data;

@Data
public class SimCardActivationResponse {
    private boolean success;

    private boolean active;
}
