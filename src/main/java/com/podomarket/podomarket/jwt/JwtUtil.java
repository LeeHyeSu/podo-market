package com.podomarket.podomarket.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("jwtService")
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private String createToken(Map<String, Object> claims) {
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .signWith(key)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000 * 60 * 60 * 24))
                .compact();
    }

    private Claims extractAllClaims(String token) {
        if (StringUtils.isEmpty(token)) return null;

        String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder().setSigningKey(secretKeyEncodeBase64).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            claims = null;
        }

        return claims;
    }

    public String extractMemberId(String token) {
        final Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        else return claims.get("memberId", String.class);
    }

    public String generateToken(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);
        return createToken(claims);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
