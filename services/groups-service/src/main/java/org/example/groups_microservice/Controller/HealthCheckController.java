package org.example.groups_microservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("groups/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Service is healthy");
    }
}
