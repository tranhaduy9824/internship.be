package com.hnv.api.service.priv.tpy;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefDB;
import com.hnv.api.def.DefJS;
import com.hnv.api.def.DefTime;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.api.service.common.APIAuth;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.aut.TaAutAuthUser;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.nso.TaNsoGroup;
import com.hnv.db.nso.TaNsoPost;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyCategory;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.vi.ViTpyCategoryDyn;
import com.hnv.def.DefDBExt;
import com.hnv.def.DefRight;


/**
 * ----- ServiceTpyCategory by H&V
 * ----- Copyright 2017------------
 */
public class ServiceTpyCategory implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 				= "EC_V3".toLowerCase();

	public static final String SV_CLASS 				= "ServiceTpyCategory".toLowerCase();	

	public static final String SV_GET 					= "SVGet"		.toLowerCase();	
	public static final String SV_LST 					= "SVLst"		.toLowerCase();
	public static final String SV_LST_SEARCH			= "SVLstSearch"	.toLowerCase(); 
	public static final String SV_LST_DYN				= "SVLstDyn"	.toLowerCase();
	public static final String SV_LST_PAGE				= "SVLstPage"	.toLowerCase();

	public static final String SV_LST_TEST_BLOOD		= "SVLstPageTestBlood"	.toLowerCase();
	public static final String SV_LST_TEST_IMG			= "SVLstPageTestImg"	.toLowerCase();

	public static final String SV_NEW 					= "SVNew".toLowerCase();	
	public static final String SV_MOD 					= "SVMod".toLowerCase();	
	public static final String SV_DEL 					= "SVDel".toLowerCase();

	public static final String SV_NEW_DISEASE 			= "SVNewDisease".toLowerCase();	
	public static final String SV_MOD_DISEASE 			= "SVModDisease".toLowerCase();	
	public static final String SV_DEL_DISEASE 			= "SVDelDisease".toLowerCase();
	public static final String SV_GET_DISEASE 			= "SVGetDisease".toLowerCase();	

	public static final String SV_NEW_TEST_BLOOD 		= "SVNewTestBlood".toLowerCase();	
	public static final String SV_MOD_TEST_BLOOD 		= "SVModTestImg".toLowerCase();	
	public static final String SV_DEL_TEST_BLOOD 		= "SVDelTestImg".toLowerCase();

	public static final String SV_NEW_TEST_IMG 			= "SVNewTestImg".toLowerCase();	

	public static final String SV_NEW_DISEASE_SUB 		= "SVNewDiseaseSub".toLowerCase();
	public static final String SV_GET_DISEASE_SUB 		= "SVGetDiseaseSub".toLowerCase();

	public static final String SV_DUPLICATE 			= "SVDuplicate".toLowerCase();

	public static final String SV_LCK_REQ 				= "SVLckReq".toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 				= "SVLckSav".toLowerCase(); //save and continue
	public static final String SV_LCK_END 				= "SVLckEnd".toLowerCase();
	public static final String SV_LCK_DEL 				= "SVLckDel".toLowerCase();

	public static final String SV_LST_CAT 				= "SVLstCat".toLowerCase();
	public static final String SV_LST_GROUP  			= "SVLstGroup".toLowerCase();

	public static final Integer	ENT_TYP					= DefDBExt.ID_TA_TPY_CATEGORY;
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceTpyCategory(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
		if(filePath == null) {
			filePath 	= API.reqContextParameter("PATH_FILE");
		}
		if (urlPath==null) {
			urlPath 	= API.reqContextParameter("PATH_URL");
		}
	}

	//-----------------------------------------------------------------------------------------------
	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			if(sv.equals(SV_GET) 				){
				doGet(user, json, response);
			} else if(sv.equals(SV_LST)			){
				doLst(user, json, response);
			} else if(sv.equals(SV_LST_SEARCH)	){
				doLstSearch(user, json, response);
			} else if(sv.equals(SV_LST_DYN)		){
				doLstDyn(user,  json, response);

			} else if(sv.equals(SV_LST_PAGE)	){
				doLstPage(user, json, response);

			} else if(sv.equals(SV_LST_TEST_BLOOD)	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_BLOOD_GET)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstPageTestBlood(user, json, response);

			} else if(sv.equals(SV_LST_TEST_IMG)	&& (APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_IMG_GET)
					||	APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstPageTestImg(user, json, response);

				//------------------------------------------------------------------------------------------------------------------------------------------------
			} else if(sv.equals(SV_NEW)			){
				doNew(user, json, response);
			} else if(sv.equals(SV_MOD)			){
				doMod(user, json, response);
			} else  if(sv.equals(SV_DEL)		){
				doDel(user, json, response);
			} else if(sv.equals(SV_DUPLICATE)	){
				doDuplicate(user, json, response);

				//-----------------------------------------------------------------------------------------------------------------------------------------------------
			} else if(sv.equals(SV_NEW_DISEASE)	&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_TPY_CAT_DISEASE_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNewDisease(user, json, response);

			}else if(sv.equals(SV_NEW_TEST_BLOOD)	&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_TPY_CAT_TEST_BLOOD_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNewTestBlood(user, json, response);

			}else if(sv.equals(SV_NEW_TEST_IMG)	&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_TPY_CAT_TEST_IMG_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNewTestImg(user, json, response);

			} else if(sv.equals(SV_NEW_DISEASE_SUB)	&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_TPY_CAT_DISEASE_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNewDiseaseSub(user, json, response);

			} else if(sv.equals(SV_GET_DISEASE_SUB) ){
				doGetDiseaseSub(user, json, response);

				//-----------------------------------------------------------------------------------------------------------------------------------------------------
			} else if(sv.equals(SV_LCK_REQ)		&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckReq(user, json, response);
			} else if(sv.equals(SV_LCK_SAV)		&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_TPY_CATEGORY_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckSav(user, json, response);
			} else if(sv.equals(SV_LCK_END)		&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_TPY_CATEGORY_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckEnd(user, json, response);
			} else if(sv.equals(SV_LCK_DEL)		&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_TPY_CATEGORY_DEL)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckDel(user, json, response);		

			} else if(sv.equals(SV_LST_CAT)		&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstCat(user, json, response);		

			} else if(sv.equals(SV_LST_GROUP)	&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstGroup(user, json, response);		


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
		if (params ==null || params.length==0) return true;

		TaTpyCategory 	ent 		= null;
		if (params[0] instanceof List) {
			List lst = (List)params[0];
			if (lst.size()==0) return true;

			ent = (TaTpyCategory)lst.get(0);
		}else if (params[0] instanceof TaTpyCategory) {
			ent 		= (TaTpyCategory)params[0];
		}else 
			return true;

		Integer 			entTyp01	= ent.reqInt(TaTpyCategory.ATT_I_TYPE_01);
		if (entTyp01==null) return true;

		switch(typWork){
		case WORK_LST : 
		case WORK_GET : 
			switch (entTyp01) {
			case TaTpyCategory.TYPE_01_DISEASE		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_DISEASE_GET);
			case TaTpyCategory.TYPE_01_TEST_IMG		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_IMG_GET);
			case TaTpyCategory.TYPE_01_TEST_BLOOD	: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_BLOOD_GET);
			default 								: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_GET);
			}

		case WORK_NEW : 
			switch (entTyp01) {
			case TaTpyCategory.TYPE_01_DISEASE		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_DISEASE_NEW);
			case TaTpyCategory.TYPE_01_TEST_IMG		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_IMG_NEW);
			case TaTpyCategory.TYPE_01_TEST_BLOOD	: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_BLOOD_NEW);
			default 								: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_NEW);
			}
		case WORK_MOD : 
			switch (entTyp01) {
			case TaTpyCategory.TYPE_01_DISEASE		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_DISEASE_MOD);
			case TaTpyCategory.TYPE_01_TEST_IMG		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_IMG_MOD);
			case TaTpyCategory.TYPE_01_TEST_BLOOD	: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_BLOOD_MOD);
			default 								: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_MOD);
			}	
		case WORK_DEL : 
			switch (entTyp01) {
			case TaTpyCategory.TYPE_01_DISEASE		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_DISEASE_DEL);
			case TaTpyCategory.TYPE_01_TEST_IMG		: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_IMG_DEL);
			case TaTpyCategory.TYPE_01_TEST_BLOOD	: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CAT_TEST_BLOOD_DEL);
			default 								: return APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_TPY_CATEGORY_DEL);
			}	
		case WORK_UPL : 
			//check something with params
			return true;
		}
		return false;
	}

	private static CacheData<Map<Integer, TaTpyCategory>> 	cache_lstGrp 	= new CacheData<Map<Integer, TaTpyCategory>>(100, DefTime.TIME_05_00_00_000);	
	private static CacheData<ResultPagination>				cache_pagina 	= new CacheData<ResultPagination>	(100, DefTime.TIME_02_00_00_000);

	//---------------------------------------------------------------------------------------------------------
	private static CacheData<TaTpyCategory> 				cache_ent 		= new CacheData<TaTpyCategory>();		
	private static void doGet(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			objId		= ToolData.reqInt	(json, "id"				, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"			, false	);
		Boolean				wAvar		= ToolData.reqBool	(json, "wAvatar"		, false	);
		Boolean 			wChild	 	= ToolData.reqBool 	(json, "wChild"			, false );
		Boolean 			wParent	 	= ToolData.reqBool 	(json, "wParent"		, false );

		TaTpyCategory 		ent 		= reqGet(objId, forced, wAvar, wParent, wChild);

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

	private static TaTpyCategory reqGet(Integer id, Boolean forced, Boolean wAvatar, Boolean wParent, Boolean wChild ) throws Exception{
		String key 	= id + "_" + wAvatar + "_" + wParent + "_" + wChild;

		TaTpyCategory 		ent 	= cache_ent.reqData(key);

		if(ent==null||forced) {
			ent = TaTpyCategory.DAO.reqEntityByID(id);

			if(ent==null) return ent;

			if (wAvatar) ent.doBuildDocuments(false);
			if (wParent) ent.doBuildParent();
			if (wChild ) ent.doBuildChildren();

			cache_ent.reqPut(key, ent);
		} else {
			cache_ent.reqCheckIfOld(key);

		}
		//---do build something other of ent like details....
		return ent;
	}

	private static void doGetDiseaseSub(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		Integer 				pId		= ToolData.reqInt	(json, "id"	, -1	);

		List<TaTpyCategory> 	ent 	= reqGetDiseaseSub(pId);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}

	private static List<TaTpyCategory> reqGetDiseaseSub(Integer pId) throws Exception{
		List<TaTpyCategory> lst 	= TaTpyCategory.DAO.reqList(Restrictions.eq(TaTpyCategory.ATT_I_PARENT, pId));
		return lst;
	}
	//--------------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		List<TaTpyCategory> 	list = reqLst(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response	, ToolJSON.reqJSonString(
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA	, list));

	}

	private static List<TaTpyCategory> reqLst(TaAutUser user, JSONObject json, Object...params) throws Exception {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, 10);
		String 				searchkey	= ToolData.reqStr		(json, "searchkey"	, null);
		Set<Integer> 		stat		= ToolData.reqSetInt	(json, "stat"		, null);
		Set<Integer> 		typ01		= ToolData.reqSetInt	(json, "typ01"		, null);
		Set<Integer> 		typ02		= ToolData.reqSetInt	(json, "typ02"		, null);
		Boolean				wAvatar		= ToolData.reqBool		(json, "wAvatar"	, false);

		//other params here
		if (!canWorkWithObj(user, WORK_LST, typ01)){ //other param after objTyp...
			return null;
		}
		Criterion				cri		= reqRestriction (searchkey, null, null, stat, typ01, typ02,null);
		List<TaTpyCategory> 	list	= TaTpyCategory.DAO.reqList(0, nbLine, Order.asc(TaTpyCategory.ATT_T_NAME), cri);
		if(list == null) return null;
		
		if (wAvatar) 		TaTpyDocument	.reqBuildAvatar(list, DefDBExt.ID_TA_TPY_CATEGORY, TaTpyCategory.ATT_O_AVATAR);

		return list;
	}

	private static Criterion reqRestriction(
	        String 			searchKey,
	        Integer 		parId,
	        Integer 		manId,
	        Set<Integer> 	stat,
	        Set<Integer> 	typ01,
	        Set<Integer> 	typ02,
	        Set<Integer> 	typ03
	) throws Exception {

	    //--Pre-Check condition---------------------------------------------------
	    if (stat == null) {
	        stat = new HashSet<>();
	        stat.add(TaTpyCategory.STAT_ACTIV);
	    }
	    
	    
	    Criterion cri 	= 	Restrictions.in(TaTpyCategory.ATT_I_STATUS, stat);
		
		if (typ01!=null&& typ01.size()>0)
		cri 			= 	Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_01, typ01));

		if (typ02!=null&& typ02.size()>0)
		cri 			= 	Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_01, typ01));
		if (typ02!=null&& typ02.size()>0)
		cri 			= 	Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_01, typ01));
		
		if (searchKey!=null) {
			searchKey = '%' + searchKey + '%';
			cri = Restrictions.and(	cri, Restrictions.or(
					Restrictions.ilike(TaTpyCategory.ATT_T_CODE, searchKey), 
					Restrictions.ilike(TaTpyCategory.ATT_T_NAME, searchKey))
					);
		}
		
		if (manId!=null)
		cri 			= 	Restrictions.and(cri, 
							Restrictions.eq(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		
		if (parId!=null)
		cri 			= 	Restrictions.and(cri, 
							Restrictions.eq(TaTpyCategory.ATT_I_PARENT, parId));
		
		return cri;
	}


	//---------------------------------------------------------------------------------------------------------
	private static void doLstTreeStruct(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");
		Integer 				manId 		= ToolData.reqInt 	(json, "manId"		, null);
		Integer 				typ01 		= ToolData.reqInt 	(json, "typ01"		, null);

		Boolean 				treeBuild 	= ToolData.reqBool 	(json, "treeType"	, false);
		Boolean 				withAvatar 	= ToolData.reqBool 	(json, "withAvatar"	, false);
		Boolean 				isCommon 	= ToolData.reqBool 	(json, "isCommon"	, false);
		if (isCommon)			manId		= 1;

		if (typ01 ==null) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		String keyWord 	= manId + "_" + typ01 + "_" + treeBuild + "_" + isCommon ;
		ResultPagination rs =	cache_pagina.reqData(keyWord);

		if(rs==null) {
			List  	list 		= TaTpyCategory.reqListByType(typ01, manId!=null?manId:user.reqPerManagerId());
			if(list==null){
				API.doResponse(response,DefAPI.API_MSG_KO);
				return;
			}else {
				if (withAvatar) 
					TaTpyDocument.doBuildTpyDocuments(list, typ01, null, null, TaTpyCategory.ATT_O_DOCUMENTS, false);

				if (treeBuild) 
					list = ToolDBEntity.reqTreeStruct(list, TaTpyCategory.ATT_I_ID, TaTpyCategory.ATT_I_PARENT, TaTpyCategory.ATT_O_CHILDREN);
			}

			rs		= new ResultPagination(list, 0,0,0);
			cache_pagina.reqPut(keyWord, rs);			
		} else {
			cache_pagina.reqCheckIfOld(keyWord); //cache in 2 hour
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, rs.reqList()
				));	

	}

	//---------------------------------------------------------------------------------------------------------
	private static CacheData<List<ViTpyCategoryDyn>> cache_VI = new CacheData<List<ViTpyCategoryDyn>>(100, DefTime.TIME_01_00_00_000);	
	private static void  doLstSearch(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		//----sử dụng ViTpyCategory để đơn giản dữ liệu lấy từ CSDL

		//		Integer 			manId 		= ToolData.reqInt 	(json, "manId"		, null);
		String				searchkey	= ToolData.reqStr	(json, "searchkey"	, "%");// Integer.parseInt(request.getParameter("typ01")); 	
		Integer 			typ01 		= ToolData.reqInt 	(json, "typ01"		, 100);
		Integer 			typ02 		= ToolData.reqInt 	(json, "typ02"		, 1	 );
		Integer				nbLineMax	= ToolData.reqInt	(json, "nbLine"		, 10 );

		String 				keyWord 	= searchkey + "_" + typ01 + "_" + typ02 ;
		List<ViTpyCategoryDyn> list		= cache_VI.reqData(keyWord);

		if (list==null){
			Criterion cri	= null;
			cri 			= Restrictions.like(TaTpyCategory.ATT_T_NAME , "%"+searchkey+"%");
			cri				= Restrictions.and(cri, 
					Restrictions.eq(TaTpyCategory.ATT_I_TYPE_01		, typ01),
					Restrictions.eq(TaTpyCategory.ATT_I_TYPE_02	   	, typ02)

					//					Restrictions.eq(TaTpyCategory.ATT_I_PER_MANAGER	, manId)
					);

			list = ViTpyCategoryDyn.DAO.reqList(0, nbLineMax, Order.asc(ViTpyCategoryDyn.ATT_T_NAME), cri);
			cache_VI.reqPut(keyWord, list);	
		} else {
			cache_VI.reqCheckIfOld(keyWord); //cache in 1 hour
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list
				));	
	}

	//--------------------------------------------------------------------------------------------
	private static CacheData<ResultPagination> cacheEnt_rs = new CacheData<ResultPagination>(100, DefTime.TIME_02_00_00_000);

	private static void doLstPage(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {	
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
	
	private static void doLstPageTestBlood(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {	
		ResultPagination  	res = reqLstPageTestBlood(user, json); //and other params if necessary

		if (res.reqList()==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, res));
	}
	
	private static void doLstPageTestImg(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {	
		ResultPagination  	res = reqLstPageTestImg(user, json); //and other params if necessary

		if (res.reqList()==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA				, res));
	}
	
	private static ResultPagination reqLstPageTestBlood(TaAutUser user, JSONObject json) throws Exception {
		String				searchKey		= ToolData.reqStr		(json, "searchKey"	, null);
		Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
		Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
		Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);

		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typ01			= ToolData.reqSetInt	(json, "typ01"		, null);
		Set<Integer>		typ02			= ToolData.reqSetInt	(json, "typ02"		, null);
		Set<Integer>		typ03			= ToolData.reqSetInt	(json, "typ03"		, null);
		ResultPagination 	rs 				= null;
		Criterion 			cri 			= reqRestrictionSearch(user, searchKey, stats, typ01, typ02, typ03);				
		List<TaTpyCategory> list 			= TaTpyCategory.DAO.reqList(begin, number, cri);
		
		if (total == 0 ) {
			total							= TaTpyCategory.DAO.reqCount(cri).intValue();
		}
		rs									= new ResultPagination(list, total, begin, number);


		return rs;
	}
	private static ResultPagination reqLstPageTestImg(TaAutUser user, JSONObject json) throws Exception {
		String				searchKey		= ToolData.reqStr		(json, "searchKey"	, null);
		Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
		Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
		Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);

		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typ01			= ToolData.reqSetInt	(json, "typ01"		, null);
		Set<Integer>		typ02			= ToolData.reqSetInt	(json, "typ02"		, null);
		Set<Integer>		typ03			= ToolData.reqSetInt	(json, "typ03"		, null);

		ResultPagination 	rs 				= null;
		Criterion 			cri 			= reqRestrictionSearch(user, searchKey, stats, typ01, typ02, typ03);			
		List<TaTpyCategory> list 			= TaTpyCategory.DAO.reqList(begin, number, cri);


		if (total == 0 ) {
			total				= TaTpyCategory.DAO.reqCount(cri).intValue();
		}
		rs						= new ResultPagination(list, total, begin, number);


		return rs;
	}
	private static ResultPagination reqLstPage(TaAutUser user, JSONObject json) throws Exception {
		String				searchKey		= ToolData.reqStr		(json, "searchKey"	, null);
		Integer 			begin			= ToolData.reqInt		(json, "begin"		, 0	);
		Integer 			number			= ToolData.reqInt		(json, "number"		, 10); 
		Integer 			total			= ToolData.reqInt		(json, "total"		, 0	);

		Set<Integer>		stats			= ToolData.reqSetInt	(json, "stats"		, null);
		Set<Integer>		typ01			= ToolData.reqSetInt	(json, "typ01"		, null);
		Set<Integer>		typ02			= ToolData.reqSetInt	(json, "typ02"		, null);
		Set<Integer>		typ03			= ToolData.reqSetInt	(json, "typ03"		, null);

		ResultPagination 	rs 				= null;

		//-------------------------------------------------------------------
		Criterion 			cri 			= reqRestrictionSearch(user, searchKey, stats, typ01, typ02, typ03);				

		List<TaTpyCategory> list 			= TaTpyCategory.DAO.reqList(begin, number, cri);

		if (total == 0 ) {
			total				= TaTpyCategory.DAO.reqCount(cri).intValue();
		}
		rs						= new ResultPagination(list, total, begin, number);


		return rs;
	}

	private static Criterion reqRestrictionSearch(TaAutUser user, String searchKey, Set<Integer> stats, Set<Integer> typ01, Set<Integer> typ02, Set<Integer> typ03) {
		if (stats == null){
			stats = new HashSet<Integer>() ; 
			stats.add(TaTpyCategory.STAT_ACTIV);
		}

		Criterion cri 	= Restrictions.in(TaTpyCategory.ATT_I_STATUS, stats);
		
		if (typ01!=null && typ01.size()>0)
			cri 		= Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_01, typ01));
		
		if (typ02!=null && typ02.size()>0)
			cri 		= Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_02, typ02));
		
		if (typ03!=null && typ03.size()>0)
			cri 		= Restrictions.and(cri, 
							Restrictions.in(TaTpyCategory.ATT_I_TYPE_03, typ03));

		if (searchKey!=null) {
			searchKey = '%' + searchKey + '%';
			cri = Restrictions.and(	cri, Restrictions.or(
					Restrictions.ilike(TaTpyCategory.ATT_T_NAME, searchKey), 
					Restrictions.ilike(TaTpyCategory.ATT_T_CODE, searchKey))
					);
		}

		//		cri = Restrictions.and(cri, Restrictions.eq(ViAutUserDyn.ATT_I_PER_MANAGER, user.reqPerManagerId()));
		return cri;
	}
	//---------------------------------------------------------------------------------------------------------

	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
		{
			put("action", -1);
			put("id"	, 0 );
			put("name"	, 1 );
			put("code"	, 2 );
		}
	};

	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Integer 			typ01 			= ToolData.reqInt (json, "typ01"		, null);
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, mapCol);
		Set<String>			searchKey		= (Set<String>)dataTableOption[0];	

		//		Set<Integer>		objTypMult		= new HashSet<Integer>() ;

		Criterion 	cri 				= reqRestriction(typ01, searchKey);				

		List<ViTpyCategoryDyn> list 	= reqListDyn(dataTableOption, cri);		
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} 

		Integer iTotalRecords 			= reqNbListDyn().intValue();				
		Integer iTotalDisplayRecords 	= reqNbListDyn(cri).intValue();

		if (list.size()>0) {
			doBuildParentName(list);
		}
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
				));

	}
	//--------------------------------------------------------------------------------------------------------------------------------------------------
		//--------------------------------------------------------------------------------------------------------------------------------------------------

	private static List<ViTpyCategoryDyn> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<ViTpyCategoryDyn> list 	= new ArrayList<ViTpyCategoryDyn>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = ViTpyCategoryDyn.ATT_I_ID; break;		
		case 1: colName = ViTpyCategoryDyn.ATT_T_NAME; break;	
		case 2: colName = ViTpyCategoryDyn.ATT_T_CODE; break;	
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;	
			}
		}

		if (order==null)
			list	= ViTpyCategoryDyn.DAO.reqList(begin, number, cri);
		else
			list	= ViTpyCategoryDyn.DAO.reqList(begin, number, order, cri);			

		return list;
	}
	private static Criterion reqRestriction(Integer typ01, Set<String> searchKey ) throws Exception {
		Criterion cri = Restrictions.conjunction();

		if(typ01 != null) {
			cri = Restrictions.eq(ViTpyCategoryDyn.ATT_I_TYPE_01, typ01);
		}

		for (String s : searchKey){
			if (cri==null)
				cri = 	Restrictions.or(Restrictions.ilike(ViTpyCategoryDyn.ATT_T_NAME	, s), 
						Restrictions.ilike(ViTpyCategoryDyn.ATT_T_CODE	, s));

			else
				cri = 	Restrictions.and(cri, 
						Restrictions.or(Restrictions.ilike(ViTpyCategoryDyn.ATT_T_NAME	, s), 
								Restrictions.ilike(ViTpyCategoryDyn.ATT_T_CODE	, s))
						);
		}


		return cri;
	}
	private static Number reqNbListDyn() throws Exception {						
		return ViTpyCategoryDyn.DAO.reqCount();		
	}

	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return ViTpyCategoryDyn.DAO.reqCount(cri);
	}

	private static void doBuildParentName( List<ViTpyCategoryDyn> lst) throws Exception {
		HashSet<Integer> set = new HashSet<Integer>();
		List<ViTpyCategoryDyn> lstChk = new ArrayList<ViTpyCategoryDyn>();
		for(ViTpyCategoryDyn o : lst) {
			Integer pId = o.reqInt(ViTpyCategoryDyn.ATT_I_PARENT);
			if (pId!=null) {
				set.add(pId);
				lstChk.add(o);
			}
		}
		if (set.size()>0) {
			List<ViTpyCategoryDyn> 				par = ViTpyCategoryDyn.DAO.reqList_In(ViTpyCategoryDyn.ATT_I_ID, set);
			Hashtable<Integer,EntityAbstract> 	tab = ToolDBEntity.reqTabKeyInt(par, ViTpyCategoryDyn.ATT_I_ID);
			for(ViTpyCategoryDyn o : lstChk) {
				Integer pId = o.reqInt(ViTpyCategoryDyn.ATT_I_PARENT);
				if (pId!=null) {
					ViTpyCategoryDyn p = (ViTpyCategoryDyn) tab.get(pId);
					if (p!=null) o.reqSet(ViTpyCategoryDyn.ATT_O_PARENT_NAME, p.req(ViTpyCategoryDyn.ATT_T_NAME));
				}
			}
		}
	}
	//---------------------------------------------------------------------------------------------------------

	private static void doLstGroup(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");
		Integer 						objParTyp	= ToolData.reqInt	(json, "typ01", null);
		Integer							manId		= user.reqPerManagerId();

		String							key			= objParTyp+"-"+manId;
		Map<Integer, TaTpyCategory> 	map 		= cache_lstGrp.reqData(key);

		if (map ==null) {
			map 		= TaTpyCategory.reqMapByType(objParTyp, manId);
			cache_lstGrp.reqPut(key, map);
		}else {
			cache_lstGrp.reqCheckIfOld(key);
		}

		if (map==null || map.size()==0){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, map 
				));				
	}

	private static void doDuplicate(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		JSONObject			obj			= ToolData.reqJson (json, "obj"	, null);
		JSONObject			catLstDup	= ToolData.reqJson (obj , "cats", null);
		
		Integer				socId		= Integer.parseInt((String) obj.get("socId"));

		Map<Integer, TaTpyCategory> 	catLst 		= TaTpyCategory.DAO.reqMap(Restrictions.eq(TaTpyCategory.ATT_I_PER_MANAGER, user.reqPerManagerId()));
		
		if (!canWorkWithObj(user, WORK_NEW, catLst.values())){ //other param after obj...
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		
		List<TaTpyCategory>				listNewCat	= new ArrayList<TaTpyCategory>();
		if (catLstDup!=null&& catLstDup.size()>0) {

			for (Object o : catLstDup.entrySet()) {
				Map.Entry<Object, Object> 	entry 	= (Entry<Object, Object>) o;
				Integer						idCat	= Integer.parseInt(entry.getKey().toString());
				Integer 					check 	= Integer.parseInt(entry.getValue().toString());
				if(check == 1) {
					//---check dependency
					TaTpyCategory	catNew 			= catLst.get(idCat);
					Boolean 		existTpyCat 	= do_check_code_tpy_cat_duplicate(catNew, socId);
					if(!existTpyCat) {
						catNew.reqSet(TaTpyCategory.ATT_I_ID, null);
						catNew.reqSet(TaTpyCategory.ATT_I_PER_MANAGER, socId);
						listNewCat.add(catNew);
					}
				}			
			}

			//--persist the new relationships
			TaTpyCategory.DAO.doPersist(listNewCat);
		}

		if (listNewCat.size() == 0){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
		}else{				
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES
					));				
		}		
	}

