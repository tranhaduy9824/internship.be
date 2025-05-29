package com.hnv.api.service.priv.aut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefJS;
import com.hnv.api.def.DefTime;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDatatable;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.per.TaPerPerson;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyInformation;
import com.hnv.def.DefDBExt;


public class ServiceAutUserChat implements IService {

	//--------------------------------Service Definition----------------------------------
	public static final String SV_MODULE 					= "EC_V3"					.toLowerCase();

	public static final String SV_CLASS 					= "ServiceAutUserChat"		.toLowerCase();

	public static final String SV_LST						= "SVLst"			.toLowerCase(); 
	public static final String SV_LST_FOR_CHAT				= "SVLstForChat"	.toLowerCase();
	
	//-------------------------Default Constructor - Required -------------------------------------
	public ServiceAutUserChat(){
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//-----------------------------------------------------------------------------------------------

	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		
		try {
			if (sv.equals(SV_LST)){
				doLst(user, json, response);
			} else if (sv.equals(SV_LST_FOR_CHAT)){
				doLstForChat(user, json, response);
			} else {
				API.doResponse(response, DefAPI.API_MSG_ERR_UNKNOW);
			}

		}catch(Exception e){
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
		}
	}

	//---------------------------------------------------------------------------------------------------------------------------
	private static Hashtable<String,Integer> mapCol = new Hashtable<String, Integer>(){
		{
			put("action"	, -1);
			put("id"		, 0 );
//			put("code"		, 1 );
			put("name"		, 2 );
			put("email"		, 3 );	    
		}
	};

	

	//---------------------------------------------------------------------------------------------------------------------------
	private static CacheData<ResultPagination>		cache_rs 			= new CacheData<ResultPagination>	(100, DefTime.TIME_00_30_00_000); //30 minutes if project or epic
	private static CacheData<ResultPagination>		cache_rs_chat 		= new CacheData<ResultPagination>	(100, DefTime.TIME_00_01_00_000); //30 s if task
	private static CacheData<ResultPagination>		cache_rs_shipment 	= new CacheData<ResultPagination>	(100, DefTime.TIME_00_01_00_000); //30 minutes if project or epic

