package com.hnv.api.service.priv.aut;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefDB;
import com.hnv.api.def.DefJS;
import com.hnv.api.def.DefTime;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.api.service.common.APIAuth;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.api.service.main.ServiceAPILoginCheck;
import com.hnv.api.service.tool.ToolCSV;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.tool.ToolString;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutAuthUser;
import com.hnv.db.aut.TaAutHistory;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.nso.TaNsoGroup;
import com.hnv.db.nso.TaNsoGroupMember;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyFavorite;
import com.hnv.db.tpy.TaTpyRelationship;
import com.hnv.def.DefDBExt;

/**
 * ----- ServiceNsoPost by H&V
 * ----- Copyright 2017------------
 */
public class ServiceAutUser implements IService {
	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 					= "EC_V3".toLowerCase();

	public static final String SV_CLASS 					= "ServiceAutUser".toLowerCase();	

	public static final String SV_GET 						= "SVGet".toLowerCase();	
	public static final String SV_GET_SIMPLE				= "SVGetSimple".toLowerCase();	
	
	public static final String SV_LST_IN_IDS				= "SVLstInIds".toLowerCase();
	public static final String SV_LST 						= "SVLst".toLowerCase();
	public static final String SV_LST_DYN					= "SVLstDyn".toLowerCase(); 
	public static final String SV_LST_SEARCH				= "SVLstSearch".toLowerCase(); 
	
	public static final String SV_LST_BY_GRP 				= "SVLstByGrp".toLowerCase();
	public static final String SV_LST_GRP_WORK				= "SVLstGrpWork".toLowerCase();	//---get lst grp work where user is member
	
	public static final String SV_LST_BY_RELATION 			= "SVLstByRelation".toLowerCase();
	public static final String SV_LST_FOR_CALEND			= "SVLstForCalend".toLowerCase();
	
	public static final String SV_NEW 						= "SVNew".toLowerCase();	
	public static final String SV_MOD 						= "SVMod".toLowerCase();	
	public static final String SV_DEL 						= "SVDel".toLowerCase();
	
	public static final String SV_MOD_SELF					= "SVModSelf".toLowerCase();
	
	public static final String SV_IMPORT 					= "SVImport".toLowerCase();
	
	public static final String SV_LCK_REQ 					= "SVLckReq".toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 					= "SVLckSav".toLowerCase(); //save and continue
	public static final String SV_LCK_END 					= "SVLckEnd".toLowerCase();
	public static final String SV_LCK_DEL 					= "SVLckDel".toLowerCase();

	public static final String SV_LST_HISTORY_CONNECTION	= "SVLstHistoryConnection".toLowerCase(); 
	public static final String SV_UPDATE_STATUS 			= "SVUpdateStat".toLowerCase();
	public static final String SV_GET_FILE 					= "SVGetFile".toLowerCase();	
	public static final String SV_MOD_TRANSL				= "SVModTransl".toLowerCase();	

	public static final String SV_LOGOUT 					= "SVLogout".toLowerCase();	
	
	
	
