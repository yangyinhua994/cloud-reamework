package com.example.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 密码加密工具类
 * 使用PBKDF2算法进行密码加密，保证安全性
 */
public class PasswordUtil {

    // 加密算法配置
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;  // 迭代次数
    private static final int KEY_LENGTH = 256;    // 密钥长度

    /**
     * 生成加密后的密码
     * @param password 原始密码
     * @return 加密后的密码字符串，包含盐值和哈希值
     */
    public static String encryptPassword(String password) {
        try {
            // 生成随机盐值
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // 使用PBKDF2算法生成哈希值
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // 将盐值和哈希值编码为字符串返回
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码是否正确
     * @param password 输入的密码
     * @param encryptedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String password, String encryptedPassword) {
        try {
            // 分离盐值和哈希值
            String[] parts = encryptedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);

            // 使用相同参数对输入密码进行加密
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            // 比较两个哈希值是否相等
            return slowEquals(storedHash, testHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        }
    }

    /**
     * 安全地比较两个字节数组是否相等
     * 使用恒定时间比较防止时序攻击
     * @param a 第一个数组
     * @param b 第二个数组
     * @return 是否相等
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
