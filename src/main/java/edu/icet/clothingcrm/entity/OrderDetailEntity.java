package edu.icet.clothingcrm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "orderDetail")
public class OrderDetailEntity {
    @ManyToMany
    private List<OrderEntity> orderId;

    @ManyToMany
    private List<CategoryEntity> categoryId;

    @ManyToMany
    private List<ProductEntity> productId;

    private Integer qtyCustomer;
    private Double descount;
    private Double totalCost;
}
