package com.hnv.api.service.priv.mat;

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
import com.hnv.db.mat.TaMatMaterial;
import com.hnv.db.mat.vi.ViMatMaterialDyn;

public class ServiceMatMaterial implements IService {
	//--------------------------------Service Definition----------------------------------
		public static final String SV_MODULE 					= "EC_V3".toLowerCase();

		public static final String SV_CLASS 					= "ServiceMatMaterial".toLowerCase();	

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
		
		
		
		public static final Integer	ENT_TYP						= DefDBExt.ID_TA_MAT_MATERIAL;
		//-----------------------------------------------------------------------------------------------
		//-------------------------Default Constructor - Required -------------------------------------
		public ServiceMatMaterial(){
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
				
				} else if(sv.equals(SV_LST_SEARCH)		&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_AUT_USER_GET)
														||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
					doLstSearch(user,  json, response);
					
				} else if(sv.equals(SV_NEW)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_AUT_USER_NEW) 
														|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
					doNew(user, json, response);

				} else if(sv.equals(SV_MOD)				&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_AUT_USER_MOD) 
														||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
					doMod(user, json, response);
				
				} else if(sv.equals(SV_DEL)			&&  (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_AUT_USER_DEL)
														||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
					doDel(user, json, response);		
					
				} else {
					API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
				}
			} catch (Exception e) {
				API.doResponse(response, DefAPI.API_MSG_ERR_API);
				ToolLogServer.doLogErr("-----Exception in SVMatMaterialMatMaterial-----:" + e.getMessage());
				ToolLogServer.doLogErr(e, true);
//				e.printStackTrace();
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
//				TaAutUser post 	= (TaAutUser) params[0];
//				Integer   uId 	= post.reqInt(post, TaAutUser.ATT_I_AUT_USER_NEW);
//				if (uId!=null && uId.equals(user.reqUserId())) return true;
//				return false;
				
				return true;
			case WORK_UPL : 
				//check something with params
				return true;
			}
			return false;
		}
		//---------------------------------------------------------------------------------------------------------
		private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
			//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

			Integer 			entId		= ToolData.reqInt	(json, "id"			, -1	);				
			Boolean				forced		= ToolData.reqBool	(json, "forced"		, true	);
