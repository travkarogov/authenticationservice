package twitsec.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    private String email;

    private String password;

    private Role role;

    private Profile profile;
}
