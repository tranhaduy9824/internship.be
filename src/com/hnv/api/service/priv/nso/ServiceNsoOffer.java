package com.hnv.api.service.priv.nso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolDate;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolString;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.nso.vi.ViNsoOfferSearch;
import com.hnv.db.nso.vi.ViNsoPostSearch;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefDBExt;

/**
 * ----- ServiceNsoOffer by H&V ----- Copyright 2023------------
 */
public class ServiceNsoOffer implements IService {
	private static String filePath = null;
	private static String urlPath  = null;

	// --------------------------------Service
	// Definition----------------------------------
	public static final String  SV_MODULE     			= "HNV".toLowerCase();

	public static final String  SV_CLASS      			= "ServiceNsoOffer".toLowerCase();

	public static final String  SV_GET        			= "SVGet".toLowerCase();
	public static final String  SV_LST_BY_PAR 			= "SVLstByPar".toLowerCase();
	public static final String  SV_LST_BY_IDS 			= "SVLstByIds".toLowerCase();
	public static final String  SV_LST_VI_SEARCH_BY_IDS = "SVLstViSearchByIds".toLowerCase();
	public static final String  SV_LST        			= "SVLst".toLowerCase();
	public static final String  SV_LST_DYN    			= "SVLstDyn".toLowerCase();
	public static final String  SV_LST_PAGE   			= "SVLstPage".toLowerCase();

	public static final String  SV_NEW        			= "SVNew".toLowerCase();
	public static final String  SV_MOD        			= "SVMod".toLowerCase();
	public static final String  SV_MOD_TIME   			= "SVModTime".toLowerCase();
	public static final String  SV_DEL        			= "SVDel".toLowerCase();

	public static final String  SV_LCK_REQ    			= "SVLckReq".toLowerCase();       // req or refresh
	public static final String  SV_LCK_SAV    			= "SVLckSav".toLowerCase();       // save and continue
	public static final String  SV_LCK_END    			= "SVLckEnd".toLowerCase();
	public static final String  SV_LCK_DEL    			= "SVLckDel".toLowerCase();

	public static final Integer ENT_TYP       			= DefDBExt.ID_TA_NSO_OFFER;

