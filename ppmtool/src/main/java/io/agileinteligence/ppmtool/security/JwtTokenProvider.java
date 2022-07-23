package io.agileinteligence.ppmtool.security;

import static io.agileinteligence.ppmtool.security.SecurityConstants.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.agileinteligence.ppmtool.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication auth) {

	User user = (User) auth.getPrincipal();
	Date now = new Date(System.currentTimeMillis());
	Date expriyDate = new Date(now.getTime() + EXPIRATION_TIME);
	String userId = Long.toString(user.getId());

	Map<String, Object> claims = new HashMap<>();
	claims.put("id", userId);
	claims.put("username", user.getUsername());
	claims.put("fullName", user.getFullName());

	return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expriyDate)
		.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token) {

	try {
	    Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
	    return true;
	} catch (SignatureException e) {
	    System.out.println("Invalid JWT signature");
	} catch (MalformedJwtException e) {
	    System.out.println("Invalid JWT token");
	} catch (ExpiredJwtException e) {
	    System.out.println("Expired JWT token");
	} catch (JwtException e) {
	    System.out.println("Unsupported JWT token");
	} catch (IllegalArgumentException e) {
	    System.out.println("JWT claims string is empty");
	}
	return false;
    }

    public Long getUserIdFromJWT(String token) {

	Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	String id = (String) claims.get("id");

	return Long.parseLong(id);
    }

}
