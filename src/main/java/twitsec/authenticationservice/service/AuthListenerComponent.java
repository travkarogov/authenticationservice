package twitsec.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import twitsec.authenticationservice.repository.TOTPRepository;

@Component
@RequiredArgsConstructor
public class AuthListenerComponent {

    private final TOTPRepository repository;
    private final JwtTokenComponent tokenComponent;

    @RabbitListener(queues = "${twitsec.rabbitmq.authqueue}")
    @Transactional
    public void deleteTOTPByUserId(String token) {
        repository.deleteAllByUserId(tokenComponent.getUserIdFromToken(token));
    }
}