	public static final Integer	ENT_TYP						= DefDBExt.ID_TA_AUT_USER;
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutUser(){
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

			if(sv.equals(SV_GET) 					&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doGet(user,  json, response);
			
			} else if(sv.equals(SV_GET_SIMPLE) 		) {
				doGetSimple(user,  json, response);
			
			} else if(sv.equals(SV_LST_IN_IDS)		&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstInIds(user, json, response);
			} else if(sv.equals(SV_LST)				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLst(user,  json, response);
			
			} else if(sv.equals(SV_LST_BY_RELATION) &&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstByRelationship(user,  json, response);
			
			} else if(sv.equals(SV_LST_FOR_CALEND)) {
				doLst(user,  json, response);
				
			} else if(sv.equals(SV_LST_DYN)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstDyn(user,  json, response);
			
			} else if(sv.equals(SV_LST_SEARCH)		&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstSearch(user,  json, response);
				
			}else  if(sv.equals(SV_LST_HISTORY_CONNECTION)	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET) 
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstHistoryConnection(user,  json, response);

			} else if(sv.equals(SV_UPDATE_STATUS)	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doUpdateStatus(user,  json, response);
			
			}  else if(sv.equals(SV_NEW)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_AUT_USER_NEW) 
													|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doNew(user, json, response);

			} else if(sv.equals(SV_MOD)				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doMod(user, json, response);
			
			} else if(sv.equals(SV_MOD_SELF)		) {
				doModSelf(user, json, response);

			}else if(sv.equals(SV_LCK_REQ)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckReq(user, json, response);
			} else if(sv.equals(SV_LCK_SAV)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckSav(user, json, response);
			} else if(sv.equals(SV_LCK_END)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckEnd(user, json, response);
			} else if(sv.equals(SV_LCK_DEL)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckDel(user, json, response);

			} else  if(sv.equals(SV_DEL)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_AUT_USER_DEL)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doDel(user, json, response);		
				
			} else if(sv.equals(SV_IMPORT)){
//				doImport(user, json, response);
				
			} else  if(sv.equals(SV_LOGOUT)) {
				doLogout(user, json, response);

			} else  if(sv.equals(SV_LST_BY_GRP)) {
				doLstByGrp(user, json, response);	
				
			} else  if(sv.equals(SV_LST_GRP_WORK)) {
				doLstGrpWork(user, json, response);	
					
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
	private static Set<String> filter = new HashSet<String>();
	static {
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_01);
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_02);
		filter.add(TaAutUser.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_03);
		
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_01);
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_02);
		filter.add(ViAutUserDyn.class.getSimpleName()+"."+TaAutUser.ATT_T_PASS_03);
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, true	);
//		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaAutUser 			ent 		= reqGet(entId, forced);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	
	private static CacheData<TaAutUser> 		cache_entity= new CacheData<TaAutUser>		(500, DefTime.TIME_24_00_00_000 );
	public static TaAutUser reqGet(Integer entId, Boolean forced) throws Exception{
		String 			key		= entId+"";
		TaAutUser 		ent 	= cache_entity.reqData(key);	
		
		if (forced || ent == null) {
			ent 	= TaAutUser.DAO.reqEntityByRef(entId);
			if (ent!=null) {
				//---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqNsoAreaGet use cache-----");
			cache_entity.reqCheckIfOld(key); //cache in 20 hour
		}


		//---do build something other of ent like details....
		if (ent!=null){		
			ent.doBuildDocuments(forced);
			ent.doBuildAvatar	(forced);
			ent.doBuildPerson	(forced);
			
//			ent.doBuilAuth		(forced, null);
			ent.doBuilAuths		(forced);			
			ent.doBuildManager	(forced);	
			ent.doBuildSuperior	(forced);
			ent.doBuildHistoryConnection(forced);
			
			ent.doHidePwd();
		}

		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGetSimple(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);	
		Integer 			wLev		= ToolData.reqInt	(json, "workLev"	, TaNsoGroupMember.TYP_MEMBER_LEV_01);

		TaAutUser 			ent 		= reqGetSimple(entId, wLev);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	
	private static CacheData<TaAutUser> 		cache_simple= new CacheData<TaAutUser>		(500, DefTime.TIME_24_00_00_000 );
	public static TaAutUser reqGetSimple(Integer entId, Integer wLev) throws Exception{
		String 			key		= entId+"";
		TaAutUser 		ent 	= cache_simple.reqData(key);	
		
		if (ent == null) {
			ent 	= TaAutUser.DAO.reqEntityByRef(entId);
			if (ent!=null) {
				//---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqNsoAreaGet use cache-----");
			cache_entity.reqCheckIfOld(key); //cache in 20 hour
		}


		//---do build something other of ent like details....
		if (ent!=null){		
//			ent.doBuildDocuments(false);
			ent.doBuildAvatar		(false);
			ent.doBuildPerson		(false);
			ent.doBuildGroupWork	(false, wLev);
			ent.doHidePwd();
		}

		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLstInIds(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		Set<Integer> 	ids     	= ToolData.reqSetInt 	(json, "ids" 		, null);
		Boolean			buildAvatar	= ToolData.reqBool		(json, "buildAvatar", null);
		
		if(ids == null || ids.size() <= 0) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		Criterion 			cri 	= Restrictions.in(ViAutUserDyn.ATT_I_ID, ids);
		
		List<ViAutUserDyn> 	list 	= ViAutUserDyn.DAO.reqList(cri); //and other params if necessary
		
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		if(Boolean.TRUE.equals(buildAvatar)) {
			ViAutUserDyn.doBuildAvatarForList(list);
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}
	
	//---------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViAutUserDyn> 	list = reqLst(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	
	private static List<ViAutUserDyn> reqLst(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, 10);
		String 				searchkey	= ToolData.reqStr		(json, "searchkey"	, null);
		Set<Integer>		stats		= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typ01		= ToolData.reqSetInt	(json, "typ01"		, null);
		Integer				typ02		= ToolData.reqInt		(json, "typ02"		, null);
		Boolean				wAvatar		= ToolData.reqBool		(json, "wAvatar"	, false);
		
		Criterion 			cri			= reqRestrictionSearch(user, searchkey, stats, typ01);
		List<ViAutUserDyn>	list		= ViAutUserDyn.DAO.reqList(0, nbLine, Order.asc(ViAutUserDyn.ATT_T_LOGIN_01), cri);

		if (wAvatar) 		TaTpyDocument	.reqBuildAvatar(list, DefDBExt.ID_TA_AUT_USER, ViAutUserDyn.ATT_O_AVATAR);
		return list;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLstByRelationship(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViAutUserDyn> 	list = reqLstByRelationship(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}


	private static List<ViAutUserDyn> reqLstByRelationship(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, 100);
		String 				searchkey	= ToolData.reqStr		(json, "searchkey"	, null);
		Set<Integer>		stats		= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typ01		= ToolData.reqSetInt	(json, "typ01"		, null);
		Integer				typ02		= ToolData.reqInt		(json, "typ02"		, null);
		Boolean				wAvatar		= ToolData.reqBool		(json, "wAvatar"	, false);
		Integer 			entId01      = ToolData.reqInt		(json, "entId01" 	, null); //---entId01 in relationship

		if (entId01==null)  return null;
		
		Criterion 			cri			= reqRestrictionSearchByRelationship(user, searchkey, stats, typ01, entId01);
		List<ViAutUserDyn>	list		= ViAutUserDyn.DAO.reqList(0, nbLine, Order.asc(ViAutUserDyn.ATT_T_LOGIN_01), cri);

		if (wAvatar) 		TaTpyDocument	.reqBuildAvatar(list, DefDBExt.ID_TA_AUT_USER, ViAutUserDyn.ATT_O_AVATAR);
		return list;
	}
	//---------------------------------------------------------------------------------------------------------		
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
		{
			put("action"	, -1);
			put("id"		, 0 );
			put("login01"	, 1 );
			put("inf01"		, 2 );
		}
	};
	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, null);
		Set<String>			searchKey		= (Set<String>)dataTableOption[0];
		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"	, null);
		Integer				typ01			= ToolData.reqInt		(json, "typ01"	, null);
		Integer				typ02			= ToolData.reqInt		(json, "typ02"	, null);
		
		if(typ01 == null && typ02== null && stats ==null) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		if (!canWorkWithObj(user, WORK_LST, null, stats)){ //other param after objTyp...
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		//-------------------------------------------------------------------
		Criterion 	cri 				= reqRestriction(user, searchKey, null, stats, typ01, typ02);				

		List<ViAutUserDyn> list 		= reqListDyn(dataTableOption, cri);
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} else {
//			for(TaAutUser u : list) {
//				p.doBuildUserLogin(true);
//			}
		}

		Integer iTotalRecords 			= reqNbNsoPostListDyn().intValue();				
		Integer iTotalDisplayRecords 	= reqNbNsoPostListDyn(cri).intValue();


		API.doResponse(response, ToolJSON.reqJSonString(		
				filter,
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
				));

	}
	
	private static void doLstSearch(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination  	res = reqListSearch(user, json); //and other params if necessary
		
		if (res.reqList()==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, res));
	}
	
	public static ResultPagination reqListSearch(TaAutUser user, JSONObject json) throws Exception {
		String				searchKey		= ToolData.reqStr		(json, "searchKey"	, null);
		Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
		Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
		Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);
		
		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typs			= ToolData.reqSetInt	(json, "typs"		, null);
		Boolean				buildInfo		= ToolData.reqBool		(json, "buildInfo"	, false);
		Boolean				hardLoad		= ToolData.reqBool		(json, "hardLoad"	, false);
		
		String 				keyWord 		= stats.toString();
		ResultPagination 	rs 				= null;
		boolean				addCache		= true;
		
		if (!canWorkWithObj(user, WORK_LST, null, stats)){ //other param after objTyp...
			return null;
		}
		
		if(stats == null || stats.size() <= 0) {
			rs			= cacheEnt_rs.reqData(keyWord);
		} else {
			addCache = false;
		}
		
		//-------------------------------------------------------------------
		if(rs == null || hardLoad) {
			Object[] 	dataTableOption 	= reqDataTableOption(searchKey, begin, number, null, null);
			Criterion 	cri 				= reqRestrictionSearch(user, searchKey, stats, typs);				

			List<ViAutUserDyn> list 		= ViAutUserDyn.DAO.reqList(begin, number, cri);
			
			ViAutUserDyn.doBuildAvatarForList(list);
			
			if(buildInfo) {
				ViAutUserDyn.doBuildPer(list);
			}
			
			if (total == 0 ) {
		//		total						= ViAutUserDyn.DAO.reqCount().intValue();
				total						= reqListDynCount(dataTableOption, cri);
			}
			rs								= new ResultPagination(list, total, begin, number);
			
			if (addCache) cacheEnt_rs.reqPut(keyWord, rs);
		} else {
			ToolLogServer.doLogInf("---reqListSearch use cache-----");
			cacheEnt_rs.reqCheckIfOld(keyWord); //cache in 2 hour
		}

		return rs;
	}
	private static Integer reqListDynCount(Object[] dataTableOption, Criterion cri) throws Exception {
		Integer result = ViAutUserDyn.reqCountUserFilter(cri).intValue();
		return result;
	}

	private static Object[] reqDataTableOption(String searchKey, int beginDisplay, int nbDisplay, String colN, Integer sortOption){
		Object[] res = new Object[10];


		List<String> keyword 	= new ArrayList<String>();
		if (searchKey!=null && searchKey.length()>0){				
			StringTokenizer token = new StringTokenizer(searchKey, " ");
			while (token.hasMoreTokens()){
				String s = "%" +token.nextToken()+ "%";
				s = s.replace("%+", "");
				s = s.replace("+%", "");
				keyword.add(s.toLowerCase());
			}			
		}else{
			keyword.add("%");
		}

		Integer colToSort= colN==null?null:mapCol.get(colN);
		res[0]		= keyword;
		res[1]		= (int)beginDisplay;
		res[2]		= (int)nbDisplay;
		res[3]		= colToSort;
		res[4]		= sortOption;
		res[5]		= -1;		
		return res;

	}

	
	private static Criterion reqRestrictionSearch(TaAutUser user,  String searchKey, Set<Integer> stats, Set<Integer> typs) {
		if (user.canBeSuperAdmin()) {
			return Restrictions.gt(ViAutUserDyn.ATT_I_ID, 0);
		}
		
		Criterion cri = Restrictions.eq(ViAutUserDyn.ATT_I_PER_MANAGER, user.reqPerManagerId());
		
		if (stats == null){
			stats = new HashSet<>() ; 
			stats.add(ViAutUserDyn.STAT_ACTIVE);
		}
		cri = Restrictions.and(cri, Restrictions.in(ViAutUserDyn.ATT_I_STATUS, stats));
		
		if (!user.canBeSuperAdmin() && typs!=null) typs.remove(TaAutUser.TYPE_01_SUP_ADM);
		
		if (typs != null){
			cri = Restrictions.and(	cri, Restrictions.in(ViAutUserDyn.ATT_I_TYPE_01, typs));
		}

		if (searchKey != null) {
			searchKey = searchKey.replace("*", "%");
			searchKey = '%' + searchKey + '%';
			searchKey = searchKey.replace("/%", "");

		//	cri = Restrictions.and(	cri, Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_03, searchKey));
			cri = Restrictions.and(	cri, Restrictions.or(
					Restrictions.ilike(TaAutUser.ATT_T_INFO_03, searchKey), 
					Restrictions.ilike(TaAutUser.ATT_T_LOGIN_01, searchKey)
			));
		}
		
		return cri;
	}
	
	
	private static Criterion reqRestrictionSearchByRelationship(TaAutUser user,  String searchKey, Set<Integer> stats, Set<Integer> typs, Integer entId) throws Exception {
		if (user.canBeSuperAdmin()) {
			return Restrictions.gt(ViAutUserDyn.ATT_I_ID, 0);
		}
		
		Criterion cri = Restrictions.eq(ViAutUserDyn.ATT_I_PER_MANAGER, user.reqPerManagerId());
		
		if (stats == null){
			stats = new HashSet<>() ; 
			stats.add(ViAutUserDyn.STAT_ACTIVE);
		}
		cri = Restrictions.and(cri, Restrictions.in(ViAutUserDyn.ATT_I_STATUS, stats));
		
		if (!user.canBeSuperAdmin() && typs!=null) typs.remove(TaAutUser.TYPE_01_SUP_ADM);
		
		if (typs != null){
			cri = Restrictions.and(	cri, Restrictions.in(ViAutUserDyn.ATT_I_TYPE_01, typs));
		}

		if (searchKey != null) {
			searchKey = searchKey.replace("*", "%");
			searchKey = searchKey + '%';
			searchKey = searchKey.replace("/%", "");

			cri = Restrictions.and(	cri, Restrictions.ilike(ViAutUserDyn.ATT_T_LOGIN_01, searchKey));
		}
		
		if (entId!=null) {
			List<TaTpyRelationship> list 	= TaTpyRelationship.reqList(DefDBExt.ID_TA_PRJ_PROJECT, entId, DefDBExt.ID_TA_AUT_USER, null);
			Set<Integer>			uIds	= ToolSet.reqSetInt(list, TaTpyRelationship.ATT_I_ENTITY_ID_02);
			if (uIds!=null && uIds.size()>0) {
				cri = Restrictions.and(	cri, Restrictions.in(ViAutUserDyn.ATT_I_ID, uIds));
			}
		}
		
		return cri;
	}

	private static Criterion reqRestriction(TaAutUser user,  Set<String> searchKey, Integer manId, Set<Integer> stats, Integer typ01, Integer typ02 ) throws Exception {	
		//--Pre-Check condition---------------------------------------------------
		if (stats == null){
			stats = new HashSet<>() ; 
			stats.add(ViAutUserDyn.STAT_ACTIVE);
		}
				
		Criterion cri = Restrictions.in(ViAutUserDyn.ATT_I_STATUS, stats);
		
		if(typ01 != null) {
			cri = Restrictions.and(	cri, Restrictions.eq(ViAutUserDyn.ATT_I_TYPE_01 , typ01));
			
			if(typ01 == ViAutUserDyn.TYPE_01_AGENT) {
				cri = Restrictions.and(	cri, Restrictions.eq(ViAutUserDyn.ATT_I_TYPE_02 , typ02));
			}
		}

		if (searchKey != null) {
			for (String s : searchKey){
				cri = Restrictions.and(	cri, Restrictions.or(
						Restrictions.ilike(ViAutUserDyn.ATT_T_LOGIN_01, '%'+s+'%'),
						Restrictions.ilike(ViAutUserDyn.ATT_T_LOGIN_02, '%'+s+'%'),
						Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_01, '%'+s+'%'),
						Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_02, '%'+s+'%'))
				);
			}
		}
		
