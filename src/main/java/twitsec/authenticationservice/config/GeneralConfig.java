package twitsec.authenticationservice.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import twitsec.authenticationservice.model.Role;
import twitsec.authenticationservice.model.User;
import twitsec.authenticationservice.repository.CredentialRepository;
import twitsec.authenticationservice.service.JwtTokenComponent;

@Configuration
@RequiredArgsConstructor
public class GeneralConfig {

    private final CredentialRepository credentialRepository;
    private final JwtTokenComponent tokenComponent;

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

    @Bean
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenComponent.createCommunicationJwt(new User(Role.COMMUNICATION)));
        return headers;
    }
}
