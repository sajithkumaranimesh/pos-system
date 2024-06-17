package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private Integer id;
    private String name;
    private String size;
    private String price;
    private String quantityOnHand;
    private Integer categoryId;
    private Integer supplierId;
}
