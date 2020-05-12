package com.example.shoppyshop.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDto {
    private String username;
    private Collection<String> roles;
}
