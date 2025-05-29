package com.hnv.api.service.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.hnv.api.def.DefTime;
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolString;
import com.hnv.common.util.CacheData;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

@Service
public class ServiceAPILoginCheck implements UserDetailsService {
	public static String	SIGNIN_SN					= "SN"; //synapse
	public static String	SIGNIN_BE					= "BE";
	public static String	SIGNIN_GG					= "GG";
	public static String	SIGNIN_FB					= "FB";
	
	public static int MAX_ATTEMPT					  	= 600;
	public static int NB_MAX					  		= 10000;
	public static CacheData<Integer> 	attemptsCache 	= new CacheData<Integer>(NB_MAX, DefTime.TIME_00_10_00_000, DefTime.TIME_00_05_00_000);
	
	private static String 				SECU_SALT	  	= "";
	public static BCryptPasswordEncoder bcryptEncoder 	= new BCryptPasswordEncoder();
	static {
		attemptsCache.doCheckTimeAuto(DefTime.TIME_00_05_00_000);
		
		try {
			//----Firebase initialize
//			String 			path 		= Thread.currentThread().getContextClassLoader().getResource(".").getPath() + "/zenzobs-firebase.json";
//			FileInputStream svAccount 	= new FileInputStream(path);
//
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(svAccount))
//					.build();
//			FirebaseApp.initializeApp(options);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String  reqSecuSalt (){
		return SECU_SALT;
	}
	
	public static boolean canIPLocked (HttpServletRequest request) {
		String 	ip 			= API.reqClientIP (request);
		return canIPLocked (ip);
	}
	
	public static boolean canIPLocked (String ip) {
		Integer attempts = attemptsCache.reqData(ip);
		if (attempts==null) return false;
		if (attempts>=MAX_ATTEMPT) {
			return true;
		}
		return false;
	}
	
	public static void doIPAttemp (HttpServletRequest request) {
		String 	ip 			= API.reqClientIP (request);
		Integer attempts 	= attemptsCache.reqData(ip);
		if (attempts==null) attempts = 0;
		attemptsCache.reqPut(ip, attempts+1);
	}
	
	//-----------------------------------------------------------------------------------------
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = reqAutUserPassword(username);
		if (password!=null) {
			User sprUser = new User(username, password,	new ArrayList<>());
			return sprUser;
		}else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	private static CacheData<TaAutUser> 	cache_dbUser	= new CacheData<TaAutUser> 	(500, DefTime.TIME_00_30_00_000);
	private static CacheData<String> 		cache_uTok		= new CacheData<String> 	(500, DefTime.TIME_00_30_00_000);
	
	private static Hashtable<String,String> cache_Pass		= new Hashtable<String,String>();
	
