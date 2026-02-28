package org.shalash.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET =
            "my-super-secret-key-my-super-secret-key";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
        System.out.println("HIII");
        if (((int)(Math.random()*100))%2 == 0) {
            System.out.println("USER");
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", "USER")
                    .claim("id", "1")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        else if (((int)(Math.random()*100))%2 == 1) {
            System.out.println("SUPERADMIN");
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role", "SUPERADMIN")
                    .claim("id", "2")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        return Jwts.builder()
                .setSubject(username)
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
