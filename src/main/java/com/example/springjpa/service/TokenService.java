package com.example.springjpa.service;

import com.example.springjpa.domain.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TokenService {

  private final JwtEncoder jwtEncoder;

  public TokenService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(Authentication authentication) {
    return genericTokenGenerator(authentication.getAuthorities(), authentication.getName());
  }

  public String generateToken(SecurityUser securityUser) {
    return genericTokenGenerator(securityUser.getAuthorities(), securityUser.getUsername());
  }

  private String genericTokenGenerator(Collection<? extends GrantedAuthority> authorities, String username) {
    Instant now = Instant.now();
    String scope = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("Andrew's modest sized Library")
            .issuedAt(now)
            .expiresAt(now.plus(2, ChronoUnit.HOURS))
            .subject(username)
            .claim("scope", scope)
            .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
