package twitsec.authenticationservice.communication;

import org.springframework.stereotype.Component;
import twitsec.authenticationservice.model.User;

import java.net.http.HttpClient;

@Component
public class UserServiceCommunication {

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public User findUserById(int userId){
        return null;
    }

    public User findUserByEmail(String email){
        return null;
    }
}
