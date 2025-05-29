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
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolDBLock;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolDate;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.tool.ToolString;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.nso.TaNsoPost;
import com.hnv.db.nso.vi.ViNsoPostNew;
import com.hnv.db.sys.TaSysLock;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyTranslation;
import com.hnv.def.DefDBExt;

/**
 * ----- ServiceNsoPost by H&V
 * ----- Copyright 2017------------
 */
public class ServiceNsoPost implements IService {
	private static	String 			filePath	= null; 
	private	static	String 			urlPath		= null; 

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 				= "EC_V3".toLowerCase();
	public static final String SV_CLASS 				= "ServiceNsoPost".toLowerCase();	

	public static final String SV_GET 					= "SVGet".toLowerCase();	
	public static final String SV_GETPOST 				= "SVGetPost".toLowerCase();	
	public static final String SV_GET_SIMPLE			= "SVGetSimple".toLowerCase();	

	public static final String SV_LST 					= "SVLst".toLowerCase();
	public static final String SV_LST_BY_IDS 			= "SVLstByIds".toLowerCase();
	public static final String SV_LST_DYN				= "SVLstDyn".toLowerCase(); 
	public static final String SV_LST_ALL_NEW			= "SVLstAllNew".toLowerCase();

	public static final String SV_GET_FILE 				= "SVGetFile".toLowerCase();	

	public static final String SV_NEW 					= "SVNew".toLowerCase();	
	public static final String SV_NEW_NEWS 				= "SVNewNews".toLowerCase();	
	public static final String SV_MOD 					= "SVMod".toLowerCase();
	public static final String SV_MOD_STAT 				= "SVModStat".toLowerCase();
	public static final String SV_MOD_POST 				= "SVModPost".toLowerCase();
	public static final String SV_TICK 					= "SVTick".toLowerCase();
	public static final String SV_DEL_TICK 				= "SVDelTick".toLowerCase();
	public static final String SV_DEL 					= "SVDel".toLowerCase();
	public static final String SV_LIKE 					= "SVLike".toLowerCase();
	public static final String SV_DISLIKE 				= "SVDislike".toLowerCase();
	public static final String SV_LST_USER_LIKE 		= "SVLstUserLike".toLowerCase();




	public static final String SV_MOD_TRANSL			= "SVModTransl".toLowerCase();	

	public static final String SV_LCK_REQ 				= "SVLckReq".toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 				= "SVLckSav".toLowerCase(); //save and continue
	public static final String SV_LCK_END 				= "SVLckEnd".toLowerCase();
	public static final String SV_LCK_DEL 				= "SVLckDel".toLowerCase();

	public static final String SV_LST_PAGE		        = "SVLstPage".toLowerCase();
	public static final String SV_LST_SEARCH		    = "SVLstSearch".toLowerCase();
	public static final String SV_LST_TOP_VIEW		    = "SVLstTopView".toLowerCase();


	public static final String SV_NEW_CMT				= "SVNewCmt".toLowerCase();
	public static final String SV_MOD_CMT				= "SVModCmt".toLowerCase();
	public static final String SV_LST_CMT				= "SVLstCmt".toLowerCase();

	// SVNsoPostDel12H
	public static final String SV_DEL_COMMENT_12H 		= "SVNsoPostDel12H".toLowerCase();
	// SVNsoPostDel12H

