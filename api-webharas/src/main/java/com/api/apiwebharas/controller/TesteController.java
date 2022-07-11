package com.api.apiwebharas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/test")
public class TesteController {

    @PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
    @GetMapping
    public ResponseEntity<String> auth(){
        System.out.println("Teste");
        return ResponseEntity.ok("Testado");
    }
}
