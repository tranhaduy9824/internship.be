package com.hnv.api.service.priv.aut;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutRight;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutRoleDyn;
import com.hnv.db.sys.TaSysLock;

import com.hnv.def.DefDBExt;
import com.hnv.def.DefRight;

/**
 * ----- ServiceAutRight by H&V
 * ----- Copyright 2017------------
 */
public class ServiceAutRight implements IService {
	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 				= "EC_V3".toLowerCase();

	public static final String SV_CLASS 				= "ServiceAutRight".toLowerCase();	

	public static final String SV_GET 					= "SVGet".toLowerCase();	
	public static final String SV_LST 					= "SVLst".toLowerCase();
	public static final String SV_LST_DYN				= "SVLstDyn".toLowerCase(); 

	public static final String SV_NEW 					= "SVNew".toLowerCase();	
	public static final String SV_MOD 					= "SVMod".toLowerCase();	
	public static final String SV_DEL 					= "SVDel".toLowerCase();
	
	public static final String SV_LCK_REQ 				= "SVLckReq".toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 				= "SVLckSav".toLowerCase(); //save and continue
	public static final String SV_LCK_END 				= "SVLckEnd".toLowerCase();
	public static final String SV_LCK_DEL 				= "SVLckDel".toLowerCase();
	
	public static final Integer	ENT_TYP					= DefDBExt.ID_TA_AUT_RIGHT;

