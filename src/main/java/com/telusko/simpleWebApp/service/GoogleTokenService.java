package com.telusko.simpleWebApp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.telusko.simpleWebApp.model.GoogleUserInfo;

@Service
public class GoogleTokenService {

    private static final String GOOGLE_TOKEN_REVOKE_URL = "https://oauth2.googleapis.com/revoke";
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    public String getUserIdFromAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GOOGLE_USERINFO_URL + "?access_token=" + accessToken;

        GoogleUserInfo userInfo = restTemplate.getForObject(url, GoogleUserInfo.class);
        return userInfo.getSub(); // This is the userId
    }

    public String validateAccessToken(String accessToken) {
        System.out.println(accessToken);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://oauth2.googleapis.com/tokeninfo?access_token=" + accessToken;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                // Token is valid, return the response body
                return response.getBody();
            } else {
                // Handle unexpected status codes
                throw new RuntimeException("Unexpected response from token info endpoint");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                // Handle invalid token
                throw new RuntimeException("Invalid Access Token: " + e.getResponseBodyAsString());
            } else {
                // Handle other errors
                throw new RuntimeException("Error validating Access Token: " + e.getResponseBodyAsString());
            }
        }
    }

    public void revokeToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        // Create the request body
        String body = "token=" + refreshToken;

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        // Send the POST request to revoke the token
        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_TOKEN_REVOKE_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Token revoked successfully.");
        } else {
            System.out.println("Failed to revoke the token: " + response.getBody());
        }
    }
}