package twitsec.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitsec.authenticationservice.communication.UserServiceRestCommunication;
import twitsec.authenticationservice.model.LoginInput;
import twitsec.authenticationservice.model.User;
import twitsec.authenticationservice.service.exception.EmailInvalidException;
import twitsec.authenticationservice.service.exception.PasswordInvalidException;
import twitsec.authenticationservice.service.exception.TokenInvalidException;
import twitsec.authenticationservice.service.exception.TwoFactorInvalidException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceRestCommunication userServiceRestCommunication;
    private final HashComponent hashComponent;
    private final JwtTokenComponent jwtTokenComponent;
    private final TwoFactorComponent twoFactorComponent;

    public String login(final LoginInput input, final boolean skip2FA) {
        User user = new User();
        if (stringIsValidEmail(input.getEmail())) {
            user = findAccountByEmail(input.getEmail());
        }

        if (!hashComponent.passwordCheck(input.getPassword(), user.getPassword())){
            throw new PasswordInvalidException("Password invalid");
        }

        if (skip2FA) {
            return jwtTokenComponent.createJwt(user);
        } else {
            if (twoFactorComponent.validateGoogleAuthenticationCode(user.getId(), input.getCode())) {
                return jwtTokenComponent.createJwt(user);
            } else
                throw new TwoFactorInvalidException("2FA code invalid");
        }
    }

    public String refreshAccessToken(final String accessToken){
        if (jwtTokenComponent.validateJwt(accessToken)) {
            final User user = userServiceRestCommunication.findUserById(jwtTokenComponent.getUserIdFromToken(accessToken));
            return jwtTokenComponent.createJwt(user);
        } else throw new TokenInvalidException("Invalid token");
    }

    private User findAccountByEmail(final String emailAddress) {
        User user = userServiceRestCommunication.findUserByEmail(emailAddress.toLowerCase());
        if(user.getEmail() == null){
            throw new EmailInvalidException("Email invalid");
        }
        return user;
    }

    private boolean stringIsValidEmail(final String email) {
        try {
            new InternetAddress(email).validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}
