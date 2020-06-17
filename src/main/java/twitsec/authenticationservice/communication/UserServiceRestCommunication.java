package twitsec.authenticationservice.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitsec.authenticationservice.model.User;

@Component
@RequiredArgsConstructor
public class UserServiceRestCommunication {

    private final RestTemplate restTemplate;
    private static final String uri = "http://localhost:49502/users";

    public User findUserById(int userId){
        return restTemplate.getForObject(uri + "/" + userId, User.class);
    }

    public User findUserByEmail(String email){
        return restTemplate.getForObject(uri + "/email?email=" + email, User.class);
    }

    public User save(User user){
        return restTemplate.postForObject(uri + "/create", user, User.class);
    }
}
