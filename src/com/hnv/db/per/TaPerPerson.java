package com.hnv.db.per;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.def.DefAPI;
import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolJSON;
import com.hnv.data.json.JSONObject;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.aut.TaAutUser;
import com.hnv.db.tpy.TaTpyDocument;
import com.hnv.db.tpy.TaTpyInformation;
import com.hnv.def.DefDBExt;

/**
 * TaPerPerson by H&V SAS
 */
@Entity
@Table(name = DefDBExt.TA_PER_PERSON )
public class TaPerPerson extends EntityAbstract<TaPerPerson> {
	private static final int  ENT_TYP					= DefDBExt.ID_TA_PER_PERSON;
			
	public  static final int 	TYP_01_NATURAL			= 100; //--person
	public  static final int 	TYP_01_MORAL			= 200; //--company
	
	public  static final int 	TYP_02_INTERNAL			= 1000;
	public  static final int 	TYP_02_INTERNAL_DOCTOR	= 1100; //--doctor
	public  static final int 	TYP_02_INTERNAL_PHARMA	= 1200;
	public  static final int 	TYP_02_INTERNAL_NURSE	= 1300;
	public  static final int 	TYP_02_INTERNAL_OTHER	= 1500;
	
	public  static final int 	TYP_02_CLIENT			= 2000; //--patient
	public  static final int 	TYP_02_SUPPLIER			= 3000;
	public  static final int 	TYP_02_PRODUCER			= 4000;	
	public  static final int 	TYP_02_TPARTY			= 5000;
	public  static final int 	TYP_02_PROSPECT			= 9000;


	public  static final int 	STAT_01_NEW				= 0; //moi tao	
	public  static final int 	STAT_01_ACTIVE_LV_01	= 1; //call, email only	
	public  static final int 	STAT_01_ACTIVE_LV_02	= 2; //sign the first paper..
	public  static final int 	STAT_01_ACTIVE_LV_03	= 3; //high coperation with contract

	public  static final int 	STAT_01_REVIEW			= 5;
	public  static final int 	STAT_01_DELETED			= 10;

