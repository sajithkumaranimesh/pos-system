package edu.icet.clothingcrm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetail {
    private String orderId;
    private String categoryId;
    private String productId;
    private Integer qtyCustomer;
    private Double descount;
    private Double totalCost;
}
