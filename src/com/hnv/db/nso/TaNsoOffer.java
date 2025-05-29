package com.hnv.db.nso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.tpy.TaTpyCategoryEntity;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyTranslation;
import com.hnv.db.tpy.vi.ViTpyCategoryDyn;
import com.hnv.def.DefDBExt;	

/**
* TaNsoOffer by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_NSO_OFFER )
public class TaNsoOffer extends EntityAbstract<TaNsoOffer> {

	private static final long serialVersionUID = 1L;
	private static final int  ENT_TYP			= DefDBExt.ID_TA_NSO_OFFER;
	
	public static final int	STAT_01_NEW 		= 0;
	public static final int	STAT_01_ACTIVE 		= 1;
	public static final int	STAT_01_COMPLETED 	= 2; 
	public static final int	STAT_01_REVIEW 		= 3; 
	public static final int	STAT_01_DELETED 	= 4;
	public static final int	STAT_01_EXPIRED 	= 9;
	
	public static final int	STAT_02_PRIVATE 	= 10; 
	public static final int	STAT_02_PUBLIC 		= 20;
	
	public static final int	TYP_01_HOUR 		= 100; 
	public static final int	TYP_01_TODO_LIST 	= 101;
	
	public static final int	TYP_02_VI 			= 1;
	public static final int	TYP_02_EN 			= 2;
	public static final int	TYP_02_FR 			= 3;
	
	public static final int	TYP_03_CAN 			= 100;
	public static final int	TYP_03_JOB 			= 200;
	 
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_TITLE                           =	"T_Title";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_PARENT                          =	"I_Parent";
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
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";
	public static final String	COL_D_DATE_05                         =	"D_Date_05";
	public static final String	COL_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	COL_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	COL_I_AUT_USER_03                     =	"I_Aut_User_03";
	public static final String	COL_I_VAL_01                          =	"I_Val_01";
	public static final String	COL_I_VAL_02                          =	"I_Val_02";
	public static final String	COL_I_VAL_03                          =	"I_Val_03";
	public static final String	COL_I_VAL_04                          =	"I_Val_04";
	public static final String	COL_I_VAL_05                          =	"I_Val_05";
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	COL_T_AVATAR                     	  =	"T_Avatar";
	public static final String	COL_T_AVATAR_MAN                      =	"T_Avatar_Man";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_TITLE                           =	"T_Title";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_PARENT                          =	"I_Parent";
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
	public static final String	ATT_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	ATT_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	ATT_I_AUT_USER_03                     =	"I_Aut_User_03";
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
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String 	ATT_O_CATS 					  		  = "O_Cats";
	public static final String	ATT_O_DOCUMENTS                       =	"O_Documents";
	public static final String	ATT_O_TRANSL                	  	  =	"O_Transl";


	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaNsoOffer> 	DAO;
	static{
		DAO = new EntityDAO<TaNsoOffer>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaNsoOffer.class,RIGHTS, HISTORY, DefDBExt.TA_NSO_OFFER, DefDBExt.ID_TA_NSO_OFFER);
//		try {
//			doLstGen();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private static void doLstGen() throws Exception  {
		//ToolLogServer.doLogDebug("--------- "+ SV_CLASS+ ".doLst --------------");
		for (int i=0;i<10000;i++){
			System.out.println("------- o : " + i);
			TaNsoOffer o = new TaNsoOffer();
			o.reqSet(TaNsoOffer.ATT_T_TITLE, "T_"+i);
			o.reqSet(TaNsoOffer.ATT_T_CONTENT_04,  "[{\"id\":\"1\",\"cont\":\"\"},{\"id\":\"3\",\"cont\":\"\"}]");
			o.reqSet(TaNsoOffer.ATT_T_CONTENT_05,  "[{\"date\":\"2023/07/07\",\"time\":[{\"start\":\"0930\",\"end\":\"1200\"},{\"start\":\"1530\",\"end\":\"1930\"},{\"start\":\"1300\",\"end\":\"1430\"}]},{\"date\":\"2023/07/08\",\"time\":[{\"start\":\"1000\",\"end\":\"1230\"},{\"start\":\"1530\",\"end\":\"1700\"}]},{\"date\":\"2023/07/09\",\"time\":[{\"start\":\"1000\",\"end\":\"1330\"}]},{\"date\":\"2023/07/10\",\"time\":[{\"start\":\"1130\",\"end\":\"1400\"}]},{\"date\":\"2023/07/11\",\"time\":[{\"start\":\"1000\",\"end\":\"1230\"}]}]");
			o.reqSet(TaNsoOffer.ATT_I_STATUS_01,  TaNsoOffer.STAT_01_ACTIVE);
			o.reqSet(TaNsoOffer.ATT_D_DATE_01, new Date());
			o.reqSet(TaNsoOffer.ATT_D_DATE_05, ToolDate.reqDateByAdding(new Date(), 0, 3, 0, 0, 0, 0));//hanj 3 thang
			TaNsoOffer.DAO.doPersist(o);
		}
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

	@Column(name=COL_I_AUT_USER_03, nullable = true)
	private	Integer         I_Aut_User_03;

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
     
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	
	//-----------------------Transient Variables-------------------------

	@Transient
	private	List<TaTpyCategoryEntity>  	O_Category_Entity;
	
	@Transient
	private	List<ViTpyCategoryDyn>  	O_Cats;

	@Transient
	private List<TaTpyDocument>			O_Documents;
	
	@Transient
	private List<TaTpyTranslation> 		O_Transl;
	//---------------------Constructeurs-----------------------
	public TaNsoOffer(){}

	public TaNsoOffer(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaNsoOffer ent) {
		if (ent == this) return;
		this.T_Title                = ent.T_Title;		
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.I_Parent               = ent.I_Parent;
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
		this.I_Aut_User_03          = ent.I_Aut_User_03;
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
		this.I_Per_Manager          = ent.I_Per_Manager;


		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaNsoOffer)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}
	
	//-------------------------------------------------------------
		public static final String SQL_REQ_LIST_SELECT 		= "SELECT"
				+   " a." 			+ TaNsoOffer.COL_I_ID						+ " AS " +COL_I_ID 			+ ","
				
				+	"COALESCE(d."	+ TaTpyDocument.COL_T_INFO_05 				+ ", d." + TaTpyDocument.COL_T_INFO_03 	+") AS " +COL_T_AVATAR	+ ","
				
				+   " a." 			+ TaNsoOffer.COL_T_CODE_01					+ " AS " +COL_T_CODE_01 	+ ","
				+   " a." 			+ TaNsoOffer.COL_T_TITLE					+ " AS " +COL_T_TITLE 		+ ","
				+   " a." 			+ TaNsoOffer.COL_T_CONTENT_01				+ " AS " +COL_T_CONTENT_01	+ ","
				+   " a." 			+ TaNsoOffer.COL_T_CONTENT_05				+ " AS " +COL_T_CONTENT_05	+ ","
				
				+   " a." 			+ TaNsoOffer.COL_F_VAL_01					+ " AS " +COL_F_VAL_01 		+ ","
				+   " a." 			+ TaNsoOffer.COL_F_VAL_02					+ " AS " +COL_F_VAL_02 		+ ","
				
				+   " a." 			+ TaNsoOffer.COL_D_DATE_01					+ " AS " +COL_D_DATE_01 	+ ","
				+   " a." 			+ TaNsoOffer.COL_D_DATE_03					+ " AS " +COL_D_DATE_03 	+ ","
				+   " a." 			+ TaNsoOffer.COL_D_DATE_04					+ " AS " +COL_D_DATE_04 	+ ","
				
				+   " a." 			+ TaNsoOffer.COL_T_INFO_01					+ " AS " +COL_T_INFO_01 	+ ","									
				+   " a." 			+ TaNsoOffer.COL_T_INFO_02					+ " AS " +COL_T_INFO_02	 	+ ","	//address									
				+   " a." 			+ TaNsoOffer.COL_T_INFO_03					+ " AS " +COL_T_INFO_03 	+ ","
				+   " a." 			+ TaNsoOffer.COL_T_INFO_04					+ " AS " +COL_T_INFO_04 	+ ","
				
				+   " a." 			+ TaNsoOffer.COL_I_TYPE_01					+ " AS " +COL_I_TYPE_01 	+ ","
				+   " a." 			+ TaNsoOffer.COL_I_TYPE_02					+ " AS " +COL_I_TYPE_02 	+ ","

//				+   " NULL"														+ " AS " +COL_I_PER_MANAGER	+ ","
				+   " NULL"														+ " AS " +COL_T_AVATAR_MAN 	;
		
		
		public static final String SQL_REQ_LIST_SELECT_SEARCH_AUTOCOMPLETE 		= "SELECT"
				+   " a." 			+ TaNsoOffer.COL_I_ID						+ " AS " +COL_I_ID 			+ ","
				+	"NULL" 														+ " AS " +COL_T_AVATAR		+ ","
				
				+   " a." 			+ TaNsoOffer.COL_T_CODE_01					+ " AS " +COL_T_CODE_01 		+ ","
				+   " a." 			+ TaNsoOffer.COL_I_PARENT					+ " AS " +COL_I_PARENT 		+ ","
				+   " a." 			+ TaNsoOffer.COL_T_TITLE					+ " AS " +COL_T_TITLE 		+ ","
				+   " NULL"                                      				+ " AS " +COL_T_CONTENT_01	+ ","
				+   " NULL"                                      				+ " AS " +COL_T_CONTENT_05	+ ","
				
				
				+   " a." 			+ TaNsoOffer.COL_T_INFO_02					+ " AS " +COL_T_INFO_02 	+ ","//address
				+   " a." 			+ TaNsoOffer.COL_F_VAL_01					+ " AS " +COL_F_VAL_01 		+ ","
				+   " a." 			+ TaNsoOffer.COL_F_VAL_02					+ " AS " +COL_F_VAL_02 		+ ","
				
				+   " NULL"    													+ " AS " +COL_D_DATE_01 	+ ","
				+   " NULL" 													+ " AS " +COL_D_DATE_03 	+ ","
				+   " NULL" 													+ " AS " +COL_D_DATE_04 	+ ","
				+   " NULL" 													+ " AS " +COL_T_INFO_01 	+ ","
				+   " NULL"														+ " AS " +COL_T_INFO_03 	+ ","
				+   " NULL" 													+ " AS " +COL_T_INFO_04 	+ ","
				
				+   " NULL"              										+ " AS " +COL_I_TYPE_01 	+ ","
				+   " NULL"              										+ " AS " +COL_I_TYPE_02 	+ ","
				
//				+   " NULL"   													+ " AS " +COL_I_PER_MANAGER	+ ","
				+   " NULL"														+ " AS " +COL_T_AVATAR_MAN 	;
		
		
//		public static final String SQL_REQ_LIST_SELECT_COUNT 	= "SELECT count(*)";
		public static final String SQL_REQ_LIST_SELECT_COUNT 	= "SELECT count(DISTINCT(a."+ TaNsoOffer.COL_I_ID		+"))";
		
		public static final String SQL_REQ_LIST_FROM			= " FROM "		+ DefDBExt.TA_NSO_OFFER		+ " a";
		public static final String SQL_REQ_LIST_JOIN_CAT		= " INNER JOIN "+ DefDBExt.TA_TPY_CATEGORY_ENTITY	+ " c"	
				+	" ON (c."		+ TaTpyCategoryEntity.COL_I_ENTITY_ID		+ " = a."	+ TaNsoOffer.COL_I_ID
				+	" AND c."		+ TaTpyCategoryEntity.COL_I_ENTITY_TYPE 	+ " = " 	+ DefDBExt.ID_TA_NSO_OFFER
				+	" AND c."		+ TaTpyCategoryEntity.COL_I_TPY_CATEGORY	+ " IN (%s) "
				+	")";
		
		public static final String SQL_REQ_LIST_JOIN_DOCUMENT	= " LEFT JOIN "+ DefDBExt.TA_TPY_DOCUMENT	+ " d"	
				+	" ON (d."		+ TaTpyDocument.COL_I_ENTITY_ID				+ " = a."	+ TaNsoOffer.COL_I_ID 
				+	" AND d."		+ TaTpyDocument.COL_I_ENTITY_TYPE 			+ " = " 	+ DefDBExt.ID_TA_NSO_OFFER
				+	" AND d."		+ TaTpyDocument.COL_I_TYPE_01 				+ " = " 	+ TaTpyDocument.TYPE_01_FILE_MEDIA
				+	" AND d."		+ TaTpyDocument.COL_I_TYPE_02 				+ " = " 	+ TaTpyDocument.TYPE_02_FILE_IMG_AVATAR
				+	" AND d."		+ TaTpyDocument.COL_I_PRIORITY 				+ " = 1" 	+ ")";
				
//				+	" LEFT JOIN "	+ DefDBExt.TA_NSO_EVALUATION				+ " e"
//				+	" ON (a."		+ TaNsoOffer.COL_I_ID						+ " = e."	+ TaNsoEvaluation.COL_I_ENTITY_ID
//				+	" AND e."		+ TaNsoEvaluation.COL_I_ENTITY_TYPE			+ " = "		+ DefDBExt.ID_TA_NSO_OFFER						+ ")";		
		
//		public static final String SQL_REQ_LIST_JOIN_PROP_QUANT	= " INNER JOIN "+ com.hnv.tool.db.tpy_cfg.config.DefDBExt.TA_TPY_PROPERTY_QUANT	+ " q"	
//				+	" ON q."		+ TaTpyPropertyQuant.COL_I_ID				+ " = "	
//				+	" ( SELECT  q2." 	+ TaTpyPropertyQuant.COL_I_ID
//				+ 	" FROM " 		+ com.hnv.tool.db.tpy_cfg.config.DefDBExt.TA_TPY_PROPERTY_QUANT + " q2"
//				+	" WHERE a."		+ TaNsoOffer.COL_I_ID						+ " = q2."	+ TaTpyPropertyQuant.COL_I_ENTITY_ID
//				+	" AND q2."		+ TaTpyPropertyQuant.COL_I_ENTITY_TYPE 		+ " = " 	+ DefDBExt.ID_TA_NSO_OFFER   
//				+	" AND q2."		+ TaTpyPropertyQuant.COL_I_TYPE 			+ " in " 	+ "(%s)" 
//				+	" AND q2."		+ TaTpyPropertyQuant.COL_F_PROP_01 			+ " >0 " 	
//				+   " LIMIT 1) ";
				
		
		
		public static final String SQL_REQ_LIST_COUNT 	= "SELECT count(DISTINCT(a."+ TaNsoOffer.COL_I_ID		+"))";
		
		public static final String SQL_REQ_WHERE 		= " WHERE true"	;	
		
		public static final String SQL_AND 				= " And "	;	
		
		public static final String SQL_OR 				= " Or "	;	

		public static final String SQL_REQ_WHERE_IDS 	= " a."			+TaNsoOffer.COL_I_ID 		+ " IN (%s)";
		
		public static final String SQL_REQ_WHERE_TYPE 	= " a."			+TaNsoOffer.COL_I_TYPE_01 	+ " = %d";
			
		public static final String SQL_REQ_WHERE_TYPES = " a."			+TaNsoOffer.COL_I_TYPE_01 	+ " IN (%s)";
		
		public static final String SQL_REQ_WHERE_TYPE_02 	= " a."		+TaNsoOffer.COL_I_TYPE_02 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_STAT_01 	= " a."			+TaNsoOffer.COL_I_STATUS_01 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_STAT_02 	= " a."			+TaNsoOffer.COL_I_STATUS_02 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_BETWEEN_MON 	= " a."		+TaNsoOffer.COL_I_VAL_03 	+ " BETWEEN %d AND %d";
		
		public static final String SQL_REQ_WHERE_TYP_01 	= " a."			+TaNsoOffer.COL_I_TYPE_01 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_TYP_03 	= " a."			+TaNsoOffer.COL_I_TYPE_03 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_PER_MAN_ID 	= " a."		+TaNsoOffer.COL_I_PER_MANAGER 	+ " = %d";
		
		public static final String SQL_REQ_WHERE_SEARCH		= " lower(a."	+ TaNsoOffer.COL_T_TITLE + ") like lower('%s') OR lower(a." + TaNsoOffer.COL_T_INFO_02 + ") like lower('%s')";
		
		public static final String SQL_REQ_WHERE_DISTANCE= "(a." 		+ TaNsoOffer.COL_F_VAL_01 + ">= %f"
															+ " AND a." 	+ TaNsoOffer.COL_F_VAL_01 + "<= %f"
															+ " AND a." 	+ TaNsoOffer.COL_F_VAL_02 + ">= %f"
															+ " AND a." 	+ TaNsoOffer.COL_F_VAL_02 + "<= %f)";
		
		public static final String SQL_REQ_ORDER_LEVEL_01		= " ORDER BY %s %s"	;
		
		public static final String SQL_REQ_ORDER_LEVEL_02		= " ORDER BY %s %s, %s %s";
		
		public static final String SQL_REQ_ORDER_LEVEL_03		= " ORDER BY %s %s, %s %s, %s %s";
		
		public static final String SQL_REQ_WHERE_USER 	= " a."+TaNsoOffer.COL_I_AUT_USER_01 + " = %d";
		
		public static final String SQL_REQ_WHERE_DATE 	= " ( a." + TaNsoOffer.COL_D_DATE_04		+ " >= '%s' " + "OR a." + TaNsoOffer.COL_D_DATE_04	+ " IS NULL )";
		
//		static final String SQL_REQ_LIST_JOIN_FAVORTIE	= " INNER JOIN " 	+ DefDBExt.TA_TPY_FAVORITE 		+" fav"
//				+	" ON  a." 		+ TaNsoOffer.COL_I_ID					+ " = fav." 	+ TaTpyFavorite.COL_I_ENTITY_ID	
//				+	" AND fav." 	+ TaTpyFavorite.COL_I_ENTITY_TYPE		+ " = " 		+ DefDBExt.ID_TA_NSO_OFFER
//				+	" AND fav." 	+ TaTpyFavorite.ATT_I_TYPE				+ " = " 		+ TaTpyFavorite.FAVORITE_TYPE_LIKE
//				+	" AND fav." 	+ TaTpyFavorite.COL_I_AUT_USER			+ " = %d" 		;
	
	public void doBuildAll(boolean forced, boolean forManager) throws Exception {
		doBuildDocuments(forced);
//		doBuildUsers	(forced);
//		doBuildTransl	(forced);
//		doBuildUsers	(forced);
//		doBuildCats		(forced);

//		if (forManager && !isBuilt_manager) {
//			isBuilt_manager=true;
//		}

		//		if (forManager && this.I_Type_03.equals(POST_TYPE_BLOG)) {
		//			doBuildPosts (false);
		//		}
	}
	
	public void doBuildCats(boolean forced) throws Exception {       
		if (this.O_Cats!=null && !forced) return;
		
		Criterion 					cri 			= Restrictions.eq(TaTpyCategoryEntity.ATT_I_ENTITY_ID, this.I_ID);
		List<TaTpyCategoryEntity> 	catEntities 	= TaTpyCategoryEntity.DAO.reqList(cri);
		
		Set<Integer> lstId = new HashSet<Integer>();
		for(TaTpyCategoryEntity catEntity : catEntities) {
			lstId.add(catEntity.reqInt(TaTpyCategoryEntity.ATT_I_TPY_CATEGORY));
		}
		
		this.O_Cats = ViTpyCategoryDyn.DAO.reqList(
				Restrictions.in(ViTpyCategoryDyn.ATT_I_ID	, lstId)
		);
	}
	
	//---khong chung session duoc vi nam trong package khac
	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}

}
