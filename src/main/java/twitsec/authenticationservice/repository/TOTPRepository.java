package twitsec.authenticationservice.repository;

import org.springframework.data.repository.CrudRepository;
import twitsec.authenticationservice.entity.TOTP;

public interface TOTPRepository extends CrudRepository<TOTP, Integer> {

    TOTP getTOTPByUserId(int id);
}
