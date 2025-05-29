package com.hnv.db.nso.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.api.service.priv.tpy.ServiceTpyDocument;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolSet;
import com.hnv.common.tool.ToolString;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.mat.TaMatMaterial;
import com.hnv.db.nso.TaNsoOffer;
import com.hnv.db.nso.TaNsoPost;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyTranslation;
import com.hnv.def.DefDBExt;

/**
 * ViNsoPost by H&V SAS //rut gon de tinh evaluation nhanh hon
 */
@Entity
public class ViNsoPostNew extends EntityAbstract<ViNsoPostNew> {
	public static final double EVAL_MIN = TaNsoPost.EVAL_MIN;
	public static final double EVAL_MAX = TaNsoPost.EVAL_MAX;
	private static final long serialVersionUID = 1L;

	private static final int  ENT_TYP				= DefDBExt.ID_TA_NSO_POST;
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
		public static final String	COL_T_INFO_03                         =	"T_Info_03";
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
		public static final String	COL_I_VAL_04                          =	"I_Val_04";
		public static final String	COL_I_VAL_05                          =	"I_Val_05";
		
		public static final String	COL_F_VAL_01                          =	"F_Val_01";
		public static final String	COL_F_VAL_02                          =	"F_Val_02";
		public static final String	COL_F_VAL_03                          =	"F_Val_03";
		public static final String	COL_F_VAL_04                          =	"F_Val_04";
		public static final String	COL_F_VAL_05                          =	"F_Val_05";

		public static final String	COL_I_AUT_USER_01                     =	"I_Aut_User_01";
		public static final String	COL_I_AUT_USER_02                     =	"I_Aut_User_02";

		
		public static final String	COL_T_AVATAR                          =	"T_Avatar";
		public static final String	COL_T_AVATAR_USER                     =	"T_Avatar_User";
		public static final String	COL_T_NAME_USER                       =	"T_Name_User";
		public static final String	COL_T_AVATAR_TYPE                     =	"T_Avatar_Type";
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

		public static final String	ATT_T_AVATAR                          =	"T_Avatar";
		public static final String	ATT_T_AVATAR_USER                     =	"T_Avatar_User";
		public static final String	ATT_T_NAME_USER                       =	"T_Name_User";
		public static final String	ATT_T_TYPE_AVATAR                     =	"T_Type_Avatar";

		//---------------------------------------------------------------------------------
		public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";

		public static final String	ATT_O_TRANSLATIONS                	  =	"O_Translations";


		
		public static final String	ATT_O_AVATAR                          =	"O_Avatar";
		public static final String	ATT_O_USER_AVATAR                     =	"O_User_01_Avatar;";
		public static final String	ATT_O_TRANSL                	  	  =	"O_Transl";
		public static final String	ATT_O_CATS                      	  =	"O_Cats";

		public static final String	ATT_O_PARENT                     	  =	"O_Parent";
		public static final String	ATT_O_CHILDREN                	  	  =	"O_Children";

		public static final String	ATT_O_USER_01                        =	"O_User_01";
		public static final String	ATT_O_USER_02                        =	"O_User_02";

		public static final String	ATT_O_CMT_COUNT                      =	"O_Cmt_Count";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static	final boolean				API_CACHE 	= false;
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViNsoPostNew> 	DAO;

	public static final int POST_STATUS_DRAFT				= 0;
	public static final int POST_STATUS_NOT_VALIDATED		= 1;
	public static final int POST_STATUS_VALIDATED			= 2;
	public static final int POST_STATUS_REPORTED			= 3;
	public static final int POST_STATUS_DELETED				= 4;

	public static final int POST_TYPE_VIDEO					= 100;
	public static final int POST_TYPE_NEWS					= 101;
	public static final int POST_TYPE_FENGSHUI_SUGGES		= 200;
	public static final int POST_TYPE_FENGSHUI_OVERVIEW		= 201;
	public static final int POST_TYPE_FENGSHUI_HOUSE		= 202;
	public static final int POST_TYPE_FENGSHUI_OFFICE		= 203;
	public static final int POST_TYPE_FENGSHUI_YEAR_OLD		= 204;

	//---------------------------------------------------------------------------------------------------------------------------------------

