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
import java.security.SecureRandom;
import java.util.Date;

@Component
public class JwtTokenComponent {

    private static final String SECRET_KEY = "sdg415fd#$TR#TGWE%Ytfg$%TGwvgtyieu5uwe5nngytvigtyiue5nlsiuerwse5mbgyv,h.lrdeihyiubes5vgmi%YT$%YTVysg4d5f4g6d";
    private static final int TIME_TO_LIVE = 1800000;

    public String createJwt(final User user) {
        final Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());
        final JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(new SecureRandom().nextInt()))
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .claim("profileId", user.getProfile().getId())
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

    int getUserIdFromToken(final String token){
        return JWT.decode(token).getClaim("userId").asInt();
    }
}
