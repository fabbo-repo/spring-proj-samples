package com.prueba.homeworkapp.util;

import com.auth0.jwt.algorithms.Algorithm;

public class AuthenticationUtil {
	
	public static Algorithm getHMAC256() {
		return Algorithm.HMAC256("secret".getBytes());
	}
}
