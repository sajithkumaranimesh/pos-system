package edu.icet.clothingcrm.dto.tm;

import edu.icet.clothingcrm.dto.Supplier;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SupplierTable extends Supplier{
    private String id;
    private String name;
    private String email;
    private String company;
}
