package com.mthor.jwtsecurity.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginUserDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
