package com.kkomiding.davena.common.hash;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("sha256Hashing")
public class SaltwithSHAHasingEncoder {

	private static final int SALT_SIZE = 16;
		
	//salt 만들기
	public String getSALT() throws Exception {
		SecureRandom rnd = new SecureRandom();
		byte[] temp = new byte[SALT_SIZE];
		rnd.nextBytes(temp);
		
		return Byte_to_String(temp);
	}
	
	//byte -> 16진수로 바꾸기
	public String Byte_to_String(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for(byte a : temp) {
			sb.append(String.format("%02x", a));
		}
		return sb.toString();
	}	

	//비밀번호 Hashing처리하기
	public String Hashing(byte[] password, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		//key stretching
		for(int i = 0; i < 100; i++) {
			String temp = Byte_to_String(password) + salt;
			md.update(temp.getBytes());
			password = md.digest();
		}
		
		return Byte_to_String(password);
	}
	
	//salted된 비밀번호 만들기
	public List<String> setSaltPw(String loginId, String password) throws Exception {
		byte[] password_byte = password.getBytes();
		String Salt = getSALT();	
		String saltedPassword = Hashing(password_byte, Salt);
		
		List<String> saltPwList = new ArrayList<>();
		saltPwList.add(Salt);
		saltPwList.add(saltedPassword);
		
		return saltPwList;
	}

}
