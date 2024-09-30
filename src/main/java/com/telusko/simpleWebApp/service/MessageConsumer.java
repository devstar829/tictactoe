package com.telusko.simpleWebApp.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.simpleWebApp.config.RabbitMQConfig;
import com.telusko.simpleWebApp.model.UserTokenMessage;

@Service
public class MessageConsumer {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private GoogleTokenService googleTokenService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) throws IllegalAccessException {
        try {
            // Revoke the token using the GoogleTokenService
            UserTokenMessage userTokenMessage = objectMapper.readValue(message, UserTokenMessage.class);
            redisTemplate.delete("USER_INFO:" + userTokenMessage.getUserId());
            System.out.println(userTokenMessage.getToken());
            googleTokenService.revokeToken(userTokenMessage.getToken());
           
        } catch (Exception e) {
            System.out.println("Failed to revoke the token: " + e.getMessage());
        }


    }

    public String getTokenFromRequest(String token) throws IllegalAccessException {
        String[] parts = token.split(" ");
        if (parts.length != 2 || !parts[0].contains("Bearer")) {
            throw new IllegalAccessException("Authorization Bearer format invalid. <Bearer {token}>");
        }
        return parts[1];
    }
}
