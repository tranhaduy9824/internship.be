package com.hnv.db.tpy.vi;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hnv.api.main.Hnv_CfgHibernate;
import com.hnv.db.EntityAbstract;
import com.hnv.db.EntityDAO;
import com.hnv.def.DefDBExt;

/**
* ViTpyCategory by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_TPY_CATEGORY )
public class ViTpyCategoryDyn extends EntityAbstract<ViTpyCategoryDyn> {

	private static final long serialVersionUID = 1L;

	//---------------------------------------------------------------------------------
	public static final	int  TYPE_00_MATERIAL 	= 100;
	public static final	int  TYPE_00_POST 		= 200;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              =	"I_ID";
	public static final String	COL_T_NAME                            =	"T_Name";
	public static final String	COL_T_CODE                            =	"T_Code";
	public static final String	COL_T_INFO                            =	"T_Info";
	public static final String	COL_I_TYPE_01                         =	"I_Type_01";
	public static final String	COL_I_TYPE_02                         =	"I_Type_02";
	public static final String	COL_I_TYPE_03                         =	"I_Type_03";
	public static final String	COL_I_STATUS                     	  =	"I_Status";
	
	public static final String	COL_I_PARENT                     	  =	"I_Parent";
	public static final String	COL_I_PER_MANAGER                     =	"I_Per_Manager";



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              =	"I_ID";
	public static final String	ATT_T_NAME                            =	"T_Name";
	public static final String	ATT_T_CODE                            =	"T_Code";
	public static final String	ATT_T_INFO                            =	"T_Info";
	public static final String	ATT_I_TYPE_01                         =	"I_Type_01";
	public static final String	ATT_I_TYPE_02                         =	"I_Type_02";
	public static final String	ATT_I_TYPE_03                         =	"I_Type_03";
	public static final String	ATT_I_STATUS                     	  =	"I_Status";
	
	public static final String	ATT_I_PARENT                      	  =	"I_Parent";
	public static final String	ATT_I_PER_MANAGER                     =	"I_Per_Manager";
	
	public static final String  ATT_O_PARENT_NAME                  	  =	"O_Parent_Name";

	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, false, false, false, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 
	private 	static 	final boolean[]				HISTORY		= {false, false, false}; //add, mod, del

	public		static 	final EntityDAO<ViTpyCategoryDyn> 	DAO;
	static{
		DAO = new EntityDAO<ViTpyCategoryDyn>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN) , ViTpyCategoryDyn.class, RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;

	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;

	@Column(name=COL_T_CODE, nullable = true)
	private	String          T_Code;

	@Column(name=COL_I_PER_MANAGER, nullable = false)
	private	Integer         I_Per_Manager;

	@Column(name=COL_I_PARENT, nullable = true)
	private	Integer         I_Parent;	

	@Column(name=COL_I_TYPE_01, nullable = true)
	private	Integer         I_Type_01;

	@Column(name=COL_I_TYPE_02, nullable = true)
	private	Integer         I_Type_02;

	@Column(name=COL_I_TYPE_03, nullable = false)
	private	Integer         I_Type_03;
	
	@Column(name=COL_I_STATUS, nullable = true)
	private	Integer         I_Status;
	
	@Transient
	private	String O_Parent_Name;
	
	//---------------------Constructeurs-----------------------
	private ViTpyCategoryDyn(){}

	public ViTpyCategoryDyn(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		doInitDBFlag();
	}

	public ViTpyCategoryDyn(String T_Name, Integer I_Per_Manager, Integer I_Type_01) throws Exception {
		this.reqSetAttr(
				ATT_T_NAME       , T_Name,
				ATT_I_PER_MANAGER, I_Per_Manager,
				ATT_I_TYPE_01	 , I_Type_01
				);
		doInitDBFlag();
	}
	public ViTpyCategoryDyn(String T_Name, String T_Code, String T_INFO , Integer I_Per_Manager, Integer I_Type_01) throws Exception {
		this.reqSetAttr(
				ATT_T_NAME                 , T_Name,
				ATT_T_CODE                 , T_Code,
				ATT_T_INFO          	   , T_INFO ,
				ATT_I_PER_MANAGER          , I_Per_Manager,
				ATT_I_TYPE_01          	   , I_Type_01
				);
		doInitDBFlag();
	}


	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(ViTpyCategoryDyn ent) {
		if (ent == this) return;
		this.T_Name                 = ent.T_Name;
		this.T_Code                 = ent.T_Code;
		this.I_Type_01              = ent.I_Type_01;
		this.I_Type_02              = ent.I_Type_02;
		this.I_Type_03              = ent.I_Type_03;
		this.I_Parent           	= ent.I_Parent;
		this.I_Per_Manager          = ent.I_Per_Manager;

		//---------------------Merge Transient Variables if exist-----------------------
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;

		ok = (I_ID == ((ViTpyCategoryDyn)o).I_ID);
		if (!ok) return ok;


		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;
	}

	


}
