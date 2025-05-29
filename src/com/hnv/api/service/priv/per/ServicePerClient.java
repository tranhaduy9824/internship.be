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
public class ServicePerClient implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 
	

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 					= "HNV".toLowerCase();

	public static final String SV_CLASS 					= "ServicePerClient".toLowerCase();	
	
	public static final String SV_GET 						= "SVGet"		.toLowerCase();	
	public static final String SV_LST 						= "SVLst"		.toLowerCase();
	public static final String SV_LST_DYN					= "SVLstDyn"	.toLowerCase();
	public static final String SV_LST_PAGE					= "SVLstPage"	.toLowerCase(); 
		
	

	public static final String SV_NEW 						= "SVNew"		.toLowerCase();	
	public static final String SV_MOD 						= "SVMod"		.toLowerCase();	
	public static final String SV_DEL 						= "SVDel"		.toLowerCase();
	
	public static final String SV_GET_HIST_DISEASE			= "SVGetHistDisease".toLowerCase();	
	public static final String SV_MOD_HIST_DISEASE			= "SVModHistDisease".toLowerCase();	
	
	public static final String SV_GET_HIST_MEDICINE			= "SVGetHistMedicine".toLowerCase();	
	public static final String SV_GET_HIST_MEDICINE_BY_ID	= "SVGetHistMedicineById".toLowerCase();
	public static final String SV_MOD_HIST_MEDICINE			= "SVModHistMedicine".toLowerCase();	
	public static final String SV_LST_HIST_MEDICINE_BY_PARID= "SVLstByParId".toLowerCase();
	
	public static final String SV_MOD_WORK_GROUP			= "SVModWorkGroup"	.toLowerCase(); 
	
	public static final String SV_LCK_REQ 					= "SVLckReq"	.toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 					= "SVLckSav"	.toLowerCase(); //save and continue
	public static final String SV_LCK_END 					= "SVLckEnd"	.toLowerCase();
	public static final String SV_LCK_DEL 					= "SVLckDel"	.toLowerCase();
	
	public static final Integer	ENT_TYP						= DefDBExt.ID_TA_PER_PERSON;
		
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServicePerClient(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			//---------------------------------------------------------------------------------
			
			if(sv.equals(SV_LCK_REQ)					&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckReq(user,  json, response);
			} else if(sv.equals(SV_LCK_SAV)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckSav(user,  json, response);
			} else if(sv.equals(SV_LCK_END)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckEnd(user,  json, response);
			} else if(sv.equals(SV_LCK_DEL)				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckDel(user,  json, response);
				
			} else if(sv.equals(SV_NEW)					&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doNew(user,  json, response);
			
			} else if(sv.equals(SV_MOD) 				&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doMod(user,  json, response);
			
			
			
			} else  if(sv.equals(SV_DEL)				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_PER_CLIENT_DEL) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doDel(user,  json, response);
			
			
			} else if(sv.equals(SV_GET) 				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGet(user,  json, response);
							
			} else if(sv.equals(SV_LST)					&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLst(user,  json, response);
				
				
			} else if(sv.equals(SV_LST_PAGE)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_GET) 
					|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstPage(user,  json, response);
			
			} else if(sv.equals(SV_MOD_WORK_GROUP)		&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGroupWorkSaveMember(user, json, response);
			
			} else if(sv.equals(SV_GET_HIST_DISEASE) 	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_HIST_GET)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGetHistDisease(user,  json, response);
			} else if(sv.equals(SV_MOD_HIST_DISEASE) 	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_HIST_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doModHistDisease(user,  json, response);
				
			} else if(sv.equals(SV_GET_HIST_MEDICINE) 	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_HIST_GET)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGetHistMedicine(user,  json, response);
			} else if(sv.equals(SV_GET_HIST_MEDICINE_BY_ID) && (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_PER_CLIENT_HIST_GET)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGetHistMedicineById(user,  json, response);
			} else if(sv.equals(SV_MOD_HIST_MEDICINE) 	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_HIST_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doModHistMedicine(user,  json, response);
			} else if(sv.equals(SV_LST_HIST_MEDICINE_BY_PARID) 	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_PER_CLIENT_HIST_MOD)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doModHistMedicine(user,  json, response);	
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

		TaPerPerson.DAO.doMerge(ent, attr);
		cache_entity.reqPut(entId+"", ent);
		
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	private static void doGetHistDisease(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaTpyInformation  	ent	 	=  reqGetHistDisease(user, json); 								
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
	private static TaTpyInformation reqGetHistDisease(TaAutUser user,  JSONObject json) throws Exception {
		Integer				perId 	= ToolData.reqInt 	(json, "perId"	, null);
		
		TaPerPerson 		ent 	= TaPerPerson.DAO.reqEntityByRef(perId);
		if (ent==null){
			return null;
		}
		
		TaTpyInformation inf		= TaTpyInformation.DAO.reqEntityByValues(
										TaTpyInformation.ATT_I_ENTITY_ID	, perId,
										TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON,
										TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_DISEASE
									);
		return inf;
	}	
	
	//---------------------------------------------------------------------------------------------------------
	private static void doModHistDisease(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaTpyInformation  	ent	 		=  reqModHistDisease(user, json); 								
		if (ent==null){
			API.doResponse(response		, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
			));	
		}		
	}

	private static TaTpyInformation reqModHistDisease(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson 	(json, "obj"	, null);
		Integer				perId 	= ToolData.reqInt 	(json, "perId"	, null);
		
		TaPerPerson 		ent 	= TaPerPerson.DAO.reqEntityByRef(perId);
		if (ent==null){
			return null;
		}
		
		TaTpyInformation inf		= TaTpyInformation.DAO.reqEntityByValues(
										TaTpyInformation.ATT_I_ENTITY_ID	, perId,
										TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON,
										TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_DISEASE
									);
		
		Map<String, Object> attr 					= API.reqMapParamsByClass(obj, TaTpyInformation.class);
		attr.put(TaTpyInformation.ATT_I_ENTITY_ID	, perId);
		attr.put(TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON);
		attr.put(TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_DISEASE);
		
		attr.put(TaTpyInformation.ATT_D_DATE_02		, new Date());
		attr.put(TaTpyInformation.ATT_I_AUT_USER_02	, user.reqId());
		
		if (inf==null) {
			attr.put(TaTpyInformation.ATT_D_DATE_01		, new Date());
			attr.put(TaTpyInformation.ATT_I_AUT_USER_01	, user.reqId());
			
			inf = new TaTpyInformation(attr);
			TaTpyInformation.DAO.doPersist(inf);
		}else {
			TaTpyInformation.DAO.doMerge(inf, attr);
		}
		cache_entity_inf.reqPut(inf.reqId()+"", inf);
		return inf;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGetHistMedicine(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination  	res 	= reqGetHistMedicine(user, json, response);
		if (res.reqList()==null ){
			API.doResponse(response	, DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response		, ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1,  
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res
				));	
	}
	
	private static 	ResultPagination reqGetHistMedicine(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		Integer			perId 		= ToolData.reqInt 	(json, "perId"		, null);
		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 10);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);

		if (perId==null) return null;

		ResultPagination rs =	null;
		Criterion cri = Restrictions.and(
				Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_ID	, perId),
				Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON),
				Restrictions.eq(TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_MEDICINE)
				);
		
		List<TaTpyInformation> lst			= TaTpyInformation.DAO.reqList(begin, number, Order.desc(TaTpyInformation.ATT_D_DATE_01), cri);

		if (lst==null || lst.size()==0 ){
			rs								= new ResultPagination(lst, 0, 0, 0);
		}else {
			if (total == 0 )	total		= TaTpyInformation.DAO.reqCount(cri).intValue();
			rs								= new ResultPagination(lst, total, begin, number);
		}

		return rs;
	}
	private static CacheData<TaTpyInformation> 		cache_entity_inf = new CacheData<TaTpyInformation>		(500, DefTime.TIME_24_00_00_000 );
	private static void doGetHistMedicineById(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");
		Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaTpyInformation 		ent 		= reqGetInfor(entId, forced, forManager);

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
	//---------------------------------------------------------------------------------------------------------
	private static void doModHistMedicine(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaTpyInformation  	ent	 		=  reqModHistMedicine(user, json); 								
		if (ent==null){
			API.doResponse(response		, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}		
	}

	private static TaTpyInformation reqModHistMedicine(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson 	(json, "obj"	, null);
		
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaTpyInformation.class);
		
		attr.put(TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON);
		attr.put(TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_MEDICINE);

	//	attr.put(TaTpyInformation.ATT_D_DATE_02		, new Date());
		attr.put(TaTpyInformation.ATT_I_AUT_USER_02	, user.reqId());

		TaTpyInformation 	inf 	= new TaTpyInformation(attr);
		Integer				infId	= inf.reqId();
		Integer				perId	= inf.reqInt(TaTpyInformation.ATT_I_ENTITY_ID);
		
		if (perId == null) return null;
		
		if (infId==null) {
			attr.put(TaTpyInformation.ATT_I_AUT_USER_01	, user.reqId());

			inf = new TaTpyInformation(attr);
			TaTpyInformation.DAO.doPersist(inf);
		}else {
			inf	= TaTpyInformation.DAO.reqEntityByValues(
					TaTpyInformation.ATT_I_ID			, infId,
					TaTpyInformation.ATT_I_ENTITY_ID	, perId,
					TaTpyInformation.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_PER_PERSON,
					TaTpyInformation.ATT_I_TYPE_01		, TaTpyInformation.TYPE_01_PATIENT_HIST_MEDICINE
					);
			
			if (inf==null) return null;
			
			TaTpyInformation.DAO.doMerge(inf, attr);
		}
		cache_entity_inf.reqPut(inf.reqId()+"", inf);
		return inf;
	}
	
	private static void doHisDiseaseSub(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
//		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");
//
		Integer 			pId		= ToolData.reqInt	(json, "id"				, -1	);
		

		List<TaTpyCategory> 		ent 	= reqHisDiseaseSub(pId);

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
	private static List<TaTpyCategory> reqHisDiseaseSub(Integer pId) throws Exception{

		List<TaTpyCategory> lst 	= TaTpyCategory.DAO.reqList(Restrictions.eq(TaTpyCategory.ATT_I_PARENT, pId));
		
		return lst;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGroupWorkSaveMember(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		Integer 				userId	= ToolData.reqInt		(json, "userId"	, null);	
		JSONArray				grps 	= ToolData.reqJsonArr	(json, "grps", new JSONArray()) ;

		if(userId == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaAutUser u = TaAutUser.DAO.reqEntityByRef(userId);
		if( userId == null ) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if(grps.size() == 0)	return; //---at least one group

		List<TaNsoGroupMember> mems = TaNsoGroupMember.DAO.reqList(Restrictions.eq(TaNsoGroupMember.ATT_I_AUT_USER		, userId));

		Map<Integer, TaNsoGroupMember> mapExist = new HashMap<Integer, TaNsoGroupMember>();
		for(TaNsoGroupMember m: mems) {
			mapExist.put((Integer) m.req(TaNsoGroupMember.ATT_I_NSO_GROUP), m);
		}

		List<TaNsoGroupMember> lstRE 	= new ArrayList<TaNsoGroupMember>();
		List<TaNsoGroupMember> lstMOD 	= new ArrayList<TaNsoGroupMember>();
		List<TaNsoGroupMember> lstDEL 	= new ArrayList<TaNsoGroupMember>();

		for(int i = 0; i < grps.size(); i++) {
			JSONObject mem = (JSONObject) grps.get(i);
			JSONObject  grp 	= ToolData.reqJson(mem, "mem", null)	;
			Map<String, Object> attr 	= API.reqMapParamsByClass(mem, TaNsoGroupMember.class);
			
		//	Integer gId 				= (Integer) attr.get(TaNsoGroupMember.ATT_I_NSO_GROUP);
			Integer 				gId	= ToolData.reqInt		(grp, "id"	, null);	
			TaNsoGroupMember re 		= new TaNsoGroupMember(attr);

			if(mapExist.containsKey(gId)) {	
				lstMOD.add(mapExist.get(gId));
				mapExist.remove(gId);
				continue;
			}
			re.reqSet(TaNsoGroupMember.ATT_I_TYPE		, TaNsoGroupMember.TYP_MEMBER_LEV_01);
			re.reqSet(TaNsoGroupMember.ATT_I_AUT_USER	, userId);
			re.reqSet(TaNsoGroupMember.ATT_I_NSO_GROUP	, gId);
			re.reqSet(TaNsoGroupMember.ATT_I_STATUS		, TaNsoGroupMember.STAT_ACTIVE);
			re.reqSet(TaNsoGroupMember.ATT_D_DATE_01	, new Date());

			lstRE.add(re);
		}

		if(lstRE.size() > 0)	TaNsoGroupMember.DAO.doPersist(lstRE);
		//if(lstMOD.size() > 0) 	TaNsoGroupMember.DAO.doMerge(lstMOD);

		if(!mapExist.isEmpty()) {
			for (Map.Entry<Integer, TaNsoGroupMember> entry : mapExist.entrySet()) {
				lstDEL.add(entry.getValue());
			}
			TaNsoGroupMember.DAO.doRemove(lstDEL);
		}

		API.doResponse(response	, ToolJSON.reqJSonString(
				// filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES
				));
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
	private static CacheData<TaPerPerson> 		cache_entity= new CacheData<TaPerPerson>		(500, DefTime.TIME_24_00_00_000 );
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
//			ent.doBuildPerson	(forced);
//			ent.doBuilWorkGroup	(forced);
//			ent.doBuilAuth		(forced, null);
//			ent.doBuilAuths		(forced);			
//			ent.doBuildManager	(forced);	
//			ent.doBuildSuperior	(forced);
//			ent.doBuildHistoryConnection(forced);
			
//			ent.doHidePwd();
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
//			ent.doBuildAvatar	(forced);
//			ent.doBuildPerson	(forced);
//			ent.doBuilWorkGroup	(forced);
//			ent.doBuilAuth		(forced, null);
//			ent.doBuilAuths		(forced);			
//			ent.doBuildManager	(forced);	
//			ent.doBuildSuperior	(forced);
//			ent.doBuildHistoryConnection(forced);
			
//			ent.doHidePwd();
		}
		return ent;
	}
	
	//---------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------
	private static CacheData<List<TaPerPerson>> 	cache_lst 		= new CacheData<List<TaPerPerson>>();	
	
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
	
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
	    {
	    	put("action"	, -1);
	    	put("id"		, 0 );
	    	put("name01"	, 1 );
	    	put("name02"	, 2 );
	    	put("name03"	, 3 );
	    	put("code01"	, 4 );
	    	put("code02"	, 5 );
	    	put("code03"	, 6 );
	    }
	};
	
	private static CacheData<ResultPagination> cacheEnt_rs = new CacheData<ResultPagination>(100, DefTime.TIME_02_00_00_000);
	private static void doLstDoctor(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		ResultPagination  	res = reqLstPage(user, json); //and other params if necessary
		
		if (res.reqList()==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, res));
	}

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

	private static List<ViPerPersonDyn> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<ViPerPersonDyn> list 	= new ArrayList<ViPerPersonDyn>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = ViPerPersonDyn.ATT_I_ID; break;		
		case 1: colName = ViPerPersonDyn.ATT_T_NAME_01; break;	
		case 2: colName = ViPerPersonDyn.ATT_T_NAME_02; break;	
		case 3: colName = ViPerPersonDyn.ATT_T_NAME_03; break;	
		case 4: colName = ViPerPersonDyn.ATT_T_CODE_01; break;	
		case 5: colName = ViPerPersonDyn.ATT_T_CODE_02; break;	
		case 6: colName = ViPerPersonDyn.ATT_T_CODE_03; break;	
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;	
			}
		}

		if (order==null)
			list	= ViPerPersonDyn.DAO.reqList(begin, number, cri);
		else
			list	= ViPerPersonDyn.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbListDyn() throws Exception {						
		return ViPerPersonDyn.DAO.reqCount();		
	}
	
	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return ViPerPersonDyn.DAO.reqCount(cri);
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

			List<TaPerPerson> prjList = TaPerPerson.DAO.reqList(0, number, cri);
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
