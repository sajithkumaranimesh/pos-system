package edu.icet.clothingcrm.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Order {
    private String orderId;
    private Date orderDate;
    private String customerName;
    private String customerEmail;
    private List<OrderDetail> orderDetailList;
}
