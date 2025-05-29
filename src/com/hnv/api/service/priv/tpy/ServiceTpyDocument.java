package com.hnv.api.service.priv.tpy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefDB;
import com.hnv.api.def.DefJS;
import com.hnv.api.interf.IService;
import com.hnv.api.main.API;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserDyn;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.def.DefAPIExt;
import com.hnv.def.DefDBExt;

public class ServiceTpyDocument implements IService  {
	public static final String SV_CLASS = "ServiceTpyDocument".toLowerCase();

	public static final String SV_DO_LIST_PAGE 	= "SVListPage".toLowerCase();
	public static final String SV_DO_NEW 		= "SVNew".toLowerCase();
	public static final String SV_DO_MOD 		= "SVMod".toLowerCase();
	public static final String SV_DO_GET 		= "SVGet".toLowerCase();
	public static final String SV_DO_DEL 		= "SVDel".toLowerCase();
	
	public static final String SV_DO_DOWNLOAD	= "SVDownload".toLowerCase();
	
	public static final String SV_DO_NEW_POST 	= "SVNewInPost".toLowerCase();
	public static final String SV_DO_LIST_POST 	= "SVListForPost".toLowerCase();
	
	
	public static final String SV_DO_NEW_CHAT 	= "SVNewInChat".toLowerCase();
	public static final String SV_DO_DEL_CHAT 	= "SVDelInChat".toLowerCase();
	
	
//	public static final String SV_DO_LIST_POST 	= "SVListForPost".toLowerCase();

	public ServiceTpyDocument() {
		ToolLogServer.doLogInf("----" + SV_CLASS + " is loaded -----");
	}

	//---------------------------------------------------------------------------
	@Override
	public void doService(JSONObject json, HttpServletResponse response) throws Exception {
		//ToolLogServer.doLogInf("--------- "+ SV_CLASS+ ".doService --------------");
		String 		sv 		= API.reqSVFunctName(json);
		TaAutUser 	user	= (TaAutUser) json.get("userInfo");
		try {
			// mapping service
			if (sv.equals(SV_DO_GET)) {
				doGet(user, json, response);
			
			} else if (sv.equals(SV_DO_NEW) ) {
				doNew(user, json, response);
				
			} else if (sv.equals(SV_DO_DEL) ) {
				doDel(user, json, response);
				
			} else if (sv.equals(SV_DO_DOWNLOAD) ) {
				doDownload(user, json, response);
				
			}else if (sv.equals(SV_DO_LIST_PAGE)) {
				doListPage(user, json, response);
				
			} else if (sv.equals(SV_DO_NEW_POST) ) {
				doNewInPost(user, json, response);
			
			} else if (sv.equals(SV_DO_NEW_CHAT) ) {
				doNewInChat(user, json, response);
			} else if (sv.equals(SV_DO_DEL_CHAT) ) {
				doDelInChat(user, json, response);	
			}else {
				API.doResponse(response, DefAPI.API_MSG_ERR_RIGHT);
			}
		} catch (Exception e) {
			API.doResponse(response, DefAPI.API_MSG_ERR_API);
			e.printStackTrace();
		}
	}


	//---------------------------------------------------------------------------------------------------------------------
	private static Set<String> filter = new HashSet<String>();
	static {
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_T_INFO_02);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_T_INFO_04);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_T_INFO_10);
		
