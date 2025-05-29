package com.hnv.api.service.priv.per;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.nso.TaNsoGroup;
import com.hnv.db.nso.TaNsoGroupMember;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.per.vi.ViPerPersonDyn;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyCategory;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyInformation;
import com.hnv.def.DefDBExt;
import com.hnv.def.DefRight;
/**
* ----- ServicePerPerson by H&V
* ----- Copyright 2023------------
*/
public class ServicePerProducer implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 
	

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 					= "HNV".toLowerCase();

	public static final String SV_CLASS 					= "ServicePerProducer".toLowerCase();	
	
	public static final String SV_GET 						= "SVGet"		.toLowerCase();	
	public static final String SV_LST 						= "SVLst"		.toLowerCase();
	public static final String SV_LST_DYN					= "SVLstDyn"	.toLowerCase();
	public static final String SV_LST_PAGE					= "SVLstPage"	.toLowerCase(); 
	

	public static final String SV_NEW 						= "SVNew"		.toLowerCase();	
	public static final String SV_MOD 						= "SVMod"		.toLowerCase();	
	public static final String SV_DEL 						= "SVDel"		.toLowerCase();
	
	public static final String SV_LCK_REQ 					= "SVLckReq"	.toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 					= "SVLckSav"	.toLowerCase(); //save and continue
	public static final String SV_LCK_END 					= "SVLckEnd"	.toLowerCase();
	public static final String SV_LCK_DEL 					= "SVLckDel"	.toLowerCase();
	
	public static final Integer	ENT_TYP						= DefDBExt.ID_TA_PER_PERSON;
		
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServicePerProducer(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			//---------------------------------------------------------------------------------
			
			if(sv.equals(SV_LCK_REQ)					&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_PRODUCER_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckReq(user,  json, response);
			} else if(sv.equals(SV_LCK_SAV)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_PRODUCER_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckSav(user,  json, response);
			} else if(sv.equals(SV_LCK_END)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_PRODUCER_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckEnd(user,  json, response);
			} else if(sv.equals(SV_LCK_DEL)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_PRODUCER_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckDel(user,  json, response);
				
			//--------------------------------------------------------------------------------------	
			} else if(sv.equals(SV_NEW)					&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doNew(user,  json, response);
			
			} else if(sv.equals(SV_MOD) 				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_PRODUCER_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doMod(user,  json, response);
			
			} else  if(sv.equals(SV_DEL)				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_PER_PRODUCER_DEL) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doDel(user,  json, response);
			
			
			} else if(sv.equals(SV_GET) 				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_PRODUCER_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGet(user,  json, response);
							
			} else if(sv.equals(SV_LST)					&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_PRODUCER_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLst(user,  json, response);
				
				
			} else if(sv.equals(SV_LST_PAGE)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_PRODUCER_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstPage(user,  json, response);
			
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
		
		TaPerPerson  		ent	 	=  reqMod(user, json); 								
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
		
		TaPerPerson ent = reqMod(user, json);						
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
		TaPerPerson 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaPerPerson.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
		));
	}

	private static TaPerPerson reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson		 (json, "obj", null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaPerPerson.class);	
		TaPerPerson  		ent	 	= new TaPerPerson		 (attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}
		//---producer must be a moral person
		ent.reqSet(TaPerPerson.ATT_I_TYPE_01, TaPerPerson.TYP_01_MORAL);
		ent.reqSet(TaPerPerson.ATT_I_TYPE_02, TaPerPerson.TYP_02_PRODUCER);
		ent.reqSet(TaPerPerson.ATT_D_DATE_01, new Date());
		
		TaPerPerson.DAO.doPersist(ent);

		int 				entId 	= Integer.parseInt(obj.get("id").toString());
		JSONArray 			docs 	= (JSONArray) obj.get("files");
		ent.reqSet(TaPerPerson.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));
		ent.doBuildAvatar(false);
		
		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaPerPerson  		ent	 	=  reqMod(user, json); 								
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

	private static TaPerPerson reqMod(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaPerPerson.class);
		int 				entId 	= Integer.parseInt(obj.get("id").toString());
		TaPerPerson 		ent 	= TaPerPerson.DAO.reqEntityByRef(entId);
		if (ent==null){
			return null;
		}
		
		JSONArray 		docs 	= (JSONArray) obj.get("files");
		ent.reqSet(TaPerPerson.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, ENT_TYP, entId, docs));
		ent.doBuildAvatar(false);
		
		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		//---producer must be a moral person
		attr.put(TaPerPerson.ATT_I_TYPE_01, TaPerPerson.TYP_01_MORAL);
		attr.put(TaPerPerson.ATT_I_TYPE_02, TaPerPerson.TYP_02_PRODUCER);
		attr.put(TaPerPerson.ATT_D_DATE_02, new Date());
		
		TaPerPerson.DAO.doMerge(ent, attr);
		cache_entity.reqPut(entId+"", ent);
		
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static CacheData<TaPerPerson> 			cache_entity		= new CacheData<TaPerPerson>			(500, DefTime.TIME_24_00_00_000 );
	private static CacheData<TaTpyInformation> 		cache_entity_inf 	= new CacheData<TaTpyInformation>		(500, DefTime.TIME_24_00_00_000 );
	private static CacheData<List<TaPerPerson>> 	cache_lst 			= new CacheData<List<TaPerPerson>>		(500, DefTime.TIME_24_00_00_000 );
	private static CacheData<ResultPagination> 		cacheEnt_rs 		= new CacheData<ResultPagination>		(100, DefTime.TIME_02_00_00_000	);
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
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
		TaPerPerson  	ent	 	= TaPerPerson.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		TaPerPerson.DAO		.doRemove (ent);
		cache_entity.reqDel(entId+"");
		
		//---we have to check T_Aut_Right in AutRole + AutAuthorization?

		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaPerPerson 		ent 		= reqGet(entId, forced, forManager);

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
	
	public static TaPerPerson reqGet(Integer entId, Boolean forced, boolean forManager) throws Exception{
		String 			key		= entId+"";
		TaPerPerson 	ent 	= cache_entity.reqData(key);	
		if (forced || ent ==null) {
			ent 	= TaPerPerson.DAO.reqEntityByRef(entId, forced);
			
			if (ent!=null) {
				//---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqGet use cache-----");
			cache_entity.reqCheckIfOld(key); //cache in 20 hour
		}
		if (ent!=null){		
			ent.doBuildDocuments(forced);
			ent.doBuildAvatar	(forced);
		}
		return ent;
	}
	public static TaTpyInformation reqGetInfor(Integer entId, Boolean forced, boolean forManager) throws Exception{
		String 				key		= entId+"";
		TaTpyInformation 	ent 	= cache_entity_inf.reqData(key);	
		if (forced || ent ==null) {
			ent 	= TaTpyInformation.DAO.reqEntityByRef(entId, forced);
			
			if (ent!=null) {
				//---do something and put to cache
				cache_entity_inf.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqGet use cache-----");
			cache_entity_inf.reqCheckIfOld(key); //cache in 20 hour
		}
		if (ent!=null){		
			ent.doBuildDocuments(forced);
		}
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViPerPersonDyn> 	list = reqLst(user, json); //and other params if necessary
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

	private static List<ViPerPersonDyn> reqLst(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, Integer.MAX_VALUE);
		Set<String> 		searchKey	= ToolData.reqSetStr	(json, "searchkey"	, null);
		Set<Integer>		stats		= ToolData.reqSetInt	(json, "stat"		, null);
		Set<Integer>		typ01		= ToolData.reqSetInt	(json, "typ01"		, null);
		Set<Integer>		typ02		= ToolData.reqSetInt	(json, "typ02"		, null);
		
		
		if(typ01 == null && typ02==null && stats ==null) {
			return null;
		}

		Criterion cri				= reqRestriction (user, searchKey, stats, typ01, typ02);	
		List<ViPerPersonDyn>	list	= ViPerPersonDyn.DAO.reqList(0, nbLine, cri);	

		return list;
	}
	
	//---------------------------------------------------------------------------------------------------------		
	public static ResultPagination reqLstPage(TaAutUser user, JSONObject json) throws Exception {
		String				searchKey		= ToolData.reqStr		(json, "searchKey"	, null);
		Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
		Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
		Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);

		Set<Integer>		stat01			= ToolData.reqSetInt	(json, "stat01"		, null);
		Set<Integer>		stat02			= ToolData.reqSetInt	(json, "stat02"		, null);
		Set<Integer>		typ01			= ToolData.reqSetInt	(json, "typ01"		, null);

		ResultPagination 	rs 				= null;

		if (!canWorkWithObj(user, WORK_LST, null, stat01)){ //other param after objTyp...
			return null;
		}

		Criterion 	cri 		= reqRestrictionSearch(user, searchKey, stat01, stat02, typ01);				

		List<TaPerPerson> list 	= TaPerPerson.DAO.reqList(begin, number, cri);
		TaPerPerson.doBuildAvatarForList(list);
		//			TaPerPerson.doBuildPer(list);
		//			ViAutUserDyn.doBuildAvatarForList(list);

		if (total == 0 ) {
			total				= TaPerPerson.DAO.reqCount(cri).intValue();
		}
		rs						= new ResultPagination(list, total, begin, number);

		return rs;
	}
	private static Criterion reqRestrictionSearch(TaAutUser user, String searchKey, Set<Integer> stat01, Set<Integer> stat02, Set<Integer> typ01) {
		if (stat01 == null){
			stat01 = new HashSet<Integer>() ; 
			stat01.add(TaPerPerson.STAT_01_ACTIVE_LV_01);
		}
				
		Criterion cri 	= Restrictions.in(TaPerPerson.ATT_I_STATUS_01	, stat01);
		
		if (typ01!=null)
		cri 			= Restrictions.and(cri, 
						  Restrictions.in(TaPerPerson.ATT_I_TYPE_01		, typ01));
		
		if (stat02!=null)
			cri 		= Restrictions.and(cri, 
						  Restrictions.in(TaPerPerson.ATT_I_STATUS_02	, stat02));
		
		cri 			= Restrictions.and(	cri, Restrictions.in(TaPerPerson.ATT_I_TYPE_02 , TaPerPerson.TYP_02_INTERNAL_DOCTOR));
		
		if (searchKey!=null) {
			searchKey = '%' + searchKey + '%';
				cri = Restrictions.and(	cri, Restrictions.or(
						Restrictions.ilike(TaPerPerson.ATT_T_NAME_01, searchKey), 
						Restrictions.ilike(TaPerPerson.ATT_T_NAME_02, searchKey), 
						Restrictions.ilike(TaPerPerson.ATT_T_INFO_03, searchKey),
						Restrictions.ilike(TaPerPerson.ATT_T_INFO_02, searchKey))
				);
		}
		
//		cri = Restrictions.and(cri, Restrictions.eq(ViAutUserDyn.ATT_I_PER_MANAGER, user.reqPerManagerId()));
		return cri;
	}

	private static Criterion reqRestriction(TaAutUser user, Set<String> searchKey, Set<Integer> stats, Set<Integer> typ01, Set<Integer> typ02) throws Exception {		
		if (stats == null){
			stats = new HashSet<Integer>() ; 
			stats.add(ViPerPersonDyn.STAT_01_ACTIVE_LV_01);
		}
		
		Criterion cri = Restrictions.in(ViPerPersonDyn.ATT_I_STATUS_01, stats);
		
		if(typ01!=null) {
			cri = Restrictions.and(	cri, Restrictions.in(ViPerPersonDyn.ATT_I_TYPE_01 , typ01));
		}
		
		if(typ02!=null) {
			cri = Restrictions.and(	cri, Restrictions.in(ViPerPersonDyn.ATT_I_TYPE_02 , typ02));
		}

		if (searchKey!=null) {
			for (String s : searchKey){
				cri = 	 Restrictions.and	(cri, Restrictions.or(
										Restrictions.ilike(ViPerPersonDyn.ATT_T_NAME_01	, s), 
										Restrictions.ilike(ViPerPersonDyn.ATT_T_NAME_02	, s), 
										Restrictions.ilike(ViPerPersonDyn.ATT_T_NAME_03	, s), 
										Restrictions.ilike(ViPerPersonDyn.ATT_T_CODE_01	, s), 
										Restrictions.ilike(ViPerPersonDyn.ATT_T_CODE_02	, s), 
										Restrictions.ilike(ViPerPersonDyn.ATT_T_CODE_03	, s),
										Restrictions.ilike(ViPerPersonDyn.ATT_T_INFO_01	, s),//email
										Restrictions.ilike(ViPerPersonDyn.ATT_T_INFO_02	, s))//phone 
										);
			}
		}
		
		Integer manId = user.reqPerManagerId();
		if (!user.canBeSuperAdmin()) 
			cri = Restrictions.and(	cri, Restrictions.in(ViPerPersonDyn.ATT_I_PER_MANAGER , manId));
		
		return cri;
	}

	
	private static CacheData<ResultPagination>		cache_rs 			= new CacheData<ResultPagination>	(100, DefTime.TIME_00_30_00_000);
	private static void doLstPage(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination  	res = reqLstPage(user, json, response);
		if (res.reqList()==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response,ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1,  
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res
				));	
	}
	
	private static 	ResultPagination reqLstPage(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		Integer 		manId 		= user.reqPerManagerId();
		Integer 		uId			= user.reqId();


		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 10);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);
		String 			searchKey   = ToolData.reqStr	(json, "searchKey"	, null);
		
		Integer			typ01		= ToolData.reqInt	(json, "typ01"		, null); 
		Integer			typ02		= ToolData.reqInt	(json, "typ02"		, null); 
		
		Set<Integer>	stats		= ToolData.reqSetInt(json, "stats"		, null);
		
		Boolean 		wAva 		= ToolData.reqBool	(json, "wAva"		, true);
		
		Boolean 		forced 		= ToolData.reqBool	(json, "forced"		, false);

		Boolean 		isAll 		= ToolData.reqBool	(json, "isAll"		, false);

		if (uId==null) return null;

		if(isAll) uId = null;

		String keyWord 	= manId + "_" + uId + "_" + begin + "_" + number + "_" + total + "_" + searchKey + "_" + wAva + "_" + isAll+ "_" + typ01 + "_" + typ02;

		ResultPagination rs =	null;
		if(!forced) 	 rs =   cache_rs.reqData(keyWord);
				
		if(Boolean.TRUE.equals(forced) || rs == null){
			Criterion cri 	= Restrictions.gt(TaPerPerson.ATT_I_ID,0);
			
			if (searchKey!=null) {
				searchKey = '%' + searchKey + '%';
				cri = Restrictions.and(	cri, Restrictions.or(
						Restrictions.ilike(TaPerPerson.ATT_T_NAME_01, searchKey),
						Restrictions.ilike(TaPerPerson.ATT_T_NAME_02, searchKey),
						Restrictions.ilike(TaPerPerson.ATT_T_NAME_03, searchKey))
				);
			}
			
			if(typ01 != null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaPerPerson.ATT_I_TYPE_01, typ01));

			if(typ02 != null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaPerPerson.ATT_I_TYPE_02, typ02)); 

			if(manId != null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaPerPerson.ATT_I_PER_MANAGER, manId));
			
			if(stats != null)
				cri = 	Restrictions.and(cri, Restrictions.in(TaPerPerson.ATT_I_STATUS_01, stats));	

			List<TaPerPerson> prjList = TaPerPerson.DAO.reqList(begin, number, cri);
			if (wAva) 	TaPerPerson.doBuildAvatarForList(prjList);

			if (prjList==null || prjList.isEmpty() ){
				rs								= new ResultPagination(prjList, 0, 0, 0);
			}else {
				if (total == 0 )	total		= TaPerPerson.DAO.reqCount(cri).intValue();
				rs								= new ResultPagination(prjList, total, begin, number);
			}

			cache_rs.reqPut(keyWord, rs);			
		} else {
			ToolLogServer.doLogInf("---reqViPrjLst use cache-----");
			cacheEnt_rs.reqCheckIfOld(keyWord); 
		}

		return rs;

	}
}
