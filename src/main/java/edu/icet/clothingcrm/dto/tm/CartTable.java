package edu.icet.clothingcrm.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CartTable {
    private String categoryId;
    private String productId;
    private Integer qtyCustomer;
    private Double unitPrice;
    private Double total;
    private Double discount;
}
