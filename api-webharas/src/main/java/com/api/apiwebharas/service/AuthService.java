package com.api.apiwebharas.service;

import com.api.apiwebharas.dto.ApiResponseDTO;
import com.api.apiwebharas.dto.LoginDTO;
import com.api.apiwebharas.dto.TokenDTO;
import com.api.apiwebharas.entity.Usuario;
import com.api.apiwebharas.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ApiResponseDTO login(LoginDTO loginDTO) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        bCryptPasswordEncoder.encode(loginDTO.getSenha());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsuario(), loginDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.generateJwtToken(usuario);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setType("Bearer");
        tokenDTO.setToken(token);

        apiResponseDTO.setData(tokenDTO);
        apiResponseDTO.setStatus(HttpStatus.OK);
        apiResponseDTO.setMessage("Login realizado com sucesso");

        return apiResponseDTO;
    }
}
