package com.api.apiwebharas.controller;

import com.api.apiwebharas.dto.ApiResponseDTO;
import com.api.apiwebharas.dto.LoginDTO;
import com.api.apiwebharas.dto.TokenDTO;
import com.api.apiwebharas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<TokenDTO>> auth(@RequestBody @Validated LoginDTO loginDTO){
        ApiResponseDTO apiResponseDTO = authService.login(loginDTO);
        return ResponseEntity.status(apiResponseDTO.getStatus().value()).body(apiResponseDTO);
    }
}