	static{
		DAO = new EntityDAO<ViNsoPostNew>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViNsoPostNew.class, RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

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

	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;

	@Column(name=COL_T_TITLE, nullable = true)
	private	String          T_Title;

	@Column(name=COL_T_CONTENT_01, nullable = true)
	private	String          T_Content_01;

	//	@Column(name=COL_T_CONTENT_02, nullable = true)
	//	private	String          T_Content_02;
	// 
	//	@Column(name=COL_T_CONTENT_03, nullable = true)
	//	private	String          T_Content_03;
	// 
	//	@Column(name=COL_T_CONTENT_04, nullable = true)
	//	private	String          T_Content_04;
	// 
	//	@Column(name=COL_T_CONTENT_05, nullable = true)
	//	private	String          T_Content_05;

	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;

	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;

	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;

//	@Column(name=COL_T_COMMENT, nullable = true)
//	private	String          T_Comment;
	
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;

	@Column(name=COL_I_VAL_03, nullable = true)
	private	Integer         I_Val_03; //num response
//	
//	@Column(name=COL_I_VAL_01, nullable = true)
//	private	Integer         I_Entity_Type;
//
//	@Column(name=COL_I_VAL_02, nullable = true)
//	private	Integer         I_Entity_ID;
//
//	@Column(name=COL_T_ENTITY_TITLE, nullable = true)
//	private	String          T_Entity_Title;

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;

	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;

//	@Column(name=COL_T_PROPERTY_01, nullable = true)
//	private	String          T_Property_01;

//	@Column(name=COL_T_PROPERTY_02, nullable = true)
//	private	String          T_Property_02;

//	@Column(name=COL_T_PROPERTY_03, nullable = true)
//	private	String          T_Property_03;

//	@Column(name=COL_T_PROPERTY_04, nullable = true)
//	private	String          T_Property_04;
	
	@Column(name=COL_T_AVATAR, nullable = true)
	private	String          T_Avatar;
	
	@Column(name=COL_T_AVATAR_USER, nullable = true)
	private	String          T_Avatar_User;
	
	@Column(name=COL_T_NAME_USER, nullable = true)
	private	String          T_Name_User;
	
	@Column(name=COL_T_AVATAR_TYPE, nullable = true)
	private	Integer         T_Avatar_Type;
	//-----------------------Transient Variables-------------------------
	@Transient
	private List<TaTpyDocument>			O_Documents;
	
	@Transient
	private List<TaTpyTranslation>     	O_Translations;
	
	@Transient
	private TaAutUser					O_User_01;

	
	//---------------------Constructeurs-----------------------
	private ViNsoPostNew(){}

	public ViNsoPostNew(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}



	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(ViNsoPostNew ent) {
		if (ent == this) return;
		this.F_Val_01              = ent.F_Val_01;
		this.F_Val_02              = ent.F_Val_02;
		this.F_Val_03              = ent.F_Val_03;
		this.F_Val_04              = ent.F_Val_04;
		this.F_Val_05              = ent.F_Val_05;

		this.I_Status_01           = ent.I_Status_01;
		this.I_Status_02           = ent.I_Status_02;
//		this.T_Comment              = ent.T_Comment;

		this.D_Date_01             = ent.D_Date_01;
		this.D_Date_02             = ent.D_Date_02;

		this.I_Parent               = ent.I_Parent;
//		this.I_Entity_Type          = ent.I_Entity_Type;
//		this.I_Entity_ID            = ent.I_Entity_ID;
//		this.T_Entity_Title         = ent.T_Entity_Title;

		this.I_Aut_User_01          = ent.I_Aut_User_01;

		this.I_Type_01            	= ent.I_Type_01;
		this.I_Type_02            	= ent.I_Type_02;


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((ViNsoPostNew)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	public static void doBuildListUsers(List<ViNsoPostNew> list) throws Exception{
		Set<Integer> 		ids 	= ToolSet.reqSetInt(list, ATT_I_AUT_USER_01);
		Set<Integer> 		id02 	= ToolSet.reqSetInt(list, ATT_I_AUT_USER_02);
		ids.addAll(id02);
		
		List<TaAutUser> 	usr 	= TaAutUser.DAO.reqList_In(TaAutUser.ATT_I_ID, ids);
		Hashtable<Integer,EntityAbstract> 	tab = ToolDBEntity.reqTabKeyInt(usr, TaAutUser.ATT_I_ID);
		for (ViNsoPostNew p : list) {
			if(p.I_Aut_User_01 != null && p.I_Aut_User_01 > 0) {
				TaAutUser u 	= (TaAutUser) tab.get(p.I_Aut_User_01);
				if(u!=null) {
					u.doBuildDocuments(true);
					p.O_User_01 = u;
				}		
			}
		}
	}
	
	public static void doBuildListDocuments(List<ViNsoPostNew> list) throws Exception{
		for (ViNsoPostNew p : list) {
			if(p.O_Documents == null) {
				p.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, p.I_ID, null, null);
			}
		}
	}
	

	//-----------------------------------------------------------------------
	//-------------------------------------------------------------
	private static final String SQL_REQ_LIST_SELECT		= "SELECT"
				+   " a." 			+ TaNsoPost.COL_I_ID						+ " AS " + COL_I_ID 			+ "," 
				+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d." + TaTpyDocument.COL_T_INFO_05 	+") AS " + COL_T_AVATAR + ","
				+	"d."	        + TaTpyDocument.COL_I_TYPE_02 				+ " AS " + COL_T_AVATAR_TYPE    + ","
				+	"null"														+ " AS " + COL_T_AVATAR_USER	+ ","
				+ 	"null"														+ " AS " + COL_T_NAME_USER		+ ","	
				+   " a." 			+ TaNsoPost.COL_T_CODE_01						+ " AS " + COL_T_CODE_01 			+ ","
				+   " a." 			+ TaNsoPost.COL_T_TITLE						+ " AS " + COL_T_TITLE 			+ ","
				+   " a." 			+ TaNsoPost.COL_T_CONTENT_01				+ " AS " + COL_T_CONTENT_01		+ ","
				//				+   " NULL" 													+ " AS " + COL_T_CONTENT_02 	+ ","
				//				+   " NULL" 													+ " AS " + COL_T_CONTENT_03 	+ ","
				//				+   " NULL" 													+ " AS " + COL_T_CONTENT_04 	+ ","
				//				+   " NULL" 													+ " AS " + COL_T_CONTENT_05 	+ ","
				+   " a." 			+ TaNsoPost.COL_D_DATE_02					+ " AS " + COL_D_DATE_02 		+ ","
				+   " a." 			+ TaNsoPost.COL_D_DATE_01					+ " AS " + COL_D_DATE_01		+ ","
				
				+   " a." 			+ TaNsoPost.COL_I_STATUS_01					+ " AS " + COL_I_STATUS_01 		+ ","
				+   " a." 			+ TaNsoPost.COL_I_STATUS_02					+ " AS " + COL_I_STATUS_02 		+ ","

				+   " a." 		    + TaNsoPost.COL_I_TYPE_01					+ " AS " + COL_I_TYPE_01 		+ ","
				+   " a." 		    + TaNsoPost.COL_I_TYPE_02					+ " AS " + COL_I_TYPE_02 		+ ","

				+   " NULL" 													+ " AS " + COL_I_VAL_03 		+ ","
				+   " NULL" 													+ " AS " + COL_I_PARENT 		+ ","
				+   " a." 			+ TaNsoPost.COL_I_VAL_01					+ " AS " + COL_I_VAL_01 	+ ","
				+   " a." 			+ TaNsoPost.COL_I_VAL_02					+ " AS " + COL_I_VAL_02 		+ ","
				+   " a." 			+ TaNsoPost.COL_I_AUT_USER_01				+ " AS " + COL_I_AUT_USER_01 		+ ","

				//				+   " p." 			+ ViNsoPost.COL_T_ENTITY_TITLE				+ " AS " + COL_T_ENTITY_TITLE 	+ ","
//				+   " NULL" 													+ " AS " + COL_T_ENTITY_TITLE 		+ ","

				+	" a."			+ TaNsoPost.COL_F_VAL_01					+ " AS " + COL_F_VAL_01		+ ","
				+	" a."			+ TaNsoPost.COL_F_VAL_02					+ " AS " + COL_F_VAL_02		+ ","
				+	" a."			+ TaNsoPost.COL_F_VAL_03					+ " AS " + COL_F_VAL_03		+ ","
				+	" a."			+ TaNsoPost.COL_F_VAL_04					+ " AS " + COL_F_VAL_04		+ ","
				+	" a."			+ TaNsoPost.COL_F_VAL_05					+ " AS " + COL_F_VAL_05		;//+ ","

	//				+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d." + TaTpyDocument.COL_T_PATH_01 	+") AS " + COL_T_AVATAR;

	//-------------------------------------------------------------
		private static final String SQL_REQ_LIST_SELECT_LST_BY_IDS		= "SELECT"
					+   " a." 			+ TaNsoPost.COL_I_ID						+ " AS " + COL_I_ID 			+ "," 
					+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d." + TaTpyDocument.COL_T_INFO_05 	+") AS " + COL_T_AVATAR + ","
					+	"COALESCE(d2."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d2." + TaTpyDocument.COL_T_INFO_05 +") AS " + COL_T_AVATAR_USER	+ ","
					+   "user." 		+ TaAutUser.COL_T_LOGIN_01					+ " AS " + COL_T_NAME_USER 		+ ","
					+	"d."	        + TaTpyDocument.COL_I_TYPE_02 				+ " AS " + COL_T_AVATAR_TYPE    + ","
					+   " a." 			+ TaNsoPost.COL_T_CODE_01						+ " AS " + COL_T_CODE_01 			+ ","
					+   " a." 			+ TaNsoPost.COL_T_TITLE						+ " AS " + COL_T_TITLE 			+ ","
					+   " a." 			+ TaNsoPost.COL_T_CONTENT_01				+ " AS " + COL_T_CONTENT_01		+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_02 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_03 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_04 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_05 	+ ","
					+   " a." 			+ TaNsoPost.COL_D_DATE_02					+ " AS " + COL_D_DATE_02 		+ ","
					+   " a." 			+ TaNsoPost.COL_D_DATE_01					+ " AS " + COL_D_DATE_01		+ ","
					
					+   " a." 			+ TaNsoPost.COL_I_STATUS_01					+ " AS " + COL_I_STATUS_01 		+ ","
					+   " a." 			+ TaNsoPost.COL_I_STATUS_02					+ " AS " + COL_I_STATUS_02 		+ ","

					+   " a." 		    + TaNsoPost.COL_I_TYPE_01					+ " AS " + COL_I_TYPE_01 		+ ","
					+   " a." 		    + TaNsoPost.COL_I_TYPE_02					+ " AS " + COL_I_TYPE_02 		+ ","

					+   " NULL" 													+ " AS " + COL_I_VAL_03 		+ ","
					+   " NULL" 													+ " AS " + COL_I_PARENT 		+ ","
					+   " a." 			+ TaNsoPost.COL_I_VAL_01					+ " AS " + COL_I_VAL_01 	+ ","
					+   " a." 			+ TaNsoPost.COL_I_VAL_02					+ " AS " + COL_I_VAL_02 		+ ","
					+   " a." 			+ TaNsoPost.COL_I_AUT_USER_01				+ " AS " + COL_I_AUT_USER_01 		+ ","

					//				+   " p." 			+ ViNsoPost.COL_T_ENTITY_TITLE				+ " AS " + COL_T_ENTITY_TITLE 	+ ","