	//------------------------------------------------------------------------------
	private static final long 	serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_NAME_01                         =	"T_Name_01";//Họ / Tên doanh nghiệp
	public static final String	COL_T_NAME_02                         =	"T_Name_02";//tên đệm
	public static final String	COL_T_NAME_03                         =	"T_Name_03";//tên
	public static final String	COL_T_NAME_04                         =	"T_Name_04";
	public static final String	COL_T_NAME_05                         =	"T_Name_05";
	public static final String	COL_I_STATUS_01                       =	"I_Status_01";
	public static final String	COL_I_STATUS_02                       =	"I_Status_02";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_I_TYPE_04                         =	"I_Type_04";
	public static final String	COL_I_TYPE_05                         =	"I_Type_05";
	public static final String	COL_I_TYPE_06                         =	"I_Type_06";
	public static final String	COL_I_TYPE_07                         =	"I_Type_07";
	public static final String	COL_I_TYPE_08                         =	"I_Type_08";
	public static final String	COL_I_TYPE_09                         =	"I_Type_09";
	public static final String	COL_I_TYPE_10                         =	"I_Type_10";
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	public static final String	COL_T_CODE_01                         =	"T_Code_01"; //id cong dan, id doanh nghiep
	public static final String	COL_T_CODE_02                         =	"T_Code_02"; //MSinh viên, MQL nội bộ doanh nghiệp
	public static final String	COL_T_CODE_03                         =	"T_Code_03"; //Số BHXH
	public static final String	COL_T_CODE_04                         =	"T_Code_04"; //Mã số thuế
	public static final String	COL_T_CODE_05                         =	"T_Code_05"; 
	public static final String	COL_T_CODE_06                         =	"T_Code_06";
	public static final String	COL_T_CODE_07                         =	"T_Code_07"; //email chính liên lạc
	public static final String	COL_T_CODE_08                         =	"T_Code_08"; //email phụ liên lạc
	public static final String	COL_T_CODE_09                         =	"T_Code_09"; //điện thoại chính liên lạc
	public static final String	COL_T_CODE_10                         =	"T_Code_10"; //điện thoại phụ liên lạc
	public static final String	COL_T_INFO_01                         =	"T_Info_01"; //địa chỉ thường trú, địa chỉ doanh nghiệp
	public static final String	COL_T_INFO_02                         =	"T_Info_02"; //thông tin thân nhân
	public static final String	COL_T_INFO_03                         =	"T_Info_03"; //thông tin khác
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_T_INFO_06                         =	"T_Info_06";
	public static final String	COL_T_INFO_07                         =	"T_Info_07";
	public static final String	COL_T_INFO_08                         =	"T_Info_08";
	public static final String	COL_T_INFO_09                         =	"T_Info_09";
	public static final String	COL_T_INFO_10                         =	"T_Info_10";
	public static final String	COL_D_DATE_01                         =	"D_Date_01"; //ngày tạo dữ liệu
	public static final String	COL_D_DATE_02                         =	"D_Date_02"; //ngày sửa dữ liệu
	public static final String	COL_D_DATE_03                         =	"D_Date_03"; //ngày sinh/ thành lập
	public static final String	COL_D_DATE_04                         =	"D_Date_04"; 
	public static final String	COL_D_DATE_05                         =	"D_Date_05";
	public static final String	COL_D_DATE_06                         =	"D_Date_06";
	public static final String	COL_D_DATE_07                         =	"D_Date_07";
	public static final String	COL_D_DATE_08                         =	"D_Date_08";
	public static final String	COL_D_DATE_09                         =	"D_Date_09";
	public static final String	COL_D_DATE_10                         =	"D_Date_10";
	public static final String	COL_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	COL_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_NAME_01                         =	"T_Name_01";
	public static final String	ATT_T_NAME_02                         =	"T_Name_02";
	public static final String	ATT_T_NAME_03                         =	"T_Name_03";
	public static final String	ATT_T_NAME_04                         =	"T_Name_04";
	public static final String	ATT_T_NAME_05                         =	"T_Name_05";
	public static final String	ATT_I_STATUS_01                       =	"I_Status_01";
	public static final String	ATT_I_STATUS_02                       =	"I_Status_02";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_I_TYPE_04                         =	"I_Type_04";
	public static final String	ATT_I_TYPE_05                         =	"I_Type_05";
	public static final String	ATT_I_TYPE_06                         =	"I_Type_06";
	public static final String	ATT_I_TYPE_07                         =	"I_Type_07";
	public static final String	ATT_I_TYPE_08                         =	"I_Type_08";
	public static final String	ATT_I_TYPE_09                         =	"I_Type_09";
	public static final String	ATT_I_TYPE_10                         =	"I_Type_10";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_T_CODE_03                         =	"T_Code_03";
	public static final String	ATT_T_CODE_04                         =	"T_Code_04";
	public static final String	ATT_T_CODE_05                         =	"T_Code_05";
	public static final String	ATT_T_CODE_06                         =	"T_Code_06";
	public static final String	ATT_T_CODE_07                         =	"T_Code_07";
	public static final String	ATT_T_CODE_08                         =	"T_Code_08";
	public static final String	ATT_T_CODE_09                         =	"T_Code_09";
	public static final String	ATT_T_CODE_10                         =	"T_Code_10";
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
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_D_DATE_05                         =	"D_Date_05";
	public static final String	ATT_D_DATE_06                         =	"D_Date_06";
	public static final String	ATT_D_DATE_07                         =	"D_Date_07";
	public static final String	ATT_D_DATE_08                         =	"D_Date_08";
	public static final String	ATT_D_DATE_09                         =	"D_Date_09";
	public static final String	ATT_D_DATE_10                         =	"D_Date_10";
	public static final String	ATT_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	ATT_I_AUT_USER_02                     =	"I_Aut_User_02";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";

	public static final String	ATT_O_AVATAR               	  	  	  =	"O_Avatar";

