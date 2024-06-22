package edu.icet.clothingcrm.dto.tm;

import edu.icet.clothingcrm.dto.Employee;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeTable extends Employee {
    private String id;
    private String name;
    private String email;
    private String address;
}
