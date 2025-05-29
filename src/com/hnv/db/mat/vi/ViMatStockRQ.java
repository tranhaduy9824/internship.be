package com.hnv.db.mat.vi;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;	

/**
* TaInvOrder by H&V SAS
*/
@Entity
public class ViMatStockRQ extends EntityAbstract<ViMatStockRQ> {

	private static final long serialVersionUID = 1L;
	
   
 

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                             =	"I_ID";
	public static final String	COL_I_MAT_MATERIAL                   =	"I_Mat_Material";
	public static final String	COL_I_MAT_STOCK                       =	"I_Mat_Stock";
	public static final String	COL_I_SOR_ORDER                      =	"I_Sor_Order";
	
	public static final String	COL_I_SOR_ORDER_DETAIL               =	"I_Sor_Order_Detail";
	public static final String	COL_I_PER_PERSON_01                      =	"I_Per_Person_01";
	public static final String	COL_I_PER_PERSON_02                      =	"I_Per_Person_02";
	
	public static final String	COL_F_VAL_00                         =	"F_Val_00";
	public static final String	COL_F_VAL_01                         =	"F_Val_01";
	public static final String	COL_F_VAL_02                         =	"F_Val_02";
	public static final String	COL_F_VAL_03                         =	"F_Val_03";
	public static final String	COL_F_VAL_04                         =	"F_Val_04";
	public static final String	COL_F_VAL_05                         =	"F_Val_05";
	public static final String	COL_F_VAL_06                         =	"F_Val_06";
	public static final String	COL_F_VAL_07                         =	"F_Val_07";
	public static final String	COL_F_VAL_08                         =	"F_Val_08";
	public static final String	COL_F_VAL_09                         =	"F_Val_09";
	public static final String	COL_F_VAL_10                         =	"F_Val_10";
	public static final String	COL_F_VAL_11                         =	"F_Val_11";
	
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";	
	public static final String	COL_T_CODE_03                         =	"T_Code_03";	
	public static final String	COL_T_CODE_04                         =	"T_Code_04";	
	
	public static final String	COL_T_INFO_07                         =	"T_Info_07";
	public static final String	COL_T_INFO_06                         =	"T_Info_06";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";
	public static final String	COL_D_DATE_05                         =	"D_Date_05";
	
	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                             =	"I_ID";
	public static final String	ATT_I_MAT_MATERIAL                   =	"I_Mat_Material";
	public static final String	ATT_I_MAT_STOCK                       =	"I_Mat_Stock";
	public static final String	ATT_I_SOR_ORDER                      =	"I_Sor_Order";
	
	public static final String	ATT_I_SOR_ORDER_DETAIL               =	"I_Sor_Order_Detail";
	public static final String	ATT_I_PER_PERSON_01                      =	"I_Per_Person_01";
	public static final String	ATT_I_PER_PERSON_02                      =	"I_Per_Person_02";
	
	public static final String	ATT_F_VAL_00                         =	"F_Val_00";
	public static final String	ATT_F_VAL_01                         =	"F_Val_01";
	public static final String	ATT_F_VAL_02                         =	"F_Val_02";
	public static final String	ATT_F_VAL_03                         =	"F_Val_03";
	public static final String	ATT_F_VAL_04                         =	"F_Val_04";
	public static final String	ATT_F_VAL_05                         =	"F_Val_05";
	public static final String	ATT_F_VAL_06                         =	"F_Val_06";
	public static final String	ATT_F_VAL_07                         =	"F_Val_07";
	public static final String	ATT_F_VAL_08                         =	"F_Val_08";
	public static final String	ATT_F_VAL_09                         =	"F_Val_09";
	public static final String	ATT_F_VAL_10                         =	"F_Val_10";
	public static final String	ATT_F_VAL_11                         =	"F_Val_11";
	
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";	
	public static final String	ATT_T_CODE_03                         =	"T_Code_03";	
	public static final String	ATT_T_CODE_04                         =	"T_Code_04";	
	
	public static final String	ATT_T_INFO_07                         =	"T_Info_07";
	public static final String	ATT_T_INFO_06                         =	"T_Info_06";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_D_DATE_05                         =	"D_Date_05";
	
	
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, true}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViMatStockRQ> 	DAO;
	static{
		DAO = new EntityDAO<ViMatStockRQ>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViMatStockRQ.class, RIGHTS,  HISTORY, DefDBExt.TA_MAT_STOCK, DefDBExt.ID_TA_MAT_STOCK);
	}

	
	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_MAT_MATERIAL, nullable = true)
	private	Integer         I_Mat_Material;
	
	@Column(name=COL_I_MAT_STOCK, nullable = true)
	private	Integer         I_Mat_Stock;
       
	@Column(name=COL_I_SOR_ORDER, nullable = true)
	private	Integer         I_Sor_Order;
	
	@Column(name=COL_I_SOR_ORDER_DETAIL, nullable = true)
	private	Integer         I_Sor_Order_Detail;
	
	@Column(name=COL_I_PER_PERSON_01, nullable = true)
	private	Integer         I_Per_Person_01;
	
	@Column(name=COL_I_PER_PERSON_02, nullable = true)
	private	Integer         I_Per_Person_02;
	
	
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
	

	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
	
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
	@Column(name=COL_T_CODE_03, nullable = true)
	private	String          T_Code_03;
	
	@Column(name=COL_T_CODE_04, nullable = true)
	private	String          T_Code_04;

	
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
	
	
	@Column(name=COL_F_VAL_00, nullable = true)
	private	Double         	F_Val_00;

	@Column(name=COL_F_VAL_01, nullable = true)
	private	Double         	F_Val_01;

	@Column(name=COL_F_VAL_02, nullable = true)
	private	Double         	F_Val_02;

	@Column(name=COL_F_VAL_03, nullable = true)
	private	Double         	F_Val_03;

	@Column(name=COL_F_VAL_04, nullable = true)
	private	Double         	F_Val_04;

	@Column(name=COL_F_VAL_05, nullable = true)
	private	Double         	F_Val_05;

	@Column(name=COL_F_VAL_06, nullable = true)
	private	Double         	F_Val_06;

	@Column(name=COL_F_VAL_07, nullable = true)
	private	Double         	F_Val_07;
	
	@Column(name=COL_F_VAL_08, nullable = true)
	private	Double         	F_Val_08;

	@Column(name=COL_F_VAL_09, nullable = true)
	private	Double         	F_Val_09;
	
	@Column(name=COL_F_VAL_10, nullable = true)
	private	Double         	F_Val_10;
	
	@Column(name=COL_F_VAL_11, nullable = true)
	private	Double         	F_Val_11;
    
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private ViMatStockRQ(){}

	public ViMatStockRQ(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void doMergeWith(ViMatStockRQ arg0) {
		// TODO Auto-generated method stub
		
	}

}

