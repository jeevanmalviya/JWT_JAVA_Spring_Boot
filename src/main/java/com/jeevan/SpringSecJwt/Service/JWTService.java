package com.jeevan.SpringSecJwt.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretKey = "";

    public JWTService(){
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("hmacSHA256");
            SecretKey sk =keyGen.generateKey();
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 300))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte [] byteArrayofKey = Decoders.BASE64.decode((secretKey));
        return Keys.hmacShaKeyFor(byteArrayofKey);
    }


    // we have to extract the userName, but before extracting the Username, extract claims
    public String extractUsername(String token) {
        //extract userName from jwt token
        return extractClaim(token , Claims::getSubject);
    }

    // in this extracting all the claims
    private <T> T extractClaim(String token, Function<Claims , T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return  claimResolver.apply(claims);
    }

    // extarct the claims with the help of signing key
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, String username) {
        final String userName =extractUsername(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
