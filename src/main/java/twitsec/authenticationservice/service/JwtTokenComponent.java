package twitsec.authenticationservice.service;

import com.auth0.jwt.JWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import twitsec.authenticationservice.model.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenComponent {

    private static final String SECRET_KEY = "sdg415fd#$TR#TGWE%Ytfg$%TGwvgtyieu5uwe5nngytvigtyiue5nlsiuerwse5mbgyv,h.lrdeihyiubes5vgmi%YT$%YTVysg4d5f4g6d";
    private static final int TIME_TO_LIVE = 1800000;
    private static final String ROLE = "role";
    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String PROFILE_ID = "profileId";

    public String createJwt(final User user) {
        final Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        final JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .claim(USER_ID, user.getId())
                .claim(ROLE, user.getRole())
                .claim(EMAIL, user.getEmail())
                .claim(PROFILE_ID, user.getProfile().getId())
                .setSubject("TwitSecToken")
                .setIssuer("TWITSEC_TOKEN_SERVICE")
                .signWith(signingKey);
        if (TIME_TO_LIVE > 0) builder.setExpiration(new Date(System.currentTimeMillis() + TIME_TO_LIVE));
        return builder.compact();
    }

    boolean validateJwt(final String jwt) {
        try{
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(jwt.replace("Bearer", "").trim()).getBody();
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    Integer getUserIdFromToken(final String jwt){
        return Integer.parseInt(JWT.decode(jwt).getClaim("userId").asString());
    }
}
