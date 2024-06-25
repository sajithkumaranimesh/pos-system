package edu.icet.clothingcrm.dto.tm;

import edu.icet.clothingcrm.dto.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductTable extends Product {
    private String id;
    private String name;
    private String size;
    private String price;
    private String quantityOnHand;
    private String categoryId;
    private String supplierId;
}
