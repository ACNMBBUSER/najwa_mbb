package com.userprofile.profile.service;

import com.userprofile.profile.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    //Secret Key used in symmetric(used single key) encryption. To encode and decode token
    private String SECRET_KEY = "4bb0ff1faee16c7671340f3af15dfae6e54e34726a1e6ea7ac8de9bba9b51a2c";

    //Extracts the username (subject) from the JWT token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Validates whether the given token is valid for the provided user details.
    // It checks if the username extracted from the token matches the username of the given UserDetails object
    // and if the token is not expired.
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExprired(token);
    }

    //Checks if the provided token is expired by comparing its expiration date with the current date.
    private boolean isTokenExprired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Extracts the expiration date from the JWT token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims:: getExpiration);
    }

    //Generic method to extract a specific claim (information) from the JWT token
    // using the provided resolver function.
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    //Parses and verifies the JWT token, then extracts all claims from it
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Generates a new JWT token for the given User object.
    // It sets the subject (username), issue time, expiration time (24 hours from the current time),
    // and signs the token using the signing key
    public String generateToken(User user) {
        String token = Jwts
                .builder() //Returns a new JwtBuilder instance that can be configured and then used to create JWT compact serialized strings.
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000)).
                signWith(getSigninKey())
                .compact();

        return token;
    }

    //Retrieves the secret key used for signing the JWT token. The key is decoded from a base64 encoded string
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
