package com.override.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret.path}")
    private String keyStorePath;

    @Value("${jwt.secret.password}")
    private String keyStorePassword;

    @Value("${jwt.secret.alias}")
    private String keyAlias;


    @SneakyThrows
    public String generateToken(String login, Collection<? extends GrantedAuthority> authorityList) {
        String authorities = authorityList.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", login);
        claims.put("authorities", authorities);
        Key key = keyStore().getKey(keyAlias, keyStorePassword.toCharArray());
        return Jwts.builder()
                .setSubject(login)
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.RS256, key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(keyStore().getKey(keyAlias, keyStorePassword.toCharArray())).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("invalid token");
        }
        return false;
    }

    @SneakyThrows
    public String getLoginFromToken(String token) {
        return (String) Jwts.parser().setSigningKey(keyStore()
                .getKey(keyAlias, keyStorePassword.toCharArray()))
                .parseClaimsJws(token)
                .getBody()
                .get("username");
    }

    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Unable to load keystore: {}", keyStorePath, e);
        }

        throw new IllegalArgumentException("Unable to load keystore");
    }
}