	public static final Integer	ENT_TYP					= DefDBExt.ID_TA_NSO_POST;
	private static Set<String> filter = new HashSet<String>();

	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceNsoPost(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");

		if (filePath	==null) filePath		= API.reqContextParameter("NSO_POST_PATH_FILE");
		if (urlPath		==null) urlPath			= API.reqContextParameter("NSO_POST_PATH_URL");	
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");

		try {
			//---------------------------------------------------------------------------------
			//---------------------------------------------------------------------------------
			//---------------------------------------------------------------------------------
			if(sv.equals(SV_GET) 						&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGet(user, json, response);
			} else if(sv.equals(SV_GET_SIMPLE) 			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGetSimple(user, json, response);		

			} else if(sv.equals(SV_LST)					&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLst(user, json, response);
				
			} else if(sv.equals(SV_GETPOST)				){ 
				doGetNews(user, json, response);
			
			} else if(sv.equals(SV_LST_BY_IDS)			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstByIds(user, json, response);
			} else if(sv.equals(SV_LST_DYN)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstDyn(user, json, response);


				//} else if(sv.equals(SV_GET_FILE)			&& APIAuth.canAuthorize(user, SV_CLASS, svNSO_POST_G)){
			} else if(sv.equals(SV_GET_FILE)			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doGetFile(user, json, response);	

				//} else if(sv.equals(SV_NEW)					&& APIAuth.canAuthorize(user, SV_CLASS, svNSO_POST_N)){
			} else if(sv.equals(SV_NEW)					&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_NSO_POST_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNew(user, json, response);
			} else if(sv.equals(SV_NEW_NEWS)			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW, APIAuth.R_NSO_POST_NEW)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doNewNews(user, json, response);




			} else if(sv.equals(SV_TICK)				){
				doTick(user, json, response);
			} else if(sv.equals(SV_DEL_TICK)			){
				doDelTick(user, json, response);

			} else if(sv.equals(SV_LIKE)				){
				doLike(user, json, response);
			} else if(sv.equals(SV_DISLIKE)				){
				doDislike(user, json, response);
			} else if(sv.equals(SV_LST_USER_LIKE)		){
				doLstUserLike(user, json, response);


			} else if(sv.equals(SV_MOD)					){
				doMod(user, json, response);
			} else if(sv.equals(SV_MOD_STAT)			){
				doModStat(user, json, response);
			} else if(sv.equals(SV_MOD_POST)			){
				doModPost(user, json, response);

			} else  if(sv.equals(SV_DEL)				){
				//if (APIAuth.canAuthorize(user, SV_CLASS, sv))
				doDel(user, json, response);

			}else  if(sv.equals(SV_DEL_COMMENT_12H)		){
				doDelComment12h(user, json, response);

			}else if(sv.equals(SV_MOD_TRANSL)			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_NSO_POST_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doModTransl(user, json, response);


			} else if(sv.equals(SV_LCK_REQ)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckReq(user, json, response);
				//} else if(sv.equals(SV_LCK_SAV)				&& APIAuth.canAuthorize(user, SV_CLASS, svNSO_POST_M)){
			} else if(sv.equals(SV_LCK_SAV)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_NSO_POST_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckSav(user, json, response);
				//} else if(sv.equals(SV_LCK_END)				&& APIAuth.canAuthorize(user, SV_CLASS, svNSO_POST_M)){
			} else if(sv.equals(SV_LCK_END)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD, APIAuth.R_NSO_POST_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckEnd(user, json, response);
				//} else if(sv.equals(SV_LCK_DEL)				&& APIAuth.canAuthorize(user, SV_CLASS, svNSO_POST_M)){
			} else if(sv.equals(SV_LCK_DEL)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_NSO_POST_MOD)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLckDel(user, json, response);		

			} else if(sv.equals(SV_NEW_CMT)				){
				doNewCmt(user, json, response);

			} else if(sv.equals(SV_MOD_CMT)				){
				doModCmt(user, json, response);

			}else if(sv.equals(SV_LST_CMT)				){
				doLstCmt(user, json, response);

			}else if(sv.equals(SV_LST_ALL_NEW)				){
				doLstAllNew(user, json, response);
				
			}else if(sv.equals(SV_LST_PAGE)				&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))){
				doLstPage(user, json, response);

			} else if(sv.equals(SV_LST_SEARCH)			&& ( APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET, APIAuth.R_NSO_POST_GET)
					||	 APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLstSearch(user, json, response);

			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}
		} catch (Exception e) {
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			ToolLogServer.doLogErr("-----Exception in SVAutUser-----:" + e.getMessage());
			ToolLogServer.doLogErr(e, true);
		}	
	}
	//---------------------------------------------------------------------------------------------------------
	private static final int WORK_ALL 	= 0;
	private static final int WORK_GET 	= 1;
	private static final int WORK_LST 	= 2;
	private static final int WORK_NEW 	= 3;
	private static final int WORK_MOD 	= 4;
	private static final int WORK_DEL 	= 5;
	private static final int WORK_UPL 	= 10; //upload

	private static boolean canWorkWithObj ( TaAutUser user, int typWork, TaNsoPost ent, Object...params){
		//if (user.canBeSuperAdmin()) return true;
		Integer ownerId	;
		switch(typWork){
		case WORK_ALL : 
			ownerId		= ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01);
			if (!user.reqId().equals(ownerId)) {
				return false;
			}
			return true;
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
			ownerId		= ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01);

			if (!user.reqId().equals(ownerId)) {
				if (!APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_NSO_POST_MOD)) return false;
			}
			return true;	
		case WORK_DEL : 
			ownerId		= ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01);

			if (!user.reqId().equals(ownerId)) {
				if (!APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL, APIAuth.R_NSO_POST_DEL)) return false;
			}

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

		TaNsoPost 			ent 		= reqGet(user, json);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}

	private static CacheData<TaNsoPost> 		cache_entity= new CacheData<TaNsoPost>		(500, DefTime.TIME_24_00_00_000 );
	public static TaNsoPost reqGet(TaAutUser user, JSONObject json) throws Exception{
		Integer 			objId		= ToolData.reqInt	(json, "id"			, -1	);	
		String				objCode		= ToolData.reqStr	(json, "code"		, null);
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		if(objCode == null|| objId < 0) return null;

		String 			key		= objId+":"+objCode;
		TaNsoPost 		ent 	= cache_entity.reqData(key);	
		if (forced || ent == null) {
			ent 	= TaNsoPost.DAO.reqEntityByValues(TaNsoPost.ATT_I_ID, objId,TaNsoPost.ATT_T_CODE_01, objCode);
		}

		//---do build something other of ent like details....
		if (ent!=null){	
			Set<Integer> statSet = new HashSet<>();
			statSet.add(TaNsoPost.STAT_01_NEW);
			statSet.add(TaNsoPost.STAT_01_ACTIVE);
			statSet.add(TaNsoPost.STAT_01_ACTIVE_02);
			statSet.add(TaNsoPost.STAT_01_ACTIVE_03);

			Integer statEnt = (Integer) ent.req(TaNsoPost.ATT_I_STATUS_01);

			if(!forManager && !statSet.contains(statEnt))	return null;			
			ent.doBuildAll(forced, forManager);
			//			ent.doBuildViewCount(true, objId);

			cache_entity.reqPut(key, ent);
		}

		return ent;
	}
	//--------------------------------------------------------
	private static void doGetNews(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		TaNsoPost 			ent 		= reqGetPost(user, json);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	public static TaNsoPost reqGetPost(TaAutUser user, JSONObject json) throws Exception{
		Integer 			objId		= ToolData.reqInt	(json, "id"			, -1	);	
		String				objCode		= ToolData.reqStr	(json, "code"		, null);
		Boolean				forced		= ToolData.reqBool	(json, "forced"		, false	);
		Boolean				forManager	= ToolData.reqBool	(json, "forManager"	, false	);

		if(objId < 0) return null;

		String 			key		= objId+":";
		TaNsoPost 		ent 	= cache_entity.reqData(key);
		if (forced || ent == null) {
			ent 	= TaNsoPost.DAO.reqEntityByValues(TaNsoPost.ATT_I_ID, objId);
		}

		//---do build something other of ent like details....
		if (ent!=null){	
			String entCode = ent.reqStr(TaNsoPost.ATT_T_CODE_01);
			if (objCode!=null && entCode!=null && objCode.toLowerCase().equals(entCode.toLowerCase())) return null;
			
			Set<Integer> statSet = new HashSet<>();
			statSet.add(TaNsoPost.STAT_01_NEW);
			statSet.add(TaNsoPost.STAT_01_ACTIVE);
			statSet.add(TaNsoPost.STAT_01_ACTIVE_02);
			statSet.add(TaNsoPost.STAT_01_ACTIVE_03);

			Integer statEnt = (Integer) ent.req(TaNsoPost.ATT_I_STATUS_01);

			if(!forManager && !statSet.contains(statEnt))	return null;				
			ent.doBuildAll(forced, forManager);
			//			ent.doBuildViewCount(true, objId);

			cache_entity.reqPut(key, ent);
		}

		return ent;
	}


	private static void doGetSimple(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");

		Integer 			objId		= ToolData.reqInt	(json, "id"			, -1	);		
		TaNsoPost 			ent 		= reqGetSimple(objId);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		
				filter_EntSimple	,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}
	private static Set<String> filter_EntSimple = new HashSet<String>();
	static {
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_02);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_03);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_04);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_05);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_06);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_07);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_08);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_09);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_10);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_STATUS_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_TYPE_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_AUT_USER_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_D_DATE_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_D_DATE_02);


		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_INFO_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_INFO_02);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_INFO_03);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_INFO_04);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_INFO_05);

		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_TYPE_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_TYPE_02);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_PARENT);

		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_D_DATE_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_D_DATE_02);

		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_VAL_01);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_VAL_02);
		filter_EntSimple.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_I_VAL_03);
	}

	public static TaNsoPost reqGetSimple(Integer objId) throws Exception{
		String 			key		= objId+"";
		TaNsoPost 		ent 	= cache_entity.reqData(key);	
		if (ent ==null) {
			ent 	= TaNsoPost.DAO.reqEntityByRef(objId, false);
		}
		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGetFile(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doGet --------------");
		JSONObject		obj			= ToolData.reqJson (json, "obj"		, null);
		Integer 			objId	= ToolData.reqInt	(json, "id"		, -1	);				
		Boolean				forced	= ToolData.reqBool	(json, "forced"	, true	);

		TaNsoPost 		ent 	= reqGetFile(obj, forced);

		if (ent==null){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}

	private static TaNsoPost reqGetFile(JSONObject obj, Boolean forced) throws Exception{
		//		TaNsoPost 		ent 	= TaNsoPost.DAO.reqEntityByRef(objId, false);

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);
		TaNsoPost 		ent 		= new TaNsoPost(attr);

		//---do build something other of ent like details....
		ent.doBuildDocuments(forced);

		return ent;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<TaNsoPost> 	list = reqLst(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	private static List<TaNsoPost> reqLst(TaAutUser user, JSONObject json) throws Exception {
		Integer 			objMan		= ToolData.reqInt	(json, "manId"	, null	);
		Integer 			objTyp		= ToolData.reqInt	(json, "typ"		, null	);
		Boolean 			objContBuild= ToolData.reqBool	(json, "withBuild"	, false	);

		Criterion 			cri		= reqCriterion (objTyp); //and other params	
		List<TaNsoPost> 	list 	= null;
		if (cri==null) 
			list =   TaNsoPost.DAO.reqList();
		else
			list =   TaNsoPost.DAO.reqList(cri);


		//do something else with request
		if (objContBuild){

		}

		return list;
	}

	//---------------------------------------------------------------------------------------------------------
	private static CacheData<List<ViNsoPostNew>> 		cache_lst_vi_post_new = new CacheData<List<ViNsoPostNew>>		(500, DefTime.TIME_24_00_00_000 );
	private static void doLstByIds(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViNsoPostNew> 	list = reqLstByIds(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));				
	}

	private static List<ViNsoPostNew> reqLstByIds(TaAutUser user, JSONObject json) throws Exception {
		Set<Integer> 		ids			= ToolData.reqSetInt	(json, "ids"		, null	);
		Integer 			begin		= ToolData.reqInt		(json, "begin"		, 0		);
		Integer 			nbLine		= ToolData.reqInt		(json, "nbLine"		, 20	);

		if (ids == null) {
			return null;
		}

		String 					keyCache	= ids.toString();
		List<ViNsoPostNew>		lst			= cache_lst_vi_post_new.reqData(keyCache);

		if (lst != null && lst.size() > 0) {
			cache_lst_vi_post_new.reqCheckIfOld(keyCache);
			return lst;
		}

		lst = ViNsoPostNew.reqNsoPostLstByIds(ids, begin, nbLine);

		cache_lst_vi_post_new.reqPut(keyCache, lst);
		return lst;
	}

	//---------------------------------------------------------------------------------------------------------
	private static Criterion reqCriterion(Object...params) throws Exception{
		if (params==null || params.length==0) return null;

		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (params!=null && params.length>0){
			//int type 	= (int) params[0];
			//cri 		= Restrictions.and(cri, Restrictions.eqOrIsNull(TaNsoPost.ATT_I_TYPE, type));
		}			

		if (params!=null && params.length>1){
			//do something
		}

		return cri;
	}

	private static void doLstSearch(TaAutUser user,  JSONObject json, HttpServletResponse response)	throws Exception {

		String				searchkey		= ToolData.reqStr	(json, "searchkey"	, "%").toLowerCase();// Integer.parseInt(request.getParameter("typ01")); 	
		Integer				nbLineMax		= ToolData.reqInt	(json, "nbLine"		, 10 );

		Integer 		    type			= ToolData.reqInt	(json, "type"		, null	);
		String				typeMultiStr	= ToolData.reqStr	(json, "typMulti"	, null);
		Set<Integer>		typeMulti		= typeMultiStr!=null?ToolSet.reqSetInt(typeMultiStr):null;

		Criterion cri	= null;

		cri 			= Restrictions.ilike(TaNsoPost.ATT_T_TITLE							, "%"+searchkey+"%");

		if (typeMulti!=null) {
			cri				= Restrictions.and(cri, Restrictions.in(TaNsoPost.ATT_I_TYPE_01	, typeMulti));
		}else if (type!=null){
			cri				= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_01	, type));
		}


		List<TaNsoPost> 	postLst		= TaNsoPost.DAO.reqList(0, nbLineMax, Order.asc(TaNsoPost.ATT_T_TITLE), cri);

		API.doResponse(response		, ToolJSON.reqJSonStringWithNull(
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES, 
				DefJS.RES_DATA		, postLst));

	}
	//---------------------------------------------------------------------------------------------------------		
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
		{
			put("action", -1);
			put("id"	, 0 );
			put("title"	, 1 );
			put("stat"	, 2 );
			put("code01", 3 );
			put("code02", 4 );
		}
	};
	private static Long countAllEle = null;
	private static void doLstDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {	
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, mapCol);
		Set<String>			searchKey		= (Set<String>)dataTableOption[0];	
		Integer				stat01			= ToolData.reqInt	(json, "stat01"		, null);
		Integer				stat02			= ToolData.reqInt	(json, "stat02"		, null);

		Integer				typ01			= ToolData.reqInt	(json, "typ01"		, null);
		Set<Integer>		typ01Multi		= ToolData.reqSetInt(json, "typ01Multi"	, null);

		Set<Integer>		objTypMult		= new HashSet<Integer>() ;
		if(typ01 == null && typ01Multi  == null) {
			objTypMult.add(TaNsoPost.TYPE_01_BLOG);
			objTypMult.add(TaNsoPost.TYPE_01_OFFER);

		}else if (typ01Multi  != null) {
			objTypMult = typ01Multi;
		}else if(typ01!=null ){
			objTypMult.add(typ01);
		}

		if (!canWorkWithObj(user, WORK_LST, null, stat01)){ //other param after objTyp...
			API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			return;
		}

		Criterion 	cri 			= reqRestriction(searchKey, stat01, stat02, objTypMult);				

		List<TaNsoPost> list 		= reqListDyn(dataTableOption, cri);		
		if (list==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		} else {
			TaNsoPost.doBuildListUsers (list,true);
		}

		if (countAllEle==null)
			countAllEle = ((long)reqNbListDyn());

		Integer iTotalRecords 			= (countAllEle.intValue());				
		Integer iTotalDisplayRecords 	= reqNbListDyn(cri).intValue();


		API.doResponse(response			, ToolJSON.reqJSonString(		filter,
				DefJS.SESS_STAT			, 1, 
				DefJS.SV_CODE			, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"			, iTotalRecords,
				"iTotalDisplayRecords"	, iTotalDisplayRecords,
				"aaData"				, list
				));

	}

	private static Criterion reqRestriction(Set<String> searchKey, Integer stat01, Integer stat02, Set<Integer> type01s) throws Exception {		

		Criterion cri = null;
		if(stat01 != null){
			cri = Restrictions.eq(TaNsoPost.ATT_I_STATUS_01, stat01);
		}

		if(stat02 != null){
			cri = Restrictions.eq(TaNsoPost.ATT_I_STATUS_02, stat02);
		}

		for (String s : searchKey){
			if (cri==null)
				cri = 	Restrictions.ilike(TaNsoPost.ATT_T_TITLE		, s);

			else
				cri = 	Restrictions.and(	cri, 
						Restrictions.ilike(TaNsoPost.ATT_T_TITLE		, s)
						);
		}

		if(type01s!=null) {
			cri = Restrictions.and(	cri, 
					Restrictions.in(TaNsoPost.ATT_I_TYPE_01 , type01s)
					);
		}

		return cri;
	}

	private static List<TaNsoPost> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<TaNsoPost> list 	= new ArrayList<TaNsoPost>();		

		Order 	order 	= null;			
		String 	colName = null;

		switch(sortCol){
		case 0: colName = TaNsoPost.ATT_I_ID		; break;		
		case 1: colName = TaNsoPost.ATT_T_TITLE		; break;		
		case 2: colName = TaNsoPost.ATT_I_STATUS_01	; break;		
		case 3: colName = TaNsoPost.ATT_T_CODE_01	; break;		
		case 4: colName = TaNsoPost.ATT_T_CODE_02	; break;		
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc (colName); break;
			case 1: order = Order.desc(colName); break;								
			}
		}

		if (order==null)
			list	= TaNsoPost.DAO.reqList(begin, number, cri);
		else
			list	= TaNsoPost.DAO.reqList(begin, number, order, cri);			

		return list;
	}

	private static Number reqNbListDyn() throws Exception {		
		return TaNsoPost.DAO.reqCount();		
	}

	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return TaNsoPost.DAO.reqCount(cri);
	}

	private static Number reqNbUserListDyn(String sql) throws Exception {			
		return TaNsoPost.DAO.reqCount(sql);
	}
	//------------------------------------------------------------------------------------------------------------
	private static void doNewNews(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaNsoPost 			ent		= reqNewNews		(user, json);
		if (ent==null){
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, DefDBExt.ID_TA_NSO_POST, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));

		Integer	  		manId		= user.reqPerManagerId();
		TaAutUser 		autUser 	= TaAutUser.DAO.reqEntityByRef(manId);