	public static String reqAutUserPassword(String uName) {
		try {
			//--for test ------------------------------------------------------------------
			//password = ToolString.reqHashString(ToolString.SHA_256, password);
			//password = ToolString.reqHashString(ToolString.SHA_256, password + salt);
			//-----------------------------------------------------------------------------
			if (uName==null) return null;
			uName = uName.toLowerCase();
			
			StringTokenizer token 		= new StringTokenizer (uName, " ");
			String 			passSalt 	= "";
			String 			login		= uName;
			String			method		= "BE";
			
			if (token.countTokens()>1) {
				login 		= token.nextToken();//login = auth_code
				login		= login.toLowerCase();
				passSalt	= token.nextToken();
				method		= token.nextToken();
			}
			
			//------check cache first-----------------------------------------------------
			String		dbPass 	= cache_Pass.get(login);
			TaAutUser 	user 	= null; 
			if (dbPass!=null) {
				user = cache_dbUser.reqData(login);
				//---cache contains user 
				if (user!=null) 
					return dbPass;
				else 
					cache_Pass.remove(login);
				
//				user = cache_dbUser.reqCheckIfOld(login);
//				if (user!=null) 
//					cache_Pass.remove(login);
//				else 
//					return dbPass;
			}
			
			//--Nothing in cache then ---------------------------------------------------------------------------
			if (method.equals(SIGNIN_BE)){
				Criterion cri 	= Restrictions.and(	
						Restrictions.eq(TaAutUser.ATT_T_LOGIN_01, login),
						Restrictions.eq(TaAutUser.ATT_I_STATUS	, TaAutUser.STAT_ACTIVE),
						Restrictions.or(
								Restrictions.isNull	(TaAutUser.ATT_D_DATE_03), 
								Restrictions.lt		(TaAutUser.ATT_D_DATE_03, new Date())),
						Restrictions.or(
								Restrictions.isNull	(TaAutUser.ATT_D_DATE_04), 
								Restrictions.gt		(TaAutUser.ATT_D_DATE_04, new Date()))
						);

				List<TaAutUser>users = TaAutUser.DAO.reqList(cri);
				if(users != null && users.size()>0) {
					user 	= users.get(0);
					dbPass 	= (String)user.req(TaAutUser.ATT_T_PASS_01).toString();

					if (passSalt.length()>0)
						dbPass = ToolString.reqHashString(ToolString.SHA_256, dbPass + passSalt);

					dbPass 	= bcryptEncoder.encode(dbPass);

					user. doHidePwd();

					cache_dbUser.reqPut	(login, user);
					cache_Pass	.put	(login, dbPass);

					return 		dbPass;		
				}

			}else if (method.equals(SIGNIN_GG)){
				//----sign-on with google account
				String 		idTok	= login;
				UserRecord  record	= reqUserRecord(idTok);
				if (record==null) return "";
				
				user				= reqCheckAccountGG (record);
				if (user!=null){
					idTok 	= bcryptEncoder.encode(idTok);
					user. doHidePwd();

					cache_dbUser.reqPut	(login, user);
					cache_Pass	.put	(login, idTok);
					
					return 		idTok;	
				}
			}
			
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
		return null;
	}

	public static TaAutUser reqAutUser(String uName) {
		if (uName==null) return null;
		uName = uName.toLowerCase();
		
		TaAutUser u = cache_dbUser.reqData(uName);
		try{
			if (u!=null) {
				u.doBuilAuths(false);
//				u.doBuildManager();
				u.doBuildAvatar(false);
				u.doBuildPerson(false);
			}
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
		return u;
	}
	
	public static TaAutUser reqAutUserAndSaveToken(String uName, String authTok) {
		if (uName==null) return null;
		uName = uName.toLowerCase();
		
		TaAutUser u = cache_dbUser.reqData(uName);
		try{
			if (u!=null) {
				u.doBuilAuths(false);
//				u.doBuildManager();
				u.doBuildAvatar(false);
				u.doBuildPerson(false);
				u.doBuildHistoryConnection(false);
				
				if (authTok!=null) cache_uTok.reqPut(uName, authTok);
			}
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
		return u;
	}
	
	public static TaAutUser reqAutUserWithToken(String uName, String authTok) {
		if (uName==null) return null;
		uName = uName.toLowerCase();
		
		String	tok = cache_uTok.reqData(uName);
		if (tok==null || !tok.equals(authTok)) return null;
		
		TaAutUser u = cache_dbUser.reqData(uName);
		try{
			if (u!=null) {
				u.doBuilAuths(false);
//				u.doBuildManager();
				u.doBuildAvatar(false);
				u.doBuildPerson(false);
			}
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
		return u;
	}

	//----------------------------------------------------------------------------------------------------
	private static UserRecord reqUserRecord(String idToken) {
		// [START verify_id_token_check_revoked]
		try {
			boolean checkRevoked = true;
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken, checkRevoked);
			// Token is valid and not revoked.
			String uid = decodedToken.getUid();
			UserRecord user = FirebaseAuth.getInstance().getUser(uid);
			return user;
		} catch (Exception e) {
			return null;
		}
		// [END verify_id_token_check_revoked]
	}
	
	private static TaAutUser reqCheckAccountGG(UserRecord  record) throws Exception {
		Criterion cri 	= Restrictions.eq(TaAutUser.ATT_T_LOGIN_02, record.getUid());
	
		List<TaAutUser> users = TaAutUser.DAO.reqList(cri);
		if(users != null && users.size()>0) {
			TaAutUser  ent = users.get(0);
			//--prevent if user is locked by adm
			if (!ent.reqInt(TaAutUser.ATT_I_STATUS).equals(TaAutUser.STAT_ACTIVE)) return null;
			return 	ent;		
		}else {
			TaAutUser ent	= TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_INFO_01, record.getEmail());
			if (ent==null){
				return reqNewGG (record);
			}
			
			//--prevent if user is locked by adm => create new
			if (!ent.reqInt(TaAutUser.ATT_I_STATUS).equals(TaAutUser.STAT_ACTIVE)) return reqNewGG (record);
			
			//---exist email not login02
			return reqModGG (record, ent);
		}
	}
	
	private static TaAutUser reqNewGG(UserRecord obj) throws Exception {
		String 		login02		= obj.getUid();
		String 		pass02 		= obj.getProviderId();
		String 		email 		= obj.getEmail();
		String		tel			= obj.getPhoneNumber();
		
		String 		name01		= obj.getDisplayName();
		String 		path01		= obj.getPhotoUrl();
		
		Date 		now 		= new Date();
		//Create per
		Map<String, Object> attrPer = new HashMap(); 
		attrPer.put(TaPerPerson.ATT_D_DATE_01		, now);
		attrPer.put(TaPerPerson.ATT_D_DATE_02		, null);
		attrPer.put(TaPerPerson.ATT_T_NAME_01		, name01);
		attrPer.put(TaPerPerson.ATT_I_STATUS_01		, TaPerPerson.STAT_01_ACTIVE_LV_01);
		attrPer.put(TaPerPerson.ATT_I_PER_MANAGER	, 1);

		TaPerPerson entPer = new TaPerPerson(attrPer);
		TaPerPerson.DAO.doPersist(entPer);
				
		Map<String, Object> attrUsr = new HashMap();
		attrUsr.put(TaAutUser.ATT_T_LOGIN_02	, login02);
		attrUsr.put(TaAutUser.ATT_T_PASS_02		, pass02);
		attrUsr.put(TaAutUser.ATT_T_INFO_01		, email);
		attrUsr.put(TaAutUser.ATT_T_INFO_02		, tel);
		
		
		attrUsr.put(TaAutUser.ATT_D_DATE_01, now);
		attrUsr.put(TaAutUser.ATT_D_DATE_02, null);
		attrUsr.put(TaAutUser.ATT_D_DATE_03, now);
		attrUsr.put(TaAutUser.ATT_D_DATE_04, null);
		attrUsr.put(TaAutUser.ATT_I_STATUS, TaAutUser.STAT_ACTIVE);
		attrUsr.put(TaAutUser.ATT_I_TYPE_01, TaAutUser.TYPE_01_AGENT);
		
		attrUsr.put(TaAutUser.ATT_I_PER_PERSON, entPer.reqId());
		
		TaAutUser ent = new TaAutUser(attrUsr);
		TaAutUser.DAO.doPersist(ent);
		
		Integer entId = ent.reqId();
		ent.reqSet(TaAutUser.ATT_O_PER_PERSON, entPer);
		
		//Create avatar
		File 	file 			= new File(path01);
		Double 	filesize 		= file.length() * 1.0;
		TaTpyDocument doc		= new TaTpyDocument(
				TaTpyDocument.TYPE_01_FILE_MEDIA, 
				TaTpyDocument.TYPE_02_FILE_IMG_AVATAR, 
				TaTpyDocument.TYPE_03_PUBLIC, 
				filesize, 
				"GG_Avatar", 
				path01, path01, null, 
				new Date(), 
				entId, 
				DefDBExt.ID_TA_AUT_USER, -1, 
				TaTpyDocument.STAT_VALIDATED, 0);
		TaTpyDocument.DAO.doPersist(doc);
		ent.reqSet(TaAutUser.ATT_O_AVATAR, doc);
		return ent;
	}
	
	private static TaAutUser reqModGG(UserRecord obj, TaAutUser	ent) throws Exception {
		String 		login02		= obj.getUid();
		String 		pass02 		= obj.getProviderId();
		String 		email 		= obj.getEmail();
		String		tel			= obj.getPhoneNumber();
		
		Map<String, Object> attrUsr = new HashMap();
		attrUsr.put(TaAutUser.ATT_T_LOGIN_02	, login02);
		attrUsr.put(TaAutUser.ATT_T_PASS_02		, pass02);
		attrUsr.put(TaAutUser.ATT_I_STATUS		, TaAutUser.STAT_ACTIVE);
		attrUsr.put(TaAutUser.ATT_T_INFO_01		, email);
		attrUsr.put(TaAutUser.ATT_T_INFO_02		, tel);
		
		TaAutUser.DAO.doMerge(ent, attrUsr);
		return ent;
	}
	
	//--------------------------------------------------------------------------------------------
	public static void doRemoveFromCache (String login) {
		if (login==null) return;
		login	= login.toLowerCase();
		
		cache_dbUser.reqDel(login);
		cache_Pass.remove(login);
		cache_uTok.reqDel(login);
	}

}