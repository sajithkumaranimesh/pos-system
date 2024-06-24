package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private String username;
    private String password;
    private String email;
    private String userType;
    private String employeeId;
}
