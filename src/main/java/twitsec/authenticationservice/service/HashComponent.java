package twitsec.authenticationservice.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashComponent {

    private String hashPassword(String submittedPassword){
        return BCrypt.hashpw(submittedPassword, BCrypt.gensalt(13));
    }

    boolean passwordCheck(String submittedPassword, String hashedPassword){
        return BCrypt.checkpw(submittedPassword, hashedPassword);
    }
}
