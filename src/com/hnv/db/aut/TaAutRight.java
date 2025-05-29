package com.hnv.db.aut;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

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
* TaAutRight by H&V SAS
*/
@Entity
@Table(name = DefDBExt.TA_AUT_RIGHT )
public class TaAutRight extends EntityAbstract<TaAutRight> {

	private static final long serialVersionUID = 1L;

	//---------------------------List of Column from DB-----------------------------
	public static final String	COL_I_ID                              	= "I_ID";
	public static final String	COL_T_NAME                            	= "T_Name";
	public static final String 	COL_T_INFO_01 							= "T_Info_01"; //description
	public static final String 	COL_T_INFO_02 							= "T_Info_02"; //group



	//---------------------------List of ATTR of class-----------------------------
	public static final String	ATT_I_ID                              	= "I_ID";
	public static final String	ATT_T_CODE                            	= "T_Code";
	public static final String	ATT_T_NAME                            	= "T_Name";
	public static final String  ATT_T_INFO_01 							= "T_Info_01";
	public static final String  ATT_T_INFO_02 							= "T_Info_02";


	//-------every entity class must initialize its DAO from here -----------------------------
	private 	static 	final boolean[] 			RIGHTS		= {true, true, true, true, false}; //canRead, canAdd, canUpd, canDel, del physique or flag only 

	public		static 	final EntityDAO<TaAutRight> DAO;


	static{
		DAO = new EntityDAO<TaAutRight>(Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN), TaAutRight.class, RIGHTS);
	}

	//-----------------------Class Attributs-------------------------
	@Id
	@Column(name=COL_I_ID, nullable = false)
	private	Integer         I_ID;
         
	@Column(name=COL_T_NAME, nullable = false)
	private	String          T_Name;
	
	@Column(name=COL_T_INFO_01, nullable = true)
	private	String            T_Info_01;

	@Column(name=COL_T_INFO_02, nullable = true)
	private	String            T_Info_02;
       
    
    
	//-----------------------Transient Variables-------------------------
	@Transient
	private Set<Integer> O_Dependencies;

	//---------------------Constructeurs-----------------------
	private TaAutRight(){}

	public TaAutRight(Map<String, Object> attrs) throws Exception {
		this.reqSetAttrFromMap(attrs);
		//doInitDBFlag();
	}
	

	public TaAutRight(String T_Code, String T_Name,  Integer I_Role) throws Exception {
		this.reqSetAttr(
			ATT_T_CODE                 , T_Code,
			ATT_T_NAME                 , T_Name
		);
		//doInitDBFlag();
	}
	
	
	//---------------------EntityInterface-----------------------
	@Override
	public Serializable reqRef() {
		return this.I_ID;

	}

	@Override
	public void doMergeWith(TaAutRight ent) {
		if (ent == this) return;
		this.T_Name                 = ent.T_Name;		
		
	}

	@Override
	public boolean equals(Object o)  {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		boolean ok = false;
		
		ok = (I_ID == ((TaAutRight)o).I_ID);
		if (!ok) return ok;

				
		if (!ok) return ok;
		return ok;
	}

	@Override
	public int hashCode() {
		return this.I_ID;

	}

}