//					+   " NULL" 													+ " AS " + COL_T_ENTITY_TITLE 		+ ","

					+	" a."			+ TaNsoPost.COL_F_VAL_01					+ " AS " + COL_F_VAL_01		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_02					+ " AS " + COL_F_VAL_02		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_03					+ " AS " + COL_F_VAL_03		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_04					+ " AS " + COL_F_VAL_04		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_05					+ " AS " + COL_F_VAL_05		;//+ ","

	//-----------------------------------------------------------------------
		//-------------------------------------------------------------
		private static final String SQL_REQ_LIST_SELECT_PRJ		= "SELECT"
					+   " a." 			+ TaNsoPost.COL_I_ID						+ " AS " + COL_I_ID 			+ "," 
					+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d." + TaTpyDocument.COL_T_INFO_05 	+") AS " + COL_T_AVATAR + ","
					+	"COALESCE(d2."	+ TaTpyDocument.COL_T_INFO_03 				+ ", d2." + TaTpyDocument.COL_T_INFO_05 +") AS " +COL_T_AVATAR_USER	+ ","
					+   "user." 		+ TaAutUser.COL_T_LOGIN_01					+ " AS " + COL_T_NAME_USER 		+ ","
					+	"d."	        + TaTpyDocument.COL_I_TYPE_02 				+ " AS " + COL_T_AVATAR_TYPE    + ","
					+   " a." 			+ TaNsoPost.COL_T_CODE_01					+ " AS " + COL_T_CODE_01 		+ ","
					+   " a." 			+ TaNsoPost.COL_T_TITLE						+ " AS " + COL_T_TITLE 			+ ","
					+   " a." 			+ TaNsoPost.COL_T_CONTENT_01				+ " AS " + COL_T_CONTENT_01		+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_02 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_03 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_04 	+ ","
					//				+   " NULL" 													+ " AS " + COL_T_CONTENT_05 	+ ","
					+   " a." 			+ TaNsoPost.COL_D_DATE_02					+ " AS " + COL_D_DATE_02 		+ ","
					+   " a." 			+ TaNsoPost.COL_D_DATE_01					+ " AS " + COL_D_DATE_01		+ ","
					+   " a." 			+ TaNsoPost.COL_I_TYPE_01					+ " AS " + COL_I_TYPE_01		+ ","
					+   " a." 			+ TaNsoPost.COL_I_STATUS_01					+ " AS " + COL_I_STATUS_01 		+ ","
