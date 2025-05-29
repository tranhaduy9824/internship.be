package com.hnv.db.nso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefTime;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.api.service.common.ResultPagination;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolData;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.util.CacheData;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.aut.vi.ViAutUserMember;
import com.hnv.db.nso.vi.ViNsoPostCount;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyTranslation;
import com.hnv.def.DefDBExt;

/**
 * TaNsoPost by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_NSO_POST )
public class TaNsoPost extends EntityAbstract<TaNsoPost> {
	public static final double EVAL_MIN = 1.0;
	public static final double EVAL_MAX = 5.0;

	private static final long serialVersionUID 	= 1L;
	private static final int  ENT_TYP			= DefDBExt.ID_TA_NSO_POST;
	
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_TITLE                           =	"T_Title";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	
	public static final String	COL_I_PARENT                      	  =	"I_Parent";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	
	public static final String	COL_T_CONTENT_01                      =	"T_Content_01";
	public static final String	COL_T_CONTENT_02                      =	"T_Content_02";
	public static final String	COL_T_CONTENT_03                      =	"T_Content_03";
	public static final String	COL_T_CONTENT_04                      =	"T_Content_04";
	public static final String	COL_T_CONTENT_05                      =	"T_Content_05";
	public static final String	COL_T_CONTENT_06                      =	"T_Content_06";
	public static final String	COL_T_CONTENT_07                      =	"T_Content_07";
	public static final String	COL_T_CONTENT_08                      =	"T_Content_08";
	public static final String	COL_T_CONTENT_09                      =	"T_Content_09";
	public static final String	COL_T_CONTENT_10                      =	"T_Content_10";
	
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03"; //ids vote
	public static final String	COL_T_INFO_04                         =	"T_Info_04"; //url
	public static final String	COL_T_INFO_05                         =	"T_Info_05"; //comment
	
	public static final String	COL_D_DATE_01                         =	"D_Date_01"; //new
	public static final String	COL_D_DATE_02                         =	"D_Date_02"; //mod
	public static final String	COL_D_DATE_03                         =	"D_Date_03"; //begin
	public static final String	COL_D_DATE_04                         =	"D_Date_04"; //end
	public static final String	COL_D_DATE_05                         =	"D_Date_05";

	public static final String	COL_I_VAL_01                          =	"I_Val_01";  //entType
	public static final String	COL_I_VAL_02                          =	"I_Val_02";  //ent_id
	public static final String	COL_I_VAL_03                          =	"I_Val_03";	 //nbResp
	public static final String	COL_I_VAL_04                          =	"I_Val_04";	 //pointVote
	public static final String	COL_I_VAL_05                          =	"I_Val_05";
	
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";

	public static final String	COL_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	COL_I_AUT_USER_02                     =	"I_Aut_User_02";

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_TITLE                           =	"T_Title";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_PARENT                      	  =	"I_Parent";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_T_CONTENT_01                      =	"T_Content_01";
	public static final String	ATT_T_CONTENT_02                      =	"T_Content_02";
	public static final String	ATT_T_CONTENT_03                      =	"T_Content_03";
	public static final String	ATT_T_CONTENT_04                      =	"T_Content_04";
	public static final String	ATT_T_CONTENT_05                      =	"T_Content_05";
	public static final String	ATT_T_CONTENT_06                      =	"T_Content_06";
	public static final String	ATT_T_CONTENT_07                      =	"T_Content_07";
	public static final String	ATT_T_CONTENT_08                      =	"T_Content_08";
	public static final String	ATT_T_CONTENT_09                      =	"T_Content_09";
	public static final String	ATT_T_CONTENT_10                      =	"T_Content_10";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05"; 
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_D_DATE_05                         =	"D_Date_05";

	public static final String	ATT_I_VAL_01                          =	"I_Val_01";
	public static final String	ATT_I_VAL_02                          =	"I_Val_02";
	public static final String	ATT_I_VAL_03                          =	"I_Val_03";
	public static final String	ATT_I_VAL_04                          =	"I_Val_04";
	public static final String	ATT_I_VAL_05                          =	"I_Val_05";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";

	public static final String	ATT_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	ATT_I_AUT_USER_02                     =	"I_Aut_User_02";

	//---------------------------------------------------------------------------------
	public static final String	ATT_O_AVATAR                          =	"O_Avatar";
	public static final String	ATT_O_USER_AVATAR                     =	"O_User_01_Avatar;";
	public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";
	public static final String	ATT_O_TRANSL                	  	  =	"O_Transl";
	public static final String	ATT_O_CATS                      	  =	"O_Cats";
	
	public static final String	ATT_O_PARENT                     	  =	"O_Parent";
	public static final String	ATT_O_CHILDREN                	  	  =	"O_Children";
	
	public static final String	ATT_O_USER_01                        =	"O_User_01";
	public static final String	ATT_O_USER_02                        =	"O_User_02";
	
	public static final String	ATT_O_CMT_COUNT                      =	"O_Cmt_Count";


	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaNsoPost> 	DAO;

	//---------------------------------------------------------------------------------------------------------------------------------------
	public static final int	STAT_01_UNDEF 		= -1;
	public static final int	STAT_01_NEW 		= 0;  //en attente
	public static final int	STAT_01_ACTIVE 		= 1; 
	public static final int	STAT_01_ACTIVE_02	= 2; //réformé
	public static final int	STAT_01_ACTIVE_03	= 3; //réservé 
	public static final int	STAT_01_REVIEW 		= 5; //en cours de réaffectation
	public static final int	STAT_01_DELETED 	= 10;
	
	public static final int	STAT_02_PRIVATE 				= 10; 
	public static final int	STAT_02_PUBLIC 					= 20;

	public static final int TYPE_01_OFFER					= 100;
	public static final int TYPE_01_BLOG					= 101;
	public static final int TYPE_01_EVAL					= 102;
	public static final int TYPE_01_NEWS					= 103;
	
	public static final int TYPE_01_CMT						= 110;
	
	public static final int VAL_01_BLOG						= 1000;
	public static final int VAL_01_OFFER					= 1001;
	
	
	static{
		DAO = new EntityDAO<TaNsoPost>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaNsoPost.class, RIGHTS, HISTORY, DefDBExt.TA_NSO_POST, DefDBExt.ID_TA_NSO_POST);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_TITLE, nullable = true)
	private	String          T_Title;
      
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
     
	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;
 
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_I_TYPE_03, nullable = true)
	private	Integer         I_Type_03;
    
	@Column(name=COL_T_CONTENT_01, nullable = true)
	private	String          T_Content_01;
 
	@Column(name=COL_T_CONTENT_02, nullable = true)
	private	String          T_Content_02;
 
	@Column(name=COL_T_CONTENT_03, nullable = true)
	private	String          T_Content_03;
 
	@Column(name=COL_T_CONTENT_04, nullable = true)
	private	String          T_Content_04;
 
	@Column(name=COL_T_CONTENT_05, nullable = true)
	private	String          T_Content_05;
 
	@Column(name=COL_T_CONTENT_06, nullable = true)
	private	String          T_Content_06;
 
	@Column(name=COL_T_CONTENT_07, nullable = true)
	private	String          T_Content_07;
 
	@Column(name=COL_T_CONTENT_08, nullable = true)
	private	String          T_Content_08;
 
	@Column(name=COL_T_CONTENT_09, nullable = true)
	private	String          T_Content_09;
 
	@Column(name=COL_T_CONTENT_10, nullable = true)
	private	String          T_Content_10;
 
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
    
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;
    
	@Column(name=COL_D_DATE_03, nullable = true)
	private	Date            D_Date_03;
    
	@Column(name=COL_D_DATE_04, nullable = true)
	private	Date            D_Date_04;
    
	@Column(name=COL_D_DATE_05, nullable = true)
	private	Date            D_Date_05;
    
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer         I_Aut_User_02;

	@Column(name=COL_I_VAL_01, nullable = true)
	private	Integer         I_Val_01;
     
	@Column(name=COL_I_VAL_02, nullable = true)
	private	Integer         I_Val_02;
     
	@Column(name=COL_I_VAL_03, nullable = true)
	private	Integer         I_Val_03;
     
	@Column(name=COL_I_VAL_04, nullable = true)
	private	Integer         I_Val_04;
     
	@Column(name=COL_I_VAL_05, nullable = true)
	private	Integer         I_Val_05;
     
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
	
	//-----------------------Transient Variables-------------------------
	@Transient
	private List<TaTpyDocument>			O_Documents;
	@Transient
	private List<TaTpyTranslation> 		O_Transl;
	@Transient
	private List<TaTpyCategoryEntity>	O_Cats;
	
	@Transient
	private TaNsoPost 		    		O_Parent;
	@Transient
	private List<TaNsoPost> 		    O_Children;

	@Transient
	private Object						O_User_01;
	@Transient
	private String						O_User_02;
	
	@Transient
	private TaTpyDocument				O_Avatar; 
	
	@Transient
	private Object						O_User_01_Avatar;
	
	@Transient
	private Integer						O_Cmt_Count;
	//---------------------Constructeurs-----------------------
	public TaNsoPost(){}

	public TaNsoPost(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);		

		if (this.F_Val_01==null || this.F_Val_01<EVAL_MIN) this.F_Val_01 = EVAL_MIN;
		if (this.F_Val_01>EVAL_MAX) this.F_Val_01 = EVAL_MAX;

		if (this.F_Val_02==null || this.F_Val_02<EVAL_MIN) this.F_Val_02 = EVAL_MIN;
		if (this.F_Val_02>EVAL_MAX) this.F_Val_02 = EVAL_MAX;

		if (this.F_Val_03==null || this.F_Val_03<EVAL_MIN) this.F_Val_03 = EVAL_MIN;
		if (this.F_Val_03>EVAL_MAX) this.F_Val_03 = EVAL_MAX;

		if (this.F_Val_04==null || this.F_Val_04<EVAL_MIN) this.F_Val_04 = EVAL_MIN;
		if (this.F_Val_04>EVAL_MAX) this.F_Val_04 = EVAL_MAX;



		this.F_Val_01              = Math.ceil(this.F_Val_01);
		this.F_Val_02              = Math.ceil(this.F_Val_02);
		this.F_Val_03              = Math.ceil(this.F_Val_03);
		this.F_Val_04              = Math.ceil(this.F_Val_04);


		//doInitDBFlag();
	}





	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaNsoPost ent) {
		if (ent == this) return;
		this.T_Title                = ent.T_Title;
		this.I_Status_01               = ent.I_Status_01;
		this.I_Parent          	 	= ent.I_Parent;
		this.T_Code_01              = ent.T_Code_01;
		this.T_Code_02              = ent.T_Code_02;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.T_Content_01           = ent.T_Content_01;
		this.T_Content_02           = ent.T_Content_02;
		this.T_Content_03           = ent.T_Content_03;
		this.T_Content_04           = ent.T_Content_04;
		this.T_Content_05           = ent.T_Content_05;
		this.T_Content_06           = ent.T_Content_06;
		this.T_Content_07           = ent.T_Content_07;
		this.T_Content_08           = ent.T_Content_08;
		this.T_Content_09           = ent.T_Content_09;
		this.T_Content_10           = ent.T_Content_10;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;
		this.D_Date_05              = ent.D_Date_05;
		this.I_Aut_User_01          = ent.I_Aut_User_01;
		this.I_Aut_User_02          = ent.I_Aut_User_02;
		this.I_Val_01               = ent.I_Val_01;
		this.I_Val_02               = ent.I_Val_02;
		this.I_Val_03               = ent.I_Val_03;
		this.I_Val_04               = ent.I_Val_04;
		this.I_Val_05               = ent.I_Val_05;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaNsoPost)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	public void doAddChild (TaNsoPost child) {
		if (this.O_Children==null) O_Children = new ArrayList<TaNsoPost>();
		O_Children.add(child);
	}
	
	//----------------------------------------------------------------------------

	public static final String SQL_REQ_LIST_COUNT 		= "SELECT count(DISTINCT(a."+ TaNsoPost.COL_I_ID		+"))";

	public static final String SQL_REQ_WHERE 			= " WHERE true"	;	

	public static final String SQL_REQ_WHERE_IDS 		= " a."			+ TaNsoPost.COL_I_ID 		+ " IN (%s)";

	public static final String SQL_REQ_WHERE_TYPE 		= " a."			+ TaNsoPost.COL_I_TYPE_01 	+ " = %d";

	public static final String SQL_REQ_WHERE_TYPE_01 	= " a."			+ TaNsoPost.COL_I_TYPE_01 	+ " IN (%s)";
	
	public static final String SQL_REQ_WHERE_TYPE_02 	= " a."			+ TaNsoPost.COL_I_TYPE_02 	+ " IN (%s)";

	public static final String SQL_REQ_WHERE_STAT 		= " a."			+ TaNsoPost.COL_I_STATUS_01 	+ " = %d";
	
	public static final String SQL_REQ_WHERE_ENTID 	= " a."			+ TaNsoPost.COL_I_VAL_01 	+ " = %d";
	
	public static final String SQL_REQ_WHERE_CAT 		=  " AND cat."	+ TaTpyCategoryEntity.COL_I_TPY_CATEGORY	+ " in %s "	;
	
	public static final String SQL_REQ_WHERE_STAT_NOT_EQUAL 	= " a."	+TaNsoPost.COL_I_STATUS_01 	+ " != %d";

	public static final String SQL_REQ_WHERE_SEARCH= " lower(a."	+ TaNsoPost.COL_T_TITLE + ") like lower('%s')";

	

	//--------------------------------------------------------------------------
//	@Transient
//	private boolean isBuilt_public 	= false;
//	@Transient
//	private boolean isBuilt_manager = false;

	public void doBuildAll(boolean forced, boolean forManager) throws Exception {
		doBuildDocuments(forced);
		doBuildUsers	(forced);
		doBuildTransl	(forced);
	//	doBuildUsers	(forced);
		doBuildCats		(forced);
		doBuildAvatar	(forced);

//		if (forManager && !isBuilt_manager) {
//			isBuilt_manager=true;
//		}

		//		if (forManager && this.I_Type_03.equals(POST_TYPE_BLOG)) {
		//			doBuildPosts (false);
		//		}
	}

	//---khong chung session duoc vi nam trong package khac
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}
	public void doAddDocument(List<TaTpyDocument> docs) {
		if (docs==null || docs.size()==0) return;
		if (this.O_Documents== null) O_Documents =  new ArrayList<TaTpyDocument> ();
		O_Documents.addAll(docs);
	}
	
	public void doBuildUsers(boolean forced) throws Exception{
		if (!forced && (this.O_User_01!=null)){
			return;
		}

		if(I_Aut_User_01 != null && I_Aut_User_01 > 0) {
			TaAutUser u01 	= TaAutUser.DAO.reqEntityByRef(I_Aut_User_01);
			O_User_01 		= u01.reqStr(TaAutUser.ATT_T_LOGIN_01);
			u01.doBuildAvatar(true);
			this.O_User_01_Avatar= u01.req(TaAutUser.ATT_O_AVATAR);
		}
		
		if(I_Aut_User_02 != null && I_Aut_User_02 > 0) {
			TaAutUser u02 	= TaAutUser.DAO.reqEntityByRef(I_Aut_User_02);
			O_User_02 		= u02.reqStr(TaAutUser.ATT_T_LOGIN_01);
		}
	}
	public void doBuildTransl(boolean forced) throws Exception{
		if (!forced && (this.O_Transl!=null)){
			return;
		}
		O_Transl = TaTpyTranslation.reqTpyTranslations(DefDBExt.ID_TA_NSO_POST, I_ID);
	}
	public void doBuildCats(boolean forced) throws Exception {
		if (!forced && O_Cats!=null) return;
		
		this.O_Cats = TaTpyCategoryEntity.DAO.reqList(
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_NSO_POST), 
				Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID		, this.I_ID)
		);
	}
	
	
	//--------------------------------------------------------------------------
	
	//
	//	private void doBuildPosts(boolean forced) throws Exception {		
	//		if(!forced && this.O_Posts!=null) {
	//			return;
	//		}
	//
	//		List<TaNsoPost> taLst		= TaNsoPost.DAO.reqList(0,10,
	//				Restrictions.eq(TaNsoPost.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_NSO_POST),
	//				Restrictions.eq(TaNsoPost.ATT_I_ENTITY_ID, this.I_ID));
	//
	//		for (TaNsoPost p : taLst) {
	//			p.doBuildAll(forced, false);
	//		}
	//
	//		this.O_Posts = taLst;
	//	}


	public void doCalculateEvalTotal(int nbEval) throws Exception {
		if (F_Val_05!=null) return;

		if (this.F_Val_01==null || this.F_Val_01<EVAL_MIN) this.F_Val_01 = EVAL_MIN;
		if (this.F_Val_01>EVAL_MAX) this.F_Val_01 = EVAL_MAX;

		if (this.F_Val_02==null || this.F_Val_02<EVAL_MIN) this.F_Val_02 = EVAL_MIN;
		if (this.F_Val_02>EVAL_MAX) this.F_Val_02 = EVAL_MAX;

		if (this.F_Val_03==null || this.F_Val_03<EVAL_MIN) this.F_Val_03 = EVAL_MIN;
		if (this.F_Val_03>EVAL_MAX) this.F_Val_03 = EVAL_MAX;

		if (this.F_Val_04==null || this.F_Val_04<EVAL_MIN) this.F_Val_04 = EVAL_MIN;
		if (this.F_Val_04>EVAL_MAX) this.F_Val_04 = EVAL_MAX;


		switch(nbEval) {
		case 1: F_Val_05 = F_Val_01;									break;
		case 2: F_Val_05 = F_Val_01+F_Val_02;						break;
		case 3: F_Val_05 = F_Val_01+F_Val_02+F_Val_03;				break;
		case 4: F_Val_05 = F_Val_01+F_Val_02+F_Val_03+F_Val_04;	break;
		}	

		F_Val_05 = (F_Val_05/nbEval);		
		if (F_Val_05>EVAL_MAX) F_Val_05= EVAL_MAX;
		if (F_Val_05<EVAL_MIN) F_Val_05= EVAL_MIN;
	}

	public static List<TaNsoPost> reqNsoPostLstThreadEvaluation(Date begin, Date end) throws Exception {
		List<TaNsoPost> 	list 	= TaNsoPost.DAO.reqList(Restrictions.between(TaNsoPost.ATT_D_DATE_02, begin, end));
		return list;
	}


	public void doCheckVal(int nbEval) {
		if (F_Val_05!=null) return;

		if (this.F_Val_01==null || this.F_Val_01<EVAL_MIN) this.F_Val_01 = EVAL_MIN;
		if (this.F_Val_01>EVAL_MAX) this.F_Val_01 = EVAL_MAX;

		if (this.F_Val_02==null || this.F_Val_02<EVAL_MIN) this.F_Val_02 = EVAL_MIN;
		if (this.F_Val_02>EVAL_MAX) this.F_Val_02 = EVAL_MAX;

		if (this.F_Val_03==null || this.F_Val_03<EVAL_MIN) this.F_Val_03 = EVAL_MIN;
		if (this.F_Val_03>EVAL_MAX) this.F_Val_03 = EVAL_MAX;

		if (this.F_Val_04==null || this.F_Val_04<EVAL_MIN) this.F_Val_04 = EVAL_MIN;
		if (this.F_Val_04>EVAL_MAX) this.F_Val_04 = EVAL_MAX;

		//		if (this.F_Val_05==null || this.F_Val_05<EVAL_MIN) this.F_Val_05 = EVAL_MIN;
		//		if (this.F_Val_05>EVAL_MAX) this.F_Val_05 = EVAL_MAX;

		this.F_Val_01              = Math.ceil(this.F_Val_01);
		this.F_Val_02              = Math.ceil(this.F_Val_02);
		this.F_Val_03              = Math.ceil(this.F_Val_03);
		this.F_Val_04              = Math.ceil(this.F_Val_04);
		//		this.F_Val_05              = Math.ceil(this.F_Val_05);

		switch(nbEval) {
		case 1: F_Val_05 = F_Val_01;									break;
		case 2: F_Val_05 = F_Val_01+F_Val_02;						break;
		case 3: F_Val_05 = F_Val_01+F_Val_02+F_Val_03;				break;
		case 4: F_Val_05 = F_Val_01+F_Val_02+F_Val_03+F_Val_04;	break;
		}	

		//		F_Val_05 = Math.ceil(F_Val_05/nbEval);
		F_Val_05 = F_Val_05/nbEval;

		if (F_Val_05>EVAL_MAX) F_Val_05= EVAL_MAX;
		if (F_Val_05<EVAL_MIN) F_Val_05= EVAL_MIN;
	}

	public double reqEval(int index) {

		switch(index) {
		case 0: return F_Val_01!=null? F_Val_01 : EVAL_MIN;
		case 1: return F_Val_02!=null? F_Val_02 : EVAL_MIN;
		case 2: return F_Val_03!=null? F_Val_03 : EVAL_MIN;
		case 3: return F_Val_04!=null? F_Val_04 : EVAL_MIN;
		case 4: return F_Val_05!=null? F_Val_05 : EVAL_MIN;
		}
		return 0;
	}

	//--------------------------------------------------------------------------------------------------------------------------
	private static CacheData<ResultPagination>	 cacheEnt_rs = new CacheData<ResultPagination>(DefTime.TIME_01_00_00_000, 1000);
	public static ResultPagination reqGetPost (TaAutUser user,  JSONObject json, Integer entTyp, Integer entId) throws Exception {
		if (entTyp==null) entTyp	= ToolData.reqInt	(json, "entTyp"		, null);	
		if (entId==null)  entId 	= ToolData.reqInt 	(json, "entId"		, null);
		Integer 		begin		= ToolData.reqInt	(json, "begin"		, 0	);
		Integer 		number		= ToolData.reqInt	(json, "number"		, 10);
		Integer 		total		= ToolData.reqInt	(json, "total"		, 0	);
		Integer			typ01	    = ToolData.reqInt	(json, "typ01"		, TaNsoPost.TYPE_01_CMT);	
		Integer			typ02	    = ToolData.reqInt	(json, "typ02"		, null);	
		Integer			stat01		= ToolData.reqInt	(json, "stat01"		, TaNsoPost.STAT_01_ACTIVE);	
		Integer			stat02		= ToolData.reqInt	(json, "stat02"		, TaNsoPost.STAT_02_PUBLIC);	
		Integer			nbLevel		= ToolData.reqInt	(json, "nbLevel"	, 1);	
		Boolean			reBuild		= ToolData.reqBool	(json, "reBuild"	, false);
		Boolean 		forced		= ToolData.reqBool	(json, "forced"		, false	);
		//other params here


		String keyWord 	= entTyp + "_" + entId + "_" +"_" + begin + "_" + number + "_" + total + "_" + typ01 + "_" + typ02 + "_" + stat01 + "_" + stat02 +"_"+nbLevel;
		ResultPagination rs =	(!reBuild)?cacheEnt_rs.reqData(keyWord): null;

		if(rs==null) {
			Criterion 				cri = null;
									cri = Restrictions.eq(TaNsoPost.ATT_I_VAL_02	, entId);
			if (entTyp  != null)	cri = Restrictions.and(cri,	Restrictions.eq(TaNsoPost.ATT_I_VAL_01	, entTyp));

			if (typ01	!= null) 	cri = Restrictions.and( cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_01		, typ01));
			if (typ02	!= null) 	cri = Restrictions.and( cri, Restrictions.eq(TaNsoPost.ATT_I_TYPE_02		, typ02));
			if (stat01	!= null) 	cri = Restrictions.and( cri, Restrictions.eq(TaNsoPost.ATT_I_STATUS_01		, stat01));
			if (stat02	!= null) 	cri = Restrictions.and( cri, Restrictions.eq(TaNsoPost.ATT_I_STATUS_02		, stat02));
			
			//only get post with status != draft or != not_validated
			//				cri				= Restrictions.and(cri, Restrictions.or(Restrictions.ne(TaNsoPost.ATT_I_STATUS	, TaNsoPost.POST_STATUS_DRAFT),
			//																		Restrictions.ne(TaNsoPost.ATT_I_STATUS	, TaNsoPost.POST_STATUS_NOT_VALIDATED)));

			Criterion criParent		= Restrictions.isNull(TaNsoPost.ATT_I_PARENT);
			Criterion criLst 		= Restrictions.and(cri, criParent);

			List<TaNsoPost> list	= TaNsoPost.DAO.reqList(begin, number, Order.desc(TaNsoPost.ATT_D_DATE_01), criLst);		

			if(list != null && list.size() > 0 && nbLevel > 1) {
				doBuildPostChildren(list);
			}
			
			//---build avatar
			TaTpyDocument	.reqBuildAvatar( list, DefDBExt.ID_TA_NSO_POST, TaNsoPost.ATT_O_AVATAR);
			
			//---build users + their avatars
			List<ViAutUserMember> lstUsers = TaAutUser		.reqBuildUserMember	(list, TaNsoPost.ATT_I_AUT_USER_01, TaNsoPost.ATT_O_USER_01);
			TaTpyDocument	.reqBuildAvatar( lstUsers, DefDBExt.ID_TA_AUT_USER, ViAutUserMember.ATT_O_AVATAR);
			

			if (total == 0 )	total		= TaNsoPost.DAO.reqCount(criLst).intValue();
			rs								= new ResultPagination(list, total, begin, number);
			cacheEnt_rs.reqPut(keyWord, rs);			
		} 
		return rs;

	}
	
	private static void doBuildPostChildren (List<TaNsoPost> list) throws Exception{
		if (list==null||list.size()==0) return;
		Set<Integer> ids = ToolSet.reqSetInt(list, ATT_I_ID);
		if(!ids.isEmpty()) {
			 new HashMap<Integer, TaNsoPost>();
			
			List<TaNsoPost> children = TaNsoPost.DAO.reqList_In(TaNsoPost.ATT_I_PARENT, ids);

			if(children != null && children.size() > 0) {
				Map<Integer,  EntityAbstract> map = ToolDBEntity.reqTabKeyInt(list, TaNsoPost.ATT_I_ID);

				for(TaNsoPost p : children) {
					TaNsoPost par = (TaNsoPost) map.get(p.I_Parent);
					if (par!=null) par.doAddChild(p);
				}
				
				//---build avatar
				TaTpyDocument	.reqBuildAvatar(children, DefDBExt.ID_TA_NSO_POST, TaNsoPost.ATT_O_AVATAR);
				
				//---build users + their avatars
				List<ViAutUserMember> lstUsers = TaAutUser		.reqBuildUserMember	(children, TaNsoPost.ATT_I_AUT_USER_01, TaNsoPost.ATT_O_USER_01);
				TaTpyDocument	.reqBuildAvatar(lstUsers, DefDBExt.ID_TA_AUT_USER, ViAutUserMember.ATT_O_AVATAR);
			}
		}
	}
	public static void doBuildCmtCount (TaAutUser user,  List<TaNsoPost> lst) throws Exception {
		if (lst==null||lst.size()==0) return;
		Hashtable  				dict 		= ToolDBEntity.reqTabKeyInt(lst, TaNsoPost.ATT_I_ID);
		Set<Integer>			ids			= dict.keySet();
		String					sql 		= String.format(ViNsoPostCount.SQL_COUNT_CMT, ToolSet.reqStr(ids, "", ",", ""));//%d: integer, %s:string %f
		List<ViNsoPostCount> 	lstCount 	= ViNsoPostCount.DAO.reqList(sql);
		
		
		for (ViNsoPostCount ele : lstCount) {
			Integer 	id 		= ele.reqId();
			Integer   	count 	= ele.reqInt(ViNsoPostCount.ATT_I_COUNT);
			TaNsoPost 	p 		= (TaNsoPost) dict.get(id);
			p.O_Cmt_Count 		= count;
		}
	}
	//--------------------------------------------------------------------------
	public static void doBuildListTransl(List<TaNsoPost> list) throws Exception{
		TaTpyTranslation.doBuildTpyTranslations(list, DefDBExt.ID_TA_NSO_POST, ATT_O_TRANSL, false);
	}

	public static void doBuildListUsers(List<TaNsoPost> list, boolean withAvatar) throws Exception{
		Set<Integer> 		ids 	= ToolSet.reqSetInt(list, ATT_I_AUT_USER_01);
		Set<Integer> 		id02 	= ToolSet.reqSetInt(list, ATT_I_AUT_USER_02);
		ids.addAll(id02);

		List<TaAutUser> 	usr 	= TaAutUser.DAO.reqList_In(TaAutUser.ATT_I_ID, ids);
		Hashtable<Integer,EntityAbstract> 	tab = ToolDBEntity.reqTabKeyInt(usr, TaAutUser.ATT_I_ID);
		for (TaNsoPost p : list) {
			if(p.I_Aut_User_01 != null && p.I_Aut_User_01 > 0) {
				TaAutUser u 	= (TaAutUser) tab.get(p.I_Aut_User_01);
				if(u!=null)
					p.O_User_01 = u.reqStr(TaAutUser.ATT_T_LOGIN_01);
			}
			
			if(p.I_Aut_User_02 != null && p.I_Aut_User_02 > 0) {
				TaAutUser u 	= (TaAutUser) tab.get(p.I_Aut_User_02);
				if(u!=null)
					p.O_User_02 = u.reqStr(TaAutUser.ATT_T_LOGIN_01);
			}
		}
		if(withAvatar) TaAutUser.doBuildAvatarForList(usr);
		
	}
	
	public void doBuildAvatar(boolean forced) throws Exception {
		if(this.O_Avatar != null || !forced) return;
		try {
			TaTpyDocument avatar = TaTpyDocument.DAO.reqEntityByValues(
					TaTpyDocument.ATT_I_ENTITY_TYPE	, DefDBExt.ID_TA_NSO_POST,
					TaTpyDocument.ATT_I_ENTITY_ID	, this.I_ID,
					TaTpyDocument.ATT_I_TYPE_01		, TaTpyDocument.TYPE_01_FILE_MEDIA,
					TaTpyDocument.ATT_I_TYPE_02		, TaTpyDocument.TYPE_02_FILE_IMG_AVATAR
			);
			this.O_Avatar = avatar;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void doBuildAvatarForList(List<TaNsoPost>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_NSO_POST, ATT_O_AVATAR);
	}
	
}
