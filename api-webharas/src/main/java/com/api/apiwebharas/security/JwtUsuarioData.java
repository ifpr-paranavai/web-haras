package com.api.apiwebharas.security;

import com.api.apiwebharas.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUsuarioData {
    private Long id;
    private String email;
    private String nome;
    private List<String> roles;
}
