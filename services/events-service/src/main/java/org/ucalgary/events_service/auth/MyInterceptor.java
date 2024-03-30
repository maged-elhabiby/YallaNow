package org.ucalgary.events_service.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.ucalgary.events_service.Configuration.AuthConfig;

import java.util.Collections;


@Component
public class MyInterceptor implements HandlerInterceptor {

    private final AuthConfig authConfig;

    @Autowired
    public MyInterceptor(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        if (request.getMethod().equals("GET") && request.getRequestURI().equals("/events/health")){
            return true;
        }

        try {
            HttpHeaders headers = extractHeaders(request);
            ResponseEntity<AuthResponse> authResponse = new RestTemplate().exchange(
                    authConfig.getAuthDns(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    AuthResponse.class
            );

            if (authResponse.getStatusCode().is2xxSuccessful()) {
                AuthResponse responseBody = authResponse.getBody();
                if (responseBody != null) {
                    request.setAttribute("Email", responseBody.getEmail());
                    request.setAttribute("Id", responseBody.getUid());
                    request.setAttribute("Name", "John Doe");
                }
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Changed from SC_FORBIDDEN to SC_UNAUTHORIZED
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Changed from SC_FORBIDDEN to SC_UNAUTHORIZED
            return false;
        }
    }

    private HttpHeaders extractHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames())
                .forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
        return headers;
    }

    private static class AuthResponse {
        private String email;
        private String uid;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
