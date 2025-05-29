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
* TaMatStockIo by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_STOCK )
public class TaMatStock extends EntityAbstract<TaMatStock>  {
	public static final int		STAT_KO 	= 0;
	public static final int		STAT_OK 	= 1;
	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	COL_I_MAT_WAREHOUSE                   =	"I_Mat_Warehouse";
	public static final String	COL_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_F_VAL_00                          =	"F_Val_00";
	public static final String	COL_F_VAL_01                          =	"F_Val_01";
	public static final String	COL_F_VAL_02                          =	"F_Val_02";
	public static final String	COL_F_VAL_03                          =	"F_Val_03";
	public static final String	COL_F_VAL_04                          =	"F_Val_04";
	public static final String	COL_F_VAL_05                          =	"F_Val_05";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_D_DATE_01                         =	"D_Date_01";
	public static final String	COL_D_DATE_02                         =	"D_Date_02";
	public static final String	COL_D_DATE_03                         =	"D_Date_03";
	public static final String	COL_D_DATE_04                         =	"D_Date_04";

	/*
	--  `F_Val_01` : 'Q1 quantity from order',
	--  `F_Val_02` : 'R1 Ratio base',
	--  `F_Val_03` : 'Q base Qb = Q1 * R1',
	--  `F_Val_04` : 'RQ base (RQ2) of stock, RQ = 0 => stock out',
	--  `F_Val_05` : 'RQ unit input of stock, RQ = RQ2/R1, chi mang tính tham khảo, đúng sai ko quan trong',
	--  `D_Date_01` : 'date creation',
	--  `D_Date_02` : 'date mod',
	*/

	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	public static final String	ATT_I_MAT_WAREHOUSE                   =	"I_Mat_Warehouse";
	public static final String	ATT_I_MAT_MATERIAL                    =	"I_Mat_Material";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_F_VAL_00                          =	"F_Val_00";
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
	public static final String	ATT_D_DATE_01                         =	"D_Date_01";
	public static final String	ATT_D_DATE_02                         =	"D_Date_02";
	public static final String	ATT_D_DATE_03                         =	"D_Date_03";
	public static final String	ATT_D_DATE_04                         =	"D_Date_04";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatStock> 	DAO;
	static{
		DAO = new EntityDAO<TaMatStock>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatStock.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_STOCK, DefDBExt.ID_TA_MAT_STOCK);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	@Column(name=COL_I_MAT_WAREHOUSE, nullable = true)
	private	Integer         I_Mat_Warehouse;

	@Column(name=COL_I_MAT_MATERIAL, nullable = true)
	private	Integer         I_Mat_Material;

	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
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
      

    
	//-----------------------Transient Variables-------------------------


	//---------------------Constructeurs-----------------------
	private TaMatStock(){}

	public TaMatStock(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		// doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatStock ent) {
		if (ent == this) return;
		this.I_Per_Manager          = ent.I_Per_Manager;
		this.I_Mat_Warehouse        = ent.I_Mat_Warehouse;
		this.I_Mat_Material         = ent.I_Mat_Material;
		this.I_Status               = ent.I_Status;
		this.F_Val_00               = ent.F_Val_00;
		this.F_Val_01               = ent.F_Val_01;
		this.F_Val_02               = ent.F_Val_02;
		this.F_Val_03               = ent.F_Val_03;
		this.F_Val_04               = ent.F_Val_04;
		this.F_Val_05               = ent.F_Val_05;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.D_Date_01              = ent.D_Date_01;
		this.D_Date_02              = ent.D_Date_02;
		this.D_Date_03              = ent.D_Date_03;
		this.D_Date_04              = ent.D_Date_04;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatStock)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

}