	// -----------------------------------------------------------------------------------------------
	// -------------------------Default Constructor - Required
	// -------------------------------------
	public ServiceNsoOffer() {
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	// -----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String    sv   = API.reqSVFunctName(json);
		TaAutUser user = (TaAutUser) json.get("userInfo");
		try {
			// ---------------------------------------------------------------------------------
			if (sv.equals(SV_LCK_REQ) 						&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLckReq(user, json, response);
			} else if (sv.equals(SV_LCK_SAV) 				&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLckSav(user, json, response);
			} else if (sv.equals(SV_LCK_END) 				&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLckEnd(user, json, response);
			} else if (sv.equals(SV_LCK_DEL) 				&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLckDel(user, json, response);

			} else if (sv.equals(SV_NEW) 					&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doNew(user, json, response);
			} else if (sv.equals(SV_MOD) 					&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doMod(user, json, response);
			} else if (sv.equals(SV_MOD_TIME) 				&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doModTime(user, json, response);
			} else if (sv.equals(SV_DEL) 					&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doDel(user, json, response);
			} else if (sv.equals(SV_GET) 					&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doGet(user, json, response);
			} else if (sv.equals(SV_LST) 					&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLst(user, json, response);
			} else if (sv.equals(SV_LST_DYN) 				&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLstDyn(user, json, response);
			} else if (sv.equals(SV_LST_BY_PAR) 			&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLstByPar(user, json, response);
			} else if (sv.equals(SV_LST_BY_IDS) 			&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLstByIds(user, json, response);
			} else if (sv.equals(SV_LST_VI_SEARCH_BY_IDS)	&& APIAuth.canAuthorize(user, SV_CLASS, sv)) {
				doLstViSearchByIds(user, json, response);
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
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
			// check something with params
			return true;
		case WORK_UPL:
			// check something with params
			return true;
		}
		return false;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	private void doLckReq(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckReq --------------");

		Integer   entId = ToolData.reqInt(json, "id", null);
		TaSysLock lock  = ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);

		if (lock == null || lock.reqStatus() == 0) {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA	, lock));
		} else {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA	, lock));
		}
	}

	private void doLckDel(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckDel --------------");

		if (ToolDBLock.canDeleteLock(json))
			API.doResponse(response, DefAPI.API_MSG_OK);
		else
			API.doResponse(response, DefAPI.API_MSG_KO);

	}

	private void doLckSav(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckSav --------------");
		boolean isLocked = ToolDBLock.canExistLock(json);
		if (!isLocked) {
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}

		TaNsoOffer ent = reqMod(user, json);
		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA	, ent));
		}
	}

	// user when modify object with lock
	private void doLckEnd(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckEnd --------------");
		boolean isLocked = ToolDBLock.canExistLock(json);
		if (!isLocked) {
			API.doResponse(response, DefAPI.API_MSG_ERR_LOCK);
			return;
		}

		TaNsoOffer ent = reqMod(user, json);
		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			ToolDBLock.canDeleteLock(json);
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA	, ent));
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		TaNsoOffer ent = reqNew(user, json);
		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaSysLock lock = ToolDBLock.reqLock(json, 
				"lock"		, DefDB.DB_LOCK_NEW, 
				ENT_TYP		, ent.reqId(), 
				user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		
		API.doResponse(response	, ToolJSON.reqJSonString( // filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, ent, "lock", lock));
	}

	private static TaNsoOffer reqNew(TaAutUser user, JSONObject json) throws Exception {
		JSONObject          obj  = ToolData.reqJson(json, "obj", null);
		Map<String, Object> attr = API.reqMapParamsByClass(obj, TaNsoOffer.class);
		
		
		TaNsoOffer          ent  = new TaNsoOffer(attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)) { // other param after obj...
			return null;
		}
		
		String 		code01		= ent.reqStr(TaNsoOffer.ATT_T_CODE_01);
		String 		code02		= ent.reqStr(TaNsoOffer.ATT_T_CODE_02);
		Integer 	type01		= ent.reqInt(TaNsoOffer.ATT_I_TYPE_01);
		Integer 	type02		= ent.reqInt(TaNsoOffer.ATT_I_TYPE_02);
		Integer 	stat01		= ent.reqInt(TaNsoOffer.ATT_I_STATUS_01);
		Integer 	stat02		= ent.reqInt(TaNsoOffer.ATT_I_STATUS_02);
		String 		title		= ent.reqStr(TaNsoOffer.ATT_T_TITLE);
		String  	dtNow		= ToolDate.reqString(new Date(), "yyMMddHHmm");
		String 		urlGen 		= ToolString.reqCovertStringToURL(title);
		
		if (code01==null || code01.trim().length()==0) {
			code01 = urlGen + "-" + dtNow;
		}
		if (code02==null || code02.trim().length()==0) {
			code01 = "O_" + dtNow;
		}
		
		if (type01==null){	
//			type01 = TaNsoOffer.TYPE_01_;
		}
		
		if (stat01 ==null) {
			stat01 = TaNsoOffer.STAT_01_NEW;
		}
//		else if (stat01.equals(TaNsoOffer.STAT_01_ACTIVE) && user.canBeClient()) {
//			stat01 = TaNsoOffer.STAT_01_NEW;
//		}
		
		if (stat02 ==null) {
			stat02 = TaNsoOffer.STAT_02_PUBLIC;
		}
		
		ent.reqSet(TaNsoOffer.ATT_T_CODE_01		, code01);
		ent.reqSet(TaNsoOffer.ATT_T_CODE_02		, code02);
		ent.reqSet(TaNsoOffer.ATT_I_STATUS_01	, stat01);
		ent.reqSet(TaNsoOffer.ATT_I_STATUS_02	, stat02);
		ent.reqSet(TaNsoOffer.ATT_D_DATE_01		, new Date());
		ent.reqSet(TaNsoOffer.ATT_D_DATE_02		, new Date());

		TaNsoOffer.DAO.doPersist(ent);
		int				entId 		= ent.reqId();
		Set<Integer> 	catIdArr 	= ToolData.reqSetInt(obj, "cats", null);
		ent.reqSet(TaNsoOffer.ATT_O_CATS, TaTpyCategoryEntity.reqListNew(ENT_TYP, ent.reqId(), catIdArr));

		JSONArray		docs		= (JSONArray) obj.get("files");
		ent.reqSet(TaNsoOffer.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));

		return ent;
	}

	// ---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaNsoOffer ent = reqMod(user, json);
		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA	, ent));
		}
	}

	private static TaNsoOffer reqMod(TaAutUser user, JSONObject json) throws Exception {
		JSONObject          obj   = ToolData.reqJson(json, "obj", null);
		Map<String, Object> attr  = API.reqMapParamsByClass(obj, TaNsoOffer.class);
		int                 entId = Integer.parseInt(obj.get("id").toString());
		TaNsoOffer          ent   = TaNsoOffer.DAO.reqEntityByRef(entId);
		if (ent == null) {
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)) { // other param after obj...
			return null;
		}

		attr.remove(TaNsoOffer.ATT_D_DATE_01);
		attr.remove(TaNsoOffer.ATT_T_CODE_01);
		attr.remove(TaNsoOffer.ATT_T_CODE_02);
		//---check other attri if needed
		
		ent.reqSet(TaNsoOffer.ATT_D_DATE_02		, new Date());
		
		TaNsoOffer.DAO.doMerge(ent, attr);
		cache_entity.reqPut(entId + "", ent);

		Set<Integer> catIdArr = ToolData.reqSetInt(obj, "cats", null);
		ent.reqSet(TaNsoOffer.ATT_O_CATS, TaTpyCategoryEntity.reqListMod(ENT_TYP, ent.reqId(), catIdArr));

		JSONArray 		docs 	= (JSONArray) obj.get("files");
		ent.reqSet(TaNsoOffer.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, ENT_TYP, entId, docs));
				
		return ent;
	}

	private static void doModTime(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaNsoOffer ent = reqModTime(user, json);
		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA	, ent));
		}
	}

	private static TaNsoOffer reqModTime(TaAutUser user, JSONObject json) throws Exception {
		String     obj   = ToolData.reqStr(json, "obj", null);
		int        entId = Integer.parseInt(ToolData.reqStr(json, "id", null));
		TaNsoOffer ent   = TaNsoOffer.DAO.reqEntityByRef(entId);
		if (ent == null) {
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)) { // other param after obj...
			return null;
		}

		cache_entity.reqPut(entId + "", ent);

		ent.reqSet(TaNsoOffer.ATT_T_CONTENT_05, obj);
		TaNsoOffer.DAO.doMerge(ent);

		return ent;
	}

	// ---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");

		int       entId = ToolData.reqInt(json, "id", null);
		TaSysLock lock  = ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		if (lock == null || lock.reqStatus() == 0) {
			API.doResponse(response	, ToolJSON.reqJSonString(
					DefJS.SESS_STAT	, 1, 
					DefJS.SV_CODE	, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA	, lock));
			return;
		}

		if (!canDel(user, json)) {
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, DefAPI.API_MSG_OK);
		}

		ToolDBLock.canDeleteLock(lock);
	}

	private static boolean canDel(TaAutUser user, JSONObject json) throws Exception {
		Integer    entId = ToolData.reqInt(json, "id", null);
		TaNsoOffer ent   = TaNsoOffer.DAO.reqEntityByRef(entId);
		if (ent == null) {
			return false;
		}

		Session sess = TaNsoOffer.DAO.reqSessionCurrent();
		try {
			TaTpyDocument		.doListDel(sess, ENT_TYP, ent.reqId());
			TaTpyCategoryEntity	.doListDel(sess, ENT_TYP, ent.reqId());
			
			TaNsoOffer.DAO		.doRemove(sess, ent);
			
			cache_entity		.reqDel(entId + "");
			
			TaNsoOffer.DAO		.doSessionCommit(sess);
		}catch (Exception e){
			if (sess!=null) TaNsoOffer.DAO.doSessionRollback(sess);
			return false;
		}

		return true;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	private static CacheData<TaNsoOffer> cache_entity = new CacheData<TaNsoOffer>(500, DefTime.TIME_24_00_00_000);

	private static void doGet(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer    entId      = ToolData.reqInt(json, "id", -1);
		Boolean    forced     = ToolData.reqBool(json, "forced", false);
		Boolean    forManager = ToolData.reqBool(json, "forManager", false);

		TaNsoOffer ent        = reqGet(entId, forced, forManager);

		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)) {
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response	, ToolJSON.reqJSonString( // filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, ent));
	}

	public static TaNsoOffer reqGet(Integer entId, Boolean forced, boolean forManager) throws Exception {
		String     key = entId + "";
		TaNsoOffer ent = cache_entity.reqData(key);
		if (forced || ent == null) {
			ent = TaNsoOffer.DAO.reqEntityByRef(entId, forced);

			if (ent != null) {
				// ---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		} else {
			ToolLogServer.doLogInf("---reqGet use cache-----");
			cache_entity.reqCheckIfOld(key); // cache in 20 hour
		}


		//---do build something other of ent like details....
		if (ent!=null){					
			ent.doBuildAll(forced, forManager);
//			ent.doBuildViewCount(true, objId);
		}

		return ent;
	}

	// ---------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------
	private static CacheData<List<TaNsoOffer>> cache_lst = new CacheData<List<TaNsoOffer>>();

	private static void doLst(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<TaNsoOffer> list = reqLst(user, json); // and other params if necessary
		if (list == null ) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response	, ToolJSON.reqJSonString(// filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, list));
	}

	private static List<TaNsoOffer> reqLst(TaAutUser user, JSONObject json) throws Exception {
		Integer      nbLine    = ToolData.reqInt	(json, "nbLine"		, Integer.MAX_VALUE);
		Set<String>  searchKey = ToolData.reqSetStr	(json, "searchkey"	, null);
		Set<Integer> stat01    = ToolData.reqSetInt	(json, "stat01"		, null);
		Set<Integer> stat02    = ToolData.reqSetInt	(json, "stat02"		, null);
		Set<Integer> typs      = ToolData.reqSetInt	(json, "typ"		, null);

		if (typs == null && stat01 == null && stat02 == null) {
			return null;
		}

		Criterion        cri  = reqRestriction(user, searchKey, stat01, stat02, typs);
		List<TaNsoOffer> list = TaNsoOffer.DAO.reqList(0, nbLine, cri);

		return list;
	}

	private static void doLstByPar(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		List<TaNsoOffer> ent = reqLstByPar(user, json);

		if (ent == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)) {
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString( // filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, ent));
	}

	public static List<TaNsoOffer> reqLstByPar(TaAutUser user, JSONObject json) throws Exception {
		Integer parId  = ToolData.reqInt(json, "parId", null);
		Integer nbLine = ToolData.reqInt(json, "limit", 1);

		if (parId == null) {
			return null;
		}

		Criterion        cri  = Restrictions.eq(TaNsoOffer.ATT_I_PARENT, parId);

		List<TaNsoOffer> list = TaNsoOffer.DAO.reqList(0, nbLine, cri);
		
		TaTpyDocument.doBuildTpyDocuments(list, 
				DefDBExt.ID_TA_NSO_OFFER, 
				TaTpyDocument.TYPE_01_FILE_MEDIA, 
				TaTpyDocument.TYPE_02_FILE_IMG_AVATAR, TaNsoOffer.ATT_O_DOCUMENTS, false);

		return list;
	}
	
	private static void doLstByIds(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		// ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		List<TaNsoOffer> lst = reqLstByIds(user, json);

		if (lst == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

//		if (!canWorkWithObj(user, WORK_GET, ent)) {
//			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
//			return;
//		}

		API.doResponse(response, ToolJSON.reqJSonString( // filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA	, lst)
				);
	}

	public static List<TaNsoOffer> reqLstByIds(TaAutUser user, JSONObject json) throws Exception {
		Set<Integer>	lstIds  	= ToolData.reqSetInt	(json, "lstIds"		, null);
		Integer 		nbLine 		= ToolData.reqInt		(json, "limit"		, 10);

		if (lstIds == null) {
			return null;
		}

		Criterion        cri  = Restrictions.in(TaNsoOffer.ATT_I_ID, lstIds);

		List<TaNsoOffer> list = TaNsoOffer.DAO.reqList(0, nbLine, cri);
		
		TaTpyDocument.doBuildTpyDocuments(list, 
				DefDBExt.ID_TA_NSO_OFFER, 
				TaTpyDocument.TYPE_01_FILE_MEDIA, 
				TaTpyDocument.TYPE_02_FILE_IMG_AVATAR, TaNsoOffer.ATT_O_DOCUMENTS, false);

		return list;
	}
	
	// --------------------------------------------------------------------------------------------------------------
	private static CacheData<List<ViNsoOfferSearch>> cache_lst_vi_search = new CacheData<List<ViNsoOfferSearch>>();
	
	private void doLstViSearchByIds(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		List<ViNsoOfferSearch> lst = reqLstViSearchByIds(user, json);

		if (lst == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(
			DefJS.SESS_STAT	, 1, 
			DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
			DefJS.RES_DATA	, lst)
		);
	}

	private List<ViNsoOfferSearch> reqLstViSearchByIds(TaAutUser user, JSONObject json) throws Exception {
		Set<Integer>	ids				= ToolData.reqSetInt(json, "ids", null);
		Integer			begin			= ToolData.reqInt(json, "begin", 0);
		Integer			nbLine			= ToolData.reqInt(json, "nbLine", 20);

		if (ids == null) {
			return null;
		}
		
		String 					keyCache	= ids.toString();
		List<ViNsoOfferSearch>	lst			= cache_lst_vi_search.reqData(keyCache);
		
		if (lst != null && lst.size() > 0) {
			cache_lst_vi_search.reqCheckIfOld(keyCache);
			return lst;
		}
		
		lst = ViNsoOfferSearch.reqLstByIds(ids, begin, nbLine);
		
		cache_lst_vi_search.reqPut(keyCache, lst);
		return lst;
	}

	private static Criterion reqRestriction(
			TaAutUser user, Set<String> searchKey, Set<Integer> stat01, Set<Integer> stat02, Set<Integer> typs
	) throws Exception {
		if (stat01 == null) {
			stat01 = new HashSet<Integer>();
			stat01.add(TaNsoOffer.STAT_01_ACTIVE);
		}
		if (stat02 == null) {
			stat02 = new HashSet<Integer>();
			stat02.add(TaNsoOffer.STAT_02_PUBLIC);
		}

		Criterion cri = Restrictions.in(TaNsoOffer.ATT_I_STATUS_01, stat01);
		cri = Restrictions.and(cri, Restrictions.in(TaNsoOffer.ATT_I_STATUS_02, stat02));

		if (typs != null) {
			cri = Restrictions.and(cri, Restrictions.in(TaNsoOffer.ATT_I_TYPE_01, typs));
		}

		if (searchKey != null) {
			for (String s : searchKey) {
				cri = Restrictions.and(cri, 
						Restrictions.or(Restrictions.ilike(TaNsoOffer.ATT_T_TITLE, s), Restrictions.ilike(TaNsoOffer.ATT_T_CODE_01, s)));
			}
		}

		return cri;
	}

	// ---------------------------------------------------------------------------------------------------------

	private static Hashtable<String, Integer> mapCol = new Hashtable<String, Integer>() {
		{
			put("action", -1);
			put("id", 0);
		}
	};

	private static void doLstDyn(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Object[]     dataTableOption = ToolDatatable.reqDataTableOption(json, mapCol);
		Set<String>  searchKey       = (Set<String>) dataTableOption[0];

		Set<Integer> stat01          = ToolData.reqSetInt(json, "stat01", null);
		Set<Integer> stat02          = ToolData.reqSetInt(json, "stat02", null);
		Set<Integer> typs            = ToolData.reqSetInt(json, "typ", null);

		// --Pre-Check condition---------------------------------------------------
		if (typs == null && stat01 == null && stat02 == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_LST, null, null)) { // other param after objTyp...
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		Criterion        cri  = reqRestriction(user, searchKey, stat01, stat02, typs);

		List<TaNsoOffer> list = reqListDyn(dataTableOption, cri);
		if (list == null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		Integer iTotalRecords        = reqNbListDyn().intValue();
		Integer iTotalDisplayRecords = reqNbListDyn(cri).intValue();

		API.doResponse(response	, ToolJSON.reqJSonString(
				// filter,
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES, 
				"iTotalRecords"	, iTotalRecords,
				"iTotalDisplayRecords", iTotalDisplayRecords, 
				"aaData"		, list));

	}

	private static List<TaNsoOffer> reqListDyn(Object[] dataTableOption, Criterion cri) throws Exception {
		int              begin   = (int) dataTableOption[1];
		int              number  = (int) dataTableOption[2];
		int              sortCol = (int) dataTableOption[3];
		int              sortTyp = (int) dataTableOption[4];

		List<TaNsoOffer> list    = new ArrayList<TaNsoOffer>();

		Order            order   = null;
		String           colName = null;

		switch (sortCol) {
		case 0:
			colName = TaNsoOffer.ATT_I_ID;
			break;
//		case 1: colName = TaNsoOffer.ATT_T_CODE; break;	
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
			list = TaNsoOffer.DAO.reqList(begin, number, cri);
		else
			list = TaNsoOffer.DAO.reqList(begin, number, order, cri);

		return list;
	}

	private static Number reqNbListDyn() throws Exception {
		return TaNsoOffer.DAO.reqCount();
	}

	private static Number reqNbListDyn(Criterion cri) throws Exception {
		return TaNsoOffer.DAO.reqCount(cri);
	}

}
