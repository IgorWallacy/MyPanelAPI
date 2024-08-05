package com.doks.mypanel.controller;

import com.doks.mypanel.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @Value("${encryption.secret.key}")
    private String secretKeyHex;

    @Autowired
    public TokenController(AuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private String decryptData(String encryptedData, String secretKeyHex) throws Exception {
        byte[] keyBytes = hexStringToByteArray(secretKeyHex);

        // Decoding the Base64 encoded encrypted data
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);

        // Extracting IV and encrypted bytes
        byte[] ivBytes = new byte[16]; // AES block size is 16 bytes
        byte[] encryptedBytes = new byte[decodedData.length - 16];

        System.arraycopy(decodedData, 0, ivBytes, 0, 16);
        System.arraycopy(decodedData, 16, encryptedBytes, 0, encryptedBytes.length);

        // Configuring the cipher for AES-256-CBC with PKCS5Padding
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public ResponseEntity<Map<String, String>> authenticate(@RequestParam("encryptedData") String encryptedData) throws Exception {
        String decryptedData = decryptData(encryptedData, secretKeyHex);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.readValue(decryptedData, Map.class);

        String email = data.get("email");
        String password = data.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = authenticationService.authenticate(authentication);
        Map<String, String> response = new HashMap<>();
        response.put("access_token", token);
        return ResponseEntity.ok(response);
    }
}