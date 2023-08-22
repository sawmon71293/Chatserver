package com.mon.Chatserver.service;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class JwtSecretKeyGenerator {
    private static final int KEY_LENGTH_BYTES = 32; // 256 bits
    private final String jwtSecretKey;
    
    public JwtSecretKeyGenerator() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH_BYTES];
        secureRandom.nextBytes(keyBytes);
        
        // Encode the key in Base64
        jwtSecretKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
    
    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
    }