//	private static List<TaTpyCategory> reqLst(TaAutUser user,  JSONObject json) throws Exception {
//		Integer 			manId		= user.reqPerManagerId();//ToolData.reqInt	(json, "manId"	, null	);

//		Boolean 			objContBuild= ToolData.reqBool	(json, "withBuild"		, false	);

//		Integer 			typ01		= ToolData.reqInt	(json, "typ01"			, null	);
//		Integer 			typ02		= ToolData.reqInt	(json, "typ02"			, null	);

		//other params here

		

//		Criterion 			cri		= reqCriterion (manId, typ01, typ02); //and other params	
//		List<TaTpyCategory> list 	= null;
//		if (cri==null) 
//			list =   TaTpyCategory.DAO.reqList();
//		else
//			list =   TaTpyCategory.DAO.reqList(cri);
//
//		if (!canWorkWithObj(user, WORK_LST, list)){ //other param after objTyp...
//			return null;
//		}
//		
//		//do something else with request
//		if (objContBuild){
//
//		}
//
//		return list;
//	}

	private static Criterion reqCriterion(Object...params) throws Exception{
		if (params==null || params.length==0) return null;

		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (params!=null && params.length>0){
			//Integer type 	= (Integer) params[0];
			//cri 		= Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE, type));			
		}			

		if (params!=null && params.length>1){
			//do something
			Integer manId	= (Integer) params[1];
			cri 			= Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		}

		if (params!=null && params.length>2){
			//do something
			Integer typ		= (Integer) params[2];
			cri 			= Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE_01, typ));
		}

		if (params!=null && params.length>3){
			//do something
			Integer typ		= (Integer) params[3];
			cri 			= Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyCategory.ATT_I_TYPE_02, typ));
		}
		return cri;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doNewDiseaseSub(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");
		//--- in simple case, obj has only header , no details ----------------------
		//Map<String, Object> attr 	= API.reqMapParamsByClass(request, TaTpyCategory.class);
		//TaTpyCategory  ent	 = new TaTpyCategory(attr);
		//TaTpyCategory.DAO.doPersist(ent);
		//----------------------------------------------------------------------------------------------------------------------
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);

		//		Boolean 	existTpyCat 	= doCheckCode(obj);
		//		if(existTpyCat){
		//			API.doResponse(response, DefAPI.API_MSG_ERR_API);
		//			return;
		//		}
		List<TaTpyCategory> 			ent		= reqNewSub		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		//		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent
				//				"lock"				, lock
				));

	}

	//---------------------------------------------------------------------------------------------------------
	private static void doNewDisease(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");
		//--- in simple case, obj has only header , no details ----------------------
		//Map<String, Object> attr 	= API.reqMapParamsByClass(request, TaTpyCategory.class);
		//TaTpyCategory  ent	 = new TaTpyCategory(attr);
		//TaTpyCategory.DAO.doPersist(ent);
		//----------------------------------------------------------------------------------------------------------------------
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);
		Boolean 	existTpyCat 	= doCheckCode(obj);
		if(existTpyCat){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			return;
		}
		TaTpyCategory 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));

	}
	private static void doNewTestBlood(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");
		//--- in simple case, obj has only header , no details ----------------------
		//Map<String, Object> attr 	= API.reqMapParamsByClass(request, TaTpyCategory.class);
		//TaTpyCategory  ent	 = new TaTpyCategory(attr);
		//TaTpyCategory.DAO.doPersist(ent);
		//----------------------------------------------------------------------------------------------------------------------
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);
		Boolean 	existTpyCat 	= doCheckCode(obj);
		if(existTpyCat){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			return;
		}
		TaTpyCategory 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));

	}
	private static void doNewTestImg(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");
		//--- in simple case, obj has only header , no details ----------------------
		//Map<String, Object> attr 	= API.reqMapParamsByClass(request, TaTpyCategory.class);
		//TaTpyCategory  ent	 = new TaTpyCategory(attr);
		//TaTpyCategory.DAO.doPersist(ent);
		//----------------------------------------------------------------------------------------------------------------------
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);
		Boolean 	existTpyCat 	= doCheckCode(obj);
		if(existTpyCat){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			return;
		}
		TaTpyCategory 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));

	}

	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");
		//--- in simple case, obj has only header , no details ----------------------
		//Map<String, Object> attr 	= API.reqMapParamsByClass(request, TaTpyCategory.class);
		//TaTpyCategory  ent	 = new TaTpyCategory(attr);
		//TaTpyCategory.DAO.doPersist(ent);
		//----------------------------------------------------------------------------------------------------------------------
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);
		Boolean 	existTpyCat 	= doCheckCode(obj);
		if(existTpyCat){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			return;
		}
		TaTpyCategory 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, ENT_TYP, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));

	}


	private static Boolean doCheckCode(JSONObject obj) throws Exception {
		// TODO Auto-generated method stub
		String code = obj.get("code").toString().trim();
		if(code.equals("") || code == null)	return false;
		TaTpyCategory cat 	= TaTpyCategory.DAO.reqEntityByValue(TaTpyCategory.ATT_T_CODE, code);
		if(cat != null)
			return true;
		else
			return false;
	}

	private static Boolean do_check_code_tpy_cat_duplicate(TaTpyCategory cat, Integer manId) throws Exception {
		// TODO Auto-generated method stub
		String code = (String) cat.req(TaTpyCategory.ATT_T_CODE);
		List<TaTpyCategory> catLst 	= TaTpyCategory.DAO.reqList(Restrictions.eq(TaTpyCategory.ATT_T_CODE, code), Restrictions.eq(TaTpyCategory.ATT_I_PER_MANAGER, manId));
		if(catLst != null && catLst.size() > 0)
			return true;
		else
			return false;
	}

	private static TaTpyCategory reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject				obj		= ToolData.reqJson (json, "obj", null);
		//		String 					pId		= ToolData.reqStr(obj, "parentID", null);

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaTpyCategory.class);
		String codeTpy 				= attr.get(TaTpyCategory.ATT_T_CODE).toString().trim();

		int 				manId 	=  (int)user.reqPerManagerId();

		attr.put(TaTpyCategory.ATT_I_PER_MANAGER, manId);
		//		if((Integer)attr.get(TaTpyCategory.ATT_I_TYPE_00) == DefDBExt.ID_TA_MAT_MATERIAL && (codeTpy.equals("") || codeTpy == null)){
		//			Integer num = reqNumero();
		//			String code = "CAT" + String.format("%06d", num);
		//			attr.put(TaTpyCategory.ATT_T_CODE, code);
		//		}

		TaTpyCategory  		ent	 	= new TaTpyCategory(attr);
		
		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}
		
		TaTpyCategory.DAO.doPersist(ent);

		int 					entId		= (int)ent.reqRef();
		//--update documents tpy market ids------------------------
		JSONArray				docs		= (JSONArray) obj.get("files");	
		ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));


		//--get parentName----------------------------------------
		Integer pId = ent.reqInt(TaTpyCategory.ATT_I_PARENT);
		if (pId!=null) {
			TaTpyCategory par = TaTpyCategory.DAO.reqEntityByRef(pId, false);
			if (par!=null) ent.reqSet(TaTpyCategory.ATT_O_PARENT_NAME,  par.reqStr( TaTpyCategory.ATT_T_NAME));
		}

		String key 	= entId + "_" + true + "_" + true + "_" + false;
		cache_ent.reqPut(key, ent);

		return ent;
	}

	private static List<TaTpyCategory> reqNewSub(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject				obj		= ToolData.reqJson (json, "obj", null);
		Integer 				pId		= ToolData.reqInt(obj, "parId", null);
		TaTpyCategory  			ent	 	= null;

		JSONArray lst = (JSONArray) obj.get("lst");

		List<TaTpyCategory> listDiease 	= TaTpyCategory.reqListMod(pId, lst);

		cache_ent.doClear();

		return listDiease;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaTpyCategory  		ent	 		= reqMod(user, json); 								
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

	private static TaTpyCategory reqMod(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj", null);

		int 				entId	= Integer.parseInt(obj.get("id").toString());	
		TaTpyCategory  		ent	 	= TaTpyCategory.DAO.reqEntityByRef(entId);
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		Map<String, Object> attr 		= API.reqMapParamsByClass(obj, TaTpyCategory.class);

		String 				newCode 	= ToolData.reqStr  (obj	, "code", null);
		if(!ent.req(TaTpyCategory.ATT_T_CODE).equals(newCode)){
			Boolean existCode = doCheckCode(obj);
			if(existCode){
				attr.remove(TaTpyCategory.ATT_T_CODE);
			}
		}

		TaTpyCategory.DAO.doMerge(ent, attr);
		cache_ent.doClear();

		//--------mod documents-------------------
		JSONArray				docs		= (JSONArray) obj.get("files");	
		ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, ENT_TYP, entId, docs));

		//--get parentName----------------------------------------
		Integer pId = ent.reqInt(TaTpyCategory.ATT_I_PARENT);
		if (pId!=null) {
			TaTpyCategory par = TaTpyCategory.DAO.reqEntityByRef(pId, false);
			if (par!=null) ent.reqSet(TaTpyCategory.ATT_O_PARENT_NAME,  par.reqStr( TaTpyCategory.ATT_T_NAME));
		}

		//---------------------------------------------------------
		String key 	= entId + "_" + true + "_" + true + "_" + false;
		cache_ent.reqPut(key, ent);

		return ent;
	}	

	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");

		//--chk lock-----------------------------------------------------------------------------------------------------------------
		int					entId 	= ToolData.reqInt(json, "id", null);

		TaSysLock 		lock 	= ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);

		if ((Integer)lock.req(TaSysLock.ATT_I_STATUS) == 0){
			API.doResponse(response, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA		, lock
					));	
			return;
		}
		//----------------------------------------------------------------------------------------------------------------------
		boolean		ok		= canTpyCategoryDel(user, json); 				
		if (!ok){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1,	DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}		
	}
	private static boolean canTpyCategoryDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 		entId	= ToolData.reqInt	(json, "id"		, -1	);	
		TaTpyCategory  	ent	 	= TaTpyCategory.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after ent...
			return false;
		}		

		//remove all other object connecting to this ent first-------
		//		Session sessSub 	= TaTpyDocument	.DAO.reqSessionCurrent();
		Session sessMain 	= TaTpyCategory	.DAO.reqSessionCurrent();
		try {
			TaTpyDocument		.doListDel(sessMain, ENT_TYP, entId);
			//			TaTpyDocument		.doListDel(sessSub, entTyp, entId);

			TaTpyCategory		.DAO.doRemove (sessMain, ent);

			TaTpyCategory		.DAO.doSessionCommit(sessMain);
			//			TaTpyDocument		.DAO.doSessionCommit(sessSub);
		}catch(Exception e){
			TaTpyCategory		.DAO.doSessionRollback(sessMain);
			//			TaTpyDocument		.DAO.doSessionRollback(sessSub);
			e.printStackTrace();
		}
		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private void doLckReq(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
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

	private void doLckDel(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
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

	private void doLckSav(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckSav --------------");	

		//--chk lock-----------------------------------------------------------------------------------------------------------------
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}

		TaTpyCategory  		ent	 	=  reqMod(user, json); 								
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
	private void doLckEnd(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckEnd --------------");	

		//--chk lock-----------------------------------------------------------------------------------------------------------------
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}

		TaTpyCategory  		ent	 		= reqMod(user, json); 						
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
	private static void doLstCat(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");
		Integer parTyp = ToolData.reqInt (json, "parTyp", null);

		List<TaTpyCategory> list = TaTpyCategory.reqListByType(parTyp, user.reqPerManagerId()); 
		if (list==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}



	//---------------------------------------------------------------------------------

}
