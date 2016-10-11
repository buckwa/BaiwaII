package com.buckwa.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class Md5PasswordGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub;
		String pass = "0000";
		PasswordEncoder encoder = new Md5PasswordEncoder();
	      String hashedPass = encoder.encodePassword(pass, null);
	     System.out.println(">>:"+hashedPass);
	      

	}

}