//		ProcessMsg.doNewNotificationPost(ProcessMsg.TAB_POST, ProcessMsg.TYP_POST_NEW, autUser, ent);
	}

	private static TaNsoPost reqNewNews(TaAutUser user, JSONObject json) throws Exception {
		JSONObject		obj			= ToolData.reqJson 	(json, "obj"	,null);
		Integer 		stat01		= ToolData.reqInt	(obj , "stat01"	,null);

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);	

		Integer			userId		= user.reqId();
		attr.put(TaNsoPost.ATT_I_AUT_USER_01, userId);
		//cho nay khong duoc bo, phai dam bao post duoc gui boi ai do dang nhap, neu la visitor thi la id cua visitor


		TaNsoPost  	ent	 		= new TaNsoPost(attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}

		String 		code01		= ent.reqStr(TaNsoPost.ATT_T_CODE_01);
		String 		code02		= ent.reqStr(TaNsoPost.ATT_T_CODE_02);
		Integer 	type01		= ent.reqInt(TaNsoPost.ATT_I_TYPE_01);
		Integer 	type02		= ent.reqInt(TaNsoPost.ATT_I_TYPE_02);
		Integer 	type03		= ent.reqInt(TaNsoPost.ATT_I_TYPE_03);
		Integer 	stat02		= ent.reqInt(TaNsoPost.ATT_I_STATUS_02);
		String 		title		= ent.reqStr(TaNsoPost.ATT_T_TITLE);
		String  	dtNow		= ToolDate.reqString(new Date(), "yyMMddHHmm");
		String 		urlGen 		= ToolString.reqCovertStringToURL(title);

		if (code01==null || code01.trim().length()==0) {
			code01 = urlGen + "-" + dtNow;
		}
		if (code02==null || code02.trim().length()==0) {
			code01 = "O_" + dtNow;
		}

		if (stat01 ==null) {
			stat01 = TaNsoPost.STAT_01_NEW;
		}
		//		else if (stat01.equals(TaNsoPost.STAT_01_ACTIVE) && user.canBeClient()) {
		//			stat01 = TaNsoPost.STAT_01_NEW;
		//		}

		if (stat02 == 0) {
			stat02 = TaNsoPost.STAT_02_PUBLIC;
		}

		if (type01==null){	
			type01 = TaNsoPost.TYPE_01_BLOG;
		}

		ent.reqSet(TaNsoPost.ATT_T_CODE_01		, code01);
		ent.reqSet(TaNsoPost.ATT_T_CODE_02		, code02);
		ent.reqSet(TaNsoPost.ATT_I_TYPE_01		, type01);
		ent.reqSet(TaNsoPost.ATT_I_TYPE_03		, type03);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_01	, stat01);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_02	, stat02);
		ent.reqSet(TaNsoPost.ATT_I_VAL_03		, 	0	);
		ent.reqSet(TaNsoPost.ATT_D_DATE_01		, new Date());
		ent.reqSet(TaNsoPost.ATT_D_DATE_02		, new Date());

		TaNsoPost.DAO.doPersist(ent);

		//reqSavePostDetail(user, ent, obj);
		int				entId 		= (int) ent.req(TaNsoPost.ATT_I_ID);

		//Update attach files
		//--this list can contain: stat_new, stat_duplicated, stat_deleted, stat_validated
		JSONArray		docs		= (JSONArray) obj.get("files");
		ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));

		return ent;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaNsoPost 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		TaSysLock 	lock 	= ToolDBLock.reqLock (json, "lock", DefDB.DB_LOCK_NEW, DefDBExt.ID_TA_NSO_POST, (Integer)ent.req(TaNsoPost.ATT_I_ID), user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent,
				"lock"				, lock
				));
	}

	private static TaNsoPost reqNew(TaAutUser user, JSONObject json) throws Exception {
		JSONObject		obj			= ToolData.reqJson (json, "obj", null);
		obj.put("id", null);

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);	

		Integer			userId		= user.reqId();
		attr.put(TaNsoPost.ATT_I_AUT_USER_01, userId);
		//cho nay khong duoc bo, phai dam bao post duoc gui boi ai do dang nhap, neu la visitor thi la id cua visitor


		TaNsoPost  	ent	 		= new TaNsoPost(attr);

		if (!canWorkWithObj(user, WORK_NEW, ent)){ //other param after obj...
			return null;
		}

		String 		code01		= ent.reqStr(TaNsoPost.ATT_T_CODE_01);
		String 		code02		= ent.reqStr(TaNsoPost.ATT_T_CODE_02);
		Integer 	type01		= null;
		Integer 	type02		= null;
		Integer 	stat01		= null;
		Integer 	stat02		= ent.reqInt(TaNsoPost.ATT_I_STATUS_02);
		String 		title		= ent.reqStr(TaNsoPost.ATT_T_TITLE);
		String  	dtNow		= ToolDate.reqString(new Date(), "yyMMddHHmm");
		String 		urlGen 		= ToolString.reqCovertStringToURL(title);

		if (code01==null || code01.trim().length()==0) {
			code01 = urlGen + "-" + dtNow;
		}
		if (code02==null || code02.trim().length()==0) {
			code01 = "O_" + dtNow;
		}

		if (stat01 ==null) {
			stat01 = TaNsoPost.STAT_01_ACTIVE;
		}
		//		else if (stat01.equals(TaNsoPost.STAT_01_ACTIVE) && user.canBeClient()) {
		//			stat01 = TaNsoPost.STAT_01_NEW;
		//		}

		if (stat02 ==null) {
			stat02 = TaNsoPost.STAT_02_PUBLIC;
		}

		if (type01==null){	
			type01 = TaNsoPost.TYPE_01_BLOG;
		}

		ent.reqSet(TaNsoPost.ATT_T_CODE_01		, code01);
		ent.reqSet(TaNsoPost.ATT_T_CODE_02		, code02);
		ent.reqSet(TaNsoPost.ATT_I_TYPE_01		, type01);
		ent.reqSet(TaNsoPost.ATT_I_TYPE_02		, type02);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_01	, stat01);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_02	, stat02);
		ent.reqSet(TaNsoPost.ATT_I_VAL_03		, 	0	);
		ent.reqSet(TaNsoPost.ATT_D_DATE_01		, new Date());
		ent.reqSet(TaNsoPost.ATT_D_DATE_02		, new Date());
		ent.reqSet(TaNsoPost.ATT_I_ID			, null);

		TaNsoPost.DAO.doPersist(ent);

		//reqSavePostDetail(user, ent, obj);
		int				entId 		= (int) ent.req(TaNsoPost.ATT_I_ID);

		//Update attach files
		//--this list can contain: stat_new, stat_duplicated, stat_deleted, stat_validated
		JSONArray		docs		= (JSONArray) obj.get("files");
		if(docs!=null){
			ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));
		}
		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static Set<String> filterForLst = new HashSet<String>();
	static {
		filterForLst.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_02);