	private static Set<String> filter = new HashSet<String>();

	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutRight(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		
		try {
			//---------------------------------------------------------------------------------

			if(sv.equals(SV_GET) 						&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doGet(user,  json, response);

			} else if(sv.equals(SV_LST)					&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLst(user,  json, response);
			} else if(sv.equals(SV_LST_DYN)				&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstDyn(user,  json, response);
				

			}  else if(sv.equals(SV_NEW)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW) 
													|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doNew(user, json, response);

			} else if(sv.equals(SV_MOD)			&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doMod(user, json, response);
			}else if(sv.equals(SV_LCK_REQ)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckReq(user, json, response);
			} else if(sv.equals(SV_LCK_SAV)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckSav(user, json, response);
			} else if(sv.equals(SV_LCK_END)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckEnd(user, json, response);
			} else if(sv.equals(SV_LCK_DEL)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckDel(user, json, response);

			} else  if(sv.equals(SV_DEL)		&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL)
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doDel(user, json, response);	

			
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}
		} catch (Exception e) {
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			e.printStackTrace();
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
//			TaAutRight post 	= (TaAutRight) params[0];
//			Integer   uId 	= post.reqInt(post, TaAutRight.ATT_I_AUT_USER_NEW);
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
	private static CacheData<TaAutRight> 		cache_entity= new CacheData<TaAutRight>		(500, DefTime.TIME_24_00_00_000 );
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			objId		= ToolData.reqInt	(json, "id"		, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"	, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaAutRight 			ent 		= reqGet(objId, forced, forManager);

		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	
	public static TaAutRight reqGet(Integer objId, Boolean forced, boolean forManager) throws Exception{
		String 			key		= objId+"";
		TaAutRight 		ent 	= cache_entity.reqData(key);	
		if (forced || ent ==null) {
			ent 	= TaAutRight.DAO.reqEntityByRef(objId, forced);
			if (ent!=null) {
				//---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqGet use cache-----");
			cache_entity.reqCheckIfOld(key); //cache in 20 hour
		}

		return ent;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<TaAutRight> 	list = reqLst(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		
				//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	private static List<TaAutRight> reqLst(TaAutUser user,  JSONObject json) throws Exception {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, Integer.MAX_VALUE);
		Set<String> 		searchKey	= ToolData.reqSetStr	(json, "searchkey"	, null);
		Integer 			objTyp		= ToolData.reqInt		(json, "typ"		, null	);

		if (!canWorkWithObj(user, WORK_LST, objTyp)){ //other param after objTyp...
			return null;
		}

		Criterion 			cri		= reqRestriction(user, searchKey);		 //and other params	
		List<TaAutRight> 	list 	=  TaAutRight.DAO.reqList(0, nbLine, cri);

		return list;
	}
	
	//---------------------------------------------------------------------------------------------------------		

	private static Long countAllEle = null;
	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, null);
		Set<String>			searchKey		= (Set<String>)dataTableOption[0];	
		
		Set<Integer>		objTypMult		= new HashSet<Integer>() ;

		if (!canWorkWithObj(user, WORK_LST, null, null)){ //other param after objTyp...
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		Criterion 	cri 			= reqRestriction(user, searchKey);				

		List<TaAutRight> list 		= reqListDyn(dataTableOption, cri);		
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} 

		if (countAllEle==null)
			countAllEle = ((long)reqNbListDyn());

		Integer iTotalRecords 			= (countAllEle.intValue());				
		Integer iTotalDisplayRecords 	= reqNbListDyn(cri).intValue();


		API.doResponse(response, ToolJSON.reqJSonString(		filter,
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
				));

	}

	private static Criterion reqRestriction(TaAutUser user,  Set<String> searchKey) throws Exception {	
		//--Pre-Check condition---------------------------------------------------
		Criterion cri = Restrictions.gt(TaAutRight.ATT_I_ID, 0);

		if (searchKey!=null) {
			for (String s : searchKey){
				if (cri==null)
					cri = 	Restrictions.or(Restrictions.ilike(TaAutRight.ATT_T_NAME	, s), 
											Restrictions.ilike(TaAutRight.ATT_T_INFO_01	, s));

				else
					cri = 	Restrictions.and(cri, 
							Restrictions.or(Restrictions.ilike(TaAutRight.ATT_T_NAME	, s), 
											Restrictions.ilike(TaAutRight.ATT_T_INFO_01	, s))
							);
			}
		}

		//			if (manId==null && !user.canBeSuperAdmin()) manId = user.reqPerManagerId();
		//			if (manId!=null) cri = Restrictions.and(cri, Restrictions.eq(TaAutRight.ATT_I_PER_MANAGER, manId));
		return cri;
	}
	

	private static List<TaAutRight> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<TaAutRight> list 	= new ArrayList<TaAutRight>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = TaAutRight.ATT_I_ID; break;		
		case 1: colName = TaAutRight.ATT_T_NAME; break;	
		case 2: colName = TaAutRight.ATT_T_INFO_01; break;	
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;	
			}
		}

		if (order==null)
			list	= TaAutRight.DAO.reqList(begin, number, cri);
		else
			list	= TaAutRight.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbListDyn() throws Exception {						
		return TaAutRight.DAO.reqCount();		
	}
	
	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return TaAutRight.DAO.reqCount(cri);
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaAutRight 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaAutRight.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));
	}

	private static TaAutRight reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject		obj			= ToolData.reqJson (json, "obj", null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutRight.class);	
		TaAutRight  		ent	 	= new TaAutRight(attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}

		TaAutRight.DAO.doPersist(ent);

		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaAutRight  		ent	 	=  reqMod(user, json); 								
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

	private static TaAutRight reqMod(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutRight.class);

		int objId 		= Integer.parseInt(obj.get("id").toString());
		TaAutRight ent 	= TaAutRight.DAO.reqEntityByRef(objId);
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		TaAutRight.DAO.doMerge(ent, attr);
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

		boolean					ok		= canDel(user, json);
		if (!ok){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}

		ToolDBLock.canDeleteLock(lock);
	}

	private static boolean canDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 		entId	= ToolData.reqInt	(json, "id", null	);	
		TaAutRight  	ent	 	= TaAutRight.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		TaAutRight.DAO		.doRemove (ent);
		//---we have to check T_Aut_Right in AutRole + AutAuthorization?

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
		
		TaAutRight  		ent	 	=  reqMod(user, json); 								
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
		
		
		TaAutRight ent = reqMod(user, json);						
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
	
	//---------------------------------------------------------------------------------------------------------

}