	public static final String	ATT_O_PROPERTIES                   	  =	"O_Properties";
	public static final String	ATT_O_INFORMATIONS                	  =	"O_Informations";
	public static final String	ATT_O_DOCUMENTS                	  	  =	"O_Documents";
	public static final String	ATT_O_POSITIONS                	  	  =	"O_Positions";
	public static final String	ATT_O_ACCOUNT_ENT                     =	"O_Account_Ent";
	public static final String	ATT_O_FULLNAME	                      =	"O_Fullname";

	public static final String	ATT_O_SHORT_ADDR	                  =	"O_Short_Addr";
	public static final String	ATT_O_LONG_ADDR	                      =	"O_Long_Addr";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaPerPerson> 	DAO;
	static{
		DAO = new EntityDAO<TaPerPerson>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , TaPerPerson.class,RIGHTS, HISTORY, DefDBExt.TA_PER_PERSON, DefDBExt.ID_TA_PER_PERSON);


	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_NAME_01, nullable = false)
	private	String          T_Name_01;
    
	@Column(name=COL_T_NAME_02, nullable = true)
	private	String          T_Name_02;
    
	@Column(name=COL_T_NAME_03, nullable = true)
	private	String          T_Name_03;
    
	@Column(name=COL_T_NAME_04, nullable = true)
	private	String          T_Name_04;
    
	@Column(name=COL_T_NAME_05, nullable = true)
	private	String          T_Name_05;
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
	
	@Column(name=COL_I_STATUS_02, nullable = true)
	private	Integer         I_Status_02;
     
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
    
	@Column(name=COL_I_TYPE_06, nullable = true)
	private	Integer         I_Type_06;
    
	@Column(name=COL_I_TYPE_07, nullable = true)
	private	Integer         I_Type_07;
    
	@Column(name=COL_I_TYPE_08, nullable = true)
	private	Integer         I_Type_08;
    
	@Column(name=COL_I_TYPE_09, nullable = true)
	private	Integer         I_Type_09;
    
	@Column(name=COL_I_TYPE_10, nullable = true)
	private	Integer         I_Type_10;
    
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
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
	@Column(name=COL_T_CODE_03, nullable = true)
	private	String          T_Code_03;
    
	@Column(name=COL_T_CODE_04, nullable = true)
	private	String          T_Code_04;
    
	@Column(name=COL_T_CODE_05, nullable = true)
	private	String          T_Code_05;
    
	@Column(name=COL_T_CODE_06, nullable = true)
	private	String          T_Code_06;
    
	@Column(name=COL_T_CODE_07, nullable = true)
	private	String          T_Code_07;
    
	@Column(name=COL_T_CODE_08, nullable = true)
	private	String          T_Code_08;
    
	@Column(name=COL_T_CODE_09, nullable = true)
	private	String          T_Code_09;
    
	@Column(name=COL_T_CODE_10, nullable = true)
	private	String          T_Code_10;
    
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
    
	@Column(name=COL_D_DATE_06, nullable = true)
	private	Date            D_Date_06;
    
	@Column(name=COL_D_DATE_07, nullable = true)
	private	Date            D_Date_07;
    
	@Column(name=COL_D_DATE_08, nullable = true)
	private	Date            D_Date_08;
    
	@Column(name=COL_D_DATE_09, nullable = true)
	private	Date            D_Date_09;
    
	@Column(name=COL_D_DATE_10, nullable = true)
	private	Date            D_Date_10;
    
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer         I_Aut_User_02;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;


	//-----------------------Transient Variables-------------------------

	@Transient
	private	List<TaTpyDocument> 		O_Documents;

	@Transient
	private	List<TaTpyInformation> 		O_Informations;

	@Transient
	private	String 						O_FullName;

	@Transient
	private	TaTpyDocument 				O_Avatar;

	//---------------------Constructeurs-----------------------
	public TaPerPerson(){}

	public TaPerPerson(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}

	public TaPerPerson(Integer id, String name01, String name02) throws Exception {
		this.reqSetAttr(
				ATT_I_ID		  , id,
				ATT_T_NAME_01     , name01,
				ATT_T_NAME_01     , name02				
				);
		//doInitDBFlag();
	}

	public TaPerPerson(Integer I_Status) throws Exception {
		this.reqSetAttr(
				ATT_I_STATUS_01     , I_Status
				);
		//doInitDBFlag();
	}

	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	public void doMergeWith(TaPerPerson ent) {
		if (ent == this) return;
		this.T_Name_01              = ent.T_Name_01;
		this.T_Name_02              = ent.T_Name_02;
		this.T_Name_03              = ent.T_Name_03;
		this.T_Name_04              = ent.T_Name_04;
		this.T_Name_05              = ent.T_Name_05;
		this.I_Status_01            = ent.I_Status_01;
		this.I_Status_02            = ent.I_Status_02;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.I_Type_04              = ent.I_Type_04;
		this.I_Type_05              = ent.I_Type_05;
		this.I_Type_06              = ent.I_Type_06;
		this.I_Type_07              = ent.I_Type_07;
		this.I_Type_08              = ent.I_Type_08;
		this.I_Type_09              = ent.I_Type_09;
		this.I_Type_10              = ent.I_Type_10;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;
		this.T_Code_01              = ent.T_Code_01;
		this.T_Code_02              = ent.T_Code_02;
		this.T_Code_03              = ent.T_Code_03;
		this.T_Code_04              = ent.T_Code_04;
		this.T_Code_05              = ent.T_Code_05;
		this.T_Code_06              = ent.T_Code_06;
		this.T_Code_07              = ent.T_Code_07;
		this.T_Code_08              = ent.T_Code_08;
		this.T_Code_09              = ent.T_Code_09;
		this.T_Code_10              = ent.T_Code_10;
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
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;
		this.D_Date_05              = ent.D_Date_05;
		this.D_Date_06              = ent.D_Date_06;
		this.D_Date_07              = ent.D_Date_07;
		this.D_Date_08              = ent.D_Date_08;
		this.D_Date_09              = ent.D_Date_09;
		this.D_Date_10              = ent.D_Date_10;
		this.I_Aut_User_01          = ent.I_Aut_User_01;
		this.I_Aut_User_02          = ent.I_Aut_User_02;
		this.I_Per_Manager          = ent.I_Per_Manager;
		//---------------------Merge Transient Variables if exist-----------------------
	}


	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((TaPerPerson)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	//----------------------------------------------------------------------------------------------------------	
	public Integer reqID(){
		return this.I_ID;
	}
	//----------------------------------------------------------------------------------------------------------		
	public void doRemoveDocuments () throws Exception{				
		this.doBuildDocuments(true);		
		if (O_Documents!=null && O_Documents.size()>0)
			TaTpyDocument.DAO.doRemove(O_Documents);	
		O_Documents = null;
	}

	public void doRemoveDocuments (Collection docIds) throws Exception{				
		List<TaTpyDocument> lP  = TaTpyDocument.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, docIds));
		if (lP!=null && lP.size()>0) TaTpyDocument.DAO.doRemove(lP);

		this.doBuildDocuments(true);
	}

	public void doRemoveDocuments (Integer... docIds) throws Exception{		
		List<TaTpyDocument> lP  = TaTpyDocument.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, docIds));
		if (lP!=null && lP.size()>0) TaTpyDocument.DAO.doRemove(lP);

		this.doBuildDocuments(true);
	}

	//----------------------------------------------------------------------------------------------------------	
	//----------------------------------------------------------------------------------------------------------
	public static void doBuildInformations (List<TaPerPerson> lst) throws Exception{
		for (TaPerPerson per : lst) {
			per.O_Informations = new ArrayList<TaTpyInformation>();
		}

		Hashtable<Integer, EntityAbstract> dict = ToolDBEntity.reqTabKeyInt(lst, TaPerPerson.ATT_I_ID);
		List<TaTpyInformation> infos = TaTpyInformation.DAO.reqList_In ( TaTpyInformation.ATT_I_ENTITY_ID, dict.keySet(), Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE, DefDBExt.ID_TA_PER_PERSON));
		for (TaTpyInformation inf : infos) {
			Integer pId = (Integer) inf.req(TaTpyInformation.ATT_I_ENTITY_ID);
			TaPerPerson per = (TaPerPerson)dict.get(pId);
			if (per!=null) {
				per.O_Informations.add(inf);
			}
		}
	}
	public void doBuildInformations (boolean forced) throws Exception{
		if (!forced && this.O_Informations!=null) return;		
		//same base
		this.O_Informations = TaTpyInformation.DAO.reqList(Order.desc(TaTpyInformation.ATT_I_ID), Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_TYPE, DefDBExt.ID_TA_PER_PERSON), Restrictions.eq(TaTpyInformation.ATT_I_ENTITY_ID, I_ID));
	}
	public void doRemoveInformations () throws Exception{				
		this.doBuildInformations(true);		
		if (O_Informations!=null && O_Informations.size()>0)
			TaTpyInformation.DAO.doRemove(O_Informations);		
		O_Informations = null;
	}

	public void doRemoveInformations (Collection infIds) throws Exception{				
		List<TaTpyInformation> lP  = TaTpyInformation.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, infIds));
		if (lP!=null && lP.size()>0) TaTpyInformation.DAO.doRemove(lP);

		this.doBuildInformations(true);
	}

	public void doRemoveInformations (Integer... infIds) throws Exception{				
		List<TaTpyInformation> lP  = TaTpyInformation.DAO.reqList(Restrictions.in(TaTpyDocument.ATT_I_ID, infIds));
		if (lP!=null && lP.size()>0) TaTpyInformation.DAO.doRemove(lP);

		this.doBuildInformations(true);
	}
	//----------------------------------------------------------------------------------------------------------	
	//----------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------	
	//----------------------------------------------------------------------------------------------------------


	public void doBuildFullName(boolean forced) throws Exception{
		if (!forced && O_FullName!=null) return;
		String fullName = "";
		if(T_Name_01 != null) fullName += T_Name_01 + " ";
		if(T_Name_02 != null) fullName += T_Name_02 + " ";
		this.O_FullName = fullName;
	}

	public void doBuildDocuments(boolean forced) throws Exception {
		if (this.O_Documents != null && !forced) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, null, null);
	}

	public void doBuildDocuments(boolean forced, Integer typ01, Integer typ02) throws Exception {
		if (!forced && O_Documents!=null) return;
		this.O_Documents = TaTpyDocument.reqTpyDocuments(ENT_TYP, I_ID, typ01, typ02);
	}




	//-------------------------------------------------------------------------------------------------

	public static List<TaPerPerson> reqListPerson(Integer manId, String searchkey, Set<Integer>  type01, Set<Integer> type02, Integer nbLine) throws Exception {
		Criterion cri = null;		
		cri 			= Restrictions.ilike(TaPerPerson.ATT_T_NAME_01						, "%"+searchkey+"%");
		cri 			= Restrictions.or(cri, Restrictions.ilike(TaPerPerson.ATT_T_NAME_02	, "%"+searchkey+"%"));

		if(type01 != null)
			cri 	= Restrictions.and(cri, Restrictions.in(TaPerPerson.ATT_I_TYPE_01, type01));

		if(type02 != null)
			cri 	= Restrictions.and(cri, Restrictions.in(TaPerPerson.ATT_I_TYPE_02, type02)); 

		//		if(stats != null && stats.length() > 0)
		//			cri 	= Restrictions.and(cri, Restrictions.in(TaPerPerson.ATT_I_STATUS, stats));


		if(manId != null)
			cri 	= Restrictions.and(cri, Restrictions.eq(TaPerPerson.ATT_I_PER_MANAGER, manId));

		List<TaPerPerson> lstResult = TaPerPerson.DAO.reqList(0, nbLine, cri);
		return lstResult;
	}
	
	public static TaPerPerson reqCheck(Integer modeCheck, Integer uId, Integer perId, JSONObject per, TaAutUser uEnt) throws Exception {
		if(per==null) return null;
		
		Map<String, Object> attr 	= API.reqMapParamsByClass(per, TaPerPerson.class);
		
		TaPerPerson 		entPer	= new TaPerPerson();
		
		Integer uTyp 	= uEnt.reqInt(TaAutUser.ATT_I_TYPE_01);
		Integer uStat	= uEnt.reqInt(TaAutUser.ATT_I_STATUS);
		
		Integer pTyp 	= TaPerPerson.TYP_02_INTERNAL;
		Integer pStat 	= TaPerPerson.STAT_01_ACTIVE_LV_01;
		
		switch (uTyp){
		case TaAutUser.TYPE_01_SUP_ADM		: pTyp = TaPerPerson.TYP_02_INTERNAL		; break;
		case TaAutUser.TYPE_01_ADM			: pTyp = TaPerPerson.TYP_02_INTERNAL		; break;
		
		case TaAutUser.TYPE_01_DOCTOR		: pTyp = TaPerPerson.TYP_02_INTERNAL_DOCTOR	; break;
		case TaAutUser.TYPE_01_AGENT		: pTyp = TaPerPerson.TYP_02_INTERNAL_OTHER	; break;
		case TaAutUser.TYPE_01_CLIENT		: pTyp = TaPerPerson.TYP_02_CLIENT			; break;
		default								: pTyp = TaPerPerson.TYP_02_CLIENT			; break;
		}
		
		switch (uStat){
		case TaAutUser.STAT_INACTIVE		: pStat = TaPerPerson.STAT_01_NEW			; break;
		case TaAutUser.STAT_ACTIVE			: pStat = TaPerPerson.STAT_01_ACTIVE_LV_01	; break;
		case TaAutUser.STAT_ACTIVE_HIDDEN	: pStat = TaPerPerson.STAT_01_REVIEW		; break;
		case TaAutUser.STAT_LOCK			: pStat = TaPerPerson.STAT_01_REVIEW		; break;
		case TaAutUser.STAT_DELETED			: pStat = TaPerPerson.STAT_01_DELETED		; break;
		default								: pStat = TaPerPerson.STAT_01_REVIEW		; break;
		}
		
		attr.put(ATT_I_STATUS_01		, pStat);
		attr.put(ATT_I_TYPE_02			, pTyp);
		attr.put(ATT_I_TYPE_01			, TYP_01_NATURAL);
		
		if(modeCheck == DefAPI.SV_MODE_NEW) {
			attr.put(ATT_I_AUT_USER_01	, uId); //--creator
			attr.put(ATT_I_PER_MANAGER	, uEnt.reqPerManagerId());
			
			attr.put(ATT_D_DATE_01		, new Date());
			entPer						= new TaPerPerson(attr);
			
			TaPerPerson.DAO.doPersist(entPer);
			
			if (uEnt!=null) {
				uEnt.reqSet(TaAutUser.ATT_I_PER_PERSON	, entPer.reqId());
				uEnt.reqSet(TaAutUser.ATT_T_INFO_03 	, entPer.reqFullName());
				
				TaAutUser.DAO.doMerge(uEnt);
			}
		} else if(modeCheck == DefAPI.SV_MODE_MOD) {
			entPer 						= TaPerPerson.DAO.reqEntityByID(perId);
			
			attr.remove(ATT_D_DATE_01);
			attr.remove(ATT_I_ID);
			attr.remove(ATT_I_AUT_USER_01);
			
			attr.put(ATT_I_AUT_USER_02	, uId); //--editor
			attr.put(ATT_I_PER_MANAGER	, uEnt.reqPerManagerId());
			attr.put(ATT_D_DATE_02		, new Date());

			TaPerPerson.DAO.doMerge(entPer, attr);
			
			if (uEnt!=null) {
				uEnt.reqSet(TaAutUser.ATT_T_INFO_03 	, entPer.reqFullName());
				TaAutUser.DAO.doMerge(uEnt);
			}
		}
		
		return entPer;
	}

	public String reqFullName () {
		String name ="";
		if (this.T_Name_01 !=null && this.T_Name_01.length()>0) name = name + this.T_Name_01 + " ";
		if (this.T_Name_02 !=null && this.T_Name_02.length()>0) name = name + this.T_Name_02 + " ";
		if (this.T_Name_03 !=null && this.T_Name_03.length()>0) name = name + this.T_Name_03 + " ";
		if (this.T_Name_04 !=null && this.T_Name_04.length()>0) name = name + this.T_Name_04 + " ";
		if (this.T_Name_05 !=null && this.T_Name_05.length()>0) name = name + this.T_Name_05 + " ";
		return name.trim();
	}


	public static TaPerPerson reqNewCustomerForOrder(Session sess, int orderId, Integer manId, String customerStr) throws Exception {
		JSONObject customerObj	= (JSONObject) ToolJSON.reqJSonObjectFromString(customerStr);
		if (customerObj!=null) {
			Map<String, Object> info 	= API.reqMapParamsByClass(customerObj, TaPerPerson.class);
			TaPerPerson customer 		= new TaPerPerson(info);
			customer.reqSet(TaPerPerson.ATT_I_ID		, null);
			customer.reqSet(TaPerPerson.ATT_D_DATE_01	, new Date());
			customer.reqSet(TaPerPerson.ATT_I_PER_MANAGER	, manId);
			customer.reqSet(TaPerPerson.ATT_I_STATUS_01    , TaPerPerson.STAT_01_ACTIVE_LV_01);
			customer.reqSet(TaPerPerson.ATT_I_TYPE_01   , TaPerPerson.TYP_01_NATURAL);
			customer.reqSet(TaPerPerson.ATT_I_TYPE_02   , TaPerPerson.TYP_02_CLIENT);
			TaPerPerson.DAO.doPersist(sess, customer);

			Integer customerId = customer.reqID();
			customer.reqSet(TaPerPerson.COL_T_CODE_01  , "CST-" + manId.toString() + "-" + customerId.toString() );
			TaPerPerson.DAO.doMerge(sess, customer);
			return customer;
		}
		return null;
	}

	public static TaPerPerson reqNewCustomerForOrder(Session sess, int orderId, Integer manId, JSONObject customerObj) throws Exception {
		if (customerObj!=null) {
			Map<String, Object> info 	= API.reqMapParamsByClass(customerObj, TaPerPerson.class);
			TaPerPerson customer 	= new TaPerPerson(info);
			customer.reqSet(TaPerPerson.ATT_D_DATE_01	, new Date());
			customer.reqSet(TaPerPerson.ATT_I_PER_MANAGER	, manId);
			customer.reqSet(TaPerPerson.ATT_I_STATUS_01    , TaPerPerson.STAT_01_ACTIVE_LV_01);
			customer.reqSet(TaPerPerson.ATT_I_TYPE_01   , TaPerPerson.TYP_01_NATURAL);
			customer.reqSet(TaPerPerson.ATT_I_TYPE_02   , TaPerPerson.TYP_02_CLIENT);
			TaPerPerson.DAO.doPersist(sess, customer);
			Integer customerId = customer.reqID();
			customer.reqSet(TaPerPerson.COL_T_CODE_01  , "CST-" + manId.toString() + "-" + customerId.toString() );
			TaPerPerson.DAO.doMerge(sess, customer);
			return customer;
		}
		return null;
	}
	
	public static void doBuildAvatarForList(List<TaPerPerson>  list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_PER_PERSON, ATT_O_AVATAR);
	}
	
	public void doBuildAvatar(boolean forced) throws Exception {
		if (this.O_Avatar != null && !forced) return;
		
		if (this.O_Documents != null && !forced) {
			for (TaTpyDocument doc: this.O_Documents) {
				Integer typ01 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_01);
				Integer typ02 = doc.reqInt(TaTpyDocument.ATT_I_TYPE_02);
				if (typ01!=null && typ02!=null 
						&& typ01.equals(TaTpyDocument.TYPE_01_FILE_MEDIA)  
						&& typ02.equals(TaTpyDocument.TYPE_02_FILE_IMG_AVATAR)){
					this.O_Avatar = doc;
					return;
				}
			}
		}
		TaTpyDocument.reqBuildAvatar (this, DefDBExt.ID_TA_AUT_USER, ATT_O_AVATAR);
	}
}
