package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RegisterUser {
    private String username;
    private String password;
    private String email;
}
