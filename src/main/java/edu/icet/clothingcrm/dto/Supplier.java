package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Supplier {
    private Integer id;
    private String name;
    private String email;
    private String company;
}
