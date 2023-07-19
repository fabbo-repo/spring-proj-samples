package com.prueba.homeworkapp.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.homeworkapp.auth.domain.models.dtos.LoginDTO;
import com.prueba.homeworkapp.auth.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is optional, in this project is not used
 * 
 * @author Fabbo
 *
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Whenever the user tries to login
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper mapper = new ObjectMapper();
		LoginDTO credentials;
		try {
			credentials = mapper.readValue(request.getInputStream(), LoginDTO.class);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new BadCredentialsException(e.getMessage());
		}
		log.info("Trying to authenticate User with username {}", credentials.username);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			credentials.username,
			credentials.password
		);
		return authenticationManager.authenticate(authenticationToken);
	}

	/**
	 * In case of successful authentication the access and refresh JWT token should
	 * be added to the response.
	 * access_token has a validity of 10 minutes
	 * refresh_token has a validity of 30 minutes
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		Algorithm algorithm = AuthenticationUtil.getHMAC256();
		String accessToken = JWT.create().withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
			.withIssuer(request.getRequestURL().toString())
			.withClaim("roles",
				user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.sign(algorithm);
		String refreshToken = JWT.create().withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
			.withIssuer(request.getRequestURI().toString()).sign(algorithm);
		// JSON response:
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", accessToken);
		tokens.put("refresh_token", refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}

	/**
	 * This method could be used to cache unsuccessful authentication to avoid brute
	 * force attack
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
