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
import com.hnv.def.DefDBExt;		

/**
* TaMatUnit by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_MAT_UNIT )
public class TaMatUnit extends EntityAbstract<TaMatUnit>{

	public static final int	STAT_INATIVE		= 0;
	public static final int	STAT_ATIVE			= 1;
	public static final int	STAT_REVIEW 		= 5; 
	public static final int	STAT_DELETED 		= 10;
	
	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_I_STATUS                          =	"I_Status";
	public static final String	COL_T_CODE                            =	"T_Code";
	public static final String	COL_T_NAME                            =	"T_Name";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_I_STATUS                          =	"I_Status";
	public static final String	ATT_T_CODE                            =	"T_Code";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String	ATT_O_UNIT_REF                        =	"O_Unit_Ref";



	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<TaMatUnit> 	DAO;
	static{
		DAO = new EntityDAO<TaMatUnit>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaMatUnit.class,RIGHTS, HISTORY, DefDBExt.TA_MAT_UNIT, DefDBExt.ID_TA_MAT_UNIT);

	}

	//-----------------------Class Attributs-------------------------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
     
	@Column(name=COL_T_CODE, nullable = true)
	private	String          T_Code;
       
	@Column(name=COL_T_NAME, nullable = true)
	private	String          T_Name;
       
	@Column(name=COL_I_PER_MANAGER, nullable = true)
	private	Integer         I_Per_Manager;

     

    
	//-----------------------Transient Variables-------------------------
	@Transient
	private	TaMatUnit         O_Unit_Ref;

	//---------------------Constructeurs-----------------------
	private TaMatUnit(){}

	public TaMatUnit(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		if (this.T_Name!=null) this.T_Name= this.T_Name.trim();
		if (this.T_Code!=null) this.T_Code= this.T_Code.trim();
		//doInitDBFlag();
	}
	
	public TaMatUnit(Integer matId, String code, String name, Double ratio, Integer typ01, Integer typ02) throws Exception {
//		this.I_Material 	= matId;
		this.T_Code			= code;
		this.T_Name			= name;
//		this.F_Ratio		= ratio;
//		this.I_Type_01		= typ01;
//		this.I_Type_02		= typ02;
		this.I_Status		= 1;
		//doInitDBFlag();
	}
	
	
	public TaMatUnit( String name, Double ratio, Integer typ01, Integer typ02, Integer manId) throws Exception {
		this.I_Per_Manager	= manId;
		this.T_Name			= name;
//		this.F_Ratio		= ratio;
//		this.I_Type_01		= typ01;
//		this.I_Type_02		= typ02;
		this.I_Status		= 1;
		//doInitDBFlag();
	}
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaMatUnit ent) {
		if (ent == this) return;
		this.I_Status               = ent.I_Status;
		this.T_Code                 = ent.T_Code;
		this.T_Name                 = ent.T_Name;
		this.I_Per_Manager          = ent.I_Per_Manager;



		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaMatUnit)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

}
