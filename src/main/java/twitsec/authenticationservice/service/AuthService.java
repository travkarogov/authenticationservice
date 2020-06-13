package twitsec.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitsec.authenticationservice.communication.UserServiceCommunication;
import twitsec.authenticationservice.model.LoginInput;
import twitsec.authenticationservice.model.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceCommunication userServiceCommunication;
    private final HashComponent hashComponent;
    private final JwtTokenComponent jwtTokenComponent;
    private final TwoFactorComponent twoFactorComponent;

    public String login(final LoginInput input, final boolean skip2FA) throws Exception {
        User verifiedUser = new User();
        if (stringIsValidEmail(input.getEmail())) {
            verifiedUser = findAccountByEmail(input.getEmail());
        }

        if (!hashComponent.passwordCheck(input.getPassword(), verifiedUser.getPassword())){
            throw new Exception("Password invalid");
        }

        if (skip2FA) {
            return jwtTokenComponent.createJwt(verifiedUser);
        } else {
            if (twoFactorComponent.validateGoogleAuthenticationCode(verifiedUser.getId(), input.getCode())) {
                return jwtTokenComponent.createJwt(verifiedUser);
            } else
                throw new Exception("2FA code invalid");
        }
    }

    public String refreshAccessToken(final String accessToken) throws Exception {
        if (jwtTokenComponent.validateJwt(accessToken)) {
            final User user = userServiceCommunication.findUserById(jwtTokenComponent.getUserIdFromToken(accessToken));
            return jwtTokenComponent.createJwt(user);
        } else throw new Exception("Invalid token");
    }

    private User findAccountByEmail(final String emailAddress) throws Exception {
        User user = userServiceCommunication.findUserByEmail(emailAddress.toLowerCase());
        if(user.getEmail() == null){
            throw new Exception("Email invalid");
        }
        return user ;
    }

    private boolean stringIsValidEmail(final String userName) {
        try {
            new InternetAddress(userName).validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}
