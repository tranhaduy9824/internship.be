package com.hnv.api.service.main;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.main.API;
import com.hnv.api.main.SprB_Token;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolString;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutHistory;
import com.hnv.db.aut.TaAutUser;
import com.hnv.def.DefAPIExt;

/**
 * NVu.Hoang - Rin
 */

@RestController
@CrossOrigin
@RequestMapping(value = DefAPIExt.URL_API_LOGIN)
public class ServiceAPILogin {
	private static BCryptPasswordEncoder 		bcryptEncoder 	= new BCryptPasswordEncoder();
	
	@Autowired
	private AuthenticationManager authenManager; 

	@Autowired
	private SprB_Token token;

	@RequestMapping(method = RequestMethod.POST)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws Exception {
		//----check locked IP------------------------------------------------
		if (ServiceAPILoginCheck.canIPLocked(request)) {
			throw new Exception("USER_DISABLED");
		}
		
		//-------------------------------------------------------------------
		JSONObject 		json 		= API.reqJson(request);
		
		String			uName		= ToolData.reqStr(json, DefJS.USER_NAME	, ""); //uid GG/FB
		String			uPass		= ToolData.reqStr(json, DefJS.USER_PASS	, uName); //idToken
//		ref["with_salt"]	= 0;
//		ref["user_name"] 	= login;
//		ref["user_pass"] 	= pass_hash;
//		ref["user_salt"] 	= salt;
//		ref["with_hash"] 	= 1;
	
		String			method		= ToolData.reqStr(json, "method"   	, "BE"); //SN, GG, FB, BE (backEnd)
		Integer			withHash	= ToolData.reqInt(json, "with_hash"	, 1);
		Integer			withSalt	= ToolData.reqInt(json, "with_salt"	, 0);
		String			uSalt		= ToolData.reqStr(json, "user_salt"	, "0");
		doAuthenticate (request, uName, uPass, withHash, withSalt, uSalt, method);
		
		final String 	tokenStr 	= token.reqToken(uName);
		final TaAutUser uInf		= ServiceAPILoginCheck.reqAutUserAndSaveToken(uName, tokenStr);
		
		if (uInf==null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		
		API.doResponse(response, 
				ToolJSON.reqJSonString(
						DefJS.SESS_STAT	, 1, 
						DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
						DefJS.USER_TOK	, tokenStr,
						DefJS.RES_DATA	, uInf
						));
		
		//-----save conn history
		try {
			String 			ip 		= API.reqAddressIP(request);
			TaAutHistory 	conn 	= new TaAutHistory(uInf.reqId(), TaAutHistory.TYPE_CONN, new Date(), ip);
			TaAutHistory.DAO.doPersist(conn);
		}catch(Exception e){
			e.printStackTrace();
		}
	} 

	private void doAuthenticate(HttpServletRequest request, String username, String password, Integer withHash, Integer withSalt, String salt, String method) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		if (!method.equals(ServiceAPILoginCheck.SIGNIN_BE)) {
			//--keep idToken and push idToken to username
			username = username + " " + salt + " " + method;
		} else {
			if (withHash==0) password = ToolString.reqHashString(ToolString.SHA_256, password);
			if (withSalt==1) username = username + " " + salt + " " + method;
		}
		
		try {
			
			authenManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			//----> call ServiceAPILoginCheck
		} catch (DisabledException e) {
			ServiceAPILoginCheck.doIPAttemp(request);
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			ServiceAPILoginCheck.doIPAttemp(request);
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
}
