package develop.backend.Club_card.security;

import develop.backend.Club_card.exception.CustomException;
import develop.backend.Club_card.entity.User;
import develop.backend.Club_card.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret}")
    private String secret;

    @Value("${security.jwt.token.timestamp}")
    private int timestamp;

    private final MessageSource messageSource;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username) {
        Optional<User> mayBeUser = userRepository.findUserByUsername(username);

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", mayBeUser.map(user -> user.getRole().getRoleInString())
                .orElseThrow(() -> new CustomException(this.messageSource.getMessage(
                        "security.auth.errors.username.not.found", null, Locale.getDefault()
                ), HttpStatus.NOT_FOUND)));

        Date now = new Date();
        Date validity = new Date(now.getTime() + timestamp);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                this.getUsernameFromToken(token)
        );

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new CustomException(this.messageSource.getMessage(
                    "security.auth.errors.jwt.token.expired.or.invalid", null, Locale.getDefault()
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
