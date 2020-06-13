package twitsec.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitsec.authenticationservice.model.StringDto;
import twitsec.authenticationservice.model.User;
import twitsec.authenticationservice.service.RegistrationService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping(path = "/")
    public ResponseEntity<StringDto> registerNewUser(@RequestBody final User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(StringDto.builder().string(registrationService.create(user)).build());
    }
}