//					+   " a." 		    + TaNsoPost.COL_T_COMMENT					+ " AS " + COL_T_COMMENT 		+ ","
//
//					+   " a." 			+ TaNsoPost.COL_T_PROPERTY_04				+ " AS " +COL_T_PROPERTY_04 	+ ","
					
					+   " a." 		    + TaNsoPost.COL_I_TYPE_01					+ " AS " + COL_I_TYPE_01 		+ ","
					+   " a." 		    + TaNsoPost.COL_I_TYPE_02					+ " AS " + COL_I_TYPE_02 		+ ","

					+   " NULL" 													+ " AS " + COL_I_VAL_03 		+ ","
					+   " NULL" 													+ " AS " + COL_I_PARENT 		+ ","
					+   " a." 			+ TaNsoPost.COL_I_VAL_01					+ " AS " + COL_I_VAL_01 	+ ","
					+   " a." 			+ TaNsoPost.COL_I_VAL_02					+ " AS " + COL_I_VAL_02 		+ ","
					+   " a." 			+ TaNsoPost.COL_I_AUT_USER_01				+ " AS " + COL_I_AUT_USER_01 		+ ","

					//				+   " p." 			+ ViNsoPost.COL_T_ENTITY_TITLE				+ " AS " + COL_T_ENTITY_TITLE 	+ ","
