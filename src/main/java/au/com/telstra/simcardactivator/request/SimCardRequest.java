package au.com.telstra.simcardactivator.request;

import lombok.Data;

@Data
public class SimCardRequest {
    private String iccid;
    private String customerEmail;
}
