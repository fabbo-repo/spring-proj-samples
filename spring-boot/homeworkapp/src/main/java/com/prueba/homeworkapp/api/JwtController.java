package com.prueba.homeworkapp.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.homeworkapp.persistence.entity.Role;
import com.prueba.homeworkapp.persistence.entity.User;
import com.prueba.homeworkapp.service.IUserService;
import com.prueba.homeworkapp.service.dto.LoginDTO;
import com.prueba.homeworkapp.service.dto.RegisterDTO;
import com.prueba.homeworkapp.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JwtController {

    private final AuthenticationManager authenticationManager;
	
	private final IUserService userService;
	
	@PostMapping("/jwt")
    public void authenticateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginDTO loginDTO) throws JsonGenerationException, JsonMappingException, IOException{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    		loginDTO.getUsername(),
    		loginDTO.getPassword()
		);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        // Check username exists in a DB
        if(userService.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        // create user object
        userService.createUser(registerDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    
	@GetMapping("/jwt/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = AuthenticationUtil.getHMAC256();
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				User user = userService.getUser(username);
				String accessToken = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
					.withIssuer(request.getRequestURL().toString())
					.withClaim("roles",
						user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
					.sign(algorithm);
				// JSON response:
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", accessToken);
				tokens.put("refresh_token", refreshToken);
		        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				// JSON response:
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
		        new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}