//					+   " NULL" 													+ " AS " + COL_T_ENTITY_TITLE 		+ ","

					+	" a."			+ TaNsoPost.COL_F_VAL_01					+ " AS " + COL_F_VAL_01		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_02					+ " AS " + COL_F_VAL_02		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_03					+ " AS " + COL_F_VAL_03		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_04					+ " AS " + COL_F_VAL_04		+ ","
					+	" a."			+ TaNsoPost.COL_F_VAL_05					+ " AS " + COL_F_VAL_05		;//+ ","


	private static final String SQL_REQ_LIST_SELECT_COUNT 	= "SELECT count(*)";


	private static final String SQL_REQ_LIST_FROM			= " FROM "		+ DefDBExt.TA_NSO_POST	+ " a";
	private static final String SQL_REQ_LIST_FROM_SUB		= " FROM (SELECT * ";

	private static final String SQL_REQ_LIST_JOIN			= " LEFT JOIN "+ DefDBExt.TA_TPY_DOCUMENT	+ " d"	
			+	" ON (a."		+ TaNsoPost.COL_I_ID						+ " = d."	+ TaTpyDocument.COL_I_ENTITY_ID
			+	" AND d."		+ TaTpyDocument.COL_I_ENTITY_TYPE 			+ " = " 	+ DefDBExt.ID_TA_NSO_POST
			+	" AND d."		+ TaTpyDocument.COL_I_PRIORITY 			    + " = 1 "
			+	" AND (d."		+ TaTpyDocument.ATT_I_TYPE_02 				+ " = " 	+ TaTpyDocument.TYPE_02_FILE_IMG
			+	" OR d."		+ TaTpyDocument.ATT_I_TYPE_02 				+ " = " 	+ TaTpyDocument.TYPE_02_FILE_IMG_AVATAR			+"))";	
	
	private static final String SQL_REQ_LIST_JOIN_PRJ			= " LEFT JOIN "+ DefDBExt.TA_TPY_DOCUMENT	+ " d"	
			+	" ON (a."		+ TaNsoPost.COL_I_ID						+ " = d."	+ TaTpyDocument.COL_I_ENTITY_ID
			+	" AND d."		+ TaTpyDocument.COL_I_ENTITY_TYPE 			+ " = " 	+ DefDBExt.ID_TA_NSO_POST
			+	" AND d."		+ TaTpyDocument.ATT_I_TYPE_01 				+ " = " 	+ TaTpyDocument.TYPE_01_FILE_MEDIA +")";	
	
	private static final String SQL_REQ_LIST_JOIN_USER_INFO 			= " INNER JOIN "+ DefDBExt.TA_AUT_USER	+ " user"
			+	" ON (a."		+ TaNsoPost.COL_I_AUT_USER_01					+ " = user."+ TaAutUser.COL_I_ID
			+ 	")";
	
	private static final String SQL_REQ_LIST_JOIN_USER_AVATAR			= " LEFT JOIN "	+ DefDBExt.TA_TPY_DOCUMENT	+ " d2"
			+	" ON (user."	+ TaAutUser.COL_I_PER_PERSON				+ " = d2."	+ TaTpyDocument.COL_I_ENTITY_ID
			+	" AND d2."		+ TaTpyDocument.COL_I_ENTITY_TYPE 			+ " = " 	+ DefDBExt.ID_TA_PER_PERSON
			+	" AND d2."		+ TaTpyDocument.COL_I_TYPE_01 				+ " = " 	+ TaTpyDocument.TYPE_01_FILE_MEDIA
			+	" AND d2."		+ TaTpyDocument.COL_I_TYPE_02 				+ " = " 	+ TaTpyDocument.TYPE_02_FILE_IMG
			+ 	")";

	private	static final String SQL_REQ_LIST_JOIN_CAT		= " INNER JOIN " 	+ DefDBExt.TA_TPY_CATEGORY_ENTITY 		+ " cat"
			+ " ON  cat." 		+ TaTpyCategoryEntity.COL_I_ENTITY_TYPE + " = "	+ DefDBExt.ID_TA_TPY_CATEGORY			
			+ " AND cat."		+ TaTpyCategoryEntity.COL_I_ID 			+ " = (Select "		
																		+ TaTpyCategoryEntity.COL_I_ID 		+ " From "	+  DefDBExt.TA_TPY_CATEGORY_ENTITY
																		+ " tmp where tmp."					+ TaTpyCategoryEntity.COL_I_ENTITY_ID + " = a." + TaNsoPost	.COL_I_ID		
																		+ " %s "
																		+ " limit 1)";
	//	private static final String SQL_REQ_LIST_JOIN_AREA  = "LEFT JOIN " 		+ DefDBExt.TA_NSO_AREA 		+ " p"
	//			+	" ON (a."		+ TaNsoPost.COL_I_VAL_02		+ " = p."	+ TaNsoArea.COL_I_ID
	//			+	" AND a."		+ TaNsoPost.COL_I_VAL_01	+ " = "		+ DefDBExt.ID_TA_NSO_AREA	+")";
	//
	//	private static final String SQL_REQ_LIST_JOIN_OFFER  = "LEFT JOIN " 	+ DefDBExt.TA_NSO_OFFER 	+ " p"
	//			+	" ON (a."		+ TaNsoPost.COL_I_VAL_02		+ " = p."	+ TaNsoOffer.COL_I_ID
	//			+	" AND a."		+ TaNsoPost.COL_I_VAL_01	+ " = "		+ DefDBExt.ID_TA_NSO_OFFER	+")";
	//
	//	private static final String SQL_REQ_LIST_JOIN_PLAN  = "LEFT JOIN " 		+ DefDBExt.TA_NSO_PLAN 		+ " p"
	//			+	" ON (a."		+ TaNsoPost.COL_I_VAL_02		+ " = p."	+ TaNsoPlan.COL_I_ID
	//			+	" AND a."		+ TaNsoPost.COL_I_VAL_01	+ " = "		+ DefDBExt.ID_TA_NSO_PLAN	+")";



	private static String COL_EVAL	= "D_Eval";
	private static String COL_NOW	= "D_Now";
	private static final String SQL_REQ_WHERE_THREAD_EVALUATION	= " a."		+ TaNsoPost.ATT_I_PARENT 	+ " IS NULL"
			+ " AND a." + TaNsoPost.ATT_I_STATUS_01 	+ " = " 		+ TaNsoPost.STAT_01_ACTIVE
			+ " AND (a."+ TaNsoPost.ATT_D_DATE_02	+ " BETWEEN :"	+	COL_EVAL + " AND :" + COL_NOW + ")";
	//	+ " AND (a."+ TaNsoPost.ATT_D_DATE_MOD	+ " BETWEEN '%tF' AND '%tF')";


	private static final String SQL_REQ_ORDER		= " ORDER BY %s %s"	;

	private static final String SQL_REQ_ORDER_LEVEL_01		= " ORDER BY %s %s, %s %s";

	private static final String SQL_REQ_ORDER_LEVEL_02		= " ORDER BY %s %s, %s %s, %s %s";

	private static final String SQL_REQ_WHERE_USER 	= " a." + TaNsoPost.COL_I_AUT_USER_01 + " = %d";

	private static String reqRestriction(String searchKey) {			
		String restr = "";

		restr = String.format(TaNsoPost.SQL_REQ_WHERE_SEARCH, searchKey, searchKey);

		if (restr.length()==0) return null;
		else restr = "(" + restr + ")";
		return restr;
	}

	//=====================================================================================================
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}

	//=====================================================================================================

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
	
	//-----------------------------------------------------------------------------------
	private static String reqNsoPostLstByIdsSql(Set<Integer> ids) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT_LST_BY_IDS;

		sql += SQL_REQ_LIST_FROM_SUB;
		sql += SQL_REQ_LIST_FROM;
		sql += TaNsoPost.SQL_REQ_WHERE;

		sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_IDS, ToolString.reqStringFromCollection(ids, ",")); 
		sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT_NOT_EQUAL, TaNsoPost.STAT_01_DELETED);
		sql += ") a";
		sql += SQL_REQ_LIST_JOIN;
		sql += SQL_REQ_LIST_JOIN_USER_INFO;
		sql += SQL_REQ_LIST_JOIN_USER_AVATAR;


		String orderCol = ViNsoPostNew.ATT_D_DATE_02;
		String orderDir	= " DESC";
		sql += String.format(SQL_REQ_ORDER, orderCol, orderDir);

		return sql;
	}

	public static List<ViNsoPostNew> reqNsoPostLstByIds(Set<Integer> ids, Integer begin, Integer nbLine) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String 					sql 	= reqNsoPostLstByIdsSql(ids);		
		list = ViNsoPostNew.DAO.reqList(begin, nbLine, sql);
			
		return list;
	}
	//---------------------------------------------------------------------------------
	public static List<ViNsoPostNew> reqNsoPostLstByUserId(Integer userId) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String sql = reqNsoPostLstByUserIdSql(userId);		
		list = ViNsoPostNew.DAO.reqList(sql);
		return list;
	}

	//---------------------------------------------------------------------------------
	public static List<ViNsoPostNew> reqNsoPostLstByUserId(Integer userId, Integer begin, Integer nbRes) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String sql = reqNsoPostLstByUserIdSql(userId);		
		list = ViNsoPostNew.DAO.reqList(begin, nbRes, sql);
		return list;
	}

	//-----------------------------------------------------------------------------------
	private static String reqNsoPostLstByUserIdSql(Integer userId) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT;

		sql += SQL_REQ_LIST_FROM;

		//		sql += SQL_REQ_LIST_JOIN;

		sql += TaNsoPost.SQL_REQ_WHERE;

		sql += " AND " + String.format(SQL_REQ_WHERE_USER, userId);
		sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT_NOT_EQUAL, TaNsoPost.STAT_01_DELETED);


		String orderCol = ViNsoPostNew.ATT_D_DATE_02;
		String orderDir	= " DESC";
		sql += String.format(SQL_REQ_ORDER, orderCol, orderDir);

		return sql;
	}

	//====================================================================	
	public static Integer reqNsoPostLstByUserCount(Integer userId) throws Exception {
		String sql = reqNsoPostLstGridSqlCount( userId);			
		Integer count = ViNsoPostNew.DAO.reqCount(sql).intValue();

		return count;
	}

	//----------------------------------------------------------------------
	private static String reqNsoPostLstGridSqlCount(Integer userId) throws Exception {
		String sql	= TaNsoPost.SQL_REQ_LIST_COUNT;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN;

		sql += TaNsoPost.SQL_REQ_WHERE;

		sql += " AND " + String.format(SQL_REQ_WHERE_USER, userId);
		sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT_NOT_EQUAL, TaNsoPost.STAT_01_DELETED);

		return sql;
	}

	//----------------------------------------------------------------------
	public static List<ViNsoPostNew> reqNsoPostLstThreadEvaluation(Date eval, Date now) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String sql = reqNsoPostLstGridSqlThreadEvaluation();		
		list = ViNsoPostNew.DAO.reqList(sql, COL_EVAL, eval, COL_NOW, now);

		return list;
	}

	//----------------------------------------------------------------------
	private static String reqNsoPostLstGridSqlThreadEvaluation() throws Exception {
		String sql	= SQL_REQ_LIST_SELECT;

		sql += SQL_REQ_LIST_FROM;

		sql += TaNsoPost.SQL_REQ_WHERE;

		sql += " AND " + SQL_REQ_WHERE_THREAD_EVALUATION;

		return sql;
	}

	public Integer reqID() {
		return this.I_ID;
	}

	public static List<ViNsoPostNew> reqLstPost(Integer begin, Integer nbRes, Integer type, String multiType,  Integer stat, Integer style,  Set<Integer> catIds, String langId) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String 				sql 	= reqNsoPostSql(type, multiType, stat, style, catIds, langId);		
		list = ViNsoPostNew.DAO.reqList(begin, nbRes, sql);

