package com.api.apiwebharas.controller;

import com.api.apiwebharas.dto.LoginDTO;
import com.api.apiwebharas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/test")
public class TesteController {

    @PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
    @GetMapping
    public ResponseEntity<String> auth(){
        System.out.println("Teste");
        return ResponseEntity.ok("Testado");
    }

    public void validUserRequest(String role) {

    }
}
