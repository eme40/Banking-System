package com.eric.World.Banking.app.infrastructure.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;



@Component
public class JwtTokenProvider {
  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app.jwt-expiration}")
  private Long jwtExpirationDate;

  // To Generate Token
  public String generateToken(Authentication authentication){
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(key())
            .compact();
  }

  private Key key() {
    byte[] bytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(bytes);
  }

  // This method help us get the name associated with the token
  public String getUserName(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getPayload();
    return claims.getSubject();
  }

  public boolean validateToken(String token){
    try{
      Jwts.parser()
              .setSigningKey(key())
              .build()
              .parse(token);
      return true;

    } catch (ExpiredJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }
}
