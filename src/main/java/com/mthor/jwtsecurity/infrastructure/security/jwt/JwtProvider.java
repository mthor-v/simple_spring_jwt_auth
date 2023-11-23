package com.mthor.jwtsecurity.infrastructure.security.jwt;

import com.mthor.jwtsecurity.domain.entities.MainUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        MainUser mainUser = (MainUser) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = mainUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles",roles);
        return Jwts.builder().setSubject(mainUser.getUsername())
                .signWith(SignatureAlgorithm.HS512,secret)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .addClaims(claims)
                .compact();
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            logger.error("Malformed token.");
            logger.error(e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported token.");
            logger.error(e.getMessage());
        } catch (ExpiredJwtException e){
            logger.error("Expired token.");
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("Empty token.");
            logger.error(e.getMessage());
        } catch (SignatureException e){
            logger.error("Wrong signature.");
            logger.error(e.getMessage());
        }
        return false;
    }

}