//		for(ViNsoPost post : list){
//			post.doBuildDocuments(true);
//		}

		return list;
	}

	//----------------------------------------------------------------
	private static String reqNsoPostSql(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}
		
		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);

		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);
		
		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		if(style != null && style != 0) {
			String orderCol01 	= null;
			String orderCol02 	= null;
			String orderCol03 	= null;
			String orderDir		= "DESC";

			switch(style){
			case 1: {									//latest
				orderCol01 = COL_D_DATE_02; 	
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;		
			}
			case 2: {									//hot
				orderCol01 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;
			}
			case 3: {									//1st featured, 2nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 4: {									//1st featured, 2nd hot
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 5: {									//1st featured, 2nd hot, 3nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				orderCol03 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_02, orderCol01, orderDir, orderCol02, orderDir, orderCol03, orderDir);
				break;
			}
			}

			//				if (orderCol01!=null){
			//					sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
			//				}
		}

		return sql;
	}

	public static Integer reqLstPostCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId) throws Exception {
		String sql = reqLstPostCountSqlCount(type, multiType, stat, style, catIds, langId);			
		Integer count = ViNsoPostNew.DAO.reqCount(sql).intValue();

		return count;
	}

	private static String reqLstPostCountSqlCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId) throws Exception {
		String sql	= TaNsoPost.SQL_REQ_LIST_COUNT;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}

		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);
		
		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);

		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		return sql;
	}

	public static void doBuildTranslations(List<ViNsoPostNew> list) throws Exception{
//		TranslationTool.doBuildTpyTranslations(list, DefDBExt.ID_TA_NSO_POST, ViNsoPost.ATT_O_TRANSLATIONS, false);
	}
	
	//----------------------------------------------------------------

	
	public static List<ViNsoPostNew> reqLstPostPrj(Integer begin, Integer nbRes, Integer type, String multiType,  Integer stat, Integer style,  Set<Integer> catIds, String langId, Integer entId) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String sql = reqNsoPostPrjSql(type, multiType, stat, style, catIds, langId, entId);		
		list = ViNsoPostNew.DAO.reqList(begin, nbRes, sql);

		return list;
	}

	//----------------------------------------------------------------
	private static String reqNsoPostPrjSql(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT_PRJ;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN_PRJ;
		
		sql += SQL_REQ_LIST_JOIN_USER_INFO;
		
		sql += SQL_REQ_LIST_JOIN_USER_AVATAR;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}
		
		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);

		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);
		
		if(entId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_ENTID, entId);
		
		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		if(style != null && style != 0) {
			String orderCol01 	= null;
			String orderCol02 	= null;
			String orderCol03 	= null;
			String orderDir		= "DESC";

			switch(style){
			case 1: {									//latest
				orderCol01 = COL_D_DATE_02; 	
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;		
			}
			case 2: {									//hot
				orderCol01 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;
			}
			case 3: {									//1st featured, 2nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 4: {									//1st featured, 2nd hot
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 5: {									//1st featured, 2nd hot, 3nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				orderCol03 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_02, orderCol01, orderDir, orderCol02, orderDir, orderCol03, orderDir);
				break;
			}
			}

			//				if (orderCol01!=null){
			//					sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
			//				}
		}

		return sql;
	}

	public static Integer reqLstPostPrjCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql = reqLstPostPrjCountSqlCount(type, multiType, stat, style, catIds, langId, entId);			
		Integer count = ViNsoPostNew.DAO.reqCount(sql).intValue();

		return count;
	}

	private static String reqLstPostPrjCountSqlCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql	= TaNsoPost.SQL_REQ_LIST_COUNT;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN_PRJ;
		
		sql += SQL_REQ_LIST_JOIN_USER_INFO;
		
		sql += SQL_REQ_LIST_JOIN_USER_AVATAR;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}

		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);
		
		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);
		
		if(entId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_ENTID, entId);

		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		return sql;
	}
	
	public static List<ViNsoPostNew> reqLstNewsPrj(Integer begin, Integer nbRes, Integer type, String multiType,  Integer stat, Integer style,  Set<Integer> catIds, String langId, Integer entId) throws Exception {
		List<ViNsoPostNew> 	list 	= new ArrayList<ViNsoPostNew>();		
		String sql = reqNsoNewsPrjSql(type, multiType, stat, style, catIds, langId, entId);		
		list = ViNsoPostNew.DAO.reqList(begin, nbRes, sql);

		return list;
	}

	//----------------------------------------------------------------
	private static String reqNsoNewsPrjSql(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql	= SQL_REQ_LIST_SELECT_PRJ;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN_PRJ;
		
		sql += SQL_REQ_LIST_JOIN_USER_INFO;
		
		sql += SQL_REQ_LIST_JOIN_USER_AVATAR;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}
		
		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);

		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);
		
		if(entId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_ENTID, entId);
		
		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		if(style != null && style != 0) {
			String orderCol01 	= null;
			String orderCol02 	= null;
			String orderCol03 	= null;
			String orderDir		= "DESC";

			switch(style){
			case 1: {									//latest
				orderCol01 = COL_D_DATE_02; 	
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;		
			}
			case 2: {									//hot
				orderCol01 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
				break;
			}
			case 3: {									//1st featured, 2nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 4: {									//1st featured, 2nd hot
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				sql += String.format(SQL_REQ_ORDER_LEVEL_01, orderCol01, orderDir, orderCol02, orderDir);
				break;
			}
			case 5: {									//1st featured, 2nd hot, 3nd latest
				orderCol01 = COL_I_TYPE_01;
				orderCol02 = COL_F_VAL_05;
				orderCol03 = COL_D_DATE_01;
				sql += String.format(SQL_REQ_ORDER_LEVEL_02, orderCol01, orderDir, orderCol02, orderDir, orderCol03, orderDir);
				break;
			}
			}

			//				if (orderCol01!=null){
			//					sql += String.format(SQL_REQ_ORDER, orderCol01, orderDir);
			//				}
		}

		return sql;
	}
	
	public static Integer reqLstNewsPrjCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql = reqLstNewsPrjCountSqlCount(type, multiType, stat, style, catIds, langId, entId);			
		Integer count = ViNsoPostNew.DAO.reqCount(sql).intValue();

		return count;
	}

	private static String reqLstNewsPrjCountSqlCount(Integer type, String multiType, Integer stat, Integer style, Set<Integer> catIds, String langId, Integer entId) throws Exception {
		String sql	= TaNsoPost.SQL_REQ_LIST_COUNT;

		sql += SQL_REQ_LIST_FROM;

		sql += SQL_REQ_LIST_JOIN_PRJ;
		
		sql += SQL_REQ_LIST_JOIN_USER_INFO;
		
		sql += SQL_REQ_LIST_JOIN_USER_AVATAR;
		
		String catCond = "";		
		if(catIds != null) {
			String sCatIds = catIds.toString();
			sCatIds = sCatIds.replace("[", "(").replace("]", ")"); 
			catCond = String.format(TaNsoPost.SQL_REQ_WHERE_CAT, sCatIds);
			sql = sql + String.format(SQL_REQ_LIST_JOIN_CAT, catCond);
		}

		sql += TaNsoPost.SQL_REQ_WHERE;

		if(type != null && type != 0)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE, type);
		else if(multiType!=null && !multiType.isEmpty() && !multiType.equals("0"))
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_01, multiType);

		if(stat != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_STAT, stat);
		
		if(langId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_TYPE_02, langId);
		
		if(entId != null)
			sql += " AND " + String.format(TaNsoPost.SQL_REQ_WHERE_ENTID, entId);

		//searchKey = %
		String restriction	= reqRestriction("%");

		if(restriction != null) {
			sql += " AND " + restriction;
		}

		return sql;
	}

}
