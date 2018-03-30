package com.common.util;

import com.common.security.Digests;

/**
 * 密码该工具类
 */
public class PasswordUtil {


	public static final String HASH_ALGORITHM_MD5 = "MD5";
	public static final String HASH_ALGORITHM_SHA1 = "SHA-1";
	public static final int HASH_INTERATIONS = 1024; // 散列次数
	private static final int SALT_SIZE = 8; // 盐的长度

	

	/**
	 * 生成安全的密码，生成随机的16位md5并经过2次 sha-1 hash
	 */
	public static String entryptPasswordMD5(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.md5(plain.getBytes(), salt, HASH_INTERATIONS);
		System.out.println("盐:" + Encodes.encodeHex(salt));
		System.out.println("密码:" + Encodes.encodeHex(hashPassword));
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPasswordSHA1(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		System.out.println("盐:" + Encodes.encodeHex(salt));
		System.out.println("密码:" + Encodes.encodeHex(hashPassword));
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}
}

