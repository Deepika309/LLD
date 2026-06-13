package ClaimsApp.service;

import ClaimsApp.client.PolicyClient;
import ClaimsApp.model.ClaimRequest;
import ClaimsApp.model.ClaimResponse;
import ClaimsApp.model.Policy;
import org.springframework.stereotype.Service;

@Service
public class ClaimsService {
    private final PolicyClient policyClient;
    public ClaimsService(PolicyClient policyClient) {
        this.policyClient = policyClient;
    }

    public ClaimResponse evaluateClaim(ClaimRequest request) throws Exception {
        Policy policy = policyClient.getPolicy(request.getPolicyId());

        if (!policy.getStatus().equals("ACTIVE")) {
            return new ClaimResponse(false, "Policy inactive");
        }

        if(policy.getCoverageAmount() < request.getClaimAmount()) {
            return new ClaimResponse(false, "Coverage amount exceeded");
        }

        return new ClaimResponse(true, "Claim approved");

    }
}
