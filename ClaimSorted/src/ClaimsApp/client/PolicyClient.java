package ClaimsApp.client;

import ClaimsApp.model.Policy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class PolicyClient {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    public Policy getPolicy(String policyId) throws Exception {
        String fakeJson = """
                {
                "policyId":"P123",
                "status":"ACTIVE",
                "coverageAmount":50000
            }
            """;

        return objectMapper.readValue(fakeJson, Policy.class);
    }

}
