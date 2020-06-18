package twitsec.authenticationservice.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitsec.authenticationservice.model.Role;
import twitsec.authenticationservice.model.User;
import twitsec.authenticationservice.service.JwtTokenComponent;

@Component
@RequiredArgsConstructor
public class UserServiceRestCommunication {

    private final RestTemplate restTemplate;
    private final JwtTokenComponent tokenComponent;
    private static final String URI = "http://localhost:49502/users";

    public User findUserById(int userId){
        HttpEntity<Integer> integerWithHeader = new HttpEntity<>(userId, setTokenInHeader());
        return restTemplate.getForObject(URI + "/" + integerWithHeader, User.class);
    }

    public User findUserByEmail(String email){
        HttpEntity<?> entity = new HttpEntity<>(setTokenInHeader());

        return restTemplate.exchange(URI + "/email?email=" + email, HttpMethod.GET, entity, User.class).getBody();
    }

    public User save(User user){
        HttpEntity<User> userWithHeader = new HttpEntity<>(user, setTokenInHeader());
        return restTemplate.postForObject(URI + "/create", userWithHeader, User.class);
    }

    public HttpHeaders setTokenInHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenComponent.createCommunicationJwt(new User(Role.COMMUNICATION)));
        return headers;
    }
}
