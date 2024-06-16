package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private Integer id;
    private String name;
    private String email;
    private String address;
}