//		if (manId==null && !user.canBeSuperAdmin()) manId = user.reqPerManagerId();
		if (manId!=null) cri = Restrictions.and(cri, Restrictions.eq(ViAutUserDyn.ATT_I_PER_MANAGER, manId));
		return cri;
	}

	private static List<ViAutUserDyn> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int begin 		= (int)	dataTableOption[1];
		int number 		= (int)	dataTableOption[2]; 
		int sortCol 	= (int)	dataTableOption[3]; 
		int sortTyp 	= (int)	dataTableOption[4];	

		List<ViAutUserDyn> list 	= null;		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = ViAutUserDyn.ATT_I_ID; break;		
		case 1: colName = ViAutUserDyn.ATT_T_LOGIN_01; break;			
		case 2: colName = ViAutUserDyn.ATT_T_INFO_01; break;		
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc (colName); break;
			case 1: order = Order.desc(colName); break;								
			}
		}

		if (order==null)
			list	= ViAutUserDyn.DAO.reqList(begin, number, cri);
		else
			list	= ViAutUserDyn.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbNsoPostListDyn() throws Exception {						
		return ViAutUserDyn.DAO.reqCount();		
	}
	
	private static Number reqNbNsoPostListDyn(Criterion cri) throws Exception {			
		return ViAutUserDyn.DAO.reqCount(cri);
	}

	
	private static void doLstByGrp(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		Integer 			grpId			= ToolData.reqInt			(json, "grpId"		, null);
		String 				searchkey		= ToolData.reqStr			(json, "searchkey"	, null);
		Set<Integer>		stats			= ToolData.reqSetInt		(json, "stats"		, null);
		Set<Integer>		typ01			= ToolData.reqSetInt		(json, "typ01"		, null);
		Boolean				wAvatar			= ToolData.reqBool			(json, "wAvatar"	, false);

		if (grpId==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		List<TaNsoGroupMember> 	lst 		= TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_NSO_GROUP, grpId));
		Set<Integer> ids 					= ToolSet.reqSetInt(lst, TaNsoGroupMember.ATT_I_AUT_USER);
		Criterion 				cri			= reqRestrictionSearch(user, searchkey, stats, typ01);
		List<ViAutUserDyn> 		lstU 		= ViAutUserDyn.DAO.reqList_In (TaAutUser.ATT_I_ID, ids,cri);
		ViAutUserDyn.doBuildPer(lstU);
		if (wAvatar) 		TaTpyDocument	.reqBuildAvatar(lstU, DefDBExt.ID_TA_AUT_USER, ViAutUserDyn.ATT_O_AVATAR);
		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, lstU));
	}
	
	
	private static void doLstGrpWork(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		Integer 			uId			= ToolData.reqInt			(json, "uId"		, null);
		Integer 			wLev		= ToolData.reqInt			(json, "workLev"	, TaNsoGroupMember.TYP_MEMBER_LEV_01);
		if (uId==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		List<TaNsoGroupMember> 	lst 		= TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_AUT_USER, uId), Restrictions.le(TaNsoGroupMember.ATT_I_TYPE, wLev));
		Set<Integer> 			gIds		= ToolSet.reqSetInt(lst, TaNsoGroupMember.ATT_I_NSO_GROUP);
		List<TaNsoGroup> 		lstG		= TaNsoGroup.DAO.reqList_In (TaNsoGroup.ATT_I_ID, gIds, Restrictions.eq(TaNsoGroup.ATT_I_TYPE_01, TaNsoGroup.TYP_01_WORK));
		
		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, lstG));
	}
	
	//---------------------------------------------------------------------------------------------------------
	private static void doNew (TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaAutUser 			ent				= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaAutUser.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));
	}

	private static TaAutUser reqNew(TaAutUser user,  JSONObject json) throws Exception {
		Integer			userId		= user.reqId();
		Integer			manId		= user.reqPerManagerId();
		JSONObject		obj			= ToolData.reqJson(json, "obj", null);
		Integer			stat		= ToolData.reqInt(obj, "stat", null);
		//--------------------------------------------------------------------------------------------
		Map<String, Object> attrUsr = API.reqMapParamsByClass(obj	, TaAutUser.class);
		
		//----Test------------------------------------------------------------------------------------
		String 			login		= (String) attrUsr.get(TaAutUser.ATT_T_LOGIN_01);	
		String 			email		= (String) attrUsr.get(TaAutUser.ATT_T_INFO_01);	
		if (login==null || login.length()==0) return null; else login = login.toLowerCase();
		if (email==null || email.length()==0) return null;
		TaAutUser		testLogin	= TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_LOGIN_01, login);
		TaAutUser		testEmail	= TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_INFO_01, email);
		if (testLogin!=null || testEmail!=null ) return null;
		
		//----set value------------------------------------------------------------------------------------
		attrUsr.put(TaAutUser.ATT_T_LOGIN_01	, login);
		attrUsr.put(TaAutUser.ATT_D_DATE_01		, new Date());
		attrUsr.put(TaAutUser.ATT_D_DATE_02		, null);
		attrUsr.put(TaAutUser.ATT_I_AUT_USER_01	, userId);
		
		Integer uId03 = (Integer) attrUsr.get(TaAutUser.ATT_I_AUT_USER_03);
		if (uId03==null) attrUsr.put(TaAutUser.ATT_I_AUT_USER_03, userId);
		
		if (user.canBeSuperAdmin()) {
			Integer mId = (Integer) attrUsr.get(TaAutUser.ATT_I_PER_MANAGER);
			if (mId!=null)  manId = mId;
		}
		attrUsr.put(TaAutUser.ATT_I_PER_MANAGER	, manId);

		TaAutUser ent = new TaAutUser(attrUsr);	
		TaAutUser.DAO.doPersist(ent);

		int 		entId		= ent.reqId();
		//----set date validation if user is visistor.....
		JSONArray	docs 		= (JSONArray) obj.get("files");
		ent.reqSet(TaAutUser.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, ent, ENT_TYP, entId, docs));
		
		JSONObject	per 		= ToolData.reqJson	(obj, "per", null);
		ent.reqSet(TaAutUser.ATT_O_PER_PERSON, TaPerPerson.reqCheck(DefAPI.SV_MODE_NEW, user.reqId(), ent.reqInt(TaAutUser.ATT_I_PER_PERSON), per, ent));
		
		//---check authorization with multi roles
		JSONArray	authArr 		= ToolData.reqJsonArr(obj, "auths", null);
		ent.reqSet(TaAutUser.ATT_O_AUTHS, TaAutAuthUser.reqListCheck( DefAPI.SV_MODE_NEW, entId, authArr));
		
		//---check authorization with free role
		Set<Integer>	rights 		= ToolData.reqSetInt(obj, "rights"	, null);
		if (rights!=null) {
			JSONObject	role 		= ToolData.reqJson	(obj , "role"	, new JSONObject());
			Integer 	rId			= ToolData.reqInt	(role, "id"		, 0);	
			Date 		rDtBegin	= ToolData.reqDate	(role, "dt01"	, null);	
			Date 		rDtEnd		= ToolData.reqDate	(role, "dt02"	, null);
			
			TaAutAuthUser	auth	= TaAutAuthUser.reqCheck(DefAPI.SV_MODE_NEW, user, entId, rId, rights, rDtBegin,  rDtEnd);
			if (auth!=null) ent.doBuilAuth	(true, auth, rId);
		}
		
		ent.doBuildSuperior (true);
		
		ent.doBuilAvatarPath();
		TaAutUser.DAO.doMerge(ent);	
		
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");

		TaAutUser  		ent	 	=  reqMod(false, user, json, false); 								
		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}		
	}
	
	private static void doModSelf(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaAutUser  		ent	 	=  reqMod(true, user, json, false); 								
		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}		
	}
	private static TaAutUser reqMod(boolean forSelf, TaAutUser user,  JSONObject json, boolean wAuths) throws Exception {
		JSONObject			obj		= ToolData.reqJson 	(json	, "obj"	, null);
		int 				entId 	= ToolData.reqInt	(obj	, "id"	, -1);
		int 				stat 	= ToolData.reqInt	(obj	, "stat", -1);
		TaAutUser 			ent 	= TaAutUser.DAO.reqEntityByRef(entId);
		
		if (forSelf && user.reqId()!=entId)		return null;
		
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}
		
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutUser.class);			
		
		attr.remove(TaAutUser.ATT_I_ID);
		attr.remove(TaAutUser.ATT_D_DATE_01);
		attr.remove(TaAutUser.ATT_I_AUT_USER_01);
		attr.remove(TaAutUser.ATT_I_PER_PERSON);
		
		if (!user.canBeSuperAdmin()) {
			attr.remove(TaAutUser.ATT_I_PER_MANAGER);
		}

		attr.put(TaAutUser.ATT_D_DATE_02		, new Date());
		attr.put(TaAutUser.ATT_I_AUT_USER_02	, user.reqId());
		
		String pass = (String) attr.get(TaAutUser.ATT_T_PASS_01);
		if (pass==null|| pass.length()==0) attr.remove(TaAutUser.ATT_T_PASS_01);
		
		TaAutUser.DAO.doMerge(ent, attr);	
		cache_entity.reqPut(entId+"", ent);

		JSONObject	per 		= ToolData.reqJson	(obj, "per", null);
		ent.reqSet(TaAutUser.ATT_O_PER_PERSON, TaPerPerson.reqCheck(DefAPI.SV_MODE_MOD, user.reqId(), ent.reqInt(TaAutUser.ATT_I_PER_PERSON), per, ent));
		
		//merge files for user
		JSONArray	docs		= (JSONArray) obj.get("files");	
		ent.reqSet(TaAutUser.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, ent, ENT_TYP, entId, docs));

		//---check authorization with multi roles
		if (wAuths) {
			JSONArray	authArr 		= ToolData.reqJsonArr(obj, "auths", null);
			ent.reqSet(TaAutUser.ATT_O_AUTHS, TaAutAuthUser.reqListCheck( DefAPI.SV_MODE_MOD, entId, authArr));
		}

		
		//---check authorization with free role
		Set<Integer>	rights 		= ToolData.reqSetInt(obj, "rights"	, null);
		if (rights!=null) {
			JSONObject	role 		= ToolData.reqJson	(obj , "role"	, new JSONObject());
			Integer 	rId			= ToolData.reqInt	(role, "id"		, 0);	
			Date 		rDtBegin	= ToolData.reqDate	(role, "dt01"	, null);	
			Date 		rDtEnd		= ToolData.reqDate	(role, "dt02"	, null);
			
			TaAutAuthUser	auth	= TaAutAuthUser.reqCheck(DefAPI.SV_MODE_MOD, user, entId, rId, rights, rDtBegin,  rDtEnd);
			if (auth!=null) ent.doBuilAuth	(true, auth, rId);
		}
		
		ent.doBuildManager	(false);	
		ent.doBuildAvatar	(false);
		ent.doBuildSuperior	(false);

		if (attr.get(TaAutUser.ATT_T_PASS_01)!=null) ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_01));
		if (attr.get(TaAutUser.ATT_T_PASS_02)!=null) ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_02));
		if (attr.get(TaAutUser.ATT_T_PASS_03)!=null) ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_03));
		
		
		ent.doBuilAvatarPath();
		TaAutUser.DAO.doMerge(ent);	
		
		return ent;
	}


	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");
		
		int				entId 	= ToolData.reqInt(json, "id", null);
		TaSysLock 		lock 	= ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		if (lock==null || lock.reqStatus() == 0){
			API.doResponse(response, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA		, lock
					));	
			return;
		}

		if (!canDel(user, json)){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}

		ToolDBLock.canDeleteLock(lock);
	}

	private static boolean canDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 	entId	= ToolData.reqInt	(json, "id", null	);	
		TaAutUser  	ent	 	= TaAutUser.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		Integer stat = ent.reqInt(TaAutUser.ATT_I_STATUS);
		if (stat.equals(TaAutUser.STAT_DELETED)) {
			
			if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after ent...
				return false;
			}	
			//remove all other object connecting to this ent first-------

