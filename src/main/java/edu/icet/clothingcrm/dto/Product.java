package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private String id;
    private String name;
    private String size;
    private String price;
    private String quantityOnHand;
    private String categoryId;
    private String supplierId;
}
