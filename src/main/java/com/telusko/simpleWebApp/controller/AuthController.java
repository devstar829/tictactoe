package com.telusko.simpleWebApp.controller;

import java.util.HashMap;
import java.util.Map;

import com.telusko.simpleWebApp.service.GoogleTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private GoogleTokenService googleTokenService;

    @PostMapping("/google")
    public ResponseEntity<?> verifyGoogleToken(@RequestBody Map<String, String> tokenMap) {
        try {
            String code = tokenMap.get("code");

            // Prepare the request to the Google token endpoint
            String url = "https://oauth2.googleapis.com/token";

            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("client_id", "118071667465-aa58e14p3cjeqhncamleb7bvcb3gdcm0.apps.googleusercontent.com");
            requestParams.add("client_secret", "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");
            requestParams.add("code", code);
            requestParams.add("grant_type", "authorization_code");
            requestParams.add("redirect_uri", "http://localhost:3000");

            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestParams, Map.class);
            Map<String, String> tokens = response.getBody();
            String access_token = tokens.get("access_token");
            String refresh_token = tokens.get("refresh_token");

            String userId = googleTokenService.getUserIdFromAccessToken(access_token);
            redisTemplate.opsForValue().set("USER_INFO:" + userId, access_token);
            redisTemplate.opsForValue().set("USER_INFO:" + userId, refresh_token);

            Map<String, String> res = new HashMap<>();
            res.put("userId", userId);
            res.put("refresh_token", refresh_token);
            res.put("access_token", access_token);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Unauthorized");
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }

    }

    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> requestBody) {
        String userId = requestBody.get("userId");
        System.out.println(userId);
        if (!validateTokenAsRedis(userId)) {
            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
        } else {
            Map<String, String> res = new HashMap<>();
            res.put("userId", userId);
            return ResponseEntity.ok(res);
        }

    }

    private boolean validateTokenAsRedis(String userId) {
        String token = redisTemplate.opsForValue().get("USER_INFO:" + userId);
        return token != null;
    }
}