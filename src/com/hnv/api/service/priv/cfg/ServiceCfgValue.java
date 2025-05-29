package com.hnv.api.service.priv.cfg;


import java.util.ArrayList;
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
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.cfg.TaCfgValue;
//import com.hnv.db.cfg.TaCfgGroupValue;
import com.hnv.db.sys.TaSysLock;
import com.hnv.def.DefDBExt;
/**
* ----- ServiceCfgGroup by H&V
* ----- Copyright 2023------------
*/
public class ServiceCfgValue implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 
	

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 				= "HNV".toLowerCase();

	public static final String SV_CLASS 				= "ServiceCfgValue".toLowerCase();	
	
	public static final String SV_GET 					= "SVGet"		.toLowerCase();	
	public static final String SV_LST 					= "SVLst"		.toLowerCase();
	public static final String SV_LST_DYN				= "SVLstDyn"	.toLowerCase(); 
	public static final String SV_LST_PAGE				= "SVLstPage"	.toLowerCase(); 

	public static final String SV_NEW 					= "SVNew"		.toLowerCase();	
	public static final String SV_MOD 					= "SVMod"		.toLowerCase();	
	public static final String SV_DEL 					= "SVDel"		.toLowerCase();
	
	public static final String SV_LCK_REQ 				= "SVLckReq"	.toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 				= "SVLckSav"	.toLowerCase(); //save and continue
	public static final String SV_LCK_END 				= "SVLckEnd"	.toLowerCase();
	public static final String SV_LCK_DEL 				= "SVLckDel"	.toLowerCase();
	
	public static final Integer	ENT_TYP					= DefDBExt.ID_TA_CFG_VALUE;
		
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceCfgValue(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			//---------------------------------------------------------------------------------
			if(sv.equals(SV_LCK_REQ)					){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_MOD))
					doLckReq(user,  json, response);
			} else if(sv.equals(SV_LCK_SAV)				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_MOD))
					doLckSav(user,  json, response);
			} else if(sv.equals(SV_LCK_END)				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_MOD))
					doLckEnd(user,  json, response);
			} else if(sv.equals(SV_LCK_DEL)				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_MOD))
					doLckDel(user,  json, response);
				
			} else if(sv.equals(SV_NEW)					){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_NEW))
					doNew(user,  json, response);
			} else if(sv.equals(SV_MOD)					){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_MOD))
					doMod(user,  json, response);
			} else  if(sv.equals(SV_DEL)				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_DEL))
					doDel(user,  json, response);
			
			
			} else if(sv.equals(SV_GET) 				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_GET))
					doGet(user,  json, response);

			} else if(sv.equals(SV_LST)					){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_GET))
					doLst(user,  json, response);
			} else if(sv.equals(SV_LST_DYN)				){
				if (canWorkWithSV(response, user, WORK_GET, sv, APIAuth.R_CFG_VALUE_GET))
					doLstDyn(user,  json, response);

			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}	

		}catch(Exception e){
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
			//check something with params
			return true;
		case WORK_UPL : 
			//check something with params
			return true;
		}
		return false;
	}
	//---------------------------------------------------------------------------------------------------------
	private static boolean canWorkWithSV (HttpServletResponse response, TaAutUser user, int typWork, String sv, Integer...rights) throws Exception{
		Set<Integer> rLst = new HashSet<Integer>();
		rLst.add(APIAuth.R_ADMIN);
		if (rights!=null && rights.length>0){
			for (Integer r: rights) rLst.add(r);
		}

		switch(typWork){
		case WORK_GET : 
			rLst.add(APIAuth.R_AUT_ALL_GET);
			if( APIAuth.canAuthorizeWithOneRight(user, rLst)||	APIAuth.canAuthorize(user, SV_CLASS, sv))
				return true;
		case WORK_NEW : 
			rLst.add(APIAuth.R_AUT_ALL_NEW);
			if( APIAuth.canAuthorizeWithOneRight(user, rLst)||	APIAuth.canAuthorize(user, SV_CLASS, sv))
				return true;
		case WORK_MOD : 
			rLst.add(APIAuth.R_AUT_ALL_MOD);
			if( APIAuth.canAuthorizeWithOneRight(user, rLst)||	APIAuth.canAuthorize(user, SV_CLASS, sv))
				return true;
		case WORK_DEL : 
			rLst.add(APIAuth.R_AUT_ALL_DEL);
			if( APIAuth.canAuthorizeWithOneRight(user, rLst)||	APIAuth.canAuthorize(user, SV_CLASS, sv))
				return true;
		}

		API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
		return false;
	}
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

		if (ToolDBLock.canDeleteLock(json))
			API.doResponse(response, DefAPI.API_MSG_OK);		
		else 
			API.doResponse(response, DefAPI.API_MSG_KO);

	}
	private void doLckSav(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckSav --------------");	
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}
		
		TaCfgValue  		ent	 	=  reqMod(user, json); 								
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
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
		
		TaCfgValue ent = reqMod(user, json);						
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
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
	
	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaCfgValue 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaCfgValue.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
		));
	}

	private static TaCfgValue reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson		 (json, "obj", null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaCfgValue.class);	
		TaCfgValue  		ent	 	= new TaCfgValue		 (attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}

		TaCfgValue.DAO.doPersist(ent);
		
		//cache_entity.reqPut(ent.reqId()+"", ent);
		
		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaCfgValue  		ent	 	=  reqMod(user, json); 								
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
			));	
		}		
	}

	private static TaCfgValue reqMod(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson 			(json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass	(obj, TaCfgValue.class);
		Integer 			entId		= ToolData.reqInt		(obj, "id"		, -1	);	
		
		TaCfgValue 		ent 	= TaCfgValue.DAO.reqEntityByRef(entId);
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		TaCfgValue.DAO.doMerge(ent, attr);
		
		//cache_entity.reqPut(entId+"", ent);
		
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
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, DefAPI.API_MSG_OK);
		}

		ToolDBLock.canDeleteLock(lock);
	}

	private static boolean canDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 		entId	= ToolData.reqInt	(json, "id", null	);	
		TaCfgValue  	ent	 	= TaCfgValue.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after obj...
			return false;
		}
		
		//---we have to remove all other entities connected to this entity
		//---doRemove....
		
		TaCfgValue.DAO		.doRemove (ent);
