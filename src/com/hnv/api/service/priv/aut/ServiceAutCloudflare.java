package com.hnv.api.service.priv.aut;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.def.DefTime;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolURL;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.def.DefAPIExt;
import com.hnv.def.DefDBExt;

/**
 * ----- ServiceNsoPost by H&V
 * ----- Copyright 2017------------
 */
public class ServiceAutCloudflare implements IService {
	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 					= "EC_V3".toLowerCase();

	public static final String SV_CLASS 					= "ServiceAutCloudflare".toLowerCase();	

	
	public static final String SV_GET_RTC 					= "SVGetRTC".toLowerCase();
	
	public static final Integer	ENT_TYP						= DefDBExt.ID_TA_AUT_USER;
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutCloudflare(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	
	
	//-----------------------------------------------------------------------------------------------
	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		
		try {
			//---------------------------------------------------------------------------------

			if(sv.equals(SV_GET_RTC)) {
				doGetRTC(user, json, response);
				
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}
		} catch (Exception e) {
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			ToolLogServer.doLogErr("-----Exception in SVAutUser-----:" + e.getMessage());
			ToolLogServer.doLogErr(e, true);
//			e.printStackTrace();
		}
	}
	//---------------------------------------------------------------------------------------------------------
	private static final int WORK_GET 	= 1;
	private static final int WORK_LST 	= 2;
	private static final int WORK_NEW 	= 3;
	private static final int WORK_MOD 	= 4;
	private static final int WORK_DEL 	= 5;
	private static final int WORK_UPL 	= 10; //upload

	private static boolean canWorkWithObj ( TaAutUser user, int typWork, Object...params){
		if (user.canBeSuperAdmin()) return true;
		
		switch(typWork){
		case WORK_GET : 
			//check something with params
			return true;
		case WORK_LST : 
			//check something with params
			return true;
		case WORK_NEW : 
			//check something with params
			return true;
		case WORK_MOD : 
			//check something with params
			return true;	
		case WORK_DEL : 
			if (params==null || params.length<=0) return false; 
//			TaAutUser post 	= (TaAutUser) params[0];
//			Integer   uId 	= post.reqInt(post, TaAutUser.ATT_I_AUT_USER_NEW);
//			if (uId!=null && uId.equals(user.reqUserId())) return true;
//			return false;
			
			return true;
		case WORK_UPL : 
			//check something with params
			return true;
		}
		return false;
	}
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static int						ttl			= DefTime.TIME_10_00_00_000 * 2;//20h
	private static String					cacheKey	= "hnv";
	private static CacheData<String> 		cache 		= new CacheData<String>		(500, ttl);

	
	private static void doGetRTC(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		ToolURL.doDesactivateSSLCheck();
		
		String cont = cache.reqData(cacheKey);
		
		if (cont==null){
			cont = reqResponse(DefAPIExt.RTC_CFG_01, DefAPIExt.RTC_CFG_02, DefAPIExt.RTC_CFG_03, null, 0);
			if (cont!=null) cache.reqPut(cacheKey, cont);
		}
		
		if (cont==null)
			API.doResponse(response,DefAPI.API_MSG_KO);
		else
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, cont
					));
	}
	

	public static String reqResponse (String cfgURL, String cfgHeader, String cfgCont, String 	proxy_url, Integer proxy_port) throws Exception{
		try{
			String cont = reqHttpsRequest("POST", cfgURL, cfgHeader, cfgCont, proxy_url, proxy_port, null, null);
			return cont;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static String reqHttpsRequest(String method, 
			String https_url, String https_header, String params,
			String proxy_url, int proxy_port, String proxy_uname, String proxy_pwd) throws Exception {

		HttpsURLConnection conn = reqConn (https_url, proxy_url, proxy_port, proxy_uname, proxy_pwd);
		
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod	(method!=null?method:"POST");
		
		if (https_header!=null) {
			String[] headers = https_header.split(";");
			if (headers.length>0) {
				for (String head:headers) {
					String[] tok = head.split(":");
					if (tok.length>=2) {
						conn.setRequestProperty(tok[0],tok[1]);
					}
				}
			}
		}
		
		conn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		
		if (params!=null && params.length()>0) {
			conn.setDoOutput(true);
			try (OutputStream os = conn.getOutputStream()) {
			    byte[] input = params.getBytes("utf-8");
			    os.write(input, 0, input.length);
			}
		}
		
		
		int responseCode = conn.getResponseCode();
		if (responseCode==200||responseCode==201) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}
			in.close();
			return (response.toString());
		} else {
			return "Err " + responseCode;
		}
	}

	private static HttpsURLConnection reqConn(final String https_url, final String proxyName, final int port, String uname, String pwd) {
		try {
			URL  url = new URL(https_url);
			HttpsURLConnection conn;
			if (proxyName==null||proxyName.trim().length()==0) {  
				conn = (HttpsURLConnection) url.openConnection();
			} else {                
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyName, port));
				conn = (HttpsURLConnection) url.openConnection(proxy);
				if (uname!=null&&pwd!=null) {
					Authenticator authenticator = new Authenticator() {
						public PasswordAuthentication getPasswordAuthentication() {
							return (new PasswordAuthentication(uname, pwd.toCharArray()));
						}
					};
					Authenticator.setDefault(authenticator);
				}
			}

			return conn;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String []a) throws Exception{
		ToolURL.doDesactivateSSLCheck();
		String 	proxy_url 	= null;
		Integer proxy_port	= 0;
		String 	params 		= "{\"ttl\": 86400}";
		String 	https_url 	= "https://rtc.live.cloudflare.com/v1/turn/keys/cdf9cd479e048676934ebbbd8ba35cee/credentials/generate";
		String  https_header= "Authorization:Bearer a5b2a96852c26d3b8c12d876a3f66c6a887ce54b5480efef1d3f44042eb9720c;"
				+ "Content-Type:application/json;"
				+ "User-Agent:Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2;"
				+ "charset:utf-8";
		
		
//		String certificatesTrustStorePath = "C:\\WS\\eclipse\\jre\\lib\\security\\cacerts";
//		System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//		
//		System.setProperty("javax.net.ssl.keyStore", certificatesTrustStorePath);
//		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");

		
		String cont = reqHttpsRequest("POST", https_url, https_header, params, proxy_url, proxy_port, null, null);
		System.out.println(cont);
	}
}
