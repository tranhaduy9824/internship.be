package com.hnv.db.tpy;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolDate;
import com.hnv.common.tool.ToolFile;
import com.hnv.common.tool.ToolImage;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.common.tool.ToolSet;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.def.DefAPIExt;
import com.hnv.def.DefDBExt;

/**
 * TaTpyDocument by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_TPY_DOCUMENT )
public class TaTpyDocument extends EntityAbstract<TaTpyDocument> {

	private static final long serialVersionUID = 1L;
	
	private static final String  F_SEPA = File.separator; 
	private static final String  U_SEPA = "/"; 

	public final static int 		STAT_NEW 						= 0; //--just up, not confirmed, wait to copy to final destination
	public final static int 		STAT_DUPLICATED					= 1;
	public final static int 		STAT_VALIDATED 					= 2; //--OK
	public final static int 		STAT_DELETED 					= 3; //--Deleted, wait to delete

	public final static int 		TYPE_01_FILE_MEDIA 				= 1; //--img, video
	public final static int 		TYPE_01_FILE_OTHER				= 2; //--all for entity
	public final static int 		TYPE_01_FILE_POST				= 10; //--file used in post/description/summernote 

	public final static int 		TYPE_02_FILE_IMG_AVATAR			= 1; //--img
	public final static int 		TYPE_02_FILE_IMG 				= 2; //--img
	public final static int 		TYPE_02_FILE_VIDEO 				= 3; //--video
	public final static int 		TYPE_02_FILE_DOC 				= 4; //--pdf, doc....
	public final static int         TYPE_02_LINK_YTB 				= 5; //--link youtube
	public final static int         TYPE_02_UNKNOW 					= 10;

	public final static int         TYPE_03_PUBLIC 					= 1; //-- can be re-used for different entity => one file but multi tpyDoc
	public final static int         TYPE_03_PRIVATE 				= 2; //-- only use in user workspace

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_I_TYPE_04                         =	"I_Type_04";
	public static final String	COL_I_TYPE_05                         =	"I_Type_05";
	
	public static final String	COL_F_VAL_01                          =	"F_Val_01";  //--filesize
	public static final String	COL_F_VAL_02                          =	"F_Val_02";  //--fCode
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	
	public static final String	COL_T_INFO_01                         =	"T_Info_01"; //--filename
	public static final String	COL_T_INFO_02                         =	"T_Info_02"; //--path real
	public static final String	COL_T_INFO_03                         =	"T_Info_03"; //--url 
	public static final String	COL_T_INFO_04                         =	"T_Info_04"; //--path real preview
	public static final String	COL_T_INFO_05                         =	"T_Info_05"; //--url preview
	public static final String	COL_T_INFO_06                         =	"T_Info_06";
	public static final String	COL_T_INFO_07                         =	"T_Info_07";
	public static final String	COL_T_INFO_08                         =	"T_Info_08";
	public static final String	COL_T_INFO_09                         =	"T_Info_09"; //--comment
	public static final String	COL_T_INFO_10                         =	"T_Info_10"; //--path tmp
	
	public static final String	COL_D_DATE_01                         =	"D_Date_01"; //--date new
	public static final String	COL_D_DATE_02                         =	"D_Date_02"; //--date mod
	public static final String	COL_D_DATE_03                         =	"D_Date_03"; //--date begin
	public static final String	COL_D_DATE_04                         =	"D_Date_04"; //--date end
	public static final String	COL_D_DATE_05                         =	"D_Date_05"; //--date other
	
	public static final String 	COL_I_AUT_USER_01 					  = "I_Aut_User_01"; //--owner, user up
	public static final String 	COL_I_AUT_USER_02 					  = "I_Aut_User_02"; //-- user mod

	public static final String	COL_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	COL_I_ENTITY_TYPE                     =	"I_Entity_Type";

	public static final String	COL_I_STATUS                     	  =	"I_Status";
	public static final String	COL_I_PRIORITY                    	  =	"I_Priority";

	public static final String	COL_I_PARENT                      =	"I_Parent"; //--ref of tpyDocument if this is a copie 

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_I_TYPE_04                         =	"I_Type_04";
	public static final String	ATT_I_TYPE_05                         =	"I_Type_05";
	
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_T_INFO_06                         =	"T_Info_06";
	public static final String	ATT_T_INFO_07                         =	"T_Info_07";
	public static final String	ATT_T_INFO_08                         =	"T_Info_08";
	public static final String	ATT_T_INFO_09                         =	"T_Info_09";
	public static final String	ATT_T_INFO_10                         =	"T_Info_10";
	
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	
	public static final String 	ATT_I_AUT_USER_01 					  = "I_Aut_User_01";
	public static final String 	ATT_I_AUT_USER_02 					  = "I_Aut_User_02";

	public static final String	ATT_I_ENTITY_ID                       =	"I_Entity_ID";
	public static final String	ATT_I_ENTITY_TYPE                     =	"I_Entity_Type";

	public static final String	ATT_I_STATUS                     	  =	"I_Status";
	public static final String	ATT_I_PRIORITY                    	  =	"I_Priority";

	public static final String	ATT_I_PARENT                      	  =	"I_Parent";

	public static final String 	ATT_T_URL							  = "T_URL";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyDocument> 	DAO;

	static{
		DAO = new EntityDAO<TaTpyDocument>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaTpyDocument.class,RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_I_TYPE_01, nullable = false)
	private	Integer          I_Type_01;
	@Column(name=COL_I_TYPE_02, nullable = false)
	private	Integer          I_Type_02;
	@Column(name=COL_I_TYPE_03, nullable = false)
	private	Integer          I_Type_03;
	@Column(name=COL_I_TYPE_04, nullable = false)
	private	Integer          I_Type_04;
	@Column(name=COL_I_TYPE_05, nullable = false)
	private	Integer          I_Type_05;

	@Column(name=COL_F_VAL_01, nullable = false)
	private	Double            F_Val_01;
	@Column(name=COL_F_VAL_02, nullable = false)
	private	Double            F_Val_02;
	@Column(name=COL_F_VAL_03, nullable = false)
	private	Double            F_Val_03;
	@Column(name=COL_F_VAL_04, nullable = false)
	private	Double            F_Val_04;
	@Column(name=COL_F_VAL_05, nullable = false)
	private	Double            F_Val_05;
	
	@Column(name=COL_T_INFO_01, nullable = false)
	private	String            T_Info_01;
	@Column(name=COL_T_INFO_02, nullable = false)
	private	String            T_Info_02;
	@Column(name=COL_T_INFO_03, nullable = false)
	private	String            T_Info_03;
	@Column(name=COL_T_INFO_04, nullable = false)
	private	String            T_Info_04;
	@Column(name=COL_T_INFO_05, nullable = false)
	private	String            T_Info_05;
	@Column(name=COL_T_INFO_06, nullable = false)
	private	String            T_Info_06;
	@Column(name=COL_T_INFO_07, nullable = false)
	private	String            T_Info_07;
	@Column(name=COL_T_INFO_08, nullable = false)
	private	String            T_Info_08;
	@Column(name=COL_T_INFO_09, nullable = false)
	private	String            T_Info_09;
	@Column(name=COL_T_INFO_10, nullable = false)
	private	String            T_Info_10;

	@Column(name=COL_D_DATE_01, nullable = false)
	private	Date            D_Date_01;
	@Column(name=COL_D_DATE_02, nullable = false)
	private	Date            D_Date_02;
	@Column(name=COL_D_DATE_03, nullable = false)
	private	Date            D_Date_03;
	@Column(name=COL_D_DATE_04, nullable = false)
	private	Date            D_Date_04;
	@Column(name=COL_D_DATE_05, nullable = false)
	private	Date            D_Date_05;
	
	@Column(name=COL_I_AUT_USER_01, nullable = false)
	private	Integer            I_Aut_User_01;
	@Column(name=COL_I_AUT_USER_02, nullable = false)
	private	Integer            I_Aut_User_02;

	@Column(name=COL_I_ENTITY_TYPE, nullable = false)
	private	Integer         I_Entity_Type;
	@Column(name=COL_I_ENTITY_ID, nullable = false)
	private	Integer         I_Entity_ID;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
	
	@Column(name=COL_I_PRIORITY, nullable = true)
	private	Integer         I_Priority;

	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;

	//-----------------------Transient Variables-------------------------

	@Transient
	private String T_URL;

	//---------------------Constructeurs-----------------------
	private TaTpyDocument(){}

	public TaTpyDocument(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}

	//	public TaTpyDocument(String T_Name, Date D_Date) throws Exception {
	//		this.reqSetAttr(
	//			ATT_T_INFO_01       , T_Name,
	//			ATT_D_DATE_01       , D_Date
	//		);
	//		doInitDBFlag();
	//	}
	public TaTpyDocument(
			Integer typ01, 	Integer typ02, Integer typ03, 
			Double fSize,  
			String fName, 	
			String pathReal,
			String pathUrl,
			String pathTmp,
			Date dNew,		Integer usrNew, 
			Integer entTyp, Integer entId, 
			Integer stat,	Integer priority) {

		this.I_Type_01              = typ01;
		this.I_Type_02     			= typ02;
		this.I_Type_03              = typ03;

		this.F_Val_01			   	= fSize;

		this.T_Info_01			   	= fName;
		
		this.T_Info_02			   	= pathReal;
		this.T_Info_03			   	= pathUrl;
		this.T_Info_10			   	= pathTmp;

		this.D_Date_01			   	= dNew;
		this.I_Aut_User_01		 	= usrNew;

		this.I_Entity_Type          = entTyp;
		this.I_Entity_ID            = entId ;
		this.I_Status         		= stat;
		this.I_Priority            	= priority;
	}
	public TaTpyDocument(Integer I_Type_01, Integer I_Type_02, Integer I_Type_03, 
			Double F_Val_01,  Double F_Val_02, 
			String T_Info_01, String T_Info_02, String T_Info_03, String T_Info_04, String T_Info_05,
			String T_Info_06, String T_Info_07, String T_Info_08, String T_Info_09, String T_Info_10,
			Date D_Date_01, Date D_Date_02,
			Integer I_Aut_User_01, Integer I_Aut_User_02, 
			Integer I_Entity_Type, Integer I_Entity_ID, 
			Integer I_Status,	Integer I_Priority) {
		this.I_Type_01              = I_Type_01;
		this.I_Type_02     			= I_Type_02;
		this.I_Type_03              = I_Type_03;

		this.F_Val_01			   	= F_Val_01;
		this.F_Val_02			   	= F_Val_02;

		this.T_Info_01			   	= T_Info_01;
		this.T_Info_02			   	= T_Info_02;
		this.T_Info_03			   	= T_Info_03;
		this.T_Info_04			   	= T_Info_04;
		this.T_Info_05			   	= T_Info_05;
		this.T_Info_06			   	= T_Info_06;
		this.T_Info_07			  	= T_Info_07;
		this.T_Info_08			   	= T_Info_08;
		this.T_Info_09			   	= T_Info_09;
		this.T_Info_10			   	= T_Info_10;

		this.D_Date_01			   	= D_Date_01;
		this.D_Date_02			   	= D_Date_02;

		this.I_Aut_User_01		 	= I_Aut_User_01;
		this.I_Aut_User_02			= I_Aut_User_02;

		this.I_Entity_Type          = I_Entity_Type;
		this.I_Entity_ID            = I_Entity_ID ;
		this.I_Status         		= I_Status;
		this.I_Priority            	= I_Priority;
	}


	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}


	@Override
	public void doMergeWith(TaTpyDocument ent) {
		if (ent == this) return;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.T_Info_06              = ent.T_Info_06;
		this.T_Info_07              = ent.T_Info_07;
		this.T_Info_08              = ent.T_Info_08;
		this.T_Info_09              = ent.T_Info_09;

		this.I_Entity_Type          = ent.I_Entity_Type;
		this.I_Entity_ID            = ent.I_Entity_ID;

		this.I_Status          		= ent.I_Status;
		this.I_Priority          	= ent.I_Priority;

		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaTpyDocument)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}


	public void doBuildURL(String serverUrl) {
		this.T_URL = serverUrl + "?" + this.T_Info_02;
	}

	public Integer reqID() {
		return this.I_ID;
	}
	//--------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	public static List<TaTpyDocument> reqTpyDocuments (Integer parentType, Integer parentId, Integer type01, Integer type02) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	
		if (parentType!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType));

		if (parentId!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_ID, parentId));

		if (type01!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_TYPE_01, type01));
		else
			cri = Restrictions.and(cri,Restrictions.ne(TaTpyDocument.ATT_I_TYPE_01, TaTpyDocument.TYPE_01_FILE_POST));	

		if (type02!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_TYPE_02, type02));

		//List<Order> ord = new ArrayList<Order>(Arrays.asList(Order.asc(TaTpyDocument.ATT_I_TYPE_01), Order.asc(TaTpyDocument.ATT_I_TYPE_02)));
		List<TaTpyDocument> lst = TaTpyDocument.DAO.reqList(Order.asc(TaTpyDocument.ATT_T_INFO_01),cri);
		
		doHideInfo(lst);
		return lst;
	}

	public static List<TaTpyDocument> reqTpyDocuments (Integer entType, Set<Integer> entId, Integer type01, Integer type02, Integer type03, String searchKey, boolean hideInfo) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (entType!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, entType));
		else {
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, DefDBExt.ID_TA_AUT_USER));
		}
		
		
		if (type01!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_TYPE_01, type01));
		else
			cri = Restrictions.and(cri,Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01, TaTpyDocument.TYPE_01_FILE_POST));	
		
		if (type02!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02, type02));
		else
			cri = Restrictions.and(cri,Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02, TaTpyDocument.TYPE_02_UNKNOW));	
		
		if (type03!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_03, type03));

		if (searchKey!=null)
			cri = Restrictions.and(cri, Restrictions.ilike(TaTpyDocument.COL_T_INFO_01, "%"+searchKey+"%"));
		
		List<TaTpyDocument> lst = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ENTITY_ID, entId, cri);
		
		if (hideInfo) doHideInfo(lst);
		return lst;
	}
	

	public static List<TaTpyDocument> reqTpyDocuments (Integer parentType, Set<Integer> parentId, Criterion...cris) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (parentType!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType));

		if (cris!=null)
			for (Criterion c: cris)
				cri = Restrictions.and(cri, c);

		List<TaTpyDocument> lst = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ENTITY_ID, parentId, cri);
		
		doHideInfo(lst);
		return lst;
	}
	
	private static String ATT_O_DOCUMENTS    =	"O_Documents";
	public static void doBuildTpyDocuments(List listEnts, Integer parentType, Integer type01, Integer type02, String attrDocsName, boolean forced) throws Exception{
		if (attrDocsName==null)  attrDocsName = ATT_O_DOCUMENTS;
		Hashtable<Integer, List				> tabDocs 	= new Hashtable<Integer, List>();
		Hashtable<Integer, EntityAbstract	> tabEnt 	= new Hashtable<Integer, EntityAbstract>();
		for (Object o: listEnts) {
			EntityAbstract 	e 		= (EntityAbstract)o;
			List 			docs 	= (List)e.req(attrDocsName);
			if (!forced && docs!=null) continue;

			if (docs == null||forced) {
				docs = new ArrayList();
				e.reqSet(attrDocsName, docs);
			}

			tabDocs	.put((Integer)e.reqRef(), docs);
			tabEnt	.put((Integer)e.reqRef(), e);
		}

		List<TaTpyDocument> docs = reqTpyDocuments(parentType, tabDocs.keySet(), type01, type02, null, null, false);

		if (docs!=null && docs.size()>0) {
			Collections.sort(docs , new Comparator	<TaTpyDocument>() {
				@Override
				public int compare(TaTpyDocument e1, TaTpyDocument e2) {			
					return e1.reqID() - e2.reqID();
				}
			});
			for (TaTpyDocument d : docs) {
				Integer eId 	= (Integer) d.req(TaTpyDocument.ATT_I_ENTITY_ID);
				List 	eDocs 	= tabDocs.get(eId);
				eDocs.add(d);
				tabEnt.remove(eId); //remove ent has doc
			}
		}

		if (tabEnt.size()>0) {
			for (Entry<Integer, EntityAbstract> e: tabEnt.entrySet()) {
				EntityAbstract ent = e.getValue();
				ent.reqSet(attrDocsName, null); //reset doc list to null
			}
		}
	}

	//---------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	private static String	files_root_dir 			= null;
	private static String	files_url_docBase 		= null;
	private static String 	files_url_docBase_tmp	= null;
	static {
		if(files_root_dir == null) {
			files_root_dir 		=  DefAPIExt.API_PATH_DOCUMENT;
		}
		if (files_url_docBase==null) {
			files_url_docBase 	= DefAPIExt.API_PATH_URL_DOCBASE;
		}
		if (files_url_docBase_tmp==null) {
			files_url_docBase_tmp 	= DefAPIExt.API_PATH_URL_DOCBASE_TMP;
		}
	}

	private static final Double		SIZE_MAX_IMG_WH					= 1920.0;		//1920px
	private static final int 		SIZE_MAX_IMG_FILE				= 512*1024; 	//0.5MB
	private static final int 		SIZE_MAX_IMG_PREVIEW_FILE 		= 128*1024; 	//0.1MB
	private static final Double		SIZE_MAX_IMG_PREVIEW_WH			= 500.0;		//500px
	private static final int 		SIZE_MAX_VIDEO_PREVIEW_FILE 	= 2*1024*1024; 	//2MB
	private static final String 	DIR_PREVIEW						= "prev";
	private static final String 	DIR_ORIGIN						= "raw";
	private static final Float  	IMG_QUAL						= 0.7f;
	private static final String 	IMG_EXT							= "jpg";

	// 
	public static List<TaTpyDocument> reqListCheck (int mode, TaAutUser user, Integer entTyp, Integer entID,  JSONArray lstJson, Integer...typs) throws Exception {
		if (mode==DefAPI.SV_MODE_NEW && (lstJson == null || lstJson.size() == 0)) return null; //add address
		
		List<TaTpyDocument> lstEntDocs = new ArrayList<TaTpyDocument>();
		Set<Integer> 		docIdsNew 	= new HashSet<Integer>();
		Set<Integer> 		docIdsDupl 	= new HashSet<Integer>();
		Set<Integer> 		docIdsValid	= new HashSet<Integer>();
		Set<Integer> 		docIdsDel 	= new HashSet<Integer>();
		Set<Integer>		docSet		= new HashSet<Integer>();
		
		if (mode==DefAPI.SV_MODE_MOD) {
			Criterion	cri = Restrictions.and (Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE	, entTyp), 
												Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID	, entID ));
			if (typs!=null&& typs.length>0) {
				if (typs.length>0) cri = Restrictions.and (cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01	, typs[0]));
				if (typs.length>1) cri = Restrictions.and (cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02	, typs[1]));
				if (typs.length>2) cri = Restrictions.and (cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_03	, typs[2]));
				if (typs.length>3) cri = Restrictions.and (cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_04	, typs[3]));
				if (typs.length>4) cri = Restrictions.and (cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_05	, typs[4]));
			}
			lstEntDocs 	= TaTpyDocument.DAO.reqList(cri);
			
			docSet		= ToolSet.reqSetInt(lstEntDocs, TaTpyDocument.ATT_I_ID);
		}
		
		if (mode==DefAPI.SV_MODE_MOD && (lstJson==null || lstJson.size()==0)) {
			if (lstEntDocs!=null&& lstEntDocs.size()>0){
				for (TaTpyDocument doc: lstEntDocs){
					doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
					doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
				}
				TaTpyDocument.DAO.doMerge(lstEntDocs);
			}
			return null;
		}

		for (Object d: lstJson){
			JSONObject 	obj 	= ((JSONObject)d);
			Integer		id 		= ToolData.reqInt(obj, "id"	 , null);
			Integer		stat	= ToolData.reqInt(obj, "stat", TaTpyDocument.STAT_NEW);
			if (id==null) continue;
			switch(stat) {
			case TaTpyDocument.STAT_NEW			: docIdsNew	 .add(id); docSet.remove(id); break;
			case TaTpyDocument.STAT_DUPLICATED	: docIdsDupl .add(id); break;
			case TaTpyDocument.STAT_VALIDATED	: docIdsValid.add(id); docSet.remove(id); break;
			case TaTpyDocument.STAT_DELETED		: docIdsDel  .add(id); break;
			}
		}
		List<TaTpyDocument> allDocs  = new ArrayList<TaTpyDocument>();

		if (docIdsNew.size()>0) {
			List<TaTpyDocument> docs = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, docIdsNew, Restrictions.eq(TaTpyDocument.ATT_I_STATUS, TaTpyDocument.STAT_NEW));
			try{
				reqListSaveFromNewToValidated (user, entTyp, entID, docs);
			}catch(Exception e) {
				ToolLogServer.doLogErr(e, true);
			}
			allDocs.addAll(docs);
		}

		if (docIdsDupl.size()>0) {
			List<TaTpyDocument> docs = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, docIdsDupl);
			for (TaTpyDocument doc: docs){
				doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, entTyp 	);
				doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, entID		);

				doc.reqSet(TaTpyDocument.ATT_D_DATE_02		, new Date());
				doc.reqSet(TaTpyDocument.ATT_I_AUT_USER_02	, user.reqId());

				doc.reqSet(TaTpyDocument.ATT_I_PARENT		, doc.req(TaTpyDocument.ATT_I_ID));

				doc.reqSet(TaTpyDocument.ATT_I_ID			, null		);//---remove id to persist as new line
			}
			TaTpyDocument.DAO.doPersist(docs);
			allDocs.addAll(docs);
		}

		//???del => dupplicate use the same source, del origin, del duplicate
		//--add all docSet to docIDDel to del
		docIdsDel.addAll(docSet);
		if (docIdsDel.size()>0) {
			List<TaTpyDocument> docs 	 = TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, docIdsDel);
			for (TaTpyDocument doc: docs){
				doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
				doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
			}
			TaTpyDocument.DAO.doMerge(docs);
		}
		if (lstEntDocs == null) {
			return lstEntDocs;
		};
		//----add old files to list
		if(lstEntDocs != null) {
			for (TaTpyDocument doc : lstEntDocs){
				Integer id = doc.reqId();
				if (!docIdsDel.contains(id)) allDocs.add(doc);
			}
		}

		//----hide real path when return to client
		doHideInfo (allDocs);
		return allDocs;
	}
	
	public static List<TaTpyDocument> reqListSaveFromNewToValidated(TaAutUser user, Integer entTyp, Integer entID, List<TaTpyDocument> docs) throws Exception {
		String sDate			= ToolDate.reqString(new Date(), "yyMMdd");
		for (TaTpyDocument doc: docs) {
			int 	fType01		= doc.reqInt	(TaTpyDocument.ATT_I_TYPE_01);
			int 	fType02		= doc.reqInt	(TaTpyDocument.ATT_I_TYPE_02);
			double 	fSize 		= doc.reqDouble	(TaTpyDocument.ATT_F_VAL_01);
			
			String 	fName		= doc.reqStr	(TaTpyDocument.ATT_T_INFO_01);
			String  fExt 		= FilenameUtils.getExtension(fName); 
			String  fNameNoExt	= FilenameUtils.getBaseName(fName); 
			
			String 	fPathTmp	= doc.reqStr	(TaTpyDocument.ATT_T_INFO_10);
			File 	f			= new File 		(fPathTmp);
			String 	fNameTmp	= f.getName();
			

			String 	newPath		= files_root_dir 	+ F_SEPA + DIR_ORIGIN  + F_SEPA  + entTyp + F_SEPA + entID + F_SEPA + sDate + F_SEPA + fNameTmp;
			String 	newURL		= files_url_docBase + U_SEPA + DIR_ORIGIN  + U_SEPA  + entTyp + U_SEPA + entID + U_SEPA + sDate + U_SEPA + fNameTmp;

			String 	newPathPrev	= null;
			String 	newURLPreV	= null;

			try {
				ToolFile.doMoveFile(fPathTmp, newPath);
			}catch(Exception e) {
				if (ToolFile.canDelFile(newPath)) {
					ToolFile.doMoveFile(fPathTmp, newPath);
				}
			}


			if (fExt.equals("jpg") || fExt.equals("jpeg") || fExt.equals("png") || fExt.equals("bmp")){// || fileExt.equals("gif")
				if (fSize>SIZE_MAX_IMG_PREVIEW_FILE) {

					newPathPrev		= files_root_dir 	+ F_SEPA + DIR_PREVIEW + F_SEPA +  user.reqId()  + F_SEPA + entTyp+ F_SEPA + sDate + F_SEPA + fNameTmp+ ".jpg";
					try {
						ToolImage.doImgCompress(newPath, newPathPrev, "jpg", 0.5f, SIZE_MAX_IMG_PREVIEW_WH, SIZE_MAX_IMG_PREVIEW_WH);
					}catch(Exception e) {
						newPathPrev = null;
					}

					//--create thumnail preview
					if (newPathPrev!=null){
						newURLPreV			= files_url_docBase + U_SEPA + DIR_PREVIEW  + U_SEPA +  user.reqId() + U_SEPA + entTyp+ U_SEPA + sDate + U_SEPA + fNameTmp + ".jpg";
					}
				}

			}else if (fExt.equals("webm") || fExt.equals("mp4")){
				//---do something with compression if need
			}		

			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, entID); 
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, entTyp); 
			doc.reqSet(TaTpyDocument.ATT_I_STATUS		, TaTpyDocument.STAT_VALIDATED); 

			doc.reqSet(TaTpyDocument.ATT_T_INFO_02 		, newPath);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_03 		, newURL);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_04 		, newPathPrev);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_05 		, newURLPreV);

			doc.reqSet(TaTpyDocument.ATT_T_INFO_10		, fNameTmp);
		}

		if (docs.size()>0) TaTpyDocument.DAO.doMerge(docs);
		
		doHideInfo (docs);

		return docs;
	}
	
	public static void doListDel(Session sess, Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyDocument>  		docs 			= TaTpyDocument.DAO.reqList(sess,	Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE, entType), 
																								Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID	 , entID));		
		for (TaTpyDocument doc: docs){
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID, -1);
		}
		TaTpyDocument.DAO.doMerge(sess, docs);
	}
	
	public static void doListDel (Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyDocument>  	lstObj 			= TaTpyDocument.DAO.reqList(Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, entType), Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID, entID));		
		for (TaTpyDocument doc : lstObj){
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyDocument.DAO.doMerge		(lstObj);	
	}
	
	
	public static  List<TaTpyDocument>  reqModByIds(Integer parentType, Integer parentID,  Set<Integer> docIds) throws Exception {
		if (docIds		==null) return null;
		if (parentType	==null) return null;

		List	<TaTpyDocument>  			lstObj 			= TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, docIds, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE,parentType ));		
		for (TaTpyDocument d : lstObj) {
			d.reqSet(TaTpyDocument.ATT_I_ENTITY_ID, parentID);
		}
		TaTpyDocument.DAO.doMerge(lstObj);

		return lstObj;
	}
	public static  List<TaTpyDocument>  reqModByIds(Integer parentType, Integer parentID, String docIds) throws Exception {
		if (docIds		==null) return null;
		if (parentType	==null) return null;

		Set<Integer> ids = ToolSet.reqSetInt(docIds);
		return reqModByIds(parentType, parentID, ids);
	}
	//---------------------------------------------------------------------------------
	/*
	 

	public static void doTpyDocumentDel (Integer parentType, Collection parentID) throws Exception {
		Collection	<TaTpyDocument>  	lstObj 			= TaTpyDocument.DAO.reqList(Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType), Restrictions.in(TaTpyDocument.ATT_I_ENTITY_ID, parentID));		
		for (TaTpyDocument doc : lstObj){
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyDocument.DAO.doMerge		(lstObj);	
	}

	public static void doTpyDocumentDel (Session sess,Integer parentType, Integer parentID) throws Exception {
		Collection	<TaTpyDocument>  	lstObj 			= TaTpyDocument.DAO.reqList(sess, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType), Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID, parentID));		
		for (TaTpyDocument doc : lstObj){
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyDocument.DAO.doMerge		(sess, lstObj);	
	}
	public static void doTpyDocumentDel (Session sess, Integer parentType, Collection parentID) throws Exception {
		Collection	<TaTpyDocument>  	lstObj 			= TaTpyDocument.DAO.reqList(sess, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType), Restrictions.in(TaTpyDocument.ATT_I_ENTITY_ID, parentID));		
		for (TaTpyDocument doc : lstObj){
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_TYPE	, -1);
			doc.reqSet(TaTpyDocument.ATT_I_ENTITY_ID	, -1);
		}
		TaTpyDocument.DAO.doMerge		(sess, lstObj);	
	}

	//---------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------
	public static  List<TaTpyDocument>  reqTpyDocumentMod(Integer parentType, Integer parentID,  Set<Integer> docIds) throws Exception {
		if (docIds		==null) return null;
		if (parentType	==null) return null;

		List	<TaTpyDocument>  			lstObj 			= TaTpyDocument.DAO.reqList_In(TaTpyDocument.ATT_I_ID, docIds, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE,parentType ));		
		for (TaTpyDocument d : lstObj) {
			d.reqSet(TaTpyDocument.ATT_I_ENTITY_ID, parentID);
		}
		TaTpyDocument.DAO.doMerge(lstObj);

		return lstObj;
	}
	public static  List<TaTpyDocument>  reqTpyDocumentMod(Integer parentType, Integer parentID, String docIds) throws Exception {
		if (docIds		==null) return null;
		if (parentType	==null) return null;

		Set<Integer> ids = ToolSet.reqSetInt(docIds);
		return reqTpyDocumentMod(parentType, parentID, ids);
	}
	//---------------------------------------------------------------------------------

	public static TaTpyDocument reqTpyDocumentNew (Integer userId, Integer entityType, Integer entityId, Double code, String name, String path01, String path02,  String path03, Integer type01, Integer type02, Double size, String comment ) throws Exception{		
		TaTpyDocument doc = new TaTpyDocument
				(type01, type02,null, 
				size,  code, 
				name, path01, path02, path03, null, null, null, null, comment, null,
				new Date(), null,
				userId, null, 
				entityType, entityId, 
				null, null);

		return doc;
	}

	public static TaTpyDocument reqTpyDocumentMod (int id, String name, String path01, String path02,  String path03,Integer type01, Integer type02, String comment ) throws Exception{		
		TaTpyDocument doc = TaTpyDocument.DAO.reqEntityByRef(id);
		if (doc!=null) {
			doc.reqSet(TaTpyDocument.ATT_T_INFO_01		, name);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_02	, path01);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_03	, path02);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_04	, path03);
			doc.reqSet(TaTpyDocument.ATT_I_TYPE_01	, type01);
			doc.reqSet(TaTpyDocument.ATT_I_TYPE_02	, type02);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_09	, comment);
			TaTpyDocument.DAO.doMerge(doc);
		}
		return doc;
	}
	public static TaTpyDocument reqTpyDocumentMod (int id, String name,  Integer type01, Integer type02, String comment ) throws Exception{		
		TaTpyDocument doc = TaTpyDocument.DAO.reqEntityByRef(id);
		if (doc!=null) {
			doc.reqSet(TaTpyDocument.ATT_T_INFO_01		, name);			
			doc.reqSet(TaTpyDocument.ATT_I_TYPE_01	, type01);
			doc.reqSet(TaTpyDocument.ATT_I_TYPE_02	, type02);
			doc.reqSet(TaTpyDocument.ATT_T_INFO_09	, comment);
			TaTpyDocument.DAO.doMerge(doc);
		}
		return doc;
	}
	public static TaTpyDocument reqTpyDocumentMod (int id, String name, String comment ) throws Exception{		
		TaTpyDocument doc = TaTpyDocument.DAO.reqEntityByRef(id);
		if (doc!=null) {
			doc.reqSet(TaTpyDocument.ATT_T_INFO_01		, name);			
			doc.reqSet(TaTpyDocument.ATT_T_INFO_09	, comment);
			TaTpyDocument.DAO.doMerge(doc);
		}
		return doc;
	}

	public static List<TaTpyDocument> reqTpyDocumentDel (Integer... ids ) throws Exception{		
		List<TaTpyDocument> docs = TaTpyDocument.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, ids));
		if (docs!=null && docs.size()>0) TaTpyDocument.DAO.doRemove(docs);
		return docs;
	}
	public static List<TaTpyDocument> reqTpyDocumentDel (Integer parentType, Integer parentId, String name , Integer type01, Integer type02 ) throws Exception{		
		Criterion cri = Restrictions.gt("I_ID", 0);	

		if (parentType!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_TYPE, parentType));

		if (parentId!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_ENTITY_ID, parentId));

		if (name!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_T_INFO_01, name));

		if (type01!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_TYPE_01, type01));

		if (type02!=null)
			cri = Restrictions.and(cri, Restrictions.eqOrIsNull(TaTpyDocument.ATT_I_TYPE_02, type02));


		List<TaTpyDocument> docs = TaTpyDocument.DAO.reqList(cri);
		if (docs!=null && docs.size()>0) TaTpyDocument.DAO.doRemove(docs);
		return docs;
	}
	 */
	
	public static List<TaTpyDocument> reqTpyDocumentDel (Integer... ids ) throws Exception{		
		List<TaTpyDocument> docs = TaTpyDocument.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, ids));
		if (docs!=null && docs.size()>0) TaTpyDocument.DAO.doRemove(docs);
		return docs;
	}
	
	private static String ATT_O_AVATAR = "O_Avatar";
	public static List<TaTpyDocument> reqBuildAvatar(List lst, int entTyp, String colAvatarName) throws Exception {
		if (colAvatarName==null) colAvatarName = ATT_O_AVATAR;
		if (lst==null) return null;
		Set<Integer> ids = ToolSet.reqSetInt(lst, ATT_I_ID);
		if(ids.size()>0) {
			Map<Integer, TaTpyDocument> mapAva = new HashMap<Integer, TaTpyDocument>();
			
			List<TaTpyDocument> avas = reqTpyDocuments (entTyp, ids, TaTpyDocument.TYPE_01_FILE_MEDIA, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR,  null, null, false);

			if(avas != null && avas.size() > 0) {
				for(TaTpyDocument a : avas) {
					mapAva.put((Integer) a.req(TaTpyDocument.ATT_I_ENTITY_ID), a);
				}
				for(Object g : lst) {
					EntityAbstract ent = ((EntityAbstract)g);
					int id = ent.reqId();
					if(mapAva.containsKey(ent.reqId())){
						ent.reqSet(colAvatarName, mapAva.get(id));
					}
				}
				doHideInfo(avas);
			}
			return avas;
		}
		return null;
	}
	
	
	public static TaTpyDocument reqBuildAvatar(EntityAbstract ent, int entTyp, String colAvatarName) throws Exception {
		if (colAvatarName==null) colAvatarName = ATT_O_AVATAR;
		Integer id = ent.reqId();
		
		if(id!=null && id>0) {
			TaTpyDocument  ava = TaTpyDocument.DAO.reqEntityByValues(
					TaTpyDocument.ATT_I_ENTITY_TYPE	, entTyp,
					TaTpyDocument.ATT_I_ENTITY_ID	, id,
					TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA, 
					TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR
					);

			if(ava != null ) {
				ent.reqSet(colAvatarName, ava);
				doHideInfo(ava);
			}
			return ava;
		}
		return null;
	}
	
	public static  List<TaTpyDocument>  reqList(Integer entTyp, Integer entId,  Integer typ01, Integer typ02, Integer typ03) throws Exception {
		if (entTyp	==null) return null;

		Criterion cri = Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, entTyp);
		cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_ID, entId));
		if (typ01!=null ) cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01, typ01));
		if (typ02!=null ) cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02, typ02));
		if (typ03!=null ) cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_03, typ03));

		List	<TaTpyDocument>  			lstObj 			= TaTpyDocument.DAO.reqList(cri);		

		doHideInfo (lstObj);
		return lstObj;
	}
	
	public static void doHideInfo (List<TaTpyDocument> docs) throws Exception{
		if (docs==null) return;
		for (TaTpyDocument doc : docs) {
			doc.T_Info_02 = null;
			doc.T_Info_04 = null;
			doc.T_Info_10 = null;
			
			doc.I_Status  		= null;
			doc.I_Aut_User_02  	= null;
			doc.I_Entity_Type  	= null;
			doc.I_Entity_ID  	= null;
		}
	}
	public static void doHideInfo (TaTpyDocument doc) throws Exception{
		if (doc==null) return;
		doc.T_Info_02 = null;
		doc.T_Info_04 = null;
		doc.T_Info_10 = null;

		doc.I_Status  		= null;
		doc.I_Aut_User_02  	= null;
		doc.I_Entity_Type  	= null;
		doc.I_Entity_ID  	= null;
	}
	
	public static  List<TaTpyDocument>  reqList(Integer entTyp, Set<Integer> entId,  Integer typ01, Integer typ02, Integer typ03, String searchName) throws Exception {
		if (entTyp	==null) return null;

		Criterion cri = Restrictions.eq(TaTpyDocument.ATT_I_ENTITY_TYPE, entTyp);
		cri = Restrictions.and(cri, Restrictions.in(TaTpyDocument.ATT_I_ENTITY_ID, entId));
		if (typ01!=null ) 		cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_01, typ01));
		if (typ02!=null ) 		cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_02, typ02));
		if (typ03!=null ) 		cri = Restrictions.and(cri, Restrictions.eq(TaTpyDocument.ATT_I_TYPE_03, typ03));
		if (searchName!=null ) 	cri = Restrictions.and(cri, Restrictions.ilike(TaTpyDocument.ATT_T_INFO_01, searchName));
		
		List<TaTpyDocument>		lstObj = TaTpyDocument.DAO.reqList(Order.asc(TaTpyDocument.ATT_T_INFO_01), cri);		

		doHideInfo (lstObj);
		return lstObj;
	}
}
