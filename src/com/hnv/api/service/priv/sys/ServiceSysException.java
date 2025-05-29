package com.hnv.api.service.priv.sys;


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
import com.hnv.db.sys.TaSysException;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyCategory;
import com.hnv.def.DefDBExt;
import com.hnv.def.DefRight;

/**
* ----- ServiceSysException by H&V
* ----- Copyright 2023------------
*/
public class ServiceSysException implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 
	

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 		= "HNV".toLowerCase();

	public static final String SV_CLASS 		= "ServiceSysException".toLowerCase();	
	
	public static final String SV_GET 					= "SVGet".toLowerCase();	
	public static final String SV_LST 					= "SVLst".toLowerCase();
	public static final String SV_LST_DYN				= "SVLstDyn".toLowerCase(); 
	public static final String SV_LST_PAGE				= "SVLstPage"	.toLowerCase(); 

	public static final String SV_NEW 					= "SVNew".toLowerCase();	
	public static final String SV_MOD 					= "SVMod".toLowerCase();	
	public static final String SV_DEL 					= "SVDel".toLowerCase();
	
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceSysException(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			//---------------------------------------------------------------------------------

			if(sv.equals(SV_GET) 						&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doGet(user,  json, response);

			} else if(sv.equals(SV_LST)					&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doLst(user,  json, response);
			} else if(sv.equals(SV_LST_DYN)				&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doLstDyn(user,  json, response);
				

			} else if(sv.equals(SV_NEW)					&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doNew(user,  json, response);
			} else if(sv.equals(SV_MOD)					&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doMod(user,  json, response);
			} else  if(sv.equals(SV_DEL)				&& APIAuth.canAuthorize(user, SV_CLASS, sv)){
				doDel(user,  json, response);

			
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}	


		}catch(Exception e){
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_ERR_API
					));
			e.printStackTrace();
			return;
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
	private static CacheData<TaSysException> 		cache_entity= new CacheData<TaSysException>		(500, DefTime.TIME_24_00_00_000 );
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			objId		= ToolData.reqInt	(json, "id"			, -1	);				
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		TaSysException 		ent 		= reqGet(objId, forced, forManager);

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
	
	public static TaSysException reqGet(Integer objId, Boolean forced, boolean forManager) throws Exception{
		String 			key		= objId+"";
		TaSysException 	ent 	= cache_entity.reqData(key);	
		if (forced || ent ==null) {
			ent 	= TaSysException.DAO.reqEntityByRef(objId, forced);
			
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
	private static CacheData<List<TaSysException>> 	cache_lst 		= new CacheData<List<TaSysException>>();	
	private static void doLst(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Set<Integer>		ids			= ToolData.reqSetInt(json, "ids"			, null	);	
		Set<String>			codes		= ToolData.reqSetStr(json, "codes"			, null	);	
		Boolean				forced		= ToolData.reqBool	(json, "forced"			, false	);
		Boolean				wAvar		= ToolData.reqBool	(json, "wAvatar"		, false	);
		Boolean 			wChild	 	= ToolData.reqBool 	(json, "wChild"			, false );
		Boolean 			wParent	 	= ToolData.reqBool 	(json, "wParent"		, false );
		

		List<TaSysException>		ent 	= reqLst(ids, codes, forced, wAvar, wParent, wChild);

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
	
	private static List<TaSysException>	 reqLst(Set<Integer> ids, Set<String> codes , Boolean forced, Boolean wAvatar, Boolean wParent, Boolean wChild ) throws Exception{
		String key 	= ids + "_" +codes  + "_" + wAvatar + "_" + wParent + "_" + wChild;

		List<TaSysException> lst 	= cache_lst.reqData(key);

		if(lst==null||forced) {
			if (ids!=null) 
				lst = TaSysException.DAO.reqList_In(TaTpyCategory.ATT_I_ID, ids);
			else
				lst = TaSysException.DAO.reqList_In(TaTpyCategory.ATT_T_CODE, codes);
			
			//if (wAvatar) 	TaSysException.doBuildDocuments(lst);
			//if (wParent) 	TaSysException.doBuildParent	(lst);
			//if (wChild) 	TaSysException.doBuildChildren	(lst);
			
			if (lst!=null &&  lst.size()>0) cache_lst.reqPut(key, lst);
		} else {
			cache_lst.reqCheckIfOld(key);
		}
		//---do build something other of ent like details....
		return lst;
	}
	
	private static Criterion reqCriterion(Object...params) throws Exception{
		if (params==null || params.length==0) return null;

		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (params!=null && params.length>0){
			//int type 	= (int) params[0];
			//cri 		= Restrictions.and(cri, Restrictions.eqOrIsNull(TaAutRight.ATT_I_TYPE, type));
		}			

		if (params!=null && params.length>1){
			//do something
		}

		return cri;
	}
	
	
	//---------------------------------------------------------------------------------------------------------		
	
	private static Long countAllEle = null;
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
	    {
	    	put("action", -1);
	    	put("id"	, 0 );
	    	put("dt"	, 1 );
	    	put("uId"	, 2 );
	    	put("inf01"	, 3 );
	    	put("inf02"	, 4 );
	    	put("inf03"	, 5 );
	    	put("inf04"	, 6 );
	    }
	};
	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, mapCol);
		Set<String>		searchKey		= (Set<String>)dataTableOption[0];	
		
		Set<Integer>		objTypMult		= new HashSet<Integer>() ;

		if (!canWorkWithObj(user, WORK_LST, null, null)){ //other param after objTyp...
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		Criterion 	cri 					= reqRestriction(searchKey);				

		List<TaSysException> list 			= reqListDyn(dataTableOption, cri);		
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} 

		if (countAllEle==null)
			countAllEle = ((long)reqNbListDyn());

		Integer iTotalRecords 			= (countAllEle.intValue());				
		Integer iTotalDisplayRecords 	= reqNbListDyn(cri).intValue();


		API.doResponse(response, ToolJSON.reqJSonString(		
				//filter,
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
				));

	}

	private static Criterion reqRestriction(Set<String> searchKey) throws Exception {		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		for (String s : searchKey){
			if (cri==null)
				cri = 	Restrictions.or(Restrictions.ilike(TaSysException.ATT_T_INFO_01	, s), 
										Restrictions.ilike(TaSysException.ATT_T_INFO_02	, s),
										Restrictions.ilike(TaSysException.ATT_T_INFO_03	, s));

		}


		return cri;
	}

	private static List<TaSysException> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<TaSysException> list 	= new ArrayList<TaSysException>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = TaSysException.ATT_I_ID; break;	
		case 1: colName = TaSysException.ATT_D_DATE; break;	
		case 2: colName = TaSysException.ATT_I_AUT_USER; break;	
		case 3: colName = TaSysException.ATT_T_INFO_01; break;	
		case 4: colName = TaSysException.ATT_T_INFO_02; break;	
		case 5: colName = TaSysException.ATT_T_INFO_03; break;	
		case 6: colName = TaSysException.ATT_T_INFO_04; break;	
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;	
			}
		}

		if (order==null)
			list	= TaSysException.DAO.reqList(begin, number, cri);
		else
			list	= TaSysException.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbListDyn() throws Exception {						
		return TaSysException.DAO.reqCount();		
	}
	
	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return TaSysException.DAO.reqCount(cri);
	}
	
	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaSysException 			ent		= reqNew		(user, json);
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

	private static TaSysException reqNew(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson		 (json, "obj", null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaSysException.class);	
		TaSysException  		ent	 	= new TaSysException		 (attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}

		TaSysException.DAO.doPersist(ent);

		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaSysException  		ent	 	=  reqMod(user, json); 								
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

	private static TaSysException reqMod(TaAutUser user,  JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaSysException.class);
		int 				objId 	= Integer.parseInt(obj.get("id").toString());
		TaSysException 		ent 	= TaSysException.DAO.reqEntityByRef(objId);
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		TaSysException.DAO.doMerge(ent, attr);
		return ent;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");
		boolean					ok		= canDel(user, json);
		if (!ok){
			API.doResponse(response,DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}
	}

	private static boolean canDel(TaAutUser user,  JSONObject json) throws Exception {
		Integer 		entId	= ToolData.reqInt	(json, "id", null	);	
		Integer			entTyp	= DefDBExt.ID_TA_NSO_POST;
		TaSysException  	ent	 	= TaSysException.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		TaSysException.DAO		.doRemove (ent);
		//---we have to check T_Aut_Right in AutRole + AutAuthorization?

		return true;
	}

	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	
}