//		filterForLst.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_03); //Type user
//		filterForLst.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_04); //List like
		filterForLst.add(TaNsoPost.class.getSimpleName()+"."+TaNsoPost.ATT_T_CONTENT_05);
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLstPage(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		ResultPagination  	res = reqLstPage(user, json); //and other params if necessary

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				filterForLst		,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res 
				));	
	}

	//--------------------------------PARTNER MORAL-------------------------------------------------------------------------

	private static ResultPagination reqLstPage(TaAutUser user, JSONObject json) throws Exception {
		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 20);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);

		Integer 		typ01    	= ToolData.reqInt	(json, "type"    	, null);
		Set<Integer> 	langId 		= ToolData.reqSetInt(json, "langId"		, null); // format (1,2,3)
		Set<Integer> 	stats 		= ToolData.reqSetInt(json, "multiStat"	, null); // format (1,2,3)
		Integer	        ownerId 	= ToolData.reqInt   (json, "userID"		, null); 

		Boolean			withTransl	= ToolData.reqBool	(json, "withTransl" , true);
		Boolean			withAva		= ToolData.reqBool	(json, "withAva"  	, false);

		String 			searchkey   = ToolData.reqStr	(json, "searchkey"	, "%");
		String 			filtertyp03   = ToolData.reqStr	(json, "filtertyp03", null);

		Boolean 		forced 		= ToolData.reqBool	(json, "forced"		, false);
		Integer 		entTyp		= ToolData.reqInt	(json, "entTyp"   	, null);
		Integer 		entId    	= ToolData.reqInt	(json, "entId"    	, null);
		String keyWord 	= begin + "_" + number + "_" + total + "_" + typ01 + "_"+ entTyp + "_"+ entId + "_" + langId + stats + "_" + ownerId + "_"+searchkey + "_" +withAva ;

		ResultPagination rs = null;
		if(!forced)		 rs = cache_rs.reqData(keyWord);

		if(forced || rs == null || rs.reqList().size()==0) {
			ToolLogServer.doLogInf("---reqGetLstGrid build vi-----");
			Criterion cri   = null;
			cri 			= Restrictions.ilike(TaNsoPost.ATT_T_TITLE								, "%"+searchkey+"%");
			cri 			= Restrictions.or(cri, Restrictions.ilike(TaNsoPost.ATT_T_CONTENT_01	, "%"+searchkey+"%"));
			cri 			= Restrictions.or(cri, Restrictions.ilike(TaNsoPost.ATT_T_CONTENT_02	, "%"+searchkey+"%"));

			if(ownerId != null){
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_AUT_USER_01, user.reqId()));
				stats	= null; //--get all stats for this user
			}
			if(stats 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.in(TaNsoPost.ATT_I_STATUS_01	, stats));
			if(filtertyp03 != null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_03		, Integer.parseInt(filtertyp03)));
			if(typ01 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_01		, typ01));
			if(entTyp 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_VAL_01		, entTyp));
			if(entId 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_VAL_02		, entId));


			//List<Order> orders = Arrays.asList(Order.desc(TaNsoPost.ATT_I_ID), Order.desc(TaNsoPost.ATT_I_VAL_05));
			//List<TaNsoPost> list = TaNsoPost.DAO.reqList(begin, number, orders, cri);
			List<TaNsoPost> list=null;
			if(typ01==TaNsoPost.TYPE_01_BLOG){
				list = TaNsoPost.DAO.reqList(begin, number, Order.desc(TaNsoPost.ATT_I_VAL_05), cri);

			}else if(typ01==TaNsoPost.TYPE_01_NEWS){
				list = TaNsoPost.DAO.reqList(begin, number, Order.desc(TaNsoPost.ATT_I_ID), cri);
			}

			List<ViAutUserMember> lstUsers = TaAutUser.reqBuildUserMember(list, TaNsoPost.ATT_I_AUT_USER_01, TaNsoPost.ATT_O_USER_01);
			TaTpyDocument	.reqBuildAvatar( lstUsers, DefDBExt.ID_TA_AUT_USER, ViAutUserMember.ATT_O_AVATAR);
			TaNsoPost		.doBuildCmtCount(user, list);
			if(withAva) TaNsoPost.doBuildAvatarForList(list);

			if (list==null || list.isEmpty() ){
				rs								= new ResultPagination(list, 0, 0, 0, searchkey);
			}else {
				if (total == 0 )	total		= TaNsoPost.DAO.reqCount(cri).intValue();
				rs								= new ResultPagination(list, total, begin, number, searchkey);
			}

			cache_rs.reqPut(keyWord, rs);	
		} else {
			ToolLogServer.doLogInf("---reqNsoAreaLstGrid use cache-----");
			cache_rs.reqCheckIfOld(keyWord); //cache in 2 hour
		}

		return rs;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLstAllNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		ResultPagination  	lst = reqLstAllew(user, json); //and other params if necessary

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				filterForLst		,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, lst 
				));	
	}
	
	private static ResultPagination reqLstAllew(TaAutUser user, JSONObject json) throws Exception {
		Integer 		typ01    		= ToolData.reqInt	(json, "type"    	, null);
		Set<Integer> 	typ03    		= ToolData.reqSetInt(json, "typ03"    	, null);
		Set<Integer> 	stats 			= ToolData.reqSetInt(json, "multiStat"	, null); // format (1,2,3)
		Integer	        ownerId 		= ToolData.reqInt   (json, "userID"		, null); 

		Boolean			withTransl		= ToolData.reqBool	(json, "withTransl" , true);
		Boolean			withAva			= ToolData.reqBool	(json, "withAva"  	, false);

		String 			searchkey		= ToolData.reqStr	(json, "searchkey"	, "%");
		Integer 		filtercont05	= ToolData.reqInt	(json, "filtercont05", null);

		Boolean 		forced 			= ToolData.reqBool	(json, "forced"		, false);
		Integer 		entTyp			= ToolData.reqInt	(json, "entTyp"   	, null);
		Integer 		entId    		= ToolData.reqInt	(json, "entId"    	, null);
		
		ResultPagination rs = null;
		if(forced) {
			ToolLogServer.doLogInf("---reqGetLstGrid build vi-----");
			Criterion cri   = null;
			cri 			= Restrictions.ilike(TaNsoPost.ATT_T_TITLE								, "%"+searchkey+"%");
			cri 			= Restrictions.or(cri, Restrictions.ilike(TaNsoPost.ATT_T_CONTENT_01	, "%"+searchkey+"%"));
			cri 			= Restrictions.or(cri, Restrictions.ilike(TaNsoPost.ATT_T_CONTENT_02	, "%"+searchkey+"%"));
			
			if(filtercont05 != null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_VAL_05	, filtercont05));
			if(typ03 	!= null)
				for(Integer value : typ03) {
					cri 	= Restrictions.or(cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_03, value));
				}
			if(ownerId != null){
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_AUT_USER_01, user.reqId()));
				stats	= null; //--get all stats for this user
			}
			if(stats 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.in(TaNsoPost.ATT_I_STATUS_01	, stats));
			if(typ01 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_01		, typ01));
			if(entTyp 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_VAL_01		, entTyp));
			if(entId 	!= null)
				cri 	= Restrictions.and(cri, Restrictions.eq(TaNsoPost.ATT_I_VAL_02		, entId));
			

			//List<Order> orders = Arrays.asList(Order.desc(TaNsoPost.ATT_I_ID), Order.desc(TaNsoPost.ATT_I_VAL_05));
			//List<TaNsoPost> list = TaNsoPost.DAO.reqList(begin, number, orders, cri);
			List<TaNsoPost> list=null;
			if(typ01==TaNsoPost.TYPE_01_BLOG){
				list = TaNsoPost.DAO.reqList(Order.desc(TaNsoPost.ATT_I_VAL_05), cri);

			}else if(typ01==TaNsoPost.TYPE_01_NEWS){
				list = TaNsoPost.DAO.reqList(Order.desc(TaNsoPost.ATT_I_ID), cri);
			}

			List<ViAutUserMember> lstUsers = TaAutUser.reqBuildUserMember(list, TaNsoPost.ATT_I_AUT_USER_01, TaNsoPost.ATT_O_USER_01);
			TaTpyDocument	.reqBuildAvatar( lstUsers, DefDBExt.ID_TA_AUT_USER, ViAutUserMember.ATT_O_AVATAR);
			TaNsoPost		.doBuildCmtCount(user, list);
			if(withAva) TaNsoPost.doBuildAvatarForList(list);

			rs = new ResultPagination(list, 0, 0, 0, searchkey);
	
		}

		return rs;
	}
	//---------------------------------------------------------------------------------------------------------
	
	private static void doLstUserLike(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		ResultPagination  	res = reqLstUserLike(user, json);

		//if (ent==null){
		//	API.doResponse(response		, DefAPI.API_MSG_KO);
		//} else {
		API.doResponse(response		, ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res
				));	
		//}		
	}

	private static ResultPagination reqLstUserLike(TaAutUser user, JSONObject json) throws Exception {
		int				entId 		= ToolData.reqInt(json, "id", null);
		Boolean			withAva		= ToolData.reqBool(json, "withAva", false);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);
		List<TaAutUser> userlst 	= new ArrayList<>(); 
		String 			obj			= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);
		ResultPagination rs = null;
		if (ent==null){
			return null;
		}