//			Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

			TaMatMaterial 			ent 		= reqGet(entId, forced);

			if (ent==null){
				API.doResponse(response, DefAPI.API_MSG_KO);
				return;
			}

			if (!canWorkWithObj(user, WORK_GET, ent)){
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
				return;
			}

			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent 
					));
		}
		
		private static CacheData<TaMatMaterial> 		cache_entity= new CacheData<TaMatMaterial>		(500, DefTime.TIME_24_00_00_000 );
		public static TaMatMaterial reqGet(Integer entId, Boolean forced) throws Exception{
			String 			key		= entId+"";
			TaMatMaterial 		ent 	= cache_entity.reqData(key);	
			
			if (forced || ent == null) {
				ent 	= TaMatMaterial.DAO.reqEntityByRef(entId);
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
				ent.doBuildDetails	(forced);
			}

			return ent;
		}
		
		private static CacheData<TaMatMaterial> 		cache_simple= new CacheData<TaMatMaterial>		(500, DefTime.TIME_24_00_00_000 );
		
		//---------------------------------------------------------------------------------------------------------		
		private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
			{
				put("action"	, -1);
				put("id"		, 0 );
				put("login01"	, 1 );
				put("inf01"		, 2 );
			}
		};
		
		private static void doLstSearch(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
			ResultPagination  	res = reqListSearch(user, json); //and other params if necessary
			
			ToolLogServer.doLogInf("---reqListSearch use cache-----" + res);
			
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
			String				searchKey		= ToolData.reqStr		(json, "searchKey"	, "");
			Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
			Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
			Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);
			
			Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"		, null);
			Set<Integer>		typs			= ToolData.reqSetInt	(json, "typs"		, null);
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

				List<ViMatMaterialDyn> list 		= ViMatMaterialDyn.DAO.reqList(begin, number, cri);
				
				ViMatMaterialDyn.doBuildAvatarForList(list);
				
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
			Integer result = ViMatMaterialDyn.reqCountMaterialFilter(cri).intValue();
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
				return Restrictions.gt(ViMatMaterialDyn.ATT_I_ID, 0);
			}

        	Criterion cri = Restrictions.gt(ViMatMaterialDyn.ATT_I_ID, 0);

			if (stats == null){
				stats = new HashSet<>() ; 
				stats.add(ViMatMaterialDyn.STAT_ACTIVE);
			}
			cri = Restrictions.and(cri, Restrictions.in(ViMatMaterialDyn.ATT_I_STATUS, stats));
			
			if (typs != null){
				cri = Restrictions.and(	cri, Restrictions.in(ViMatMaterialDyn.ATT_I_TYPE_03, typs));
			}

			if (searchKey != null) {
				searchKey = searchKey.replace("*", "%");
				searchKey = '%' + searchKey + '%';
				searchKey = searchKey.replace("/%", "");

			//	cri = Restrictions.and(	cri, Restrictions.ilike(ViAutUserDyn.ATT_T_INFO_03, searchKey));
				cri = Restrictions.and(	cri, Restrictions.or(
						Restrictions.ilike(ViMatMaterialDyn.ATT_T_NAME_01, searchKey), 
						Restrictions.ilike(ViMatMaterialDyn.ATT_T_CODE_01, searchKey)
				));
			}
			
			return cri;
		}
		
		//---------------------------------------------------------------------------------------------------------
		private static void doNew (TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
			TaMatMaterial 			ent				= reqNew		(user, json);
			if (ent==null){
				API.doResponse(response, ToolJSON.reqJSonString(
						DefJS.SESS_STAT		, 1, 
						DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
						));
				return;
			}

			TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaMatMaterial.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
			API.doResponse(response, ToolJSON.reqJSonString(		//filter,
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent,
					"lock"				, lock
					));
		}

		private static TaMatMaterial reqNew(TaAutUser user,  JSONObject json) throws Exception {
			JSONObject		obj			= ToolData.reqJson(json, "obj", null);
			//--------------------------------------------------------------------------------------------
			Map<String, Object> attr = API.reqMapParamsByClass(obj	, TaMatMaterial.class);

			String code01 = (String) attr.get(TaMatMaterial.ATT_T_CODE_01);
			if (code01 != null && TaMatMaterial.DAO.reqEntityByValue(TaMatMaterial.ATT_T_CODE_01, code01) != null) {
				return null; //code already exists
			}

			attr.put(TaMatMaterial.ATT_I_PER_MANAGER, user.reqPerManagerId());
			attr.put(TaMatMaterial.ATT_I_STATUS_01, TaMatMaterial.STAT_ACTIVE);

			TaMatMaterial ent = new TaMatMaterial(attr);
			TaMatMaterial.DAO.doPersist(ent);

			JSONArray docs = (JSONArray) obj.get("files");
			if (docs != null) {
				ent.reqSet(TaMatMaterial.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, ent.reqId(), docs));
			}
			
			ent.doBuildAvatar(true);
			ent.doBuildDetails(true);
			ent.doBuildDocuments(true);
			ent.doBuildAvatarPath();
			TaMatMaterial.DAO.doMerge(ent);	

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

//				Session sessSub 	= TaTpyDocument	.DAO.reqSessionCurrent();
				Session sessMain 	= TaAutUser		.DAO.reqSessionCurrent();
				try {
					TaTpyDocument		.doListDel	(sessMain, ENT_TYP, entId);
//					TaTpyDocument		.doListDel(sessSub, entTyp, entId);
					
					TaAutAuthUser		.doListDel	(sessMain, entId);
					TaAutHistory		.doListDel	(sessMain, entId);
					TaTpyFavorite		.doListDel	(sessMain, entId);
					
					Integer 		perId	= ent.reqInt(TaAutUser.ATT_I_PER_PERSON);
					TaPerPerson		per		= TaPerPerson.DAO.reqEntityByID(sessMain, perId);
					if (per!=null)	TaPerPerson.DAO.doRemove(sessMain, per);
					
					TaAutUser.DAO		.doRemove 	(sessMain, ent);
					cache_entity.reqDel(entId+"");
					
					TaAutUser			.DAO.doSessionCommit(sessMain);
//					TaTpyDocument		.DAO.doSessionCommit(sessSub);
				}catch(Exception e){
					e.printStackTrace();
					TaAutUser			.DAO.doSessionRollback(sessMain);
//					TaTpyDocument		.DAO.doSessionRollback(sessSub);
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
		
		private static CacheData<ResultPagination> cacheEnt_rs = new CacheData<ResultPagination>(100, DefTime.TIME_02_00_00_000);
}
