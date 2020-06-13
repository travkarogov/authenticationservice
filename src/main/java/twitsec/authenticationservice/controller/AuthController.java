package twitsec.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitsec.authenticationservice.model.LoginInput;
import twitsec.authenticationservice.model.StringDto;
import twitsec.authenticationservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<StringDto> login(@RequestBody final LoginInput input) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(StringDto.builder().string(authService.login(input, false)).build());
    }

    @PostMapping(path = "/renewToken")
    public ResponseEntity<StringDto> renewAccessToken(@RequestBody final StringDto stringDto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(StringDto.builder().string(authService.refreshAccessToken(stringDto.getString())).build());
    }
}