//			Session sessSub 	= TaTpyDocument	.DAO.reqSessionCurrent();
			Session sessMain 	= TaAutUser		.DAO.reqSessionCurrent();
			try {
				TaTpyDocument		.doListDel	(sessMain, ENT_TYP, entId);
//				TaTpyDocument		.doListDel(sessSub, entTyp, entId);
				
				TaAutAuthUser		.doListDel	(sessMain, entId);
				TaAutHistory		.doListDel	(sessMain, entId);
				TaTpyFavorite		.doListDel	(sessMain, entId);
				
				Integer 		perId	= ent.reqInt(TaAutUser.ATT_I_PER_PERSON);
				TaPerPerson		per		= TaPerPerson.DAO.reqEntityByID(sessMain, perId);
				if (per!=null)	TaPerPerson.DAO.doRemove(sessMain, per);
				
				TaAutUser.DAO		.doRemove 	(sessMain, ent);
				cache_entity.reqDel(entId+"");
				
				TaAutUser			.DAO.doSessionCommit(sessMain);
//				TaTpyDocument		.DAO.doSessionCommit(sessSub);
			}catch(Exception e){
				e.printStackTrace();
				TaAutUser			.DAO.doSessionRollback(sessMain);
//				TaTpyDocument		.DAO.doSessionRollback(sessSub);
			}		
		} else {
			//Set status = -1
			ent.reqSet(TaAutUser.ATT_I_STATUS, TaAutUser.STAT_DELETED);
			TaAutUser.DAO.doMerge(ent);	
			cache_entity.reqPut(entId+"", ent);
			
			ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_01));
			ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_02));
			ServiceAPILoginCheck.doRemoveFromCache(ent.reqStr(TaAutUser.ATT_T_LOGIN_03));
		}

		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private void doLckReq(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckReq --------------");		

		Integer 		entId	= ToolData.reqInt	(json, "id", null);	
		TaSysLock 		lock 	= ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		if (lock==null || lock.reqStatus() == 0){
			API.doResponse(response, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA		, lock
					));	
		}else{
			API.doResponse(response, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, lock
					));	
		}			
	}
	private void doLckDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckDel --------------");

		boolean isDeleted = ToolDBLock.canDeleteLock(json);		
		if (isDeleted)
			API.doResponse(response, ToolJSON.reqJSonString(		
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES
				));		
		else 
			API.doResponse(response, ToolJSON.reqJSonString(		
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO
					));		

	}
	private void doLckSav(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckSav --------------");	
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}
		
		TaAutUser  		ent	 	=  reqMod(false, user, json, true); 								
		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {				
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}
	}


	//user when modify object with lock
	private void doLckEnd(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckEnd --------------");	
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}
		
		
		TaAutUser ent = reqMod(false, user, json, true);						
		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			ToolDBLock.canDeleteLock(json);
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}	
	}
	
	//---------------------------------------------------------------------------------------------
	private static void doLstHistoryConnection(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		Criterion 			cri			= Restrictions.and(Restrictions.eq(TaAutHistory.ATT_I_AUT_USER, user.reqId()),
				Restrictions.eq(TaAutHistory.ATT_I_TYPE, TaAutHistory.TYPE_CONN));
		List<TaAutHistory>	list		= TaAutHistory.DAO.reqList(cri);
		
		if (list==null || list.size() <= 0){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		
				filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));	
	}
	
	//---------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------

	private static void doUpdateStatus(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		int 			stat	= ToolData.reqInt	(json, "stat", -1);
		int				postId	= ToolData.reqInt	(json, "postId", -1);

		if(stat == -1 || postId == -1) {
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaAutUser p = TaAutUser.DAO.reqEntityByRef(postId);
		p.reqSet(TaAutUser.ATT_I_STATUS, stat);
		p.reqSet(TaAutUser.ATT_D_DATE_02, new Date());
		TaAutUser.DAO.doMerge(p);
		//		p.doBuildAll(true);
		p.doBuildDocuments(true);

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, p
				));
	}

