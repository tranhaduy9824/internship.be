package com.hnv.api.service.publ.aut;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.def.DefTime;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolDate;
import com.hnv.common.tool.ToolEmail;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolString;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.cfg.TaCfgValue;
import com.hnv.db.per.TaPerPerson;
import com.hnv.def.DefDBExt;
import com.hnv.process.ThreadManager;

/**
 * ----- ServiceNsoPost by H&V ----- Copyright 2017------------
 */
public class ServiceAutUserPubl implements IService {
	// --------------------------------Service
	// Definition----------------------------------
	public static final String  SV_MODULE        = "EC_V3".toLowerCase();

	public static final String  SV_CLASS         = "ServiceAutUserPubl".toLowerCase();

	public static final String  SV_GET           = "SVGet".toLowerCase();
	public static final String  SV_LST           = "SVLst".toLowerCase();
	public static final String  SV_LST_DYN       = "SVLstDyn".toLowerCase();

	public static final String  SV_NEW           = "SVNew".toLowerCase();
	public static final String 	SV_VALIDATION 	 = "SVValidation".toLowerCase();
	
	public static final String  SV_GET_GUEST     = "SVGetUserProfile_Guest".toLowerCase();

	public static final String  SV_GET_FILE      = "SVGetFile".toLowerCase();
	
	public static final String 	SV_PWD_MOD 			= "SVPwdMod"				.toLowerCase();
	public static final String 	SV_PWD_RESET_REQ	= "SVPwdResetReq"			.toLowerCase();
	public static final String 	SV_PWD_RESET 		= "SVPwdReset"				.toLowerCase();

	
	
	
	public static final Integer ENT_TYP          = DefDBExt.ID_TA_AUT_USER;

	// -----------------------------------------------------------------------------------------------
	// -------------------------Default Constructor - Required
	// -------------------------------------
	public ServiceAutUserPubl() {
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	// -----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String    sv   = API.reqSVFunctName(json);
		TaAutUser user = (TaAutUser) json.get("userInfo");

		try {
			// ---------------------------------------------------------------------------------

			if (sv.equals(SV_GET)) {
				doGet(user, json, response);
			} else if (sv.equals(SV_LST)) {
				doLst(user, json, response);
			} else if (sv.equals(SV_LST_DYN)) {
				doLstDyn(user, json, response);
			} else if (sv.equals(SV_GET_GUEST)) {
				doGetGuest(json, response);
			
			} else if (sv.equals(SV_NEW)) {
				doNew(json, response);
				
			}else if (sv.equals(SV_VALIDATION)){
				doValidation(json, response);

			} else if (sv.equals(SV_PWD_MOD)){
				doPwdMod(json, response);
			} else if (sv.equals(SV_PWD_RESET)){
				doPwdReset(json, response);
			} else if (sv.equals(SV_PWD_RESET_REQ)){
				doPwdResetReq(json, response);
			}
		} catch (Exception e) {
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			e.printStackTrace();
		}
	}
	// ---------------------------------------------------------------------------------------------------------

	private static final int WORK_GET = 1;
	private static final int WORK_LST = 2;
	private static final int WORK_NEW = 3;
	private static final int WORK_MOD = 4;
	private static final int WORK_DEL = 5;
	private static final int WORK_UPL = 10; // upload

	private static boolean canWorkWithObj(TaAutUser user, int typWork, Object... params) {
		if (user.canBeSuperAdmin())
			return true;

		switch (typWork) {
		case WORK_GET:
			// check something with params
			return true;
		case WORK_LST:
			// check something with params
			return true;
		case WORK_NEW:
			// check something with params
			return true;
		case WORK_MOD:
			// check something with params
			return true;
		case WORK_DEL:
			if (params == null || params.length <= 0)
				return false;
//			TaAutUser post 	= (TaAutUser) params[0];
//			Integer   uId 	= post.reqInt(post, TaAutUser.ATT_I_AUT_USER_NEW);
//			if (uId!=null && uId.equals(user.reqUserId())) return true;
//			return false;

			return true;
		case WORK_UPL:
			// check something with params
			return true;
		}
		return false;
	}

