package edu.icet.clothingcrm.entity;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter@ToString
@Entity
public class UserEntity {
    private String id;
    private String username;
    private String password;
    private String email;
    private String userType;
    private String employeeId;
}
