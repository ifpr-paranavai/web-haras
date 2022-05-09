package com.api.apiwebharas.service;

import com.api.apiwebharas.dto.LoginDTO;
import com.api.apiwebharas.entity.Usuario;
import com.api.apiwebharas.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String login(LoginDTO loginDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        bCryptPasswordEncoder.encode(loginDTO.getSenha());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsuario(), loginDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.generateToken(usuario);

        return token;
//        return ResponseEntity.ok(TokenDTO.builder().type("Bearer").token(token).build());
    }
}
