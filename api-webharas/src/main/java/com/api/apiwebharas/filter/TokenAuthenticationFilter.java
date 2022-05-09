package com.api.apiwebharas.filter;

import com.api.apiwebharas.entity.Usuario;
import com.api.apiwebharas.security.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	public TokenAuthenticationFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String tokenFromHeader = getTokenFromHeader(request);
		boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
		if(tokenValid) {
			this.authenticateFromToken(tokenFromHeader);
		}

		filterChain.doFilter(request, response);
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

	private void authenticateFromToken(String tokenFromHeader) {
//		Integer id = tokenService.getTokenId(tokenFromHeader);
		//get from token

		Usuario optionalUser = new Usuario();

		if(optionalUser != null) {

			Usuario user = optionalUser;

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

}