//		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
//			return null;
//		}
		String 			cont04		= ent.reqStr(TaNsoPost.ATT_T_CONTENT_04);
		if (cont04!=null)
		{
			if (cont04 instanceof String) {
				String jsonString = (String) cont04;
				JSONArray jsonArray = ToolJSON.reqJSonArrayFromString(jsonString);
				for (int i=0; i<jsonArray.size(); i++)
				{
					Object 		element = jsonArray.get(i);
					if (element instanceof Long) {
						long longValue 	= (long) element;
						int intValue	= (int)	longValue;
						TaAutUser 	userlike = TaAutUser.DAO.reqEntityByRef(intValue);
						userlst.add(userlike);
					}
				} 
			}   
		}
		
		if (withAva) TaTpyDocument.reqBuildAvatar(userlst, DefDBExt.ID_TA_AUT_USER, ViAutUserDyn.ATT_O_AVATAR);
		
		if (userlst==null || userlst.isEmpty() ){
			rs= new ResultPagination(userlst, 0, 0, 0);
		}
		else {
			rs= new ResultPagination(userlst, 0, 0, 0);
		}
		return rs;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLstCmt(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		ResultPagination  	res = TaNsoPost.reqGetPost(user, json, null, null); //and other params if necessary

		if (res.reqList()==null ){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response				, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res 
				));	
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doLike(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaNsoPost 		ent 	=reqLike(user,json);

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

	private static TaNsoPost reqLike(TaAutUser user, JSONObject json) throws Exception {
		Integer			manId		= user.reqId();
		int				entId 		= ToolData.reqInt(json, "id", null);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);
		String 			obj			= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		if (ent==null){
			return null;
		}

		Integer 			v04		= ent.reqInt(TaNsoPost.ATT_I_VAL_04);
		int 				total	= 1;
		int 				tam		= 0;
		String 			cont04		= ent.reqStr(TaNsoPost.ATT_T_CONTENT_04);

		if (cont04!=null)
		{

			if (cont04 instanceof String) {
				String jsonString = (String) cont04;
				JSONArray jsonArray = ToolJSON.reqJSonArrayFromString(jsonString);

				for (int i=0; i<jsonArray.size(); i++)
				{
					Object element = jsonArray.get(i);
					if (element == Long.valueOf(manId))
					{
						tam=1;
					}
				}
				if (tam==0)
				{	
					jsonArray.add(manId);
					attr.put(TaNsoPost.ATT_T_CONTENT_04	, jsonArray.toString());
				}
			}   
		}
		else {
			String s="["+manId.toString()+"]";
			attr.put	(TaNsoPost.ATT_T_CONTENT_04	, s);
		}
		if (v04!=null && tam==0)
		{
			total = v04;
			total=total+1;
		}
		else if(v04!=null && tam!=0)
		{
			total = v04;
		}
		attr.put	(TaNsoPost.ATT_I_VAL_04	, total);
		TaNsoPost.DAO.doMerge(ent,attr);
		return ent;
	}
	private static void doDislike(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");

		TaNsoPost 		ent 	=reqDislike(user,json);

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
	private static TaNsoPost reqDislike(TaAutUser user, JSONObject json) throws Exception {	
		Integer			manId		= user.reqId();
		int				entId 		= ToolData.reqInt(json, "id", null);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);
		String 			obj			= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);
		if (ent==null){
			return null;
		}

		Integer 			v04		= ent.reqInt(TaNsoPost.ATT_I_VAL_04);
		int tam=1;
		String 			cont04		= ent.reqStr(TaNsoPost.ATT_T_CONTENT_04);
		if (cont04!=null)
		{

			if (cont04 instanceof String) {
				String jsonString = (String) cont04;
				JSONArray jsonArray = ToolJSON.reqJSonArrayFromString(jsonString);

				for (int i=0; i<jsonArray.size(); i++)
				{
					Object element = jsonArray.get(i);
					if (element != Long.valueOf(manId))
					{
						tam=1;

					}
					else
					{
						jsonArray.remove(element);
						tam=0;
						break;
					}
				}
				attr.put	(TaNsoPost.ATT_T_CONTENT_04	, jsonArray.toString());
			}   
		}
		if (v04!=null && tam==0)
		{
			int total = ent.reqInt(TaNsoPost.ATT_I_VAL_04);
			if (total>0)
			{
				total=total-1;
				attr.put	(TaNsoPost.ATT_I_VAL_04	, total);
			}  
		}
		TaNsoPost.DAO.doMerge(ent,attr);
		return ent;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");

		TaNsoPost  		ent	 			=  reqMod(user, json); 								
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

	private static TaNsoPost reqMod(TaAutUser user, JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	,null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		int				entId 		= ToolData.reqInt(obj, "id"		,null);
		int				stat01 		= ToolData.reqInt(obj, "stat01"	,null);
		int				typ03 		= ToolData.reqInt(obj, "typ03"	,null);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);


		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		attr.remove	(TaNsoPost.ATT_I_TYPE_03				);
		attr.remove	(TaNsoPost.ATT_D_DATE_01				);
		attr.remove	(TaNsoPost.ATT_I_AUT_USER_01			);
		attr.put	(TaNsoPost.ATT_I_STATUS_01 		, stat01);
		attr.put	(TaNsoPost.ATT_I_TYPE_03 		, typ03	);
		attr.put	(TaNsoPost.ATT_D_DATE_02		, new Date());	
		attr.put	(TaNsoPost.ATT_I_AUT_USER_02	, user.reqId());

		Integer typPost = (Integer) ent.req(TaNsoPost.ATT_I_TYPE_01);
		switch(typPost) {
		case TaNsoPost.TYPE_01_BLOG : 	
			String title 	= (String) attr.get(TaNsoPost.ATT_T_TITLE);
			String prop04 	= ToolString.reqCovertStringToURL(title);
			attr.put(TaNsoPost.ATT_T_INFO_04, prop04);
			break;
		default: break;
		}

		TaNsoPost.DAO.doMerge(ent, attr);

		//merge other list from obj --------------
		JSONArray 		docs 	= (JSONArray) obj.get("files");
		ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, ENT_TYP, entId, docs));

		JSONArray		cats	= (JSONArray) obj.get("cats");
		ent.reqSet(TaNsoPost.ATT_O_CATS		, TaTpyCategoryEntity.reqListMod(ENT_TYP, entId, cats));

		ent.doBuildAvatar(true);
		int 			autUser01 	= ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01);
		TaAutUser 		autUser 	= TaAutUser.DAO.reqEntityByRef(autUser01);

