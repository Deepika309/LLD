package ClaimsApp.controller;

import ClaimsApp.model.ClaimRequest;
import ClaimsApp.model.ClaimResponse;
import ClaimsApp.service.ClaimsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/claims")
public class ClaimsController {
    private final ClaimsService claimsService;

    public ClaimsController(ClaimsService claimsService) {
        this.claimsService = claimsService;
    }

    @PostMapping("/evaluate")
    public ClaimResponse evaluate(@RequestBody ClaimRequest request)
    throws Exception {
        return claimsService.evaluateClaim(request);
    }
}
