package twitsec.authenticationservice.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class HashComponent {

    public String hashPassword(final String submittedPassword){
        return BCrypt.hashpw(submittedPassword, BCrypt.gensalt(8));
    }

    boolean passwordCheck(final String submittedPassword, final String hashedPassword){
        return BCrypt.checkpw(submittedPassword, hashedPassword);
    }
}
