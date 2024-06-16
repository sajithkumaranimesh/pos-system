package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private Integer id;
    private String email;
    private String password;
    private Integer employeeId;
}
