package twitsec.authenticationservice.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import twitsec.authenticationservice.repository.CredentialRepository;

@Configuration
@RequiredArgsConstructor
public class GeneralConfig {

    private final CredentialRepository credentialRepository;

    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(credentialRepository);
        return googleAuthenticator;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
