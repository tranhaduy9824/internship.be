package com.hnv.api.main;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hnv.api.def.DefAPI;
import com.hnv.api.service.main.ServiceAPILoginCheck;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.db.aut.TaAutUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class SprB_FilterRequest extends OncePerRequestFilter {

	@Autowired
	private ServiceAPILoginCheck svAut;

	@Autowired
	private SprB_Token token;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String uName 	= null;
		String tokStr 	= null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			tokStr = requestTokenHeader.substring(7);
			try {
				uName = token.reqUsername(tokStr);
			} catch (IllegalArgumentException e) {
				ToolLogServer.doLogErr("---JWT Token:  unable to get");
			} catch (ExpiredJwtException e) {
				ToolLogServer.doLogErr("---JWT Token:  expired");
			} catch (MalformedJwtException e) {
				ToolLogServer.doLogErr("---JWT Token:  malformed");
			} catch (SignatureException e) {
				ToolLogServer.doLogErr("---JWT Token:  signature does not match locally computed signature");
			} 
			
			if (uName==null){
				try {
					API.doResponse(response, DefAPI.API_MSG_ERR_AUTHEN);
					return;
				}catch(Exception e) {
				}
			}
		} else {
			ToolLogServer.doLogErr("---JWT Token: does not begin with Bearer String");
		}

		//Once we get the token validate it.
		if (uName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.svAut.loadUserByUsername(uName);

			// if token is valid configure Spring Security to manually set authentication
			if (token.canValidateToken(tokStr, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		//------------------------------------------------------------------------
		if (uName != null) {
			final TaAutUser uInf = ServiceAPILoginCheck.reqAutUser(uName);
			request.setAttribute ("userInfo", uInf);
		}
		
		//------------------------------------------------------------------------
		chain.doFilter(request, response);
	}

}