//		TaCfgGroupValue.reqListDel(ent.reqId());
		//cache_entity.reqDel(entId+"");

		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static CacheData<TaCfgValue> 		cache_entity= new CacheData<TaCfgValue>		(500, DefTime.TIME_24_00_00_000 );
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		TaCfgValue 		ent 		= reqGet(json);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
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
	
	private static TaCfgValue reqGet(JSONObject json) throws Exception{
		Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);
		
		String 				key			= entId+"";
		TaCfgValue 		ent 		= cache_entity.reqData(key);	
		
		if (forced || ent ==null) {
			ent 	= TaCfgValue.DAO.reqEntityByRef(entId, forced);
			
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
	//--------------------------------------------------------------------------------------------------------------
	private static CacheData<List<TaCfgValue>> 	cache_lst 		= new CacheData<List<TaCfgValue>>();	
	
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<TaCfgValue> 	list = reqLst(user, json); 
		
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(//filter,		
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	private static List<TaCfgValue> reqLst(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine  = ToolData.reqInt			(json	, 	"nbLine", Integer.MAX_VALUE);
		Criterion 			cri		= reqRestriction 			(user	, 	json	, null);	
		List<TaCfgValue>	list	= TaCfgValue.DAO.reqList	(0		,	nbLine	, cri);	

		return list;
	}
	
	//---------------------------------------------------------------------------------------------------------		
	
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
	    {
	    	put("action", -1);
	    	put("id"	, 0 );
	    }
	};
	
	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, mapCol);
		
		//--Pre-Check condition---------------------------------------------------
		if (!canWorkWithObj(user, WORK_LST, null, null)){ //other param after objTyp...
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		Criterion 			cri 			= reqRestriction(user, json, dataTableOption);				
		List<TaCfgValue> 	list 			= reqListDyn	(dataTableOption, cri);		
		if (list==null ){
			API.doResponse(response, 	DefAPI.API_MSG_KO);
			return;
		} 

		Integer iTotalRecords 				= reqNbListDyn()	.intValue();				
		Integer iTotalDisplayRecords 		= reqNbListDyn(cri)	.intValue();

		API.doResponse(response, ToolJSON.reqJSonString(		
				//filter,
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,	
								
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
		));

	}

	private static Criterion reqRestriction(TaAutUser user, JSONObject json, Object[]  dataTableOption) throws Exception {		
		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stat"	, null);
		Set<Integer>		typs			= ToolData.reqSetInt	(json, "typ"	, null);
		Set<String>			searchKey		= dataTableOption!=null?(Set<String>)dataTableOption[0]:null;
		
		if(typs == null && stats ==null) {
			return null;
		}
		
		/* Integer manId = user.reqPerManagerId();
		if(manId!=null) {
			cri = Restrictions.and(	cri, Restrictions.in(TaCfgGroup.ATT_I_PER_MAN , manId));
		}*/
		
		if (stats == null){
			stats = new HashSet<Integer>() ; 
			stats.add(TaCfgValue.STAT_01_ACTIVE);
		}
		Criterion cri = Restrictions.in(TaCfgValue.ATT_I_STATUS_01, stats);
		
		if(typs!=null) {
			cri = Restrictions.and(	cri, Restrictions.in(TaCfgValue.ATT_I_TYPE_01 , typs));
		}

		if (searchKey==null)
 			searchKey	= ToolData.reqSetStr(json, "searchkey"	, null);
 			
		if (searchKey!=null) {
			for (String s : searchKey){
				cri = 	 Restrictions.and	(cri, Restrictions.or(
													Restrictions.ilike(TaCfgValue.ATT_T_NAME	, s), 
													Restrictions.ilike(TaCfgValue.ATT_T_CODE	, s)
											));
			}
		}
		
		return cri;
	}

	private static List<TaCfgValue> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		if (cri==null) return null;
		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<TaCfgValue> list 	= new ArrayList<TaCfgValue>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = TaCfgValue.ATT_I_ID; break;		
//		case 1: colName = TaCfgGroup.ATT_T_CODE; break;	
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;	
			}
		}

		if (order==null)
			list	= TaCfgValue.DAO.reqList(begin, number, cri);
		else
			list	= TaCfgValue.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbListDyn() throws Exception {						
		return TaCfgValue.DAO.reqCount();		
	}
	
	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return TaCfgValue.DAO.reqCount(cri);
	}
	
	
}