	private static void doLst(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination  	res = reqLst(user, json, false); //and other params if necessary
		if (res.reqList()==null ){
			API.doResponse(response,ToolJSON.reqJSonString(DefJS.SESS_STAT, true, DefJS.SV_CODE, DefAPI.SV_CODE_API_NO));
			return;
		}
		API.doResponse(response,ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, true, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res 
				));	
	}
	
	private static void doLstForChat(TaAutUser user,  JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination  	res = reqLstForChat(user, json, true); //and other params if necessary
		if (res.reqList()==null){
			API.doResponse(response,ToolJSON.reqJSonString(DefJS.SESS_STAT, true, DefJS.SV_CODE, DefAPI.SV_CODE_API_NO));
			return;
		}
		API.doResponse(response,ToolJSON.reqJSonString(		//filter,
				DefJS.SESS_STAT		, true, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, res 
				));	
	}
	
	//-------------------------------------------------List dynamique filter mat--------------------------------------------------------------------------------------
	private static 	ResultPagination reqLst(TaAutUser user, JSONObject json, boolean forChat) throws Exception  {
		//		Integer			manId		= ToolData.reqInt (json, "manId"		, null);
		
		Integer 		manId 		= user.reqPerManagerId();
		if (user.canBeSuperAdmin()) manId = null;
		
		Integer 		begin		= ToolData.reqInt (json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt (json, "number"		, 20);
		Integer 		total		= ToolData.reqInt (json, "total"		, 0	);
		String 			searchKey   = ToolData.reqStr (json, "searchKey"	, null);

		String 			sortCol   	= ToolData.reqStr (json, "sortCol"		, "id");
		Integer 		sortDir   	= ToolData.reqInt (json, "sortDir"		, 0);

		
		Set<Integer>	stats		= new HashSet<Integer>();
		
		Integer			stat		= ToolData.reqInt (json, "stat"		, null); 
		if (stat!=null) stats.add(stat);
		
		String			statStr		= ToolData.reqStr (json, "stats"		, null); 
		if (statStr!=null) {
			Set<Integer> set		= ToolSet.reqSetInt(statStr);
			stats.addAll(set);
		}
		Integer			typ01		= ToolData.reqInt (json, "typ01"			, null); 
		Integer			typ02		= ToolData.reqInt (json, "typ02"			, null); 

		Boolean			buildInfo	= ToolData.reqBool (json, "buildInfo"	, false); 
		Boolean         hardLoad    = ToolData.reqBool (json, "hardLoad"	, false); 

		String keyWord 	= manId + "_" +  begin + "_" + number + "_" + total + "_" + searchKey + "_" + sortCol+ "_" + sortDir +"_" +  stat + "_" +  statStr + "_" + buildInfo +"_" +  typ01 +"_" +  typ02;

		CacheData<ResultPagination> cacheUsed = cache_rs;
		
		if(hardLoad) cacheUsed.doClear();
		
		ResultPagination rs =	cacheUsed.reqData(keyWord);

		if(rs==null) {
			Object[] dataTableOption 	= ToolDatatable.reqDataTableOption (json, null);
			
			List<TaAutUser> usrList 	= reqListFilterDyn(dataTableOption, manId, null, stats,  typ01, typ02, forChat);


			if (usrList==null || usrList.size() ==0 ){
				rs								= new ResultPagination(usrList, 0, 0, 0);
			}else {
				//---build avatar-----
				//---build users + their avatars
				TaTpyDocument	.reqBuildAvatar(usrList, DefDBExt.ID_TA_AUT_USER, TaAutUser.ATT_O_AVATAR);
				
				if(buildInfo)	reqPersonInfo(usrList);
				
				if (total == 0 )	total		= reqUserFilterListDynCount(dataTableOption, manId, null, stats, typ01, typ02, forChat);
				rs								= new ResultPagination(usrList, total, begin, number);
			}

			cacheUsed.reqPut(keyWord, rs);			
		} else {
			ToolLogServer.doLogInf("---reqViPrjLst use cache-----");
			cacheUsed.reqCheckIfOld(keyWord); 
		}

		return rs;

	}
	
	private static void reqPersonInfo(List<TaAutUser> usrList) throws Exception {
		Set<Integer> setPer = ToolSet.reqSetInt(usrList, TaAutUser.ATT_I_PER_PERSON);
		if(!setPer.isEmpty()) {
			List<TaPerPerson> lstPer = TaPerPerson.DAO.reqList_In(TaPerPerson.ATT_I_ID, setPer);
			if(lstPer != null && lstPer.size() > 0) {
				Map<Integer, TaPerPerson> mapPer = new HashMap<Integer, TaPerPerson>();	
				for(TaPerPerson p : lstPer) {
					mapPer.put(p.reqID(), p);
				}
				for(TaAutUser u : usrList) {
					u.reqSet(TaAutUser.ATT_O_PER_PERSON, mapPer.get(u.req(TaAutUser.ATT_I_PER_PERSON)));
				}
			}
		}
	}
	
	private static List<TaAutUser> reqListFilterDyn(Object[] dataTableOption, 
													Integer manId, Integer supID,
													Set<Integer> status,  
													Integer typ01, Integer typ02, 
													boolean forChat) throws Exception {
		List<String>	searchKey	= (List<String>)dataTableOption[0];
		int 			begin 		= (int)	dataTableOption[1];
		int 			number 		= (int)	dataTableOption[2]; 
		int 			sortCol 	= (int)	dataTableOption[3]; 
		int 			sortTyp 	= (int)	dataTableOption[4];

		String 			sortColName = null;
		String 			sortDir	   	= null;

		switch(sortCol) {
		case 0: sortColName = TaAutUser.ATT_I_ID; break;	
		case 2: sortColName = TaAutUser.ATT_T_LOGIN_01; break;
		case 3: sortColName = TaAutUser.ATT_T_INFO_01; break;
		default: sortColName = TaAutUser.ATT_T_LOGIN_01; break;
		}

		if (sortColName != null) {
			switch(sortTyp) {
			case 0: sortDir = "ASC"; break;
			case 1: sortDir = "DESC"; break;								
			}
		}

		List<TaAutUser> lst = TaAutUser.reqListUsrFilter(begin, number,  sortColName, sortDir, searchKey, manId,   supID, status, typ01, typ02, forChat);
		
		for (TaAutUser u: lst) {
			u. doHidePwd();
		}
		return lst;
	}

	private static Integer reqUserFilterListDynCount(Object[] dataTableOption, Integer manId, Integer supID, Set<Integer> status,  Integer typ01, Integer typ02, boolean forChat) throws Exception {
		List<String>	searchKey				= (List<String>)dataTableOption[0];
		Integer result = TaAutUser.reqCountUsrFilter(searchKey, manId , supID, status,  typ01, typ02, forChat).intValue();
		return result;
	}

	private static 	ResultPagination reqLstForChat(TaAutUser user, JSONObject json, boolean forChat) throws Exception  {
		//		Integer			manId		= ToolData.reqInt (json, "manId"		, null);
		Integer 		manId 		= user.reqPerManagerId();
		if (user.canBeSuperAdmin()) manId = null;
		
		Integer 		begin		= ToolData.reqInt (json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt (json, "number"		, 20);
		Integer 		total		= ToolData.reqInt (json, "total"		, 0	);
		String 			searchKey   = ToolData.reqStr (json, "searchKey"	, null);

		String 			sortCol   	= ToolData.reqStr (json, "sortCol"		, "name");
		Integer 		sortDir   	= ToolData.reqInt (json, "sortDir"		, 0);

		Set<Integer>	stats		= new HashSet<Integer>();
		
		Integer			stat		= ToolData.reqInt (json, "stat"			, null); 
		if (stat!=null) stats.add(stat);
		
		String			statStr		= ToolData.reqStr (json, "stats"		, null); 
		if (statStr != null) {
			Set<Integer> set		= ToolSet.reqSetInt(statStr);
			stats.addAll(set);
		}

		Integer			typ01		= ToolData.reqInt (json, "typ01"		, null); 
		Integer			typ02		= ToolData.reqInt (json, "typ02"		, null); 
		
		Boolean			forced		= ToolData.reqBool(json, "hardLoad"		, false);


		String keyWord 	= manId + "_" +  begin + "_" + number + "_" + total + "_" + searchKey + "_" + sortCol+ "_" + sortDir +"_" +  stat+ "_" + statStr + "_" + typ01 + "_" + typ02;

		CacheData<ResultPagination> cacheUsed = cache_rs_chat;		
		ResultPagination rs =	cacheUsed.reqData(keyWord);

		if(rs==null || forced) {
//			Object[] dataTableOption 	= ToolDatatable.reqDataTableOption(json, mapCol);
			Object[] 		dataTableOption 	= reqDataTableOption(searchKey, begin, number, sortCol, sortDir);
			List<TaAutUser> usrList 			= reqListFilterDyn(dataTableOption, manId,null, stats, typ01, typ02, forChat);

			if (usrList==null || usrList.size() ==0 ){
				rs								= new ResultPagination(usrList, 0, 0, 0);
			}else {
				//---build avatar-----
				TaTpyDocument	.reqBuildAvatar(usrList, DefDBExt.ID_TA_AUT_USER, TaAutUser.ATT_O_AVATAR);
				
				if (total == 0 )	total		= reqUserFilterListDynCount(dataTableOption, manId, null, stats, typ01, typ02, forChat);
				rs								= new ResultPagination(usrList, total, begin, number);
			}
			if (usrList!=null && usrList.size()>0)
				cacheUsed.reqPut(keyWord, rs);			
		} else {
			ToolLogServer.doLogInf("---reqViPrjLst use cache-----");
			cacheUsed.reqCheckIfOld(keyWord); 
		}

		return rs;

	}
	
	private static Object[] reqDataTableOption(String searchKey, int beginDisplay, int nbDisplay, String colN, Integer sortOption){
		Object[] res = new Object[10];

		int 	count = 0;
		List<String> keyword 	= new ArrayList<String>();
		if (searchKey!=null && searchKey.length()>0){				
			StringTokenizer token = new StringTokenizer(searchKey, " ");
			while (token.hasMoreTokens()){
				String s = count>0?"%":"" + token.nextToken()+ "%";
				s = s.replace("*" , "%");
				s = s.replace("/%",  "");
				s = s.replace("%+",  "");
				s = s.replace("+%",  "");
				keyword.add(s.toLowerCase());
				count++;
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
	
}
