package com.aliyara.smartshop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
