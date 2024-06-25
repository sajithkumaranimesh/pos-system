package edu.icet.clothingcrm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private String size;
    private String price;
    private String quantityOnHand;
    private String categoryId;
    private String supplierId;
}
