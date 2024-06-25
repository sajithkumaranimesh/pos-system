package edu.icet.clothingcrm.dto.tm;

import edu.icet.clothingcrm.dto.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserTable extends User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String userType;
    private String employeeId;
}
