package twitsec.authenticationservice.repository;

import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitsec.authenticationservice.entity.TOTP;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CredentialRepository implements ICredentialRepository {

    private final TOTPRepository totpRepository;

    @Override
    public String getSecretKey(final String userId) {
        final TOTP totp = totpRepository.getTOTPByUserId(Integer.parseInt(userId));

        return totp.getSecretKey();
    }

    @Override
    public void saveUserCredentials(final String userId,
                                    final String secretKey,
                                    final int validationCode,
                                    final List<Integer> scratchCodes) {
        final TOTP totp = TOTP.builder()
                .userId(Integer.parseInt(userId))
                .secretKey(secretKey)
                .validationCode(validationCode)
                .scratchCodes(scratchCodes)
                .build();

        totpRepository.save(totp);
    }
}
