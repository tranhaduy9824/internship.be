package com.hnv.db.mat;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.db.per.TaPerPerson;
import com.hnv.def.DefDBExt;		

/**
* TaMatWarehouse by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_WAREHOUSE )
public class TaMatWarehouse extends EntityAbstract<TaMatWarehouse> {

	private static final long serialVersionUID = 1L;


	public static final int	STAT_NEW 			= 0;
	public static final int	STAT_ACTIVE 		= 1; 
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	
	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_NAME                            =	"T_Name";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_T_CODE_01                         =	"T_Code_01";
	public static final String	COL_T_CODE_02                         =	"T_Code_02";
	public static final String	COL_T_INFO_01                         =	"T_Info_01";
	public static final String	COL_T_INFO_02                         =	"T_Info_02";
	public static final String	COL_T_INFO_03                         =	"T_Info_03";
	public static final String	COL_T_INFO_04                         =	"T_Info_04";
	public static final String	COL_T_INFO_05                         =	"T_Info_05";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_T_CODE_01                         =	"T_Code_01";
	public static final String	ATT_T_CODE_02                         =	"T_Code_02";
	public static final String	ATT_T_INFO_01                         =	"T_Info_01";
	public static final String	ATT_T_INFO_02                         =	"T_Info_02";
	public static final String	ATT_T_INFO_03                         =	"T_Info_03";
	public static final String	ATT_T_INFO_04                         =	"T_Info_04";
	public static final String	ATT_T_INFO_05                         =	"T_Info_05";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	ATT_O_PER_MANAGER					  = "O_Per_Manager";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatWarehouse> 	DAO;
	static{
		DAO = new EntityDAO<TaMatWarehouse>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatWarehouse.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_WAREHOUSE, DefDBExt.ID_TA_MAT_WAREHOUSE);

	}


	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_NAME, nullable = true)
	private	String          T_Name;
       
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;
    
	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;
    
	@Column(name=COL_T_CODE_01, nullable = true)
	private	String          T_Code_01;
    
	@Column(name=COL_T_CODE_02, nullable = true)
	private	String          T_Code_02;
    
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
    
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

	//-----------------------Transient Variables-------------------------
	@Transient
	private TaPerPerson 	O_Per_Manager 			= null;

	//---------------------Constructeurs-----------------------
	private TaMatWarehouse(){}

	public TaMatWarehouse(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	
	public TaMatWarehouse(String T_Name) throws Exception {
		this.reqSetAttr(
			ATT_T_NAME       , T_Name
		);
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatWarehouse ent) {
		if (ent == this) return;
		this.T_Name                 = ent.T_Name;
		this.I_Status               = ent.I_Status;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.T_Code_01              = ent.T_Code_01;
		this.T_Code_02              = ent.T_Code_02;
		this.T_Info_01              = ent.T_Info_01;
		this.T_Info_02              = ent.T_Info_02;
		this.T_Info_03              = ent.T_Info_03;
		this.T_Info_04              = ent.T_Info_04;
		this.T_Info_05              = ent.T_Info_05;
		this.I_Per_Manager          = ent.I_Per_Manager;



		//---------------------Merge Transient Variables if exist-----------------------
	}
	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatWarehouse)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

	public void doBuildPerManager(boolean forced) throws Exception{
		if (!forced && this.O_Per_Manager!=null) return;
		this.O_Per_Manager = TaPerPerson.DAO.reqEntityByRef(TaPerPerson.ATT_I_ID);
	}

}
