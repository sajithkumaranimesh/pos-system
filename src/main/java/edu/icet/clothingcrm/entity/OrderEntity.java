package edu.icet.clothingcrm.entity;

import edu.icet.clothingcrm.dto.OrderDetail;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderEntity {
    private String id;
    private Date orderDate;
    private String customerName;
    private String customerEmail;
    private List<OrderDetail> orderDetailList;
}
