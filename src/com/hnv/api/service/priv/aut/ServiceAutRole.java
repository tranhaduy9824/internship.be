package com.hnv.api.service.priv.aut;


import java.util.ArrayList;
import java.util.HashSet;
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
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutAuthUser;
import com.hnv.db.aut.TaAutRole;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutRoleDyn;
import com.hnv.db.nso.TaNsoPost;
import com.hnv.db.sys.TaSysLock;
import com.hnv.def.DefDBExt;
import com.hnv.def.DefRight;


/**
 * ----- ServiceAutRole by H&V
 * ----- Copyright 2017------------
 */
public class ServiceAutRole implements IService {

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 		= "EC_V3".toLowerCase();

	public static final String SV_CLASS 		= "ServiceAutRole".toLowerCase();	

	public static final String SV_GET 			= "SVGet".toLowerCase();	
	public static final String SV_LST 			= "SVLst".toLowerCase();
	public static final String SV_LST_DYN		= "SVLstDyn".toLowerCase();

	public static final String SV_NEW 			= "SVNew".toLowerCase();	
	public static final String SV_MOD 			= "SVMod".toLowerCase();	
	public static final String SV_DEL 			= "SVDel".toLowerCase();

	public static final String SV_LCK_REQ 		= "SVLckReq".toLowerCase(); //req or refresh	
	public static final String SV_LCK_SAV 		= "SVLckSav".toLowerCase(); //save and continue
	public static final String SV_LCK_END 		= "SVLckEnd".toLowerCase();
	public static final String SV_LCK_DEL 		= "SVLckDel".toLowerCase();