//		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_I_AUT_USER_01);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_I_AUT_USER_02);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_I_STATUS);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_I_ENTITY_ID);
		filter.add(TaTpyDocument.class.getSimpleName()+"."+TaTpyDocument.ATT_I_ENTITY_TYPE);
	}
	//---------------------------------------------------------------------------------------------------------------------
	private static void doNew(TaAutUser user, JSONObject json, HttpServletResponse response ) throws Exception {
		String 				filePath 	= "";
		List<TaTpyDocument> listDoc 	= reqListNew(user, json,  filePath);
		if (listDoc==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		}else{				
			API.doResponse(response, ToolJSON.reqJSonString(
					filter,
					DefJS.SESS_STAT, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, listDoc
					));				
		}
	}

	private static List<TaTpyDocument> reqListNew(TaAutUser user, JSONObject json, String filePath) throws Exception {
		Integer				entTyp			= ToolData.reqInt(json, "enttyp", DefDBExt.ID_TA_AUT_USER); //data-entTyp give enttyp
		
		Integer				fileTyp01		= ToolData.reqInt(json, "typ01"	, TaTpyDocument.TYPE_01_FILE_OTHER);
		Integer				fileTyp02		= ToolData.reqInt(json, "typ02"	, TaTpyDocument.TYPE_02_UNKNOW);
		Integer				fileTyp03		= ToolData.reqInt(json, "typ03"	, TaTpyDocument.TYPE_03_PUBLIC);
		
		Integer				stat			= TaTpyDocument.STAT_NEW; 
		
		List<String>		names			= (ArrayList) json.get("fileNames");	//filename not add dateTime
		List<String>		paths			= (ArrayList) json.get("filePaths");   //path full of filename

		List<TaTpyDocument> docs			= new ArrayList<TaTpyDocument>();

		for (int i=0; i<names.size(); i++){
			String 	fName 			= names.get(i);
			String 	fPath			= paths.get(i);
			File 	file 			= new File(fPath);
			Double 	filesize 		= file.length() * 1.0;
			
			TaTpyDocument doc		= new TaTpyDocument(fileTyp01, fileTyp02, fileTyp03, filesize, fName, null, null, fPath, new Date(),user.reqId(), entTyp, -1, stat, i);
			docs.add(doc);
		}

		if (docs.size()>0) TaTpyDocument.DAO.doPersist(docs);
		return docs;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	private static void doDel(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
			Integer			id = ToolData.reqInt(json, "key", null); //--FileInput return key
			if(id == null)	id = ToolData.reqInt(json, "id" , null);
			
			if (!canDel(user, id)){
				API.doResponse(response,DefAPI.API_MSG_KO);
				return;
			} 

			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT		, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, id
			));
	}

	private static boolean canDel(TaAutUser user, Integer id) throws Exception {
		if(id == null || id<=0) return false;
		
		TaTpyDocument  ent	 = TaTpyDocument.DAO.reqEntityByRef(id);
		if (ent==null){
			return false;
		}

		if (!user.canBeSuperAdmin() && !user.canBeAdmin() && ent.reqInt(TaTpyDocument.ATT_I_AUT_USER_01) != user.reqId())
			return false;
		
		//remove table parent id
		ent.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
		ent.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		TaTpyDocument.DAO.doMerge(ent);
		
		return true;
	}
	
	

	private static void doGet(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Integer			id = ToolData.reqInt(json, "key", null);
		if(id == null)	id = ToolData.reqInt(json, "id" , null);

		if(id == null || id<=0) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}


		TaTpyDocument d = TaTpyDocument.DAO.reqEntityByRef(id);
		API.doResponse(response, ToolJSON.reqJSonString(		
				DefJS.SESS_STAT	, 1, 
				DefJS.SV_CODE	, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA	, d 
				));
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	private static void doDownload(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Integer			id 		= ToolData.reqInt(json, "id"	, null); //--FileInput return key
		String			code	= ToolData.reqStr(json, "code" 	, null); //--Filename
		
		if(id == null || id<=0 || code==null || code.length()==0) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}


		TaTpyDocument d = TaTpyDocument.DAO.reqEntityByValues(TaTpyDocument.ATT_I_ID, id, TaTpyDocument.ATT_T_INFO_01, code);
		if (d==null) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}

		Integer typ 	= d.reqInt(TaTpyDocument.ATT_I_TYPE_03);
		Integer owner 	= d.reqInt(TaTpyDocument.ATT_I_AUT_USER_01);
		if (!typ.equals(TaTpyDocument.TYPE_03_PUBLIC) && user.reqId()!=owner) {
			API.doResponse(response, DefAPI.API_MSG_KO);
			return;
		}
		
		try {
			String 			fPath 	= d.reqStr(TaTpyDocument.ATT_T_INFO_02);
			InputStream 	in 		= new FileInputStream (fPath);
			OutputStream 	out 	= response.getOutputStream();

//			response.setContentType("text/plain");
			response.setHeader("Content-disposition","attachment; filename="+code);
			byte[] buffer = new byte[8096];

			int numBytesRead;
			while ((numBytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, numBytesRead);
			}
			in.close();
	        out.flush();
		}catch (Exception e) {
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		}
	}
	//---------------------------------------------------------------------------------------------------------------------
	private static void doNewInPost(TaAutUser user, JSONObject json, HttpServletResponse response ) throws Exception {
		String 				filePath 	= "";
		List<TaTpyDocument> listDoc 	= reqListNewInPost(user, json,  filePath);
		if (listDoc==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		}else{				
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, listDoc
					));				
		}
	}
	
	private static List<TaTpyDocument> reqListNewInPost(TaAutUser user, JSONObject json, String filePath) throws Exception {
		Integer				entTyp			= ToolData.reqInt(json, "entTyp", DefDBExt.ID_TA_AUT_USER); //data-entTyp give enttyp
		Integer				entId			= ToolData.reqInt(json, "entId"	, user.reqId()); 
		Integer				fileTyp01		= ToolData.reqInt(json, "typ01"	, TaTpyDocument.TYPE_01_FILE_POST);
		Integer				fileTyp02		= ToolData.reqInt(json, "typ02"	, TaTpyDocument.TYPE_02_UNKNOW);
		Integer				fileTyp03		= ToolData.reqInt(json, "typ03"	, TaTpyDocument.TYPE_03_PUBLIC);
		
		Integer				stat			= TaTpyDocument.STAT_NEW; 
		
		List<String>		names			= (ArrayList) json.get("fileNames");	//filename not add dateTime
		List<String>		paths			= (ArrayList) json.get("filePaths");   //path full of filename

		List<TaTpyDocument> docs			= new ArrayList<TaTpyDocument>();

		for (int i=0; i<names.size(); i++){
			String 	fName 			= names.get(i);
			String 	fPath			= paths.get(i);
			File 	file 			= new File(fPath);
			Double 	filesize 		= file.length() * 1.0;
			
			TaTpyDocument doc		= new TaTpyDocument(fileTyp01, fileTyp02, fileTyp03, filesize, fName, null, null, fPath, new Date(),user.reqId(), entTyp, -1, stat, i);
			docs.add(doc);
		}

		if (docs.size()>0) TaTpyDocument.DAO.doPersist(docs);
		
		TaTpyDocument.reqListSaveFromNewToValidated (user, entTyp, entId, docs );
		return docs;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	private static void doNewInChat(TaAutUser user, JSONObject json, HttpServletResponse response ) throws Exception {
		String 				filePath 	= "";
		List<TaTpyDocument> listDoc 	= reqListNewInChat(user, json,  filePath);
		if (listDoc==null){
			API.doResponse(response,DefAPI.API_MSG_KO);
		}else{				
			API.doResponse(response, ToolJSON.reqJSonString(
					DefJS.SESS_STAT, 1, 
					DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
					DefJS.RES_DATA		, listDoc
					));				
		}
	}

	private static List<TaTpyDocument> reqListNewInChat(TaAutUser user, JSONObject json, String filePath) throws Exception {
		Integer				entTyp			= ToolData.reqInt(json, "entTyp", DefDBExt.ID_TA_NSO_GROUP); //data-entTyp give enttyp
		Integer				entId			= ToolData.reqInt(json, "entId"	, user.reqId()); 
		Integer				fileTyp01		= ToolData.reqInt(json, "typ01"	, TaTpyDocument.TYPE_01_FILE_POST);
		Integer				fileTyp02		= ToolData.reqInt(json, "typ02"	, TaTpyDocument.TYPE_02_UNKNOW);
		Integer				fileTyp03		= ToolData.reqInt(json, "typ03"	, TaTpyDocument.TYPE_03_PUBLIC);

		Integer				stat			= TaTpyDocument.STAT_NEW; 

		List<String>		names			= (ArrayList) json.get("fileNames");	//filename not add dateTime
		List<String>		paths			= (ArrayList) json.get("filePaths");   //path full of filename

		List<TaTpyDocument> docs			= new ArrayList<TaTpyDocument>();

		for (int i=0; i<names.size(); i++){
			String 	fName 			= names.get(i);
			String 	fPath			= paths.get(i);
			File 	file 			= new File(fPath);
			Double 	filesize 		= file.length() * 1.0;

			String 	fUrl			= fPath.replace(DefAPIExt.API_PATH_UPLOAD, DefAPIExt.API_PATH_URL_DOCBASE_TMP);

			
			TaTpyDocument doc		= new TaTpyDocument(fileTyp01, fileTyp02, fileTyp03, filesize, fName, null, fUrl, fPath, new Date(),user.reqId(), entTyp, -1, stat, i);
			docs.add(doc);
		}

		if (docs.size()>0) TaTpyDocument.DAO.doPersist(docs);

//		TaTpyDocument.reqListSaveFromNewToValidated (user, entTyp, entId, docs );
		// validated in Msg creation
		return docs;
	}
	
	private static void doDelInChat(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Integer			id = ToolData.reqInt(json, "key", null); //--FileInput return key
		if(id == null)	id = ToolData.reqInt(json, "id" , null);

		if (!canDelInChat(user, id)){
			API.doResponse(response,DefAPI.API_MSG_KO);
			return;
		} 

		API.doResponse(response, ToolJSON.reqJSonString(
				DefJS.SESS_STAT		, 1, 
				DefJS.SV_CODE		, DefAPI.SV_CODE_API_YES,
				DefJS.RES_DATA		, id
				));
	}
	
	private static boolean canDelInChat(TaAutUser user, Integer id) throws Exception {
		if(id == null || id<=0) return false;
		
		TaTpyDocument  ent	 = TaTpyDocument.DAO.reqEntityByRef(id);
		if (ent==null){
			return false;
		}
		
		if (user.reqId() != ent.reqInt(TaTpyDocument.ATT_I_AUT_USER_01)) return false;

		//remove table parent id
		ent.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
		ent.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		TaTpyDocument.DAO.doMerge(ent);
		
		return true;
	}
	//--------------------------------------------------------------------------------------------------------------
	public static List<TaTpyDocument> reqNewtMod(TaAutUser user, Integer entTyp, Integer entId, JSONObject json) throws Exception {
		try{

//			JSONArray arrFiles = ToolJSON.reqJSonArrayFromString((String) json.get("files"));
			JSONArray arrFiles =  (JSONArray)json.get("files");

			return reqNewtMod (user, entTyp, entId, arrFiles);
		}catch(Exception e){	
			return null;
		}
	}
	
	public static List<TaTpyDocument> reqNewtMod(TaAutUser user, Integer entTyp, Integer entId, JSONArray arrFiles) throws Exception {
		try{
			if(arrFiles == null || arrFiles.size() == 0)		return null;

			List<TaTpyDocument> docs = new ArrayList<TaTpyDocument>();

			Set<Integer> setDocs = new HashSet<Integer>();
			for(int i = 0; i < arrFiles.size() ; i++) {
				Map<String, Object> attr 	= API.reqMapParamsByClass((JSONObject)arrFiles.get(i), TaTpyDocument.class);
				Integer 			id		=  (Integer) attr.get(TaTpyDocument.ATT_I_ID);
				if(id == null)	continue;
				setDocs.add(id);
			}

			if(!setDocs.isEmpty()) {
				List<TaTpyDocument> lst = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, setDocs);
				if(lst != null && lst.size() > 0) {
					for(TaTpyDocument d : lst) {
						d.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, entId);
						d.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE, entTyp);
						d.reqSet(TaTpyDocument.ATT_D_DATE_02	, new Date());
						docs.add(d);
					}
				}
			}

			if(docs.size() > 0)	TaTpyDocument.DAO.doMerge(docs);

			return docs;
		}catch(Exception e){	
			return null;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	private void doListPage(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		ResultPagination res = resLstPage(user, json, response);
		
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

	private static ResultPagination resLstPage(TaAutUser user, JSONObject json, HttpServletResponse response) throws Exception {
		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 10);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);
		String 			searchKey   = ToolData.reqStr	(json, "searchKey"	, null);
		
		Set<Integer>	entId		= ToolData.reqSetInt(json, "entId"		, null);
		Integer			entTyp		= ToolData.reqInt	(json, "entTyp"		, null);
		
		Integer			typ01		= ToolData.reqInt	(json, "typ01"		, null);
		Integer			typ02		= ToolData.reqInt	(json, "typ02"		, null);
		Integer			typ03		= ToolData.reqInt	(json, "typ03"		, null);
		
		Boolean 		forced 		= ToolData.reqBool	(json, "forced"	, false);
		
		if (entTyp==null && entId==null) {
			entTyp	= DefDBExt.ID_TA_AUT_USER;
			entId	= new HashSet<Integer>();
			entId.add(user.reqId());
			typ01	= TaTpyDocument.TYPE_01_FILE_POST;
			typ02	= TaTpyDocument.TYPE_02_UNKNOW;
		}
		
		if(entId == null || entTyp == null) return null;
		
		ResultPagination rs =	null;
		
		List<TaTpyDocument> list = TaTpyDocument.reqTpyDocuments(entTyp, entId, typ01, typ02, typ03, searchKey, false);
		if (list==null || list.isEmpty() ){
			rs								= new ResultPagination(list, 0, 0, 0);
		}else {
			if (total == 0 ) {
				Criterion cri = Restrictions.gt(TaTpyDocument.ATT_I_ID, 0);	

				if (entId != null)
					cri 	= Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_TYPE, entTyp));
				cri 		= Restrictions.and(cri, Restrictions.in(TaTpyDocument.ATT_I_ENTITY_ID, entId));
				
				total		= TaTpyDocument.DAO.reqCount(cri).intValue();
			}
			rs				= new ResultPagination(list, total, begin, number);
		}
		return rs;
	}
	//------------------------------------------------------------------------------------------------------------
	
}
