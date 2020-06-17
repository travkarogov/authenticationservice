package twitsec.authenticationservice.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitsec.authenticationservice.model.User;

@Component
@RequiredArgsConstructor
public class UserServiceRestCommunication {

    private final RestTemplate restTemplate;
    private static final String URI = "http://localhost:49502/users";
    private final HttpHeaders headers;

    public User findUserById(int userId){
        HttpEntity<Integer> integerWithHeader = new HttpEntity<>(userId, headers);
        return restTemplate.getForObject(URI + "/" + integerWithHeader, User.class);
    }

    public User findUserByEmail(String email){
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(URI + "/email?email=" + email, HttpMethod.GET, entity, User.class).getBody();
    }

    public User save(User user){
        HttpEntity<User> userWithHeader = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(URI + "/create", userWithHeader, User.class);
    }
}