	public static final Integer	ENT_TYP			= DefDBExt.ID_TA_AUT_ROLE;
	//-----------------------------------------------------------------------------------------------
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutRole(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");

		try {
			if(sv.equals(SV_GET) 						&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doGet(user, json, response);
			
			} else if(sv.equals(SV_LST)					&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLst(user, json, response);
			
			} else if(sv.equals(SV_LST_DYN)				&&  (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_GET)
															||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doListDyn(user, json, response);
			
			}  else if(sv.equals(SV_NEW)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_NEW) 
													|| 	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doNew(user, json, response);

			} else if(sv.equals(SV_MOD)			&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doMod(user, json, response);
			}else if(sv.equals(SV_LCK_REQ)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckReq(user, json, response);
			} else if(sv.equals(SV_LCK_SAV)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckSav(user, json, response);
			} else if(sv.equals(SV_LCK_END)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckEnd(user, json, response);
			} else if(sv.equals(SV_LCK_DEL)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_MOD) 
													||	APIAuth.canAuthorize(user, SV_CLASS, sv))) {
				doLckDel(user, json, response);

			} else  if(sv.equals(SV_DEL)		&& (	APIAuth.canAuthorizeWithOneRight(user, APIAuth.R_ADMIN, APIAuth.R_AUT_ALL_DEL)
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

	private static final int WORK_GET = 1;
	private static final int WORK_LST = 2;
	private static final int WORK_NEW = 3;
	private static final int WORK_MOD = 4;
	private static final int WORK_DEL = 5;

	private static boolean canWorkWithObj ( TaAutUser user, int typWork, Object...params){		
		if (params==null|| params.length==0 || user==null) return false;
		//int userType	= (int) user.req(TaAutUser.ATT_I_TYPE);
		//if (userType==TYPE_ADM_ALL) return true;

		switch(typWork){
		case WORK_LST : 
		case WORK_MOD : 
		case WORK_GET : return true;
		case WORK_DEL : return true;

		case WORK_NEW : 
			//check something with params
			return true;

		}

		return false;

	}
	//---------------------------------------------------------------------------------------------------------

	private void doListDyn(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		Object[]  			dataTableOption = ToolDatatable.reqDataTableOption (json, null);
		Set<String>			searchKey		= (Set<String>)dataTableOption[0];	

		Set<Integer>		stat			= ToolData.reqSetInt	(json, "stat"		, null);

		Criterion cri						= reqRestriction (user, searchKey, user.reqPerManagerId(), stat);

		List<ViAutRoleDyn> 	list 			= reqListDyn(dataTableOption, cri);

		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} 


		Integer iTotalRecords 				= reqNbListDyn().intValue();				
		Integer iTotalDisplayRecords 		= reqNbListDyn(cri).intValue();

		API.doResponse(response, ToolJSON.reqJSonString(		
				DefJS.SESS_STAT				, 1, 
				DefJS.SV_CODE				, DefAPI.SV_CODE_API_YES,					
				"iTotalRecords"				, iTotalRecords,
				"iTotalDisplayRecords"		, iTotalDisplayRecords,
				"aaData"					, list
				));
	}
	
	private static List<ViAutRoleDyn> reqListDyn(Object[] dataTableOption, Criterion 	cri) throws Exception {		
		int 		begin 		= (int)	dataTableOption[1];
		int 		number 		= (int)	dataTableOption[2]; 
		int 		sortCol 	= (int)	dataTableOption[3]; 
		int 		sortTyp 	= (int)	dataTableOption[4];	

		List<ViAutRoleDyn> list 	= new ArrayList<ViAutRoleDyn>();		

		Order order = null;			
		String colName = TaAutRole.ATT_T_NAME;

		switch(sortCol){
		//case 0: colName = TaAutRole.ATT_I_ID; break;
		case 1: colName = TaAutRole.ATT_I_ID; break;
		case 2: colName = TaAutRole.ATT_T_NAME; break;					
		}

		if (colName!=null){
			switch(sortTyp){
			case 0: order = Order.asc(colName); break;
			case 1: order = Order.desc(colName); break;								
			}
		}

		if (order==null)
			list	= ViAutRoleDyn.DAO.reqList(begin, number, cri);
		else
			list	= ViAutRoleDyn.DAO.reqList(begin, number, order, cri);			
		return list;
	}

	private static Number reqNbListDyn() throws Exception {						
		return ViAutRoleDyn.DAO.reqCount();		
	}

	private static Number reqNbListDyn(Criterion cri) throws Exception {			
		return ViAutRoleDyn.DAO.reqCount(cri);
	}
	
	//---------------------------------------------------------------------------------------------------------
	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");

		List<ViAutRoleDyn> 	list 	= reqLst(user, json); //and other params if necessary
		if (list==null ){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, true, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, list 
				));
	}

	private static List<ViAutRoleDyn> reqLst(TaAutUser user, JSONObject json) throws Exception  {
		Integer 			nbLine      = ToolData.reqInt		(json, "nbLine" 	, Integer.MAX_VALUE);
		Set<String> 		searchkey	= ToolData.reqSetStr	(json, "searchkey"	, null);
		Set<Integer>		stat		= ToolData.reqSetInt	(json, "stat"		, null);

		Criterion cri				= reqRestriction (user, searchkey, user.reqPerManagerId(), stat);	
		List<ViAutRoleDyn>	list		= ViAutRoleDyn.DAO.reqList(0, nbLine, cri);	

		return list;
	}

	private static Criterion reqRestriction(TaAutUser user,  Set<String> searchKey, Integer manId, Set<Integer> stats) throws Exception {	
		//--Pre-Check condition---------------------------------------------------
		if (stats == null){
			stats = new HashSet<Integer>() ; 
			stats.add(TaAutRole.STAT_ON);
		}

		Criterion cri = Restrictions.in(TaAutRole.ATT_I_STATUS, stats);

		if (searchKey!=null) {
			for (String s : searchKey){
				cri = Restrictions.and(	cri, Restrictions.or(
						Restrictions.ilike(TaAutRole.ATT_T_NAME		, s), 
						Restrictions.ilike(TaAutRole.ATT_T_INFO_01	, s))
						);
			}
		}

		//			if (manId==null && !user.canBeSuperAdmin()) manId = user.reqPerManagerId();
		//			if (manId!=null) cri = Restrictions.and(cri, Restrictions.eq(TaAutRole.ATT_I_PER_MANAGER, manId));
		return cri;
	}
	//---------------------------------------------------------------------------------------------------------
	private static void doGet(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {	
		Integer 			entId	= ToolData.reqInt (json, "id"		, -1	);				
		Boolean				forced	= ToolData.reqBool (json, "forced"	, true	);

		TaAutRole 			ent 	= reqGet (entId, forced);

		if (ent==null){
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, true, DefJS.SV_CODE, DefAPI.SV_CODE_API_NO));
			return;
		}

		if (!canWorkWithObj(user, WORK_GET, ent)){
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, true, DefJS.SV_CODE, DefAPI.SV_CODE_ERR_RIGHT));
			return;
		}

		API.doResponse(response, ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, ent 
				));
	}

	private static CacheData<TaAutRole> 		cache_entity= new CacheData<TaAutRole>		(500, DefTime.TIME_24_00_00_000 );
	public static TaAutRole reqGet(Integer entId, Boolean forced) throws Exception{
		String 			key		= entId+"";
		TaAutRole 		ent 	= cache_entity.reqData(key);	
		
		if (forced || ent == null) {
			ent 	= TaAutRole.DAO.reqEntityByRef(entId);
			if (ent!=null) {
				//---do something and put to cache
				cache_entity.reqPut(key, ent);
			}
		}else {				
			ToolLogServer.doLogInf("---reqNsoAreaGet use cache-----");
			cache_entity.reqCheckIfOld(key); //cache in 20 hour
		}

		return ent;
	}
	

	//---------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doNew --------------");

		TaAutRole 			ent		= reqNew		(user, json);
		if (ent==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
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


	private static TaAutRole reqNew(TaAutUser user, JSONObject json) throws Exception {
		JSONObject				obj		= ToolData.reqJson(json, "obj", null);

		if (!canWorkWithObj(user, WORK_NEW, obj)){ //other param after obj...
			return null;
		}

		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutRole.class);
		TaAutRole  			ent	 	= new TaAutRole(attr);
		
		TaAutRole.DAO.doPersist(ent);

		return ent;
	}

	//---------------------------------------------------------------------------------------------------------
	private static void doMod(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doMod --------------");
		TaAutRole	ent		= reqMod(user, json); 								
		if (ent == null){
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, true, DefJS.SV_CODE, DefAPI.SV_CODE_API_NO));
		} else {
			API.doResponse(response, ToolJSON.reqJSonString(DefJS.SESS_STAT, true,	DefJS.SV_CODE, DefAPI.SV_CODE_API_YES, DefJS.RES_DATA, ent));
		}				

	}

	private static TaAutRole reqMod(TaAutUser user, JSONObject json) throws Exception {
		JSONObject			obj		= ToolData.reqJson (json, "obj"	, null);
		Map<String, Object> attr 	= API.reqMapParamsByClass(obj, TaAutRole.class);
		int					entId 	= ToolData.reqInt(obj, "id", null);
		TaAutRole  			ent	 	= TaAutRole.DAO.reqEntityByRef(entId);
		
		if (ent==null){
			return null;
		}

		if (!canWorkWithObj(user, WORK_MOD, ent)){ //other param after obj...
			return null;
		}
		
		String rightsOld = ent.reqStr(TaAutRole.ATT_T_AUT_RIGHT);
		int statOld		 = ent.reqInt(TaAutRole.ATT_I_STATUS);
		
		
		TaAutRole.DAO.doMerge(ent, attr);
		cache_entity.reqPut(entId+"", ent);
		//---refresh all 
		String 	rightsNew 	= ent.reqStr(TaAutRole.ATT_T_AUT_RIGHT);
		int 	statNew		= ent.reqInt(TaAutRole.ATT_I_STATUS);
		if (!rightsNew.equals(rightsOld)|| statOld!=statNew) {
			List<TaAutAuthUser> lst = TaAutAuthUser.DAO.reqList(Restrictions.eq(TaAutAuthUser.ATT_I_AUT_ROLE, entId));
			for (TaAutAuthUser a : lst) {
				a.reqSet(TaAutAuthUser.ATT_T_AUT_RIGHT	, rightsNew);
				a.reqSet(TaAutAuthUser.ATT_I_STATUS	, statNew);
			}
			TaAutAuthUser.DAO.doMerge(lst);
		}
		return ent;
	}	

	//---------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception  {
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

	private static boolean canDel(TaAutUser user, JSONObject json) throws Exception {
		Integer 	entId	= ToolData.reqInt (json, "id"		, -1	);	

		TaAutRole  	ent	 	= TaAutRole.DAO.reqEntityByRef(entId);
		if (ent==null){
			return false;
		}

		if (!canWorkWithObj(user, WORK_DEL, ent)){ //other param after ent...
			return false;
		}		

		Session sess = TaAutRole.DAO.reqSessionCurrent();
		try {
			//remove all other object connecting to this ent first-------
			List<TaAutAuthUser> lA = TaAutAuthUser.DAO.reqList(sess, Restrictions.eq(TaAutAuthUser.ATT_I_AUT_ROLE, entId));
			if (lA!=null && lA.size()>0) TaAutAuthUser.DAO.doRemove(sess, lA);

			TaAutRole.DAO.doRemove(sess, ent);
			cache_entity.reqDel(entId+"");
			
			TaAutRole.DAO.doSessionCommit(sess);
		}catch(Exception e) {
			TaAutRole.DAO.doSessionRollback(sess);
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
		if (lock==null || lock.reqStatus() != TaSysLock.STAT_ACTIVE){
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

		TaAutRole  		ent	 	=  reqMod(user, json); 								
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


		TaAutRole  		ent	 	=  reqMod(user, json); 					
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


	//---------------------------------------------------------------------------------


}
