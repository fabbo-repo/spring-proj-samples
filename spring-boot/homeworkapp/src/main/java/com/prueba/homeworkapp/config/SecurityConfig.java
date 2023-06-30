package com.prueba.homeworkapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.prueba.homeworkapp.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SecurityScheme(name = SecurityConfig.SECURITY_CONFIG_NAME, in = HEADER, type = HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SecurityConfig extends  WebSecurityConfigurerAdapter {
	
	public static final String SECURITY_CONFIG_NAME = "App Bearer token";
	
	private final UserDetailsService userDetailsService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Session authentication by cookies will be disabled
		http.csrf().disable();
		// To disable default login endpoint
		http.httpBasic().disable();
		// Needed for H2 page
		http.headers().frameOptions().disable();
		http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
		//jwtAuthenticationFilter.setFilterProcessesUrl("/api/jwt");
		
		// Authorization instruction's order matters

		// Allow any request to JWT token
		http.authorizeRequests().antMatchers(
			"/api/login/**",
			"/api/jwt/**",
			"/api/jwt/refresh/**",
			// Swagger:
			"/v3/api-docs/**",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/v3/api-docs/**"
		).permitAll();
		// Allow any request to user URL only to role user
		http.authorizeRequests().antMatchers("/api/user/**").hasAnyAuthority("ROLE_USER");
		// Allow any request to user save URL only to role admin
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
		// Allow only authenticated users to api requests
		http.authorizeRequests().antMatchers("/api/**").authenticated();
		// Allow only authenticated users to other requests
		//http.authorizeRequests().anyRequest().authenticated();
		
		// Authentication Filter
		// http.addFilter(jwtAuthenticationFilter);
		// Authorization Filter
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}
}
