package twitsec.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitsec.authenticationservice.communication.UserServiceRestCommunication;
import twitsec.authenticationservice.model.Role;
import twitsec.authenticationservice.model.User;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserServiceRestCommunication userServiceRestCommunication;
    private final HashComponent hashComponent;
    private final TwoFactorComponent twoFactorComponent;

    public String create(final User user) throws Exception {
        validateNewUserAccountData(user);

        user.setPassword(hashComponent.hashPassword(user.getPassword()));
        user.setRole(Role.USER);
        user.setEmail(user.getEmail().toLowerCase());

        User createdUser = userServiceRestCommunication.save(user);

        return twoFactorComponent.createTOTP(createdUser);
    }

    private void validateNewUserAccountData(final User user) throws Exception {
        if (user.getEmail() == null
                || user.getProfile().getUsername() == null
                || user.getPassword() == null) {
            throw new Exception("Please enter email, username and password");
        }

        User userDatabase = userServiceRestCommunication.findUserByEmail(user.getEmail());

        if (userDatabase != null) {
            throw new Exception(String.format("Email address '%s' already exists", user.getEmail()));
        }
    }
}