//		if (!ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01).equals(user.reqId())){
//			ProcessMsg.doNewNotificationPost(ProcessMsg.TAB_POST, ProcessMsg.TYP_POST_MOD, autUser, ent);
//		}

		return ent;
	}

	//==============================================================================================================
	private static void doModPost(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");

		TaNsoPost  		ent	 			=  reqModPost(user, json); 								
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

	private static TaNsoPost reqModPost(TaAutUser user, JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		int				entId 		= ToolData.reqInt(obj, "id", null);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		attr.remove	(TaNsoPost.ATT_D_DATE_01);
		attr.remove	(TaNsoPost.ATT_I_AUT_USER_01);
		attr.put	(TaNsoPost.ATT_D_DATE_02		, new Date());	
		attr.put	(TaNsoPost.ATT_I_AUT_USER_02	, user.reqId());

		Integer typPost = (Integer) ent.req(TaNsoPost.ATT_I_TYPE_01);
		switch(typPost) {
		case TaNsoPost.TYPE_01_BLOG : 	
			String title 	= (String) attr.get(TaNsoPost.ATT_T_TITLE);
			String prop04 	= ToolString.reqCovertStringToURL(title);
			attr.put(TaNsoPost.ATT_T_INFO_04, prop04);
			break;
		default: break;
		}

		TaNsoPost.DAO.doMerge(ent, attr);

		//merge other list from obj --------------
		JSONArray 		docs 	= (JSONArray) obj.get("files");
		ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, ENT_TYP, entId, docs));

		JSONArray		cats	= (JSONArray) obj.get("cats");
		ent.reqSet(TaNsoPost.ATT_O_CATS		, TaTpyCategoryEntity.reqListMod(ENT_TYP, entId, cats));

		ent.doBuildAvatar(true);

		return ent;
	}
	//
	private static void doModStat(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaNsoPost 		ent 	=reqModStat(user,json);

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

	private static TaNsoPost reqModStat(TaAutUser user, JSONObject json) throws Exception {	
		int				entId 		= ToolData.reqInt(json, "id", null);
		int				stat01 		= ToolData.reqInt(json, "stat01", null);
		TaNsoPost 		ent 		= TaNsoPost.DAO.reqEntityByRef(entId);
		String 			obj			= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}

		attr.put	(TaNsoPost.ATT_I_STATUS_01	, stat01);
		attr.put	(TaNsoPost.ATT_D_DATE_02	, new Date());
		attr.put	(TaNsoPost.ATT_I_AUT_USER_02, user.reqId());
		TaNsoPost.DAO.doMerge(ent,attr);
		
//		if (ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01).equals(user.reqId())){
//			if (stat01 == TaNsoPost.STAT_01_REVIEW || stat01 == TaNsoPost.STAT_01_NEW ||   stat01 == TaNsoPost.STAT_01_DELETED ||   stat01 == TaNsoPost.STAT_01_ACTIVE_02)
//				ProcessMsg.doNewNotificationPost(ProcessMsg.TAB_POST, ProcessMsg.TYP_POST_MOD_STAT, user, ent);
//		}
		return ent;
	}
	//==============================================================================================================
	private static void doTick(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaNsoPost 		ent 	=reqTick(user,json);

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

	private static TaNsoPost reqTick(TaAutUser user, JSONObject json) throws Exception {	
		int				entId 	= ToolData.reqInt(json, "id", null);
		TaNsoPost 		ent 	= TaNsoPost.DAO.reqEntityByRef(entId);
		String 			obj		= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		if (ent==null){
			return null;
		}

		attr.put	(TaNsoPost.ATT_I_VAL_05	, Integer.parseInt(ToolDate.reqString(new Date(), "yyyyMMdd")));
		TaNsoPost.DAO.doMerge(ent,attr);
		return ent;
	}
	private static void doDelTick(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");


		TaNsoPost 		ent 	= reqDelTick(user,json);


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

	private static TaNsoPost reqDelTick(TaAutUser user, JSONObject json) throws Exception {	
		int				entId 	= ToolData.reqInt(json, "id", null);
		TaNsoPost 		ent 	= TaNsoPost.DAO.reqEntityByRef(entId);
		String 			obj		= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);

		if (ent==null){
			return null;
		}

		attr.put	(TaNsoPost.ATT_I_VAL_05	, null);
		TaNsoPost.DAO.doMerge(ent,attr);
		return ent;
	}

	//==============================================================================================================
	private static void doModTransl(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaTpyTranslation  		transl 	=  reqModTransl(user, json);

		if (transl==null){
			API.doResponse(response	, DefAPI.API_MSG_KO);
			return;
		} 

		cache_entity.reqDel(transl.reqInt(TaTpyTranslation.ATT_I_ENTITY_ID)+"");
		API.doResponse(response		, ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, transl
				));	
	}

	private static TaTpyTranslation reqModTransl(TaAutUser user, JSONObject json) throws Exception {
		JSONObject	obj			= ToolData.reqJson	(json	, "obj"			, null	);
		Integer 	translId	= ToolData.reqInt	(obj	, "translId"	, null	);			
		Integer		objLang		= ToolData.reqInt	(obj	, "lang"		, null	);//lang option
		Integer		parId		= ToolData.reqInt	(obj	, "mainId"		, null	);//entity id

		TaTpyTranslation transl	= null;
		if (translId!=null) {		
			transl = TaTpyTranslation.DAO.reqEntityByRef(translId);
			if (transl!=null) {
				Integer		parIdEnt		= transl.reqInt(TaTpyTranslation.ATT_I_ENTITY_ID);
				Integer		parTyEnt		= transl.reqInt(TaTpyTranslation.ATT_I_ENTITY_TYPE);
				if (!parIdEnt.equals(parId) || !parTyEnt.equals(ENT_TYP)) transl = null;
			}
		}

		if (transl==null) {
			transl = new TaTpyTranslation(ENT_TYP, parId, objLang, obj.toJSONString());
			TaTpyTranslation.DAO.doPersist(transl);
		}else {
			transl.reqSet(TaTpyTranslation.ATT_T_INFO_01, obj.toJSONString());
			TaTpyTranslation.DAO.doMerge(transl);
		}
		return transl;
	}


	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");

		int				entId 	= ToolData.reqInt(json, "id", null);
		TaSysLock 		lock 	= ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		if (lock==null || lock.reqStatus() == 0){
			API.doResponse(response		, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA		, lock
					));	
			return;
		}

		if (!canDel(user, json)){
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}

		ToolDBLock.canDeleteLock(lock);
	}

	private static boolean canDel(TaAutUser user, JSONObject json) throws Exception {

		Integer 	entId	= ToolData.reqInt	(json, "id", null	);	
		TaNsoPost  	ent	 	= TaNsoPost.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after obj...
			return false;
		}

		Session sessMain 	= TaNsoPost		.DAO.reqSessionCurrent();
		try {
			TaTpyCategoryEntity	.doListDel(sessMain, ENT_TYP, entId);
			TaTpyDocument		.doListDel(sessMain, ENT_TYP, entId);
			//				TaTpyDocument		.doListDel(sessSub, ENT_TYP, entId);

			TaNsoPost.DAO		.doRemove (sessMain, ent);

			TaNsoPost			.DAO.doSessionCommit(sessMain);
			//				TaTpyDocument		.DAO.doSessionCommit(sessSub);
		}catch(Exception e){
			e.printStackTrace();
			TaNsoPost			.DAO.doSessionRollback(sessMain);
			//				TaTpyDocument		.DAO.doSessionRollback(sessSub);

			return false;
		}


