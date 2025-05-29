package com.hnv.db.mat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;		

/**
* TaSorPricing by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_PRICE )
public class TaMatPrice extends EntityAbstract<TaMatPrice> implements Comparable<TaMatPrice>{
	public static final int 	STAT_NEW 		= 0;
	public static final int 	STAT_ACTIV 		= 1;
	
//	public static final int		TYP_PER_PERSON                  	  =	100; // x nbPer
//	public static final int		TYP_PER_PERSON_DAY                    =	101; // x nbPer x nbDay
//	public static final int		TYP_PER_ORDER                  		  =	200; // 1 time
//	public static final int		TYP_PER_ORDER_DAY              		  =	201; // x nbDay
//	
//	public static final int		TYP_MAT_MAIN                  	  	  =	1; // x nbPer
//	public static final int		TYP_MAT_ADVICE                 	  	  =	2; // x nbPer
	
	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              	=	"I_ID";
	public static final String	COL_I_PRIORITY                        	=	"I_Priority";
	public static final String	COL_I_STATUS                          	=	"I_Status";
	public static final String 	COL_I_MAT_MATERIAL						=  	"I_Mat_Material";
	public static final String	COL_I_MAT_UNIT                        	=	"I_Mat_Unit";
	
	public static final String	COL_F_VAL_00                        	=	"F_Val_00";//ratio
	public static final String	COL_F_VAL_01                        	=	"F_Val_01";//price 01
	public static final String	COL_F_VAL_02                        	=	"F_Val_02";//price 02
	public static final String	COL_F_VAL_03                        	=	"F_Val_03";//discount
	public static final String	COL_F_VAL_04                          	=	"F_Val_04";
	public static final String	COL_F_VAL_05                          	=	"F_Val_05";
	public static final String	COL_F_VAL_06                          	=	"F_Val_06";
	public static final String	COL_F_VAL_07                          	=	"F_Val_07";
	public static final String	COL_F_VAL_08                          	=	"F_Val_08";
	public static final String	COL_F_VAL_09                          	=	"F_Val_09";
	public static final String	COL_F_VAL_10                          	=	"F_Val_10";
	
	public static final String	COL_D_DATE_01                        	=	"D_Date_01";
	public static final String	COL_D_DATE_02                        	=	"D_Date_02";
	public static final String	COL_D_DATE_03                      		=	"D_Date_03";
	public static final String	COL_D_DATE_04                        	=	"D_Date_04";
	
	public static final String	COL_T_INFO_01                         	=	"T_Info_01";//unit
	public static final String	COL_T_INFO_02                         	=	"T_Info_02";//currency
	public static final String	COL_T_INFO_03                         	=	"T_Info_03";
	public static final String	COL_T_INFO_04                         	=	"T_Info_04";
	public static final String	COL_T_INFO_05                         	=	"T_Info_05";
	
	public static final String	COL_I_AUT_USER_01                  		=	"I_Aut_User_01";
	public static final String	COL_I_AUT_USER_02                  		=	"I_Aut_User_02";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_PRIORITY                        =	"I_Priority";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	ATT_I_MAT_UNIT                        =	"I_Mat_Unit";
	public static final String	ATT_F_VAL_00                          =	"F_Val_00";
	public static final String	ATT_F_VAL_01                          =	"F_Val_01";
	public static final String	ATT_F_VAL_02                          =	"F_Val_02";
	public static final String	ATT_F_VAL_03                          =	"F_Val_03";
	public static final String	ATT_F_VAL_04                          =	"F_Val_04";
	public static final String	ATT_F_VAL_05                          =	"F_Val_05";
	public static final String	ATT_F_VAL_06                          =	"F_Val_06";
	public static final String	ATT_F_VAL_07                          =	"F_Val_07";
	public static final String	ATT_F_VAL_08                          =	"F_Val_08";
	public static final String	ATT_F_VAL_09                          =	"F_Val_09";
	public static final String	ATT_F_VAL_10                          =	"F_Val_10";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";
	public static final String	ATT_I_AUT_USER_01                     =	"I_Aut_User_01";
	public static final String	ATT_I_AUT_USER_02                     =	"I_Aut_User_02";


	
	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatPrice> 	DAO;
	static{
		DAO = new EntityDAO<TaMatPrice>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatPrice.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_PRICE, DefDBExt.ID_TA_MAT_PRICE);

	}
	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_PRIORITY, nullable = true)
	private	Integer         I_Priority;
   
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_I_MAT_MATERIAL, nullable = true)
	private	Integer         I_Mat_Material;

	@Column(name=COL_I_MAT_UNIT, nullable = true)
	private	Integer         I_Mat_Unit;
   
	@Column(name=COL_F_VAL_00, nullable = true)
	private	Double          F_Val_00;
     
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
    
	@Column(name=COL_I_AUT_USER_01, nullable = true)
	private	Integer         I_Aut_User_01;

	@Column(name=COL_I_AUT_USER_02, nullable = true)
	private	Integer         I_Aut_User_02;


    
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	public TaMatPrice(){}

	public TaMatPrice(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------

	public Integer reqPriority() {
		return this.I_Priority;
	}
	
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatPrice ent) {
		if (ent == this) return;
		this.I_Priority             = ent.I_Priority;
		this.I_Status               = ent.I_Status;
		this.I_Mat_Material         = ent.I_Mat_Material;
		this.I_Mat_Unit             = ent.I_Mat_Unit;
		this.F_Val_00               = ent.F_Val_00;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;
		this.F_Val_06               = ent.F_Val_06;
		this.F_Val_07               = ent.F_Val_07;
		this.F_Val_08               = ent.F_Val_08;
		this.F_Val_09               = ent.F_Val_09;
		this.F_Val_10               = ent.F_Val_10;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;
		this.I_Aut_User_01          = ent.I_Aut_User_01;
		this.I_Aut_User_02          = ent.I_Aut_User_02;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatPrice)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}


	@Override
	public int compareTo(TaMatPrice o) {
		if (this.I_Priority == o.I_Priority) return 0;
		if (this.I_Priority == null) return 1;
		if (o.I_Priority==null) return -1;
		return this.I_Priority - o.I_Priority;
	}

}
