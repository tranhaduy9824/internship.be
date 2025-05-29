package com.hnv.api.main;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SprB_Token implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long TIME_VALIDITY = 1*60*60*1000;

	private static String secret = "hnv2005";

	static {
		Date d = new Date();
		secret = secret + d.getTime();
	}
	
	public String reqUsername(String token) {
		return reqClaim(token, Claims::getSubject);
	}

	public Date reqIssuedAtDate(String token) {
		return reqClaim(token, Claims::getIssuedAt);
	}

	public Date reqExpirationDate(String token) {
		return reqClaim(token, Claims::getExpiration);
	}

	public <T> T reqClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = reqAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims reqAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean canBeExpired(String token) {
		final Date expiration = reqExpirationDate(token);
		return expiration.before(new Date());
	}

	private Boolean canIgnoreExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String reqToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return reqToken(claims, userName);
	}

	private String reqToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TIME_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canBeRefreshed(String token) {
		return (!canBeExpired(token) || canIgnoreExpiration(token));
	}

	public Boolean canValidateToken(String token, UserDetails userDetails) {
		final String username = reqUsername(token);
		return (username.equals(userDetails.getUsername()) && !canBeExpired(token));
	}
}