//		if (!ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01).equals(user.reqId())){
//			ProcessMsg.doNewNotificationPost(ProcessMsg.TAB_POST, ProcessMsg.TYP_POST_DEL, user, ent);
//		}
		
		return true;
	}


	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private void doLckReq(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckReq --------------");		
		Integer 		entId	= ToolData.reqInt	(json, "id", null);	
		TaSysLock 		lock 	= ToolDBLock.reqLock(ENT_TYP, entId, user.reqId(), user.reqStr(TaAutUser.ATT_T_LOGIN_01), null);
		if (lock==null || lock.reqStatus() != TaSysLock.STAT_ACTIVE){
			API.doResponse(response		, ToolJSON.reqJSonString(						
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO,
					DefJS.RES_DATA		, lock
					));	
		}else{
			API.doResponse(response		, ToolJSON.reqJSonString(						
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
			API.doResponse(response, DefAPI.API_MSG_OK);	
		else 
			API.doResponse(response, DefAPI.API_MSG_KO);
	}

	private void doLckSav(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckSav --------------");	
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		TaNsoPost  		ent	 	=  reqMod(user, json); 								
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


	//user when modify object with lock
	private void doLckEnd(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLckEnd --------------");	
		boolean isLocked 	= ToolDBLock.canExistLock(json);
		if(!isLocked){
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}


		TaNsoPost ent = reqMod(user, json);						
		if (ent==null){
			API.doResponse(response		, DefAPI.API_MSG_KO);
		} else {
			ToolDBLock.canDeleteLock(json);
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, ent
					));	
		}	
	}



	//---------------------------------------------------------------------------------
	//--cache for UI public
	private static CacheData<ResultPagination>		cache_rs 	= new CacheData<ResultPagination>(100, DefTime.TIME_00_05_00_000);
	//-----------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------



	//--cache for UI public
	private static CacheData<List<TaNsoPost>> 		cacheEnt 		= new CacheData<List<TaNsoPost>>();
	private static CacheData<ResultPagination>		cacheEnt_rs 	= new CacheData<ResultPagination>(100, DefTime.TIME_02_00_00_000);
	//------------function get list post by entType and entId-----------------------------------------------
	//---------------------------------------------------------------------------------------------------------

	//-------------------------------------------------------------------------------------------------
	private static void doNewCmt(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaNsoPost 		ent				= reqNewCmt		(user, json);
		if (ent==null){
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}

		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent
				));
	}

	private static TaNsoPost reqNewCmt(TaAutUser user, JSONObject json) throws Exception {
		JSONObject		obj			= ToolData.reqJson (json, "obj", null);

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);	

		Integer			userId		= user.reqId();
		TaNsoPost  		ent	 		= new TaNsoPost(attr);

		ent.reqSet(TaNsoPost.ATT_I_AUT_USER_01	, userId);
		ent.reqSet(TaNsoPost.ATT_I_TYPE_01		, TaNsoPost.TYPE_01_CMT);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_01	, TaNsoPost.STAT_01_ACTIVE);
		ent.reqSet(TaNsoPost.ATT_I_STATUS_02	, TaNsoPost.STAT_02_PUBLIC);
		ent.reqSet(TaNsoPost.ATT_D_DATE_01		, new Date());
		ent.reqSet(TaNsoPost.ATT_D_DATE_02		, new Date());

		TaNsoPost.DAO.doPersist(ent);

		//int				entId 		= (int) ent.req(TaNsoPost.ATT_I_ID);

		//Update attach files
		//JSONArray		docs		= (JSONArray) obj.get("files");
		//ent.reqSet(TaNsoPost.ATT_O_DOCUMENTS, TaTpyDocument.reqListCheck(DefAPI.SV_MODE_NEW, user, ENT_TYP, entId, docs));

		return ent;
	}
	private static void doModCmt(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		TaNsoPost 		ent			= reqModCmt		 (user	, json);
		if (ent==null){
			API.doResponse(response		, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_NO					
					));
			return;
		}
		API.doResponse(response		, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent
				));
	}

	private static TaNsoPost reqModCmt(TaAutUser user, JSONObject json) throws Exception {
		Integer 			cmtId	= ToolData.reqInt(json	, "cmtId"	, null);
		String 				comment	= ToolData.reqStr(json	, "comment"	, null);
		TaNsoPost 			ent 	= TaNsoPost.DAO.reqEntityByRef(cmtId);
		String 				obj		= ent.toString();
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaNsoPost.class);	

		attr.put	(TaNsoPost.ATT_T_CONTENT_10	, comment);
		attr.put	(TaNsoPost.ATT_D_DATE_02	, new Date());

		TaNsoPost.DAO.doMerge(ent,attr);
		return ent;
	}

	//-------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	private static void doDelComment12h(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doDel --------------");

		if (!canDelComment12h(user, json)){
			API.doResponse(response, DefAPI.API_MSG_KO);
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, 1, DefJS.SV_CODE, DefAPI.SV_CODE_API_YES));
		}

	}

	private static boolean canDelComment12h(TaAutUser user, JSONObject json) throws Exception {
		Integer 	entId 	= ToolData.reqInt(json, "cmtId", null);    
		TaNsoPost 	ent 	= TaNsoPost.DAO.reqEntityByRef(entId);

		if (ent == null) {
			return false;
		}

		if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after obj...
			return false;
		}

		// Check if the duration from date01 <= 12h 
		Date dateO1 = ent.reqDate(ent, TaNsoPost.ATT_D_DATE_01);
		Date now 	= new Date();

		long duration = now.getTime() - dateO1.getTime();
		long timeOutMax = 12 * 60 * 60 * 1000;

		if (duration > timeOutMax) {
			return false;
		}

		// Check if this comment is by the user 
		Integer userId = ent.reqInt(TaNsoPost.ATT_I_AUT_USER_01);
		if (!userId.equals(userId)) {
			return false;
		}

		Session sessMain = TaNsoPost.DAO.reqSessionCurrent();
		try {
			TaTpyCategoryEntity.doListDel(sessMain, ENT_TYP, entId);
			TaTpyDocument.doListDel(sessMain, ENT_TYP, entId);

			TaNsoPost.DAO.doRemove(sessMain, ent);

			TaNsoPost.DAO.doSessionCommit(sessMain);
		} catch (Exception e) {
			e.printStackTrace();
			TaNsoPost.DAO.doSessionRollback(sessMain);
		}

		return true;
	}

	//---------------------------------------------------------------------------------------------------------




}
