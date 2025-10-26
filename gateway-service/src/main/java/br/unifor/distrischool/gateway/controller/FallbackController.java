package br.unifor.distrischool.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> userServiceFallback() {
        return buildFallbackResponse("User Service");
    }

    @GetMapping("/students")
    public ResponseEntity<Map<String, Object>> studentServiceFallback() {
        return buildFallbackResponse("Student Service");
    }

    @GetMapping("/teachers")
    public ResponseEntity<Map<String, Object>> teacherServiceFallback() {
        return buildFallbackResponse("Teacher Service");
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> adminServiceFallback() {
        return buildFallbackResponse("Admin Staff Service");
    }

    private ResponseEntity<Map<String, Object>> buildFallbackResponse(String serviceName) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Service Unavailable");
        response.put("message", serviceName + " is currently unavailable. Please try again later.");
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}
