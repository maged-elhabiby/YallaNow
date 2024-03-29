package org.ucalgary.events_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.ucalgary.events_service.Configuration.AuthConfig;

import java.util.Enumeration;

@Component
public class MyInterceptor implements HandlerInterceptor {

    public String email;
    public String id;
    public String name;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        try {
            System.out.println("Executing pre-request logic...");
            System.out.println(authConfig.getAuthDns());

            RestTemplate restTemplate = new RestTemplate();
            

            // Extract headers from the incoming request
            HttpHeaders headers = extractHeaders(request);

            // Send a GET request to the auth server with extracted headers
            ResponseEntity<String> authResponse = restTemplate.exchange(authConfig.getAuthDns(), HttpMethod.GET, new HttpEntity<>(headers), String.class);


            if (authResponse.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                AuthResponse responseBody = objectMapper.readValue(authResponse.getBody(), AuthResponse.class);
                email = responseBody.getEmail();
                id = responseBody.getUid();
                name = "John Doe";
                request.setAttribute("Email", email);
                request.setAttribute("Id", id);
                request.setAttribute("Name", name);
                return true;
            } else {
                response.setStatus(403);

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(403);
            return false;
        }
    }

    private HttpHeaders extractHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
            headers.add(headerName, request.getHeader(headerName));
        }
        return headers;
    }


}

class AuthResponse {
    private String email;
    private String uid;

    // getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setId(String uid) {
        this.uid = uid;
    }
}