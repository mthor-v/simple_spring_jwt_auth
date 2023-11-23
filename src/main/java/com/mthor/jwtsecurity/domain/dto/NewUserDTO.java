package com.mthor.jwtsecurity.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NewUserDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String userName;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Set<String> roleSet = new HashSet<>();
}
