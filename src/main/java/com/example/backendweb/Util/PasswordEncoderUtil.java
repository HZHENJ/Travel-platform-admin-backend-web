package com.example.backendweb.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        String plainTextPassword = "123"; // Replace with the actual password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(plainTextPassword);

        System.out.println("Encoded password: " + encodedPassword);
        // Copy and paste this encoded password into your SQL INSERT statement
    }
}
