package edu.icet.clothingcrm.dto.tm;

import edu.icet.clothingcrm.dto.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CategoryTable extends Category {
    private Integer id;
    private String name;
}
