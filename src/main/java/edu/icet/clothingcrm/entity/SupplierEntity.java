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
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String company;
}
