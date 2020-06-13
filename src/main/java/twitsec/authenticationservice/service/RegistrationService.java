package twitsec.authenticationservice.service;

import com.kwetter.authentication_service.dto.AccountDTO;
import com.kwetter.authentication_service.dto.ProfileDTO;
import com.kwetter.authentication_service.entity.Account;
import com.kwetter.authentication_service.entity.AccountType;
import com.kwetter.authentication_service.microservice_communication.messaging.ProfileServiceCommunicator;
import com.kwetter.authentication_service.repository.AccountRepository;
import com.kwetter.authentication_service.service.exception.AccountAlreadyExistsException;
import com.kwetter.authentication_service.service.exception.RequiredValueUnsatisfiedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitsec.authenticationservice.communication.UserServiceCommunication;
import twitsec.authenticationservice.model.User;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserServiceCommunication userServiceCommunication;
    private final HashComponent hashComponent;
    private final TwoFactorComponent twoFactorComponent;
    private final ProfileServiceCommunicator profileServiceCommunicator;

    public String create(final User user) {
        validateNewUserAccountData(user);

        final ProfileDTO profile = ProfileDTO.builder()
                .id(UUID.randomUUID())
                .displayName(accountDTO.getProfile().getDisplayName())
                .profilePictureBASE64(accountDTO.getProfile().getProfilePictureBASE64())
                .dateOfBirth(accountDTO.getProfile().getDateOfBirth())
                .biography(accountDTO.getProfile().getBiography())
                .build();
        createNewProfile(profile);

        final Account account = Account.builder()
                .emailAddress(accountDTO.getEmailAddress().toLowerCase())
                .userName(accountDTO.getUserName())
                .password(hashComponent.hashPassword(accountDTO.getPassword()))
                .accountType(AccountType.ACCOUNT_TYPE_REGULAR_USER)
                .profileId(profile.getId())
                .build();
        accountRepository.save(account);

        return twoFactorComponent.createTOTPForAccount(account);
    }

    private void createNewProfile(ProfileDTO profile) {
        profileServiceCommunicator.sendMessageCreateProfile(profile);
    }

    private void validateNewUserAccountData(User user) {
        if (accountDTO.getEmailAddress() == null
                || accountDTO.getUserName() == null
                || accountDTO.getPassword() == null) {
            throw new RequiredValueUnsatisfiedException("One ore more required values for account unfilled");
        }
        if (accountDTO.getProfile().getDisplayName() == null
                || accountDTO.getProfile().getDateOfBirth() < 1) {
            throw new RequiredValueUnsatisfiedException("One ore more required values for profile unfilled");
        }

        if (accountRepository.existsAccountByEmailAddress(accountDTO.getEmailAddress())) {
            throw new AccountAlreadyExistsException(String.format("Account with emailAddress: '%s' already exists", accountDTO.getEmailAddress()));
        }
        if (accountRepository.existsAccountByUserName(accountDTO.getUserName())) {
            throw new AccountAlreadyExistsException(String.format("Account with userName: '%s' already exists", accountDTO.getUserName()));
        }
    }
}
