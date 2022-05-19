package com.api.apiwebharas.security;

import com.api.apiwebharas.dto.RoleDTO;
import com.api.apiwebharas.dto.UsuarioContext;
import com.api.apiwebharas.entity.Role;
import com.api.apiwebharas.entity.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	ObjectMapper objectMapper = new ObjectMapper();

	public Usuario getUserFromContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((Usuario)authentication.getPrincipal());
	}

	public String generateToken(Authentication authentication) {

		Usuario usuario = (Usuario) authentication.getPrincipal();

		Date now = new Date();
		Date exp = new Date(now.getTime() + Long.parseLong(expiration));

		SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

		return Jwts.builder().setIssuer("HO_TOKEN")
                             .setSubject(usuario.getId().toString())
                             .setIssuedAt(new Date())
				             .setExpiration(exp)
                             .signWith(secretKey).compact();
	}

	public String generateToken(Usuario usuario) {
		JwtTokenData jwtTokenData = generateTokenData(usuario);

//		Map<String, Object> claims = new HashMap<>();
//		claims.putAll(customerData);

		Map<String, Object> claims = objectMapper.convertValue(jwtTokenData, Map.class);

		Date now = new Date();
		Date exp = new Date(now.getTime() + Long.parseLong(expiration));

		SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

		return Jwts.builder()
				.setIssuer("HO_TOKEN")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(secretKey).compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private JwtTokenData generateTokenData(Usuario usuario) {
		JwtTokenData jwtTokenData = new JwtTokenData();

		JwtUsuarioData jwtUsuarioData = new JwtUsuarioData();
		jwtUsuarioData.setId(usuario.getId());
		jwtUsuarioData.setEmail(usuario.getEmail());
		jwtUsuarioData.setNome(usuario.getNome());
//		Set<RoleDTO> roles = objectMapper.convertValue(usuario.getRoles(), new TypeReference<Set<RoleDTO>>() {});
		List<String> roles = new ArrayList<>();
		for(Role role : usuario.getRoles()) {
			roles.add(role.getNome());
		}
		jwtUsuarioData.setRoles(roles);

		jwtTokenData.setUsuario(jwtUsuarioData);

		return jwtTokenData;
	}

	public Date getExpirationFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	public UsuarioContext getUserContextFromJwtToken(String token) {
		Map<String, Object> usuarioObject = (Map<String, Object>) getAllClaimsFromToken(token).get("usuario");
//		Usuario usuario = new Usuario();
//		usuario.setId(Long.parseLong(String.valueOf(usuarioObject.get("id"))));
		UsuarioContext usuario = objectMapper.convertValue(usuarioObject, UsuarioContext.class);
		return usuario;
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	}

}