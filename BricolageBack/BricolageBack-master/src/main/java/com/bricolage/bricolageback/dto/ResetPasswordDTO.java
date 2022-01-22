package com.bricolage.bricolageback.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class ResetPasswordDTO {
    private String password;
    private String passwordActual;
    private String repassword;
    private String username;
};
