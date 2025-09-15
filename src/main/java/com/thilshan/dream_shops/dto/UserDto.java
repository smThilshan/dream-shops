package com.thilshan.dream_shops.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private CartDto cart;
    private List<OrderDto> orders;
}
