package com.hulzzuk.user.model.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class PasswordEncryptor {
	// SHA-256 암호화 메소드
    public static String encryptSHA256(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plainText.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b)); // byte를 16진수(hex)로 변환
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 사용할 수 없습니다.", e);
        }
    }

    // 입력 비밀번호와 저장된 비밀번호를 SHA-256 방식으로 비교하는 메소드
    public static boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        String hashedRawPassword = encryptSHA256(rawPassword);
        return encodedPassword.trim().equals(StringUtils.upperCase(hashedRawPassword));
    }
}
