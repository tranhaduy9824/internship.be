package com.hnv.db.tpy;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.def.DefTime;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolData;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONArray;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.def.DefDBExt;		

/**
* TaTpyInformation by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_INFORMATION )
public class TaTpyInformation extends EntityAbstract<TaTpyInformation> {
	public static int STAT_WAITING          = 0;
    public static int STAT_VALIDATED        = 1; //--seen
    public static int STAT_DELETED          = 2; //--wait for deleting

    public  static int TYPE_01_BASE  		= 100;
    public  static int TYPE_01_REPORT   	= 1000; //--img
    
    
	
	private static final long serialVersionUID 			= 1L;
	
	public 	static final int TYPE_01_PRJ_HISTORY		= 100;
	public  static final int TYPE_01_TEST_HISTORY 		= 200;
	
	public 	static final int TYPE_01_PATIENT_HIST_DISEASE	= 100;
	public 	static final int TYPE_01_PATIENT_HIST_MEDICINE	= 200;
	
	public static final int  TYPE_01_WORK_TASK  		= 400;

	private static final int  ENT_TYP				= DefDBExt.ID_TA_TPY_INFORMATION;
	//---------------------------List of Column from DB-----------------------------
	 public static final String COL_I_ID           = "I_ID";
     public static final String COL_I_STATUS       = "I_Status";
     public static final String COL_I_ENTITY_TYPE  = "I_Entity_Type";
     public static final String COL_I_ENTITY_ID    = "I_Entity_ID";
     public static final String COL_I_PRIORITY     = "I_Priority";
     
     public static final String	COL_I_TYPE_01      =	"I_Type_01";
     public static final String	COL_I_TYPE_02      =	"I_Type_02";
     public static final String	COL_I_TYPE_03      =	"I_Type_03";
     public static final String	COL_I_TYPE_04      =	"I_Type_04";
     public static final String	COL_I_TYPE_05      =	"I_Type_05";
    
     public static final String COL_F_VAL_01       = "F_Val_01";
     public static final String COL_F_VAL_02       = "F_Val_02";
     public static final String COL_F_VAL_03       = "F_Val_03";
     public static final String COL_F_VAL_04       = "F_Val_04";
     public static final String COL_F_VAL_05       = "F_Val_05";
     public static final String COL_F_VAL_06       = "F_Val_06";
     public static final String COL_F_VAL_07       = "F_Val_07";
     public static final String COL_F_VAL_08       = "F_Val_08";
     public static final String COL_F_VAL_09       = "F_Val_09";
     public static final String COL_F_VAL_10       = "F_Val_10";
     
     public static final String COL_T_INFO_01      = "T_Info_01";
     public static final String COL_T_INFO_02      = "T_Info_02";
     public static final String COL_T_INFO_03      = "T_Info_03";
     public static final String COL_T_INFO_04      = "T_Info_04";
     public static final String COL_T_INFO_05      = "T_Info_05";
     public static final String COL_T_INFO_06      = "T_Info_06";
     public static final String COL_T_INFO_07      = "T_Info_07";
     public static final String COL_T_INFO_08      = "T_Info_08";
     public static final String COL_T_INFO_09      = "T_Info_09";
     public static final String COL_T_INFO_10      = "T_Info_10";
     
     public static final String COL_D_DATE_01      = "D_Date_01";
     public static final String COL_D_DATE_02      = "D_Date_02";
    
     public static final String COL_I_AUT_USER_01   = "I_Aut_User_01";
     public static final String COL_I_AUT_USER_02   = "I_Aut_User_02";



     public static final String ATT_I_ID           = "I_ID";
     public static final String ATT_I_STATUS       = "I_Status";
     public static final String ATT_I_ENTITY_TYPE  = "I_Entity_Type";
     public static final String ATT_I_ENTITY_ID    = "I_Entity_ID";
     public static final String ATT_I_PRIORITY     = "I_Priority";
    
     public static final String	ATT_I_TYPE_01      =	"I_Type_01";
     public static final String	ATT_I_TYPE_02      =	"I_Type_02";
     public static final String	ATT_I_TYPE_03      =	"I_Type_03";
     public static final String	ATT_I_TYPE_04      =	"I_Type_04";
     public static final String	ATT_I_TYPE_05      =	"I_Type_05";

     public static final String ATT_F_VAL_01       = "F_Val_01";
     public static final String ATT_F_VAL_02       = "F_Val_02";
     public static final String ATT_F_VAL_03       = "F_Val_03";
     public static final String ATT_F_VAL_04       = "F_Val_04";
     public static final String ATT_F_VAL_05       = "F_Val_05";
     public static final String ATT_F_VAL_06       = "F_Val_06";
     public static final String ATT_F_VAL_07       = "F_Val_07";
     public static final String ATT_F_VAL_08       = "F_Val_08";
     public static final String ATT_F_VAL_09       = "F_Val_09";
     public static final String ATT_F_VAL_10       = "F_Val_10";

     public static final String ATT_T_INFO_01      = "T_Info_01";
     public static final String ATT_T_INFO_02      = "T_Info_02";
     public static final String ATT_T_INFO_03      = "T_Info_03";
     public static final String ATT_T_INFO_04      = "T_Info_04";
     public static final String ATT_T_INFO_05      = "T_Info_05";
     public static final String ATT_T_INFO_06      = "T_Info_06";
     public static final String ATT_T_INFO_07      = "T_Info_07";
     public static final String ATT_T_INFO_08      = "T_Info_08";
     public static final String ATT_T_INFO_09      = "T_Info_09";
     public static final String ATT_T_INFO_10      = "T_Info_10";
     
     public static final String ATT_D_DATE_01      = "D_Date_01";
     public static final String ATT_D_DATE_02      = "D_Date_02";

     public static final String ATT_I_AUT_USER_01      = "I_Aut_User_01";
     public static final String ATT_I_AUT_USER_02      = "I_Aut_User_02";

     public static final String ATT_O_AUT_USER_01		= "O_Aut_User_02";
     
     public static final String  ATT_O_DOCUMENTS                       =	"O_Documents";
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaTpyInformation> 	DAO;
	static{
		DAO = new EntityDAO<TaTpyInformation>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaTpyInformation.class,RIGHTS);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_ENTITY_TYPE, nullable = true)
	private	Integer         I_Entity_Type;

	@Column(name=COL_I_ENTITY_ID, nullable = true)
	private	Integer         I_Entity_ID;
  
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_I_PRIORITY, nullable = true)
	private	Integer         I_Priority;
   
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
    
	@Column(name=COL_I_TYPE_04, nullable = true)
	private	Integer         I_Type_04;
    
	@Column(name=COL_I_TYPE_05, nullable = true)
	private	Integer         I_Type_05;
    
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String          T_Info_01;
    
	@Column(name=COL_T_INFO_02, nullable = true)
	private	String          T_Info_02;
    
	@Column(name=COL_T_INFO_03, nullable = true)
	private	String          T_Info_03;
    
	@Column(name=COL_T_INFO_04, nullable = true)
	private	String          T_Info_04;
    
	@Column(name=COL_T_INFO_05, nullable = true)
	private	String          T_Info_05;
    
	@Column(name=COL_T_INFO_06, nullable = true)
	private	String          T_Info_06;
    
	@Column(name=COL_T_INFO_07, nullable = true)
	private	String          T_Info_07;
    
	@Column(name=COL_T_INFO_08, nullable = true)
	private	String          T_Info_08;
    
	@Column(name=COL_T_INFO_09, nullable = true)
	private	String          T_Info_09;
    
	@Column(name=COL_T_INFO_10, nullable = true)
	private	String          T_Info_10;
    
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double          F_Val_01;
     
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double          F_Val_02;
     
	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double          F_Val_03;
     
	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double          F_Val_04;
     
	@Column(name=COL_F_VAL_05, nullable = true)
	private	Double          F_Val_05;
     
	@Column(name=COL_F_VAL_06, nullable = true)
	private	Double          F_Val_06;
     
	@Column(name=COL_F_VAL_07, nullable = true)
	private	Double          F_Val_07;
     
	@Column(name=COL_F_VAL_08, nullable = true)
	private	Double          F_Val_08;
     
	@Column(name=COL_F_VAL_09, nullable = true)
	private	Double          F_Val_09;
     
	@Column(name=COL_F_VAL_10, nullable = true)
	private	Double          F_Val_10;
     
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer         I_Aut_User_02;
	
	
    
	//-----------------------Transient Variables-------------------------
	@Transient
	private Object O_Aut_User_01;

	@Transient
	private List<TaTpyDocument>			O_Documents;

	//---------------------Constructeurs-----------------------
	public TaTpyInformation(){}

	public TaTpyInformation(Integer entTyp, Integer entId) throws Exception {
		this.I_Entity_Type 	= entTyp;
		this.I_Entity_ID	= entId;
	}
	
	public TaTpyInformation(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaTpyInformation ent) {
		if (ent == this) return;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.T_Info_06              = ent.T_Info_06;
		this.T_Info_07              = ent.T_Info_07;
		this.T_Info_08              = ent.T_Info_08;
		this.T_Info_09              = ent.T_Info_09;
		this.T_Info_10              = ent.T_Info_10;
		
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Entity_Type          = ent.I_Entity_Type;
		this.I_Entity_ID            = ent.I_Entity_ID;
	


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaTpyInformation)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}

	public static void doTpyReqSaveFile(TaAutUser user, JSONObject obj, TaTpyInformation ent) throws Exception {
		JSONArray files   			= (JSONArray) obj.get("files");
		if(files!=null) {
			while(files.contains(null))
				files.remove(null);
			List<TaTpyDocument> lstDoc = TaTpyDocument.reqListCheck(DefAPI.SV_MODE_MOD, user, DefDBExt.ID_TA_TPY_INFORMATION, ent.reqId(), files) ;
		}
		ent.doBuildDocuments(true);
	}
	
	public static void doBuildDocuments (List<TaTpyInformation> list)  throws Exception{
		TaTpyDocument.doBuildTpyDocuments(list, ENT_TYP, TaTpyDocument.TYPE_01_FILE_OTHER, TaTpyDocument.TYPE_02_UNKNOW, TaTpyInformation.ATT_O_DOCUMENTS, false);
	}
	public static List<TaTpyInformation> reqList (Integer entType, Integer entId, Integer typ01) throws Exception{		
		Criterion cri = Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE, entType);	

		if (entId!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_ID, entId));

		if (typ01!=null)
			cri = Restrictions.and(cri, Restrictions.eq(TaTpyInformation.ATT_I_TYPE_01, typ01));

		return TaTpyInformation.DAO.reqList(cri);
	}
	
	
	private static CacheData<ResultPagination> cacheEnt_rs = new CacheData<ResultPagination>(DefTime.TIME_01_00_00_000, 1000);
	public static ResultPagination reqGetInfo (TaAutUser user,  JSONObject json, Integer entTyp, Integer entId, boolean buildDoc) throws Exception {
		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 10);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);
		String 			searchKey	= ToolData.reqStr	(json, "sKey"		, null	);
		Integer			typ01	    = ToolData.reqInt	(json, "typ01"		, TaTpyInformation.TYPE_01_BASE);	
		Integer			typ02	    = ToolData.reqInt	(json, "typ02"		, null);	
		Integer			stat01		= ToolData.reqInt	(json, "stat"		, TaTpyInformation.STAT_VALIDATED);	
		Boolean			reBuild		= ToolData.reqBool	(json, "reBuild"	, false);
		Boolean 		forced		= ToolData.reqBool	(json, "forced"		, false	);
		//other params here


		String keyWord 	= entTyp + "_" + entId + "_" +"_" + begin + "_" + number + "_" + total + "_" + searchKey + "_" + typ01 + "_" + typ02 + "_" + stat01 ;
		ResultPagination rs =	(!reBuild)?cacheEnt_rs.reqData(keyWord): null;

		if(rs==null || forced) {
			Criterion 					cri = Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_ID	, entId);
										cri = Restrictions.and(cri,	Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE	, entTyp));
			
			if (typ01	!= null) 		cri = Restrictions.and( cri, Restrictions.eq(TaTpyInformation.ATT_I_TYPE_01		, typ01));
			if (typ02	!= null) 		cri = Restrictions.and( cri, Restrictions.eq(TaTpyInformation.ATT_I_TYPE_02		, typ02));
			if (stat01	!= null) 		cri = Restrictions.and( cri, Restrictions.eq(TaTpyInformation.ATT_I_STATUS		, stat01));
			
			if (searchKey	!= null) 	{
				searchKey 	= "%" + searchKey + "%";
				cri = Restrictions.and(cri, Restrictions.or(
											Restrictions.like(TaTpyInformation.ATT_T_INFO_01	, searchKey),
											Restrictions.like(TaTpyInformation.ATT_T_INFO_02	, searchKey)));
			};

			List<TaTpyInformation> list	= TaTpyInformation.DAO.reqList(begin, number, Order.desc(TaTpyInformation.ATT_D_DATE_01), cri);		
			
			if (buildDoc && list.size() > 0) {
				doBuildDocuments(list);
			}

			if (total == 0 )	total		= TaTpyInformation.DAO.reqCount(cri).intValue();
			rs								= new ResultPagination(list, total, begin, number);
			cacheEnt_rs.reqPut(keyWord, rs);			
		} 
		return rs;

	}
	
	public static void doListDel(Session sess, Integer entType, Integer entID) throws Exception {
		Collection	<TaTpyInformation>  		relas			= TaTpyInformation.DAO.reqList(sess,	Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE, entType), 
																										Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_ID , entID));		
		TaTpyInformation.DAO.doRemove(sess, relas);
	}
}
