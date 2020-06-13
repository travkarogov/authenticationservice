package twitsec.authenticationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private int id;

    private String username;

    private List<Profile> following;

    private List<Profile> followers;

    private String bio;

    private String websiteUrl;

    private Date createdAt = new Date();
}
