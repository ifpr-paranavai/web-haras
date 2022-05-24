package com.api.apiwebharas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenDTO {
    String type;
    String token;
}
