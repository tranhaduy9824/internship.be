package com.hnv.db.per.vi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.hnv.api.main.API;
import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.common.tool.ToolDBEntity;
import com.hnv.common.tool.ToolJSON;
import com.hnv.common.tool.ToolSet;
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
public class ViPerPersonDyn extends EntityAbstract<ViPerPersonDyn> {
	public  static final int 	TYP_01_NATURAL			= 100;
	public  static final int 	TYP_01_MORAL			= 200;
	
	public  static final int 	TYP_02_INTERNAL			= 1000;
	
	public  static final int 	TYP_02_CLIENT			= 2000;
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

	public		static 	final EntityDAO<ViPerPersonDyn> 	DAO;
	static{
		DAO = new EntityDAO<ViPerPersonDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViPerPersonDyn.class,RIGHTS, HISTORY, DefDBExt.TA_PER_PERSON, DefDBExt.ID_TA_PER_PERSON);


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
    
	@Column(name=COL_I_STATUS_01, nullable = true)
	private	Integer         I_Status_01;
     
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double          F_Val_01;
     
	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double          F_Val_02;
     
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
	@Column(name=COL_T_CODE_03, nullable = true)
	private	String          T_Code_03;
	
	@Column(name=COL_T_CODE_07, nullable = true)
	private	String          T_Code_07;
	
	@Column(name=COL_T_CODE_09, nullable = true)
	private	String          T_Code_09;
    
	@Column(name=COL_D_DATE_01, nullable = true)
	private	Date            D_Date_01;
    
	@Column(name=COL_D_DATE_02, nullable = true)
	private	Date            D_Date_02;

	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer            I_Aut_User_01;

	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer            I_Per_Manager;
		    
	//-----------------------Transient Variables-------------------------
	@Transient
	private	String 						O_FullName;

	@Transient
	private	TaTpyDocument 				O_Avatar;

	//---------------------Constructeurs-----------------------
	private ViPerPersonDyn(){}

	public ViPerPersonDyn(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}

	public ViPerPersonDyn(Integer id, String name01, String name02) throws Exception {
		this.reqSetAttr(
				ATT_I_ID		  , id,
				ATT_T_NAME_01     , name01,
				ATT_T_NAME_01     , name02				
				);
		//doInitDBFlag();
	}

	public ViPerPersonDyn(Integer I_Status) throws Exception {
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

	public void doMergeWith(ViPerPersonDyn ent) {
	}


	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((ViPerPersonDyn)o).I_ID);
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
	//----------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------	
	//----------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------	
	public static void doBuildAvatar(List<ViPerPersonDyn> list) throws Exception {
		if (list==null || list.size()==0) return;
		TaTpyDocument.reqBuildAvatar(list, DefDBExt.ID_TA_PER_PERSON, ATT_O_AVATAR);
	}
	//----------------------------------------------------------------------------------------------------------
	public void doBuildFullName(boolean forced) throws Exception{
		if (!forced && O_FullName!=null) return;
		String fullName = "";
		if(T_Name_01 != null) fullName += T_Name_01 + " ";
		if(T_Name_02 != null) fullName += T_Name_02 + " ";
		this.O_FullName = fullName.trim();
	}

	//-------------------------------------------------------------------------------------------------
}