//--------------------------------------------------------------------------------------------

//	private static void doImport(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
//		//ServerLogTool.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");
//		boolean 			ok		= canAddFiles(user, json);
//		if (!ok){
//			API.doResponse(response, DefAPI.API_MSG_KO);
//		}else {
//			API.doResponse(response, DefAPI.API_MSG_OK);
//		}
//	}
//	private static final 	String  SEPAR = ";";
//	private static boolean 	canAddFiles(TaAutUser user,  JSONObject json) throws Exception {
//		JSONObject			obj		= ToolData.reqJson	(json, "obj", new JSONObject());
//		JSONArray			docs	= (JSONArray) obj.get("files");
//		
//		JSONObject 			fileObj = (JSONObject) docs.get(0);
//		Integer 			id 		= ((Long) fileObj.get("id")).intValue();
//		TaTpyDocument 		doc 	= TaTpyDocument.DAO.reqEntityByRef(id);
//		
//		String 				path 	= doc.reqStr(TaTpyDocument.ATT_T_INFO_10);
//		JSONArray 			data 	= ToolCSV.reqData(path, 1, SEPAR);
//		
//		
//		Hashtable<String, JSONArray> classLst = new Hashtable<String, JSONArray>();
//		
//		for (int i = 0; i < data.size(); i++) {
//			// Get each JSONObject in JSONArray
//			JSONObject object = (JSONObject) data.get(i);
//			
//			// Get data from JSONObject according to keys
//			String className 		= object.get(2).toString();
//			
//			if (!classLst.containsKey(className)) {
//				classLst.put(className, new JSONArray());
//			}
//			JSONArray 			studentList = classLst.get(className);
//			
//			
//			String MaSV 	= object.get(3).toString();
//			String password = ToolString.reqHashString(ToolString.SHA_256, MaSV);
//			String HoLot 	= object.get(4).toString();
//			String Ten 		= object.get(5).toString();
//			String email	= object.get(10).toString();
//            
//			
//			JSONObject 	studenObj 	= new JSONObject();      // Create JSONObject for students
//			JSONObject 	per 		= new JSONObject();  // Re-initialize per for each student
//			
//            studenObj.put("stat"	, 1);
//            studenObj.put("login01"	, MaSV);
//            studenObj.put("pass01"	, password);
//            studenObj.put("email"	, email);
//            
//            // Create a personal information object (per)
//            String[] part = HoLot.split(" ");
//            per.put("name01", part[0]);
//            StringBuilder remainingParts = new StringBuilder();
//            for (int j = 1; j < part.length; j++) {
//                remainingParts.append(part[j]);
//                if (j < part.length - 1) {
//                    remainingParts.append(" ");  // Add spaces between words
//                }
//            }
//            per.put("name02", remainingParts.toString());
//            per.put("name03", Ten);
//            
//            // Attach personal information to studenList
//            studenObj.put("per", per);
//            
//            studenObj.put("name", part[0]+" "+remainingParts.toString()+" "+Ten);
//            
//            // Add studentList to studentListArray
//            studentList.add(studenObj);
//		}
//		
//		for (String key:classLst.keySet()) {
//			TaNsoGroup 				grpClass 	= null;
//			Criterion 				criClass 	= Restrictions.eq(TaNsoGroup.ATT_T_NAME, key);
//			List<TaNsoGroup> 		list 		= TaNsoGroup.DAO.reqList(criClass);
//			if(list==null ||list.size()==0 ) {
//				grpClass 		= new TaNsoGroup(key, TaNsoGroup.TYP_01_WORK);
//				
//				grpClass.reqSet(TaNsoGroup.ATT_T_NAME			, key);
//				grpClass.reqSet(TaNsoGroup.ATT_I_STATUS_01		, TaNsoGroup.STAT_01_ACTIVE);
//				grpClass.reqSet(TaNsoGroup.ATT_D_DATE_01		, new Date());
//				grpClass.reqSet(TaNsoGroup.ATT_I_PER_MANAGER	, user.reqPerManagerId());
//				grpClass.reqSet(TaNsoGroup.ATT_I_AUT_USER		, user.reqId());
//				TaNsoGroup.DAO.doPersist(grpClass);
//				
//				int				entId 		= grpClass.reqId();
//				//----add user creating to manager
//				Map<String, Object> mem 	= new HashMap<String, Object>();
//				TaNsoGroupMember 	memG 	= new TaNsoGroupMember(mem);
//				memG.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, entId);
//				memG.reqSet(TaNsoGroupMember.ATT_I_AUT_USER		, user.reqId());
//				memG.reqSet(TaNsoGroupMember.ATT_I_STATUS		, TaNsoGroupMember.STAT_ACTIVE);
//				memG.reqSet(TaNsoGroupMember.ATT_I_TYPE			, TaNsoGroupMember.TYP_MANAGER);
//				memG.reqSet(TaNsoGroupMember.ATT_D_DATE_01		, new Date());
//				TaNsoGroupMember.DAO.doPersist(memG);
//				
//			} else {
//				grpClass = list.get(0);
//			}
//			
//			JSONArray 		studentList = classLst.get(key);
//			Integer			userId		= user.reqId();
//			Integer			manId		= user.reqPerManagerId();
//			
//			Integer 		rId			= 0;
//			Date 			rDtBegin	= null;	
//			Date 			rDtEnd		= null;
//			Set<Integer>	rights 		= new HashSet<>();
//			rights.add(	 5000001); //--read post
//			rights.add( 40000001); //--read req
//			rights.add( 40000002); //--new req
//			rights.add( 40000003); //--mod req
//			
//			
//			for (int i = 0; i < studentList.size(); i++) {
//				JSONObject 		object  		= (JSONObject) studentList.get(i);
//				String 	   		MaSV	   		= ToolData.reqStr(object	, "login01"	, null);
//				TaAutUser		stdUser			= TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_LOGIN_01, MaSV);
//
//				if(stdUser==null) {
//					//--------------------------------------------------------------------------------------------
//					Map<String, Object> attrUsr = API.reqMapParamsByClass(object	, TaAutUser.class);
//
//					//----Test------------------------------------------------------------------------------------
//					String 			login		= (String) attrUsr.get(TaAutUser.ATT_T_LOGIN_01);	
//					if (login==null || login.length()==0) continue; else login = login.toLowerCase();
//
//					TaAutUser		test		= TaAutUser.DAO.reqEntityByValue(TaAutUser.ATT_T_LOGIN_01, login);
//					if (test!=null) continue;
//
//					//----set value------------------------------------------------------------------------------------
//					attrUsr.put(TaAutUser.ATT_T_LOGIN_01	, login);
//					attrUsr.put(TaAutUser.ATT_D_DATE_01		, new Date());
//					attrUsr.put(TaAutUser.ATT_D_DATE_02		, null);
//					attrUsr.put(TaAutUser.ATT_I_AUT_USER_01	, userId);
//					attrUsr.put(TaAutUser.ATT_I_TYPE_01		, TaAutUser.TYPE_01_STU);
//					attrUsr.put(TaAutUser.ATT_I_PER_MANAGER	, manId);
//					
//					Integer uId03 = (Integer) attrUsr.get(TaAutUser.ATT_I_AUT_USER_03);
//					if (uId03==null) attrUsr.put(TaAutUser.ATT_I_AUT_USER_03, userId);
//	
//					stdUser = new TaAutUser(attrUsr);
//					TaAutUser.DAO.doPersist(stdUser);
//
//
//					JSONObject	person 			= ToolData.reqJson	(object, "per", null);
//					stdUser.reqSet(TaAutUser.ATT_O_PER_PERSON, TaPerPerson.reqCheck(DefAPI.SV_MODE_NEW, user.reqId(), stdUser.reqInt(TaAutUser.ATT_I_PER_PERSON), person, stdUser));
//
//					//---check authorization with multi roles
//					JSONArray	authArr 		= ToolData.reqJsonArr(object, "auths", null);
//					stdUser.reqSet(TaAutUser.ATT_O_AUTHS, TaAutAuthUser.reqListCheck( DefAPI.SV_MODE_NEW, stdUser.reqId(), authArr));
//
//					//---check authorization with free role
//					TaAutAuthUser	auth	= TaAutAuthUser.reqCheck(DefAPI.SV_MODE_NEW, user, stdUser.reqId(), rId, rights, rDtBegin,  rDtEnd);
//					if (auth!=null) stdUser.doBuilAuth	(true, auth, rId);
//					
//					TaAutUser.DAO.doMerge(stdUser);
//				}
//				// Add student into group
//				TaNsoGroupMember 	memGroup 	= new TaNsoGroupMember();
//				memGroup.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, grpClass.reqId());
//				memGroup.reqSet(TaNsoGroupMember.ATT_I_AUT_USER		, stdUser.reqId());
//				memGroup.reqSet(TaNsoGroupMember.ATT_I_STATUS		, TaNsoGroupMember.STAT_ACTIVE);
//				memGroup.reqSet(TaNsoGroupMember.ATT_I_TYPE			, TaNsoGroupMember.TYP_STU);
//				memGroup.reqSet(TaNsoGroupMember.ATT_D_DATE_01		, new Date());
//				TaNsoGroupMember.DAO.doPersist(memGroup);
//			}
//		}
//		return true;
//	}
	
	//--------------------------------------------------------------------------------------------

	//--cache for UI public
	private static CacheData<ResultPagination> cacheEnt_rs = new CacheData<ResultPagination>(100, DefTime.TIME_02_00_00_000);
	//------------function get list post by entType and entId-----------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doLogout(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		ServiceAPILoginCheck.doRemoveFromCache(user.reqStr(TaAutUser.ATT_T_LOGIN_01));
		ServiceAPILoginCheck.doRemoveFromCache(user.reqStr(TaAutUser.ATT_T_LOGIN_02));
		ServiceAPILoginCheck.doRemoveFromCache(user.reqStr(TaAutUser.ATT_T_LOGIN_03));
		API.doResponse(response,DefAPI.API_MSG_OK);
	}

}