	// ---------------------------------------------------------------------------------------------------------
	private static Set<String> filter = new HashSet<String>();
	static {
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_01);
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_02);
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_03);
		
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_01);
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_02);
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_03);
	}

	// ---------------------------------------------------------------------------------------------------------
	private static void doGet(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer entId  = ToolData.reqInt(json, "id", -1);
		Boolean forced = ToolData.reqBool(json, "forced", true);
//		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaAutUser ent = reqGet(entId, forced);

		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)) {
			API.doResponse(response,
					ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_ERR_RIGHT));
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(filter, DefJS.SESS_STAT, 1, DefJS.SV_CODE,
				DefAPI.SV_CODE_API_YES, DefJS.RES_DATA, ent));
	}

	private static CacheData<TaAutUser> cache_entity = new CacheData<TaAutUser>(500, DefTime.TIME_24_00_00_000);

	public static TaAutUser reqGet(Integer entId, Boolean forced) throws Exception {
		String    key = entId + "";
		TaAutUser ent = cache_entity.reqData(key);

		if (forced || ent == null) {
			ent = TaAutUser.DAO.reqEntityByRef(entId);
			if (ent != null) {
				// ---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		} else {
			ToolLogServer.doLogInf("---reqNsoAreaGet use cache-----");
			cache_entity.reqCheckIfOld(key); // cache in 20 hour
		}

		return ent;
	}

	// ---------------------------------------------------------------------------------------------------------
	public static void doGetGuest(JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGetGuest --------------");

		Integer entId  = ToolData.reqInt(json, "id", -1);
		Boolean forced = ToolData.reqBool(json, "forced", true);

		TaAutUser ent = reqGetGuest(entId, forced);

		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(filter, DefJS.SESS_STAT, 1, DefJS.SV_CODE,
				DefAPI.SV_CODE_API_YES, DefJS.RES_DATA, ent));
	}

	public static TaAutUser reqGetGuest(Integer entId, Boolean forced) throws Exception {
		String    key = entId + "";
		TaAutUser ent = cache_entity.reqData(key);

		if (forced || ent == null) {
			ent = TaAutUser.DAO.reqEntityByRef(entId);
			if (ent != null) {
				// ---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		} else {
			ToolLogServer.doLogInf("---reqNsoAreaGet use cache-----");
			cache_entity.reqCheckIfOld(key); // cache in 20 hour
		}

		return ent;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViAutUserDyn> list = reqLst(json); // and other params if necessary
		if (list == null ) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(filter, DefJS.SESS_STAT, 1, DefJS.SV_CODE,
				DefAPI.SV_CODE_API_YES, DefJS.RES_DATA, list));
	}

	private static List<ViAutUserDyn> reqLst(JSONObject json) throws Exception {
		Integer      nbLine    = ToolData.reqInt(json, "nbLine", 10);
		Set<String>  searchkey = ToolData.reqSetStr(json, "searchkey", null);
		Set<Integer> stat      = ToolData.reqSetInt(json, "stat", null);
		Set<Integer> typ02     = ToolData.reqSetInt(json, "typ02", null);

		if (typ02 == null && stat == null) {
			return null;
		}

		Criterion          cri  = reqRestriction(searchkey, stat, typ02);
		List<ViAutUserDyn> list = ViAutUserDyn.DAO.reqList(0, nbLine, cri);

		return list;
	}

	// ---------------------------------------------------------------------------------------------------------
	private static Hashtable<String, Integer> mapCol = new Hashtable<String, Integer>() {
		{
			put("action", -1);
			put("id", 0);
			put("login01", 1);
			put("inf01", 2);
		}
	};

	private static void doLstDyn(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Object[]     dataTableOption = ToolDatatable.reqDataTableOption(json, mapCol);
		Set<String>  searchKey       = (Set<String>) dataTableOption[0];
		Set<Integer> stat            = ToolData.reqSetInt(json, "stat", null);
		Set<Integer> typ02           = ToolData.reqSetInt(json, "typ02", null);

		if (typ02 == null && stat == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_LST, null, stat)) { // other param after objTyp...
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		// -------------------------------------------------------------------
		Criterion          cri  = reqRestriction(searchKey, stat, typ02);

		List<ViAutUserDyn> list = reqListDyn(dataTableOption, cri);
		if (list == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		} else {
//			for(TaAutUser u : list) {
//				p.doBuildUserLogin(true);
//			}
		}

		Integer iTotalRecords        = reqNbNsoPostListDyn().intValue();
		Integer iTotalDisplayRecords = reqNbNsoPostListDyn(cri).intValue();

		API.doResponse(response,
				ToolJSON.reqJSonString(filter, DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES,
						"iTotalRecords", iTotalRecords, "iTotalDisplayRecords", iTotalDisplayRecords, "aaData", list));

	}

	private static Criterion
			reqRestriction(Set<String> searchKey, Set<Integer> stats, Set<Integer> types)
					throws Exception {
		// --Pre-Check condition---------------------------------------------------
		if (stats == null) {
			stats = new HashSet<Integer>();
			stats.add(ViAutUserDyn.STAT_ACTIVE);
		}

		Criterion cri = Restrictions.in(ViAutUserDyn.ATT_I_STATUS, stats);

		if (types != null) {
			cri = Restrictions.and(cri, Restrictions.in(ViAutUserDyn.ATT_I_TYPE_02, types));
		}

		if (searchKey != null) {
			for (String s : searchKey) {
				cri = Restrictions.and(cri,
						Restrictions.or(Restrictions.ilike(ViAutUserDyn.ATT_T_LOGIN_01, s),
								Restrictions.ilike(ViAutUserDyn.ATT_T_LOGIN_02, s),
								Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_01, s),
								Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_02, s)));
			}
		}
		return cri;
	}

	private static List<ViAutUserDyn> reqListDyn(Object[] dataTableOption, Criterion cri) throws Exception {
		int                begin   = (int) dataTableOption[1];
		int                number  = (int) dataTableOption[2];
		int                sortCol = (int) dataTableOption[3];
		int                sortTyp = (int) dataTableOption[4];

		List<ViAutUserDyn> list    = null;

		Order              order   = null;
		String             colName = null;

		switch (sortCol) {
		case 0:
			colName = ViAutUserDyn.ATT_I_ID;
			break;
		case 1:
			colName = ViAutUserDyn.ATT_T_LOGIN_01;
			break;
		case 2:
			colName = ViAutUserDyn.ATT_T_INFO_01;
			break;
		}

		if (colName != null) {
			switch (sortTyp) {
			case 0:
				order = Order.asc(colName);
				break;
			case 1:
				order = Order.desc(colName);
				break;
			}
		}

		if (order == null)
			list = ViAutUserDyn.DAO.reqList(begin, number, cri);
		else
			list = ViAutUserDyn.DAO.reqList(begin, number, order, cri);

		return list;
	}

	private static Number reqNbNsoPostListDyn() throws Exception {
		return ViAutUserDyn.DAO.reqCount();
	}

	private static Number reqNbNsoPostListDyn(Criterion cri) throws Exception {
		return ViAutUserDyn.DAO.reqCount(cri);
	}

	// ---------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	private static String EMAIL_HOST				= "";
	private static String EMAIL_PORT				= "0";
	private static String EMAIL_LOGIN				= "";
	private static String EMAIL_PWD					= "";

	/*
	 <p>Xin chào,</p>
<p>Chúng tôi nhận được yêu cầu khởi tạo lại mật khẩu, bạn vui lòng nhấn vào link dưới để thay đổi mật khẩu:</p><br/>
<a href='#dns/view_usr_pw_reset.html?login=#login&code=#code'> Khởi tạo lại mật khẩu </a> 
<p>Nếu bạn không thể nhấn được link, bạn có thể dùng trình duyệt để mở đường dẫn này: </p>
<b>#dns/view_usr_pw_reset.html?login=#login&code=#code</b>
<br><br> Xin chân thành cám ơn!

<br><br><br><br>

<p>Hello #id,</p>
<p>We received the reset password request, please click this link to change your password : 
<a href='#dns/view_usr_new_confirm_simple.html?id=#id&code=#code'> Change password </a> 

<p>If you cannot open the link, you can user a web browser to open this link: </p>
<b>#dns/view_usr_new_confirm_simple.html?id=#id&code=#code</b>
<br><br> Thank you!

//------------------------------------------------------------------------
<p>Xin chào #id,</p>
<p>Vui lòng nhấn vào đường link sau để xác nhận đăng ký : 
<a href='#dns/view_usr_new_confirm_simple.html?id=#id&code=#code'> xác nhận </a> 
<p>Nếu bạn không thể nhấn được link, bạn có thể dùng trình duyệt để mở đường dẫn này: </p>
<b>#dns/view_usr_new_confirm_simple.html?id=#id&code=#code</b>
<br><br> Xin chân thành cám ơn!

<br><br><br><br>

<p>Hello #id,</p>
<p>Please confirm your email by clicking on : 
<a href='#dns/view_usr_new_confirm_simple.html?id=#id&code=#code'> this link </a> 

<p>If you cannot open the link, you can user a web browser to open this link: </p>
<b>#dns/view_usr_new_confirm_simple.html?id=#id&code=#code</b>

<br><br> Thank you!
	 */
	/*
	 {
   "prj_email_host": "smtp.ionos.fr",
   "prj_email_port": "465",
   "prj_email_login": "noreply@inotev.net",
   "prj_email_pwd": "!!!",
   "prj_email_user_register_title"		: "Welcome to our service!",
   "prj_email_user_register_content"	: "Hello,<br><br>Please confirm your email by clicking on this link: https://dhkt.zino-net.com/view_usr_new_confirm_simple.html?id=#id&code=#code<br><br>Thank you!",
   "prj_email_pwd_reset_title"			: "Password reset",
   "prj_email_pwd_reset_content"		: "<p>Bonjour,</p><p>Nous avons reçu votre demande de réinitialisation de mot de passe. Cliquez sur <a href='https://dhkt.zino-net.com/view_usr_pw_reset.html?login=#login&code=#code'>ce lien</a> pour créer un nouveau mot de passe.<br>",
	}
	 */
	
	
	private static JSONObject reqConfig (String code) throws Exception{
		TaCfgValue	config = TaCfgValue.DAO.reqEntityByValue(TaCfgValue.ATT_T_CODE, "TA_CFG_PRJ_EMAIL");
		if (config==null) return null;

		JSONObject 	cfg		= ToolJSON.reqJSonFromString(config.reqStr(TaCfgValue.ATT_T_INFO_01));

		EMAIL_HOST 			= (String) cfg.get("prj_email_host" );
		EMAIL_PORT 			= (String) cfg.get("prj_email_port" );
		EMAIL_LOGIN 		= (String) cfg.get("prj_email_login");
		EMAIL_PWD 			= (String) cfg.get("prj_email_pwd" 	);
		
		return cfg;
	}
	// ---------------------------------------------------------------------------------------------------------
	private static void doNew(JSONObject json, HttpServletResponse response) throws Exception {
		TaAutUser ent = reqNew(json);

		if (ent == null) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response, ToolJSON.reqJSonString( // filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, ent
		));
	}

	private static TaAutUser reqNew(JSONObject json) throws Exception {
		JSONObject obj = ToolData.reqJson(json, "obj", null);
		
		Map<String, Object> attrUsr = API.reqMapParamsByClass(obj, TaAutUser.class);

		String 		login01	= (String) attrUsr.get(TaAutUser.ATT_T_LOGIN_01);
		String 		email	= (String) attrUsr.get(TaAutUser.ATT_T_INFO_01);
		Integer		type01	= (Integer)attrUsr.get(TaAutUser.ATT_I_TYPE_01);
		JSONObject	per		= ToolData.reqJson(obj, "per", null); 
		
		if (login01==null||email==null||per==null) return null;
		
		//---check if login01 existing
		List<TaAutUser> test = TaAutUser.DAO.reqList(0,1,Restrictions.or (Restrictions.ilike(TaAutUser.ATT_T_LOGIN_01, login01), Restrictions.ilike(TaAutUser.ATT_T_INFO_01, email)));
		if (test!=null && test.size()>0) return null;
		
		Date now = new Date();
		attrUsr.put(TaAutUser.ATT_D_DATE_01, now);
		attrUsr.put(TaAutUser.ATT_D_DATE_02, null);
		attrUsr.put(TaAutUser.ATT_D_DATE_03, now);
		attrUsr.put(TaAutUser.ATT_D_DATE_04, ToolDate.reqDateByAdding(now, 0, 0, 5, 0, 0, 0));//5 date for validation
		attrUsr.put(TaAutUser.ATT_I_STATUS, TaAutUser.STAT_ACTIVE_HIDDEN);
		
		if (type01==null||type01<(TaAutUser.TYPE_01_AGENT))
		attrUsr.put(TaAutUser.ATT_I_TYPE_01, TaAutUser.TYPE_01_CLIENT);
		
		attrUsr.put(TaAutUser.ATT_I_PER_MANAGER, 1);
		
		String 		code	=  ToolString.reqHashString("SHA-256",now.getTime()+"");
		attrUsr.put(TaAutUser.ATT_T_INFO_05, code); 
		
		String 		tmpPer	= per.toJSONString();
		attrUsr.put(TaAutUser.ATT_T_INFO_06, tmpPer); //save person here, when validated => create a Person obj
		
		TaAutUser ent = new TaAutUser(attrUsr);
		TaAutUser.DAO.doPersist(ent);
		
		//----send email-------------
		doSendEmailValidationToNewUser(ent);
		
		return ent;
	}
	
	
	private static void doSendEmailValidationToNewUser(TaAutUser ent){
		Thread t = new Thread(){
			public void run(){
				try{
					JSONObject 	cfg  = reqConfig ("TA_CFG_PRJ_EMAIL");
					if (cfg==null) return;
					
					String prjDns			= ToolData.reqStr(cfg, "prj_dns"						, "");
					String emailTitle 		= ToolData.reqStr(cfg, "prj_email_user_register_title"	, "");
					String emailCont 		= ToolData.reqStr(cfg, "prj_email_user_register_content", "");
					
					String code				= ent.reqStr(TaAutUser.ATT_T_INFO_05);
					String email			= ent.reqStr(TaAutUser.ATT_T_INFO_01);
					//---- Please confirm your email by click on this url : https://dhkt.zino-net.com/view_user_validation.html?id=123&code=abcd
					
					emailCont	= emailCont	.replace("%dns"	, prjDns)
											.replace("#id"	, ent.reqId()+"")
											.replace("#code", code);
		
					ToolEmail.canSendEmail(
								EMAIL_HOST, EMAIL_PORT, null, EMAIL_LOGIN, EMAIL_PWD, 
								EMAIL_LOGIN, 
								emailTitle, emailCont,
								email, null, null, null);	
					
				}catch(Exception e){
				}
			}
		};
		ThreadManager.doExecute(t, DefTime.TIME_00_00_05_000);
	}
	
	private static void doSendEmailResetPasswordToUser(TaAutUser ent, String login, String code){
		
		Thread t = new Thread(){
			public void run(){
				try{
					JSONObject 	cfg  = reqConfig ("TA_CFG_PRJ_EMAIL");
					if (cfg==null) return;
		
					String prjDns			= ToolData.reqStr(cfg, "prj_dns"					, "");
					String emailTitle 		= ToolData.reqStr(cfg, "prj_email_pwd_reset_title"	, "");
					String emailCont 		= ToolData.reqStr(cfg, "prj_email_pwd_reset_content", "");
					
					emailCont  				= emailCont	.replace("%dns"		, prjDns)	
														.replace("#login"	, login	)
														.replace("#code"	, code	);
					
					String email			= ent.reqStr(TaAutUser.ATT_T_INFO_01);
			
					ToolEmail.canSendEmail(
								EMAIL_HOST, EMAIL_PORT, null, EMAIL_LOGIN, EMAIL_PWD, 
								EMAIL_LOGIN, 
								emailTitle, emailCont,
								email, null, null, null);	

				}catch(Exception e){
				}
			}
		};
		ThreadManager.doExecute(t, DefTime.TIME_00_00_05_000);
	}
	//-------------------------------------------------------------------------------------------------------------------
	private static void doValidation(JSONObject json,  HttpServletResponse response) throws Exception {
		Integer 			id 		= ToolData.reqInt(json, "id"	, null);
		String 				tok 	= ToolData.reqStr(json, "code"	, null);//compare to info_10

		
		TaAutUser 			user 	= TaAutUser.DAO.reqEntityByID(id);
		if (user==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		if (user.reqInt(TaAutUser.ATT_I_STATUS) != TaAutUser.STAT_ACTIVE_HIDDEN){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		String code = user.reqStr(TaAutUser.ATT_T_INFO_05);
		if (!code.equals(tok)){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		Date now 	= new Date();
		Date dBg	= (Date) user.req(TaAutUser.ATT_D_DATE_03 	);
		Date dEn	= (Date) user.req(TaAutUser.ATT_D_DATE_04 	);
		if ((dBg!=null && now.getTime()<dBg.getTime())||(dEn!=null && now.getTime()>dEn.getTime())){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}


		//--init person object
		String				perStr	= user.reqStr(TaAutUser.ATT_T_INFO_06);
		JSONObject			perObj	= ToolJSON.reqJSonFromString(perStr);
		Map<String, Object> attr 	= API.reqMapParamsByClass(perObj, TaPerPerson.class);				
		TaPerPerson			perEnt	= new TaPerPerson(attr);	
		
		perEnt.reqSet(TaPerPerson.ATT_I_ID			, null);
		perEnt.reqSet(TaPerPerson.ATT_I_STATUS_01	, TaPerPerson.STAT_01_ACTIVE_LV_01);
		perEnt.reqSet(TaPerPerson.ATT_D_DATE_01		, now);
		perEnt.reqSet(TaPerPerson.ATT_I_TYPE_01		, TaPerPerson.TYP_01_NATURAL);
		TaPerPerson.DAO.doPersist(perEnt);
		
		//--init role
		/*
		TaAutRole role = TaAutRole.DAO.reqEntityByValue(TaAutRole.ATT_T_NAME, "RO_AUT_CLIENT");
		if (role!=null) {
			TaAutAuthUser auth = new TaAutAuthUser(user.reqId(), role.reqId(), TaAutAuthUser.STAT_ON, role.reqStr(TaAutRole.ATT_T_AUT_RIGHT));
			TaAutAuthUser.DAO.doMerge(auth);
		}*/
		
		//--merge user info
		user.reqSet(TaAutUser.ATT_I_PER_PERSON			, perEnt.reqID());
		user.reqSet(TaAutUser.ATT_I_STATUS 				, TaAutUser.STAT_ACTIVE);
		user.reqSet(TaAutUser.ATT_T_INFO_03				, perEnt.reqFullName());
		user.reqSet(TaAutUser.ATT_T_INFO_05				, null);
		user.reqSet(TaAutUser.ATT_T_INFO_06				, null);
		user.reqSet(TaAutUser.ATT_D_DATE_04				, null);	
		
		TaAutUser.DAO.doMerge(user);
		
		API.doResponse(response,DefAPI.API_MSG_OK);
	}
	
	public static void doPwdMod(JSONObject json, HttpServletResponse response) throws Exception {
		String 				login 	= ToolData.reqStr(json, DefJS.USER_NAME	, null);
		String 				tok 	= ToolData.reqStr(json, DefJS.USER_TOK	, null);
		String 				newPwd 	= ToolData.reqStr(json, DefJS.USER_PASS	, null); 
		
		if (newPwd==null||newPwd.length()==0){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		TaAutUser 			user 	= TaAutUser.reqUser(login);
		if (user==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		if (user.reqInt(TaAutUser.ATT_I_STATUS) != TaAutUser.STAT_ACTIVE){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		String pass = user.reqStr(TaAutUser.ATT_T_PASS_01);
		if (!pass.equals(tok)){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		Date now 	= new Date();
		Date dBg	= (Date) user.req(TaAutUser.ATT_D_DATE_03 	);
		Date dEn	= (Date) user.req(TaAutUser.ATT_D_DATE_04 	);
		if ((dBg!=null && now.getTime()<dBg.getTime())||(dEn!=null && now.getTime()>dEn.getTime())){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		user.reqSet(TaAutUser.ATT_T_PASS_01, newPwd);
		TaAutUser.DAO.doMerge(user);
		
		API.doResponse(response,DefAPI.API_MSG_OK);
	}	
	
	public static void doPwdResetReq(JSONObject json, HttpServletResponse response) throws Exception {
		String 				login 	= ToolData.reqStr(json, "login01"	, null);
		String 				email 	= ToolData.reqStr(json, "email"	, null);
		TaAutUser 			user 	= null;
		if (login != null && !login.trim().isEmpty()) {
		    user = TaAutUser.reqUser(login.trim().toLowerCase());

		} else if (email != null && !email.trim().isEmpty()) {
		    user = TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_INFO_01, email.trim().toLowerCase());
		}

		
		if (user==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		if (user.reqInt(TaAutUser.ATT_I_STATUS ) != TaAutUser.STAT_ACTIVE){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		Date now 	= new Date();
		Date dBg	= (Date) user.req(TaAutUser.ATT_D_DATE_03 );
		Date dEn	= (Date) user.req(TaAutUser.ATT_D_DATE_04 );
		if ((dBg!=null && now.getTime()<dBg.getTime())||(dEn!=null && now.getTime()>dEn.getTime())){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		String 		code	=  ToolString.reqHashString("SHA-256",now.getTime()+"");
		user.reqSet(TaAutUser.ATT_T_INFO_05, code); 
		
		TaAutUser.DAO.doMerge(user);
		
		//---send email-----------------------------------
		String uEmail 	= (String) user.req(TaAutUser.ATT_T_INFO_01);
		boolean	ok		= false;
		if (uEmail!=null){
			try{
				doSendEmailResetPasswordToUser(user,login,code);
				ok = true;
//				ok = ToolEmail.canSendEmail(EMAIL_HOST, EMAIL_PORT, null, EMAIL_LOGIN, EMAIL_PWD, EMAIL_LOGIN, title, msg, uEmail, null, null, null);			
			}catch(Exception e) {
				ok = false;
			} 
		}
		
		if (ok)
			API.doResponse(response,DefAPI.API_MSG_OK);
		else
			API.doResponse(response,DefAPI.API_MSG_KO);
		
	}
	
	public static void doPwdReset(JSONObject json, HttpServletResponse response) throws Exception {
		String 				login 	= ToolData.reqStr(json, "login"	, null);
		String 				tok 	= ToolData.reqStr(json, "code"	, null);
		String 				newPwd 	= ToolData.reqStr(json, DefJS.USER_PASS	, null); 

		if (newPwd==null||newPwd.length()==0){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		TaAutUser 			user 	= TaAutUser.reqUser(login);
		if (user==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		if (user.reqInt(TaAutUser.ATT_I_STATUS) != TaAutUser.STAT_ACTIVE){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		String pass = user.reqStr(TaAutUser.ATT_T_INFO_05);
		if (!pass.equals(tok)){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		Date now 	= new Date();
		Date dBg	= (Date) user.req(TaAutUser.ATT_D_DATE_03 	);
		Date dEn	= (Date) user.req(TaAutUser.ATT_D_DATE_04 	);
		if ((dBg!=null && now.getTime()<dBg.getTime())||(dEn!=null && now.getTime()>dEn.getTime())){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		user.reqSet(TaAutUser.ATT_T_PASS_01, newPwd);
		user.reqSet(TaAutUser.ATT_T_INFO_05, null);
		
		TaAutUser.DAO.doMerge(user);
		
		API.doResponse(response,DefAPI.API_MSG_OK);
	}
	
	private static String getFormattedStr(String tmpl, String content, Integer count) {
		String cont = tmpl;
		if (count==null) count = 0;
		switch(count) {
		case 0 :  break;	
		case 1 :  cont = String.format(tmpl	, content); break;
		case 2 :  cont = String.format(tmpl	, content, content); break;
		case 3 :  cont = String.format(tmpl	, content, content, content); break;
		case 4 :  cont = String.format(tmpl	, content, content, content, content); break;
		case 5 :  cont = String.format(tmpl	, content, content, content, content, content); break;
		case 6 :  cont = String.format(tmpl	, content, content, content, content, content, content); break;
		case 7 :  cont = String.format(tmpl	, content, content, content, content, content, content, content); break;
		case 8 :  cont = String.format(tmpl	, content, content, content, content, content, content, content, content); break;
		case 9 :  cont = String.format(tmpl	, content, content, content, content, content, content, content, content, content ); break;
		case 10 : cont = String.format(tmpl	, content, content, content, content, content, content, content, content, content, content); break;
		}
		return cont;
	}
}
