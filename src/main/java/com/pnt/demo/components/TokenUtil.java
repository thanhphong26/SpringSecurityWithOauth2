package com.pnt.demo.components;

import com.pnt.demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    public String generateToken(User user) {
        var now = Instant.now();
        var expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getPhoneNumber().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("role",user.getRole().getName())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    public Boolean validateToken(String token, User user) {
        var jwt = jwtDecoder.decode(token);
        return jwt.getSubject().equals(user.getPhoneNumber().toString());
    }
    public Boolean isTokenExpired(String token) {
        var jwt = jwtDecoder.decode(token);
        return jwt.getExpiresAt().isBefore(Instant.now());
    }